package net.tigereye.facecavity.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtil;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.listeners.OrganOnHitListener;
import net.tigereye.facecavity.registration.CCFoodComponents;
import net.tigereye.facecavity.registration.CCStatusEffects;
import net.tigereye.facecavity.util.OrganUtil;

import java.util.List;

public class VenomGland extends Item implements OrganOnHitListener {

    public VenomGland() {
        super(new Item.Settings().maxCount(1).group(FaceCavity.ORGAN_ITEM_GROUP).food(CCFoodComponents.RAW_TOXIC_ORGAN_MEAT_FOOD_COMPONENT));
    }

    @Override
    public float onHit(DamageSource source, LivingEntity attacker, LivingEntity target, FaceCavityInstance cc, ItemStack organ, float damage) {
        if(attacker.getStackInHand(attacker.getActiveHand()).isEmpty()
        //venom glands don't trigger from projectiles... unless it is llama spit. Because I find that hilarious.
        ||(source instanceof ProjectileDamageSource && !(((ProjectileDamageSource)source).getSource() instanceof LlamaSpitEntity))){
            if(source instanceof ProjectileDamageSource &&
                    !(((ProjectileDamageSource)source).getSource() instanceof LlamaSpitEntity)){
                return damage;
            }
            //venom glands don't trigger if they are on cooldown,
            //unless that cooldown was applied this same tick
            if(attacker.hasStatusEffect(CCStatusEffects.VENOM_COOLDOWN)){
                StatusEffectInstance cooldown = attacker.getStatusEffect(CCStatusEffects.VENOM_COOLDOWN);
                //this is to check if the cooldown was inflicted this same tick; likely because of other venom glands
                if(cooldown.getDuration() != FaceCavity.config.VENOM_COOLDOWN){
                    return damage;
                }
            }
            //failure conditions passed, the venom gland now delivers its payload
            List<StatusEffectInstance> effects = OrganUtil.getStatusEffects(organ);
            if(!effects.isEmpty()){
                for(StatusEffectInstance effect : effects){
                    target.addStatusEffect(effect);
                }
            }
            else {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.POISON, 200, 0));
            }
            attacker.addStatusEffect(new StatusEffectInstance(CCStatusEffects.VENOM_COOLDOWN, FaceCavity.config.VENOM_COOLDOWN, 0));
            if(attacker instanceof PlayerEntity){
                ((PlayerEntity)attacker).addExhaustion(.1f);
            }
        }
        return damage;
    }

    @Override
    public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext) {
        super.appendTooltip(itemStack,world,tooltip,tooltipContext);
        if(!OrganUtil.getStatusEffects(itemStack).isEmpty()) {
            PotionUtil.buildTooltip(itemStack, tooltip, 1);
        }
    }


}
