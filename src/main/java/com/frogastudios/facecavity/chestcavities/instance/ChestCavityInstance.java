package com.frogastudios.facecavity.chestcavities.instance;

import com.frogastudios.facecavity.chestcavities.ChestCavityType;
import com.frogastudios.facecavity.interfaces.ChestCavityEntity;
import com.frogastudios.facecavity.listeners.OrganOnHitContext;
import ladysnake.requiem.api.v1.possession.PossessionComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import com.frogastudios.facecavity.ChestCavity;
import com.frogastudios.facecavity.chestcavities.ChestCavityInventory;
import net.tigereye.chestcavity.crossmod.requiem.CCRequiem;
import com.frogastudios.facecavity.util.ChestCavityUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public class ChestCavityInstance implements InventoryChangedListener {

    public static final Logger LOGGER = LogManager.getLogger();

    protected ChestCavityType type;
    public LivingEntity owner;
    public UUID compatibility_id;

    public boolean opened = false;
    public ChestCavityInventory inventory = new ChestCavityInventory();
    public Map<Identifier,Float> oldOrganScores = new HashMap<>();
    protected Map<Identifier,Float> organScores = new HashMap<>();
    public List<OrganOnHitContext> onHitListeners = new ArrayList<>();
    public LinkedList<Consumer<LivingEntity>> projectileQueue = new LinkedList<>();

    public int heartBleedTimer = 0;
    public int bloodPoisonTimer = 0;
    public int liverTimer = 0;
    public float metabolismRemainder = 0;
    public float lungRemainder = 0;
    public int projectileCooldown = 0;
    public int furnaceProgress = 0;
    public EndCrystalEntity connectedCrystal = null;

    public PacketByteBuf updatePacket = null;
    public ChestCavityInstance ccBeingOpened = null;

    public ChestCavityInstance(ChestCavityType type, LivingEntity owner){
        this.type = type;
        this.owner = owner;
        this.compatibility_id = owner.getUuid();
        ChestCavityUtil.evaluateChestCavity(this);
    }

    public ChestCavityType getChestCavityType(){
        if(CCRequiem.REQUIEM_ACTIVE){
            MobEntity victim = PossessionComponent.getPossessedEntity(owner);
            if(victim instanceof ChestCavityEntity){
                return ((ChestCavityEntity)victim).getChestCavityInstance().getChestCavityType();
            }
        }
        return this.type;
    }

    public Map<Identifier,Float> getOrganScores() {
        return organScores;
    }

    public void setOrganScores(Map<Identifier,Float> organScores) {
        this.organScores = organScores;
    }

    public float getOrganScore(Identifier id) {
        if(CCRequiem.REQUIEM_ACTIVE){
            MobEntity victim = PossessionComponent.getPossessedEntity(owner);
            if(victim instanceof ChestCavityEntity){
                return ((ChestCavityEntity)victim).getChestCavityInstance().getOrganScore(id);
            }
        }
        return organScores.getOrDefault(id, 0f);
    }

    public float getOldOrganScore(Identifier id) {
        return oldOrganScores.getOrDefault(id, 0f);
    }

    public void onInventoryChanged(Inventory sender) {
        ChestCavityUtil.clearForbiddenSlots(this);
        ChestCavityUtil.evaluateChestCavity(this);
    }

    public void fromTag(CompoundTag tag, LivingEntity owner) {
        LOGGER.debug("[Chest Cavity] Reading ChestCavityManager fromTag");
        this.owner = owner;
        if(tag.contains("ChestCavity")){
            if(ChestCavity.DEBUG_MODE) {
                System.out.println("Found Save Data");
            }
            CompoundTag ccTag = tag.getCompound("ChestCavity");
            this.opened = ccTag.getBoolean("opened");
            this.heartBleedTimer = ccTag.getInt("HeartTimer");
            this.bloodPoisonTimer = ccTag.getInt("KidneyTimer");
            this.liverTimer = ccTag.getInt("LiverTimer");
            this.metabolismRemainder = ccTag.getFloat("MetabolismRemainder");
            this.lungRemainder = ccTag.getFloat("LungRemainder");
            this.furnaceProgress = ccTag.getInt("FurnaceProgress");
            if(ccTag.contains("compatibility_id")){
                this.compatibility_id = ccTag.getUuid("compatibility_id");
            }
            else{
                this.compatibility_id = owner.getUuid();
            }
            try {
                inventory.removeListener(this);
            }
            catch(NullPointerException ignored){}
            if (ccTag.contains("Inventory")) {
                ListTag listTag = ccTag.getList("Inventory", 10);
                this.inventory.readTags(listTag);
            }
            else if(opened){
                LOGGER.warn("[Chest Cavity] "+owner.getName().asString()+"'s Chest Cavity is mangled. It will be replaced");
                ChestCavityUtil.generateChestCavityIfOpened(this);
            }
            inventory.addListener(this);
        }
        else if(tag.contains("cardinal_components")){
            CompoundTag temp = tag.getCompound("cardinal_components");
            if(temp.contains("chestcavity:inventorycomponent")){
                temp = tag.getCompound("chestcavity:inventorycomponent");
                if(temp.contains("chestcavity")){
                    LOGGER.info("[Chest Cavity] Found "+owner.getName().asString()+"'s old [Cardinal Components] Chest Cavity.");
                    opened = true;
                    ListTag listTag = temp.getList("Inventory", 10);
                    try {
                        inventory.removeListener(this);
                    }
                    catch(NullPointerException ignored){}
                    inventory.readTags(listTag);
                    inventory.addListener(this);
                }
            }
        }
        ChestCavityUtil.evaluateChestCavity(this);
    }

    public void toTag(CompoundTag tag) {
        if(ChestCavity.DEBUG_MODE) {
            System.out.println("Writing ChestCavityManager toTag");
        }
        CompoundTag ccTag = new CompoundTag();
        ccTag.putBoolean("opened", this.opened);
        ccTag.putUuid("compatibility_id", this.compatibility_id);
        ccTag.putInt("HeartTimer", this.heartBleedTimer);
        ccTag.putInt("KidneyTimer", this.bloodPoisonTimer);
        ccTag.putInt("LiverTimer", this.liverTimer);
        ccTag.putFloat("MetabolismRemainder", this.metabolismRemainder);
        ccTag.putFloat("LungRemainder", this.lungRemainder);
        ccTag.putInt("FurnaceProgress", this.liverTimer);
        ccTag.put("Inventory", this.inventory.getTags());
        tag.put("ChestCavity",ccTag);
    }

    public void clone(ChestCavityInstance other) {
        opened = other.opened;
        type = other.type;
        compatibility_id = other.compatibility_id;
        try {
            inventory.removeListener(this);
        }
        catch(NullPointerException ignored){}
        for(int i = 0; i < inventory.size(); ++i) {
            inventory.setStack(i, other.inventory.getStack(i));
            //inventory.forbiddenSlots = other.inventory.forbiddenSlots;
        }
        inventory.readTags(other.inventory.getTags());
        inventory.addListener(this);

        heartBleedTimer = other.heartBleedTimer;
        liverTimer = other.liverTimer;
        bloodPoisonTimer = other.bloodPoisonTimer;
        metabolismRemainder = other.metabolismRemainder;
        lungRemainder = other.lungRemainder;
        furnaceProgress = other.furnaceProgress;
        connectedCrystal = other.connectedCrystal;
        ChestCavityUtil.evaluateChestCavity(this);
    }

}
