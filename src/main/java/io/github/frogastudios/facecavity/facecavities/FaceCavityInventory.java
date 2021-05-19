package io.github.frogastudios.facecavity.facecavities;

import io.github.frogastudios.facecavity.facecavities.instance.FaceCavityInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;


public class FaceCavityInventory extends SimpleInventory {

    FaceCavityInstance instance;
    boolean test;

    public FaceCavityInstance getInstance() {
        return instance;
    }

    public void setInstance(FaceCavityInstance instance) {
        this.instance = instance;
    }

    public FaceCavityInventory() {
        super(27);
    }

    public FaceCavityInventory(int size, FaceCavityInstance instance) {
        super(size);
        this.instance = instance;
    }

    public void readTags(ListTag tags) {
        clear();
        for(int j = 0; j < tags.size(); ++j) {
            CompoundTag compoundTag = tags.getCompound(j);
            int k = compoundTag.getByte("Slot") & 255;
            boolean f = compoundTag.getBoolean("Forbidden");
            if (k >= 0 && k < this.size()) {
                this.setStack(k, ItemStack.fromTag(compoundTag));
            }
        }

    }

    public ListTag getTags() {
        ListTag listTag = new ListTag();

        for(int i = 0; i < this.size(); ++i) {
            ItemStack itemStack = this.getStack(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.putByte("Slot", (byte)i);
                itemStack.toTag(compoundTag);
                listTag.add(compoundTag);
            }
        }

        return listTag;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {

        if(instance == null) {
            return true;
        } //this is for if something goes wrong with that first moment before things sync
        if(instance.owner.isDead()){return true;}
        return (player.distanceTo(instance.owner) < 8);
    }
}
