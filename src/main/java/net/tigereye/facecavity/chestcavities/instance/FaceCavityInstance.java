package net.tigereye.facecavity.chestcavities.instance;

import ladysnake.requiem.api.v1.possession.PossessionComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryChangedListener;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.tigereye.facecavity.FaceCavity;
import net.tigereye.facecavity.chestcavities.FaceCavityInventory;
import net.tigereye.facecavity.chestcavities.FaceCavityType;
import net.tigereye.facecavity.crossmod.requiem.CCRequiem;
import net.tigereye.facecavity.interfaces.FaceCavityEntity;
import net.tigereye.facecavity.listeners.OrganOnHitContext;
import net.tigereye.facecavity.util.FaceCavityUtil;
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
    public float metabolismRemainder = 0;
    public float lungRemainder = 0;
    public int projectileCooldown = 0;
    public int furnaceProgress = 0;
    public EndCrystalEntity connectedCrystal = null;

    public PacketByteBuf updatePacket = null;
    public FaceCavityInstance ccBeingOpened = null;

    public FaceCavityInstance(FaceCavityType type, LivingEntity owner){
        this.type = type;
        this.owner = owner;
        this.compatibility_id = owner.getUuid();
        FaceCavityUtil.evaluateFaceCavity(this);
    }

    public FaceCavityType getFaceCavityType(){
        if(CCRequiem.REQUIEM_ACTIVE){
            MobEntity victim = PossessionComponent.getHost(owner);
            if(victim instanceof FaceCavityEntity){
                return ((FaceCavityEntity)victim).getFaceCavityInstance().getFaceCavityType();
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
            MobEntity victim = PossessionComponent.getHost(owner);
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
        FaceCavityUtil.clearForbiddenSlots(this);
        FaceCavityUtil.evaluateFaceCavity(this);
    }

    public void fromTag(NbtCompound tag, LivingEntity owner) {
        LOGGER.debug("[Chest Cavity] Reading FaceCavityManager fromTag");
        this.owner = owner;
        if(tag.contains("FaceCavity")){
            if(FaceCavity.DEBUG_MODE) {
                System.out.println("Found Save Data");
            }
            NbtCompound ccTag = tag.getCompound("FaceCavity");
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
                NbtList NbtList = ccTag.getList("Inventory", 10);
                this.inventory.readTags(NbtList);
            }
            else if(opened){
                LOGGER.warn("[Chest Cavity] "+owner.getName().asString()+"'s Chest Cavity is mangled. It will be replaced");
                FaceCavityUtil.generateFaceCavityIfOpened(this);
            }
            inventory.addListener(this);
        }
        else if(tag.contains("cardinal_components")){
            NbtCompound temp = tag.getCompound("cardinal_components");
            if(temp.contains("facecavity:inventorycomponent")){
                temp = tag.getCompound("facecavity:inventorycomponent");
                if(temp.contains("facecavity")){
                    LOGGER.info("[Chest Cavity] Found "+owner.getName().asString()+"'s old [Cardinal Components] Chest Cavity.");
                    opened = true;
                    NbtList NbtList = temp.getList("Inventory", 10);
                    try {
                        inventory.removeListener(this);
                    }
                    catch(NullPointerException ignored){}
                    inventory.readTags(NbtList);
                    inventory.addListener(this);
                }
            }
        }
        FaceCavityUtil.evaluateFaceCavity(this);
    }

    public void toTag(NbtCompound tag) {
        if(FaceCavity.DEBUG_MODE) {
            System.out.println("Writing FaceCavityManager toTag");
        }
        NbtCompound ccTag = new NbtCompound();
        ccTag.putBoolean("opened", this.opened);
        ccTag.putUuid("compatibility_id", this.compatibility_id);
        ccTag.putInt("HeartTimer", this.heartBleedTimer);
        ccTag.putInt("KidneyTimer", this.bloodPoisonTimer);
        ccTag.putInt("LiverTimer", this.liverTimer);
        ccTag.putFloat("MetabolismRemainder", this.metabolismRemainder);
        ccTag.putFloat("LungRemainder", this.lungRemainder);
        ccTag.putInt("FurnaceProgress", this.liverTimer);
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
        metabolismRemainder = other.metabolismRemainder;
        lungRemainder = other.lungRemainder;
        furnaceProgress = other.furnaceProgress;
        connectedCrystal = other.connectedCrystal;
        FaceCavityUtil.evaluateFaceCavity(this);
    }

}
