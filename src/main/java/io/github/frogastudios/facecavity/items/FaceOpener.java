package io.github.frogastudios.facecavity.items;

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
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.registration.FCItems;
import io.github.frogastudios.facecavity.registration.FCOrganScores;
import io.github.frogastudios.facecavity.ui.FaceCavityScreenHandler;
import net.tigereye.chestcavity.crossmod.requiem.CCRequiem;

import java.util.Optional;

public class FaceOpener extends Item {

	public FaceOpener(Settings settings) {
		super(settings);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		LivingEntity target = null;
		if(target == null) {
			target = player;
		}
		if (openFaceCavity(player, target,false)) {
			return TypedActionResult.success(player.getStackInHand(hand), false);
		} else {
			return TypedActionResult.pass(player.getStackInHand(hand));
		}
	}

	public boolean openFaceCavity(PlayerEntity player, LivingEntity target){
		return openFaceCavity(player,target,true);
	}
	public boolean openFaceCavity(PlayerEntity player, LivingEntity target, boolean shouldKnockback){
		Optional<FaceCavityEntity> optional = FaceCavityEntity.of(target);
		if(optional.isPresent()){
			FaceCavityEntity faceCavityEntity = optional.get();
			FaceCavityInstance fc = faceCavityEntity.getFaceCavityInstance();
			if(target == player || fc.getChestCavityType().isOpenable(fc)) {
				if (fc.getOrganScore(FCOrganScores.EASE_OF_ACCESS) > 0) {
					if(player.world.isClient) {
						player.playSound(SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.PLAYERS, .75f, 1);
					}
				}
				else{
					if (!shouldKnockback) {
						target.damage(DamageSource.GENERIC, 4f); // this is to prevent self-knockback, as that feels weird.
					} else {
						target.damage(DamageSource.player(player), 4f);
					}
				}
				if (target.isAlive()) {
					String name;
					try {
						name = target.getDisplayName().getString();
						name = name.concat("'s ");
					} catch (Exception e) {
						name = "";
					}
					FaceCavityInventory inv = FaceCavityUtil.openFaceCavity(fc);
					((FaceCavityEntity)player).getFaceCavityInstance().fcBeingOpened = fc;
					player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
						return new FaceCavityScreenHandler(i, playerInventory, inv);
					}, new TranslatableText(name + "Face Cavity")));
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
		else{
			return false;
		}
	}
}
