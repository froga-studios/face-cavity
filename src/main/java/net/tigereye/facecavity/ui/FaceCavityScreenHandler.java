package net.tigereye.facecavity.ui;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.Property;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.FaceCavityInventory;
import net.tigereye.facecavity.chestcavities.instance.FaceCavityInstance;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;

public class FaceCavityScreenHandler extends ScreenHandler {

    private final FaceCavityInventory inventory;
    private final int size;
    private final int rows;

    private static FaceCavityInventory getOrCreateFaceCavityInventory(PlayerInventory playerInventory){/*
        FaceCavityInstance playerCC = ((FaceCavityEntity)playerInventory.player).getFaceCavityInstance();
        FaceCavityInstance targetCCI = playerCC.ccBeingOpened;
        if(targetCCI != null){
            FaceCavity.LOGGER.info("Found CCI");
            return targetCCI.inventory;
        }
        FaceCavity.LOGGER.info("Missed CCI");*/
        return new FaceCavityInventory();
    }

    public FaceCavityScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, getOrCreateFaceCavityInventory(playerInventory));
    }

    public FaceCavityScreenHandler(int syncId, PlayerInventory playerInventory, FaceCavityInventory inventory) {
        super(FaceCavity.CHEST_CAVITY_SCREEN_HANDLER, syncId);
        this.size = inventory.size();
        this.inventory = inventory;
        this.rows = (size-1)/9 + 1;
        inventory.onOpen(playerInventory.player);
        int i = (rows - 4) * 18;

        int n;
        int m;
        for(n = 0; n < this.rows; ++n) {
            for(m = 0; m < 9 && (n*9)+m < size; ++m) {
                this.addSlot(new Slot(inventory, m + n * 9, 8 + m * 18, 18 + n * 18));//18 + n * 18));
            }
        }

        for(n = 0; n < 3; ++n) {
            for(m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + n * 9 + 9, 8 + m * 18, 102 + n * 18 + i));//103 + n * 18 + i));
            }
        }

        for(n = 0; n < 9; ++n) {
            this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 160 + i));//161 + i));
        }

    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);
        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();
            if (invSlot < this.inventory.size()) {
                /*if(inventory.getInstance().type.isSlotForbidden(invSlot)){
                    return ItemStack.EMPTY;
                }*/
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }
}
