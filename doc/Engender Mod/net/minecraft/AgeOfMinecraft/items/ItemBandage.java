package net.minecraft.AgeOfMinecraft.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import java.util.List;
import net.minecraft.AgeOfMinecraft.registry.EEffect;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemBandage extends ItemBEItem implements IBauble {
  public ItemBandage(String name, int durability) {
    super("bandage_" + name);
    func_77656_e(durability);
    func_77625_d(1);
  }
  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
    ItemStack stack = playerIn.func_184586_b(handIn);
    if (playerIn.func_70644_a(EEffect.BLEEDING) && (playerIn.func_70660_b(EEffect.BLEEDING).func_76458_c() <= 1 || this != EItem.bandage)) {
      playerIn.func_184185_a(SoundEvents.field_187748_db, 1.0F, 1.5F);
      playerIn.func_184596_c(EEffect.BLEEDING);
      if (!playerIn.field_70170_p.field_72995_K) {
        playerIn.func_70691_i(2.0F);
        if (this == EItem.bandage || this == EItem.bandageMedical)
          stack.func_77972_a(1, (EntityLivingBase)playerIn); 
      } 
      return new ActionResult(EnumActionResult.SUCCESS, stack);
    } 
    if (this == EItem.artifact2) {
      for (PotionEffect effect : playerIn.func_70651_bq()) {
        if (effect.func_188419_a().func_76398_f() || effect.func_188419_a() == MobEffects.field_188423_x || effect.func_188419_a() == EEffect.BLEEDING)
          playerIn.func_184596_c(effect.func_188419_a()); 
      } 
      return new ActionResult(EnumActionResult.SUCCESS, stack);
    } 
    return super.func_77659_a(worldIn, playerIn, handIn);
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    switch (func_77658_a().substring(5)) {
      case "bandage_artifact":
        return ESetup.UBEREPIC;
      case "bandage_box":
        return EnumRarity.RARE;
    } 
    return super.func_77613_e(stack);
  }
  
  @SideOnly(Side.CLIENT)
  public boolean func_77636_d(ItemStack stack) {
    return (this == EItem.artifact2 || super.func_77636_d(stack));
  }
  
  public void func_77624_a(ItemStack stack, World player, List<String> l, ITooltipFlag B) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    switch (func_77658_a().substring(5)) {
      case "bandage_artifact":
        l.add("(" + TextFormatting.GOLD + "ARTIFACT" + TextFormatting.GRAY + ")");
        l.add("A box enchanted to heal not only injuries, but even negative potion effects.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
      case "bandage_box":
        l.add("A first-aid kit that can cure bleeding an infinite amount of times.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
      case "bandage_good":
        l.add("A medical bandage that heals any bleeding.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
      case "bandage_poor":
        l.add("A simple bandage that's only good at healing hurt knees.");
        l.add(TextFormatting.GOLD + "Right click or equip in Bauble slot to use.");
        break;
    } 
  }
  
  public BaubleType getBaubleType(ItemStack itemstack) {
    return BaubleType.TRINKET;
  }
  
  public void onEquipped(ItemStack itemstack, EntityLivingBase player) {
    player.func_184185_a(SoundEvents.field_187748_db, 0.75F, 1.9F);
  }
  
  public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {
    player.func_184185_a(SoundEvents.field_187748_db, 0.75F, 2.0F);
  }
  
  public void onWornTick(ItemStack stack, EntityLivingBase entityIn) {
    if (entityIn.func_70644_a(EEffect.BLEEDING) && (entityIn.func_70660_b(EEffect.BLEEDING).func_76458_c() <= 1 || this != EItem.bandage)) {
      entityIn.func_184185_a(SoundEvents.field_187748_db, 1.0F, 1.5F);
      entityIn.func_184596_c(EEffect.BLEEDING);
      if (!entityIn.field_70170_p.field_72995_K) {
        entityIn.func_70691_i(2.0F);
        if (this == EItem.bandage || this == EItem.bandageMedical)
          stack.func_77972_a(1, entityIn); 
      } 
    } 
    if (this == EItem.artifact2 && entityIn.func_70651_bq() != null && !entityIn.func_70651_bq().isEmpty()) {
      for (PotionEffect effect : entityIn.func_70651_bq()) {
        if (effect.func_188419_a().func_76398_f() || effect.func_188419_a() == MobEffects.field_188423_x || effect.func_188419_a() == EEffect.BLEEDING)
          entityIn.func_184596_c(effect.func_188419_a()); 
      } 
      if (!entityIn.field_70170_p.field_72995_K && entityIn.field_70173_aa % 20 == 0)
        entityIn.func_70691_i(2.0F); 
    } 
  }
}
