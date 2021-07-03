package com.frogastudios.facecavity.mixin;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import com.frogastudios.facecavity.chestcavities.organs.OrganManager;
import com.frogastudios.facecavity.chestcavities.organs.OrganData;
import com.frogastudios.facecavity.util.OrganUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Item.class)
public class MixinItem {

    @Inject(at = @At("HEAD"), method = "appendTooltip")
    public void chestCavityItemAppendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context, CallbackInfo info){
        //((Item)(Object)this)
        Identifier id = Registry.ITEM.getId(((Item)(Object)this));
        if(OrganManager.GeneratedOrganData.containsKey(id)){
            OrganData data = OrganManager.GeneratedOrganData.get(id);
            if(!data.pseudoOrgan){
                OrganUtil.displayOrganQuality(data.organScores,tooltip);
                OrganUtil.displayCompatibility(stack,world,tooltip,context);
            }
        }
    }

}
