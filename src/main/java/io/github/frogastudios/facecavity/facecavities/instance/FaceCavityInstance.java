package io.github.frogastudios.facecavity.facecavities.instance;

import io.github.frogastudios.facecavity.FaceCavity;

import io.github.frogastudios.facecavity.util.FaceCavityUtil;
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
import io.github.frogastudios.facecavity.facecavities.FaceCavityInventory;
import io.github.frogastudios.facecavity.facecavities.FaceCavityType;
import io.github.frogastudios.facecavity.interfaces.FaceCavityEntity;
import io.github.frogastudios.facecavity.listeners.OrganOnHitContext;
import net.tigereye.chestcavity.crossmod.requiem.CCRequiem;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.function.Consumer;

public class FaceCavityInstance implements InventoryChangedListener {

    public static final Logger LOGGER = LogManager.getLogger();

    protected FaceCavityType type;
    public LivingEntity owner;
    public UUID compatibility_id;

    public boolean opened = false;
    public FaceCavityInventory inventory = new FaceCavityInventory();
    public Map<Identifier,Float> oldOrganScores = new HashMap<>();
    protected Map<Identifier,Float> organScores = new HashMap<>();
    public List<OrganOnHitContext> onHitListeners = new ArrayList<>();
    public LinkedList<Consumer<LivingEntity>> projectileQueue = new LinkedList<>();

    public int heartBleedTimer = 0;
    public int bloodPoisonTimer = 0;
    public int liverTimer = 0;
    public int spleenTimer = 0;
    public float lungRemainder = 0;
    public int projectileCooldown = 0;
    public EndCrystalEntity connectedCrystal = null;

    public PacketByteBuf updatePacket = null;
    public FaceCavityInstance fcBeingOpened = null;

    public FaceCavityInstance(FaceCavityType type, LivingEntity owner){
        this.type = type;
        this.owner = owner;
        this.compatibility_id = owner.getUuid();
        io.github.frogastudios.facecavity.util.FaceCavityUtil.evaluateChestCavity(this);
    }

    public FaceCavityType getChestCavityType(){
        if(CCRequiem.REQUIEM_ACTIVE){
            MobEntity victim = PossessionComponent.getPossessedEntity(owner);
            if(victim instanceof FaceCavityEntity){
                return ((FaceCavityEntity)victim).getFaceCavityInstance().getChestCavityType();
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
            if(victim instanceof FaceCavityEntity){
                return ((FaceCavityEntity)victim).getFaceCavityInstance().getOrganScore(id);
            }
        }
        return organScores.getOrDefault(id, 0f);
    }

    public float getOldOrganScore(Identifier id) {
        return oldOrganScores.getOrDefault(id, 0f);
    }

    public void onInventoryChanged(Inventory sender) {
        io.github.frogastudios.facecavity.util.FaceCavityUtil.clearForbiddenSlots(this);
        io.github.frogastudios.facecavity.util.FaceCavityUtil.evaluateChestCavity(this);
    }

    public void fromTag(CompoundTag tag, LivingEntity owner) {
        LOGGER.debug("[Chest Cavity] Reading ChestCavityManager fromTag");
        this.owner = owner;
        if(tag.contains("FaceCavity")){
            if(FaceCavity.DEBUG_MODE) {
                System.out.println("Found Save Data");
            }
            CompoundTag ccTag = tag.getCompound("FaceCavity");
            this.opened = ccTag.getBoolean("opened");
            this.heartBleedTimer = ccTag.getInt("HeartTimer");
            this.bloodPoisonTimer = ccTag.getInt("KidneyTimer");
            this.liverTimer = ccTag.getInt("LiverTimer");
            this.spleenTimer = ccTag.getInt("SpleenTimer");
            this.lungRemainder = ccTag.getFloat("LungRemainder");
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
                io.github.frogastudios.facecavity.util.FaceCavityUtil.generateChestCavityIfOpened(this);
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
        io.github.frogastudios.facecavity.util.FaceCavityUtil.evaluateChestCavity(this);
    }

    public void toTag(CompoundTag tag) {
        if(FaceCavity.DEBUG_MODE) {
            System.out.println("Writing ChestCavityManager toTag");
        }
        CompoundTag ccTag = new CompoundTag();
        ccTag.putBoolean("opened", this.opened);
        ccTag.putUuid("compatibility_id", this.compatibility_id);
        ccTag.putInt("HeartTimer", this.heartBleedTimer);
        ccTag.putInt("KidneyTimer", this.bloodPoisonTimer);
        ccTag.putInt("LiverTimer", this.liverTimer);
        ccTag.putInt("SpleenTimer", this.spleenTimer);
        ccTag.putFloat("LungRemainder", this.lungRemainder);
        ccTag.put("Inventory", this.inventory.getTags());
        tag.put("FaceCavity",ccTag);
    }

    public void clone(FaceCavityInstance other) {
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
        spleenTimer = other.spleenTimer;
        lungRemainder = other.lungRemainder;
        connectedCrystal = other.connectedCrystal;
        FaceCavityUtil.evaluateChestCavity(this);
    }

}
