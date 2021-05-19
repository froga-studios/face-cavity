package io.github.frogastudios.facecavity.items;

import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.ui.FaceCavityScreenHandler;
import io.github.frogastudios.facecavity.util.FaceCavityUtil;
import ladysnake.requiem.api.v1.possession.PossessionComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tigereye.chestcavity.chestcavities.ChestCavityInventory;
import net.tigereye.chestcavity.chestcavities.instance.ChestCavityInstance;
import net.tigereye.chestcavity.crossmod.requiem.CCRequiem;
import net.tigereye.chestcavity.interfaces.ChestCavityEntity;
import net.tigereye.chestcavity.registration.CCItems;
import net.tigereye.chestcavity.registration.CCOrganScores;
import net.tigereye.chestcavity.ui.ChestCavityScreenHandler;
import net.tigereye.chestcavity.util.ChestCavityUtil;

import java.util.Optional;

import static io.github.frogastudios.facecavity.interfaces.FaceCavityEntity.FaceC;

public class ChestOpener extends Item {

    public ChestOpener() {
        super(CCItems.CHEST_OPENER_SETTINGS);
        System.out.println("Loaded Face Opener (I think lol)");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        LivingEntity target = null;
        System.out.println("test");
        if(CCRequiem.REQUIEM_ACTIVE){
            target = PossessionComponent.getPossessedEntity(player);
            System.out.println("Requiem Method Reached");
        }
        if(target == null) {
            target = player;
            System.out.println("Set target from null to player");
        }
        if (openFaceCavity(player, target,true)) {
            return TypedActionResult.success(player.getStackInHand(hand), false);
        } else {
            return TypedActionResult.pass(player.getStackInHand(hand));
        }
    }

    public boolean openFaceCavity(PlayerEntity player, LivingEntity target){
        return openFaceCavity(player,target,true);
    }
    public boolean openFaceCavity(PlayerEntity player, LivingEntity target, boolean shouldKnockback){
        System.out.println("other method reach");
        Optional<FaceCavityEntity> optional = FaceCavityEntity.of(target);
            FaceCavityEntity faceCavityEntity = FaceC;
            FaceCavityInstance fc = faceCavityEntity.getFaceCavityInstance();
            System.out.println("FaceCavityMethodCalled");
            if(target == player || fc.getChestCavityType().isOpenable(fc)) {
                if (fc.getOrganScore(CCOrganScores.EASE_OF_ACCESS) > 0) {
                    if(player.world.isClient) {
                        player.playSound(SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.PLAYERS, .75f, 1);
                        System.out.println("Opening");
                    }
                }
                else{
                    if (!shouldKnockback) {
                        target.damage(DamageSource.GENERIC, 4f); // this is to prevent self-knockback, as that feels weird.
                        System.out.println("Did not knockback");
                    } else {
                        target.damage(DamageSource.player(player), 4f);
                        System.out.println("Did knockback");
                    }
                }
                if (target.isAlive()) {
                    String name;
                    System.out.println("Is alive");
                    try {
                        name = target.getDisplayName().getString();
                        name = name.concat("'s ");
                        System.out.println("Target is alive");
                    } catch (Exception e) {
                        name = "";
                    }
                    FaceCavityInventory inv = FaceCavityUtil.openFaceCavity(fc);
                    ((FaceCavityEntity)player).getFaceCavityInstance().fcBeingOpened = fc;
                    player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> new FaceCavityScreenHandler(i, playerInventory, inv), new TranslatableText(name + "Face Cavity")));
                    System.out.println("Attempted to open face cavity");
                }
                return true;
            }
            else{
                if(player.world.isClient) {
                    if (!target.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
                        player.playSound(SoundEvents.BLOCK_CHAIN_HIT, SoundCategory.PLAYERS, .75f, 1);
                    } else {
                        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, SoundCategory.PLAYERS, .75f, 1);
                    }
                }
            }
            return false;
    }
}