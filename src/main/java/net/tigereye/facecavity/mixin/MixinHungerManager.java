package net.tigereye.facecavity.mixin;

import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.listeners.EffectiveFoodScores;
import net.tigereye.facecavity.listeners.OrganFoodCallback;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.util.FaceCavityUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;

@Mixin(HungerManager.class)
public class MixinHungerManager {

        @Shadow
        private int foodTickTimer;
        @Shadow
        private int foodLevel;
        @Shadow
        private float foodSaturationLevel;
        @Shadow
        private float exhaustion;

        private FaceCavityEntity CC_player = null;

        @Inject(at = @At("HEAD"), method = "update", cancellable = true)
        public void chestCavityUpdateMixin(PlayerEntity player, CallbackInfo info) {
                if(CC_player == null){
                        FaceCavityEntity.of(player).ifPresent(ccPlayerEntityInterface -> {
                                CC_player = ccPlayerEntityInterface;
                        });
                }
                foodTickTimer = FaceCavityUtil.applySpleenMetabolism(CC_player.getFaceCavityInstance(),this.foodTickTimer);
        }


        @ModifyVariable(at = @At("HEAD"), method = "eat")
        public Item chestCavityEatMixin(Item item) {
                if(item.isFood() && CC_player != null){
                        //saturation gains are equal to hungerValue*saturationModifier*2
                        //this is kinda stupid, if I half the hunger gains from food I don't want to also half saturation gains
                        //so before hunger changes, calculate the saturation gain I intend
                        FoodComponent itemFoodComponent = item.getFoodComponent();
                        if(itemFoodComponent != null) {
                                EffectiveFoodScores efs = new EffectiveFoodScores(
                                        CC_player.getFaceCavityInstance().getOrganScore(CCOrganScores.DIGESTION),
                                        CC_player.getFaceCavityInstance().getOrganScore(CCOrganScores.NUTRITION));
                                efs = OrganFoodCallback.EVENT.invoker().onEatFood(item,itemFoodComponent,CC_player,efs);
                                float saturationGain = FaceCavityUtil.applyNutrition(CC_player.getFaceCavityInstance(),efs.nutrition,item.getFoodComponent().getSaturationModifier())
                                         * item.getFoodComponent().getHunger() * 2.0F;
                                //now find the modified hunger gains
                                int hungerGain = FaceCavityUtil.applyDigestion(CC_player.getFaceCavityInstance(),efs.digestion,item.getFoodComponent().getHunger());
                                //now calculate the saturation modifier that gives me what I want
                                float newSaturation = saturationGain / (hungerGain * 2);
                                //now make a dummy food item with the modified stats and feed it to HungerManager.eat();
                                FoodComponent dummyFood = new FoodComponent.Builder().hunger(hungerGain).saturationModifier(newSaturation).build();
                                Item.Settings dummySettings = new Item.Settings().food(dummyFood);
                                return new Item(dummySettings);
                        }
                }
                return item;
        }

        @ModifyVariable(at = @At("HEAD"), ordinal = 0, method = "addExhaustion")
        public float chestCavityAddExhaustionMixin(float exhaustion) {
                if(CC_player != null){
                        if(this.exhaustion != this.exhaustion){
                                //NaN check. Not sure what keep causing it...
                                this.exhaustion = 0;
                        }
                        float enduranceDif = CC_player.getFaceCavityInstance().getOrganScore(CCOrganScores.ENDURANCE)-CC_player.getFaceCavityInstance().getFaceCavityType().getDefaultOrganScore(CCOrganScores.ENDURANCE);
                        //FaceCavity.LOGGER.info("In: "+exhaustion);
                        float out;
                        if(enduranceDif > 0) {
                                out = exhaustion/(1+(enduranceDif/2));
                        }
                        else{
                                out = exhaustion*(1-(enduranceDif/2));
                        }
                        //float out = exhaustion*(float)Math.pow(FaceCavity.config.LUNG_ENDURANCE,enduranceDif/2);
                        //FaceCavity.LOGGER.info("Out: "+out);
                        return out;
                }
                return exhaustion;
        }
}