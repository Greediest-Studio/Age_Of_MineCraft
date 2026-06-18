package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class ItemEngenderStatChecker extends ItemBEItem {
  public static EntityTameBase viewedEntity;
  
  public ItemEngenderStatChecker() {
    super("statchecker");
    setMaxStackSize(1);
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    return EnumRarity.UNCOMMON;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("View the stats of any engendered mob");
    tooltip.add(TextFormatting.GOLD + "Right click on an engendered mob to view stats");
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
    if (target instanceof EntityTameBase) {
      EntityTameBase entitypig = (EntityTameBase)target;
      if (entitypig.isEntityAlive()) {
        playerIn.swingArm(hand);
        viewedEntity = entitypig;
        if (playerIn.world.isRemote)
          FMLNetworkHandler.openGui(playerIn, EngenderMod.instance, 100, playerIn.world, (int)target.posX, (int)target.posY, (int)target.posZ); 
      } 
      return true;
    } 
    return false;
  }
  
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    if (attacker instanceof EntityPlayer && target instanceof EntityLivingBase)
      itemInteractionForEntity(stack, (EntityPlayer)attacker, target, EnumHand.MAIN_HAND); 
    return true;
  }
}
