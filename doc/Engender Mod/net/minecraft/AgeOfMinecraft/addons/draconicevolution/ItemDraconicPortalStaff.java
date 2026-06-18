package net.minecraft.AgeOfMinecraft.addons.draconicevolution;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.items.ItemBEItem;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDraconicPortalStaff extends ItemBEItem {
  public ItemDraconicPortalStaff() {
    super("draconicportalstaff");
    func_77625_d(1);
    func_77637_a(ETab.draconic);
    func_77627_a(true);
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Summon a portal to utter chaos itself to destroy your enemies and build your army");
    tooltip.add(TextFormatting.GOLD + "Hold right click while standing on the ground");
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.BOW;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    switch (stack.func_77960_j()) {
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
  public boolean func_77636_d(ItemStack stack) {
    return (super.func_77636_d(stack) || stack.func_77960_j() > 3);
  }
  
  public int func_77626_a(ItemStack stack) {
    return 30;
  }
  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand hand) {
    ItemStack itemStackIn = playerIn.func_184586_b(hand);
    boolean flag = (func_185060_a(playerIn) != null);
    ActionResult<ItemStack> ret = ForgeEventFactory.onArrowNock(itemStackIn, worldIn, playerIn, hand, flag);
    if (ret != null)
      return ret; 
    if (!playerIn.field_71075_bZ.field_75098_d && !flag)
      return !flag ? new ActionResult(EnumActionResult.FAIL, itemStackIn) : new ActionResult(EnumActionResult.PASS, itemStackIn); 
    playerIn.func_184598_c(hand);
    playerIn.field_70170_p.func_184133_a(playerIn, new BlockPos((Entity)playerIn), SoundEvents.field_187680_c, SoundCategory.PLAYERS, 100.0F, 0.5F);
    return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
  }
  
  private ItemStack func_185060_a(EntityPlayer player) {
    return player.func_184586_b(EnumHand.MAIN_HAND);
  }
  
  public ItemStack func_77654_b(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
    return onItemUseFinish(stack, worldIn, (EntityPlayer)entityLiving);
  }
  
  public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    playerIn.func_71020_j(10.0F);
    playerIn.func_184811_cZ().func_185145_a((Item)this, 1500 + stack.func_77960_j() * 500);
    EntityDraconicPortal portal = new EntityDraconicPortal(worldIn);
    portal.func_70012_b((int)playerIn.field_70165_t, (int)playerIn.field_70163_u, (int)playerIn.field_70161_v, 0.0F, 0.0F);
    portal.setOwnerId(playerIn.func_110124_au());
    if (!worldIn.field_72995_K) {
      worldIn.func_72838_d((Entity)portal);
      portal.setMetaData(stack.func_77960_j());
      portal.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000.0D + portal.getMetaData() * 1000.0D);
      portal.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(20.0D + portal.getMetaData() * 10.0D);
      portal.func_70691_i(portal.func_110138_aP());
      stack.func_190918_g(1);
    } 
    return stack;
  }
  
  public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (func_194125_a(tab))
      for (int i = 0; i <= 4; i++)
        items.add(new ItemStack((Item)this, 1, i));  
  }
}
