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
    ItemCompat.setMaxStackSize(this, 1);
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
        if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(net.minecraft.AgeOfMinecraft.util.EntityCompat.world(playerIn)))
          FMLNetworkHandler.openGui(playerIn, EngenderMod.instance, 100, net.minecraft.AgeOfMinecraft.util.EntityCompat.world(playerIn), (int)net.minecraft.AgeOfMinecraft.util.EntityCompat.posX(target), (int)net.minecraft.AgeOfMinecraft.util.EntityCompat.posY(target), (int)net.minecraft.AgeOfMinecraft.util.EntityCompat.posZ(target)); 
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
