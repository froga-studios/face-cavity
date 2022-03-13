package net.tigereye.facecavity.items;

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
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.tigereye.facecavity.chestcavities.FaceCavityInventory;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.crossmod.requiem.CCRequiem;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.registration.CCItems;
import net.tigereye.facecavity.registration.CCOrganScores;
import net.tigereye.facecavity.ui.FaceCavityScreenHandler;
import net.tigereye.facecavity.util.FaceCavityUtil;

import java.util.Optional;

public class ChestOpener extends Item {
	
	public ChestOpener() {
		super(CCItems.CHEST_OPENER_SETTINGS);
	}

	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
		LivingEntity target = null;
		if(CCRequiem.REQUIEM_ACTIVE){
			target = PossessionComponent.getHost(player);

		}
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
			FaceCavityEntity chestCavityEntity = optional.get();
			FaceCavityInstance cc = chestCavityEntity.getFaceCavityInstance();
			if(target == player || cc.getFaceCavityType().isOpenable(cc)) {
				if (cc.getOrganScore(CCOrganScores.EASE_OF_ACCESS) > 0) {
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
					FaceCavityInventory inv = FaceCavityUtil.openFaceCavity(cc);
					((FaceCavityEntity)player).getFaceCavityInstance().ccBeingOpened = cc;
					player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
						return new FaceCavityScreenHandler(i, playerInventory, inv);
					}, new TranslatableText(name + "Chest Cavity")));
				}
				return true;
			}
			else{
				if(player.world.isClient) {
					if (!target.getEquippedStack(EquipmentSlot.CHEST).isEmpty()) {
						player.sendMessage(new LiteralText("Target's chest is obstructed"),true);
						player.playSound(SoundEvents.BLOCK_CHAIN_HIT, SoundCategory.PLAYERS, .75f, 1);
					} else {
						player.sendMessage(new LiteralText("Target is too healthy to open"),true);
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
