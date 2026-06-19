package net.minecraft.AgeOfMinecraft.items;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSummoningStaff extends ItemBEItem {
  public ItemSummoningStaff() {
    super("summoningstaff");
    setMaxStackSize(1);
    setHasSubtypes(true);
  }
  
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Teleport all of your engendered mobs to you");
    tooltip.add(TextFormatting.GREEN + "Tier 5 allows you to convert all wild engendered mobs also");
    tooltip.add(TextFormatting.GOLD + "Hold right click to use");
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    switch (stack.getMetadata()) {
      case 0:
        return EnumRarity.COMMON;
      case 1:
        return EnumRarity.UNCOMMON;
      case 2:
        return EnumRarity.RARE;
      case 3:
        return EnumRarity.EPIC;
      case 4:
        return ESetup.SUPEREPIC;
    } 
    return ESetup.UBEREPIC;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean hasEffect(ItemStack stack) {
    return (super.hasEffect(stack) || stack.getMetadata() > 3);
  }
  
  public int getMaxItemUseDuration(ItemStack stack) {
    return 40;
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.getHeldItem(hand);
    return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
  }
  
  private ItemStack findAmmo(EntityPlayer player) {
    return player.getHeldItem(EnumHand.MAIN_HAND);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    return stack;
  }
  
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab))
      for (int i = 0; i <= 4; i++)
        items.add(new ItemStack(this, 1, i));  
  }
}
