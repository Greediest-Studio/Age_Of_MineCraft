package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.registry.ETab;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;

@Interface(iface = "com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem", modid = "abyssalcraft")
public class ItemPEGun extends ItemBow implements IEnergyContainerItem {
  public ItemPEGun(String string) {
    func_77656_e(0);
    func_77655_b(string);
    setRegistryName(string);
    func_77637_a(ETab.abyssal);
    func_77625_d(1);
  }
  
  public void func_150895_a(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (func_194125_a(tab)) {
      ItemStack stack = new ItemStack((Item)this);
      addEnergy(stack, getMaxEnergy(stack));
      items.add(stack);
    } 
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    tooltip.add("Blast your enemies with the power of the Great Ones");
  }
  
  public ActionResult<ItemStack> func_77659_a(World worldIn, EntityPlayer player, EnumHand hand) {
    ItemStack itemStackIn = player.func_184586_b(hand);
    if (itemStackIn.func_190926_b() || (!itemStackIn.func_190926_b() && getContainedEnergy(itemStackIn) <= 0.0F)) {
      player.field_70170_p.func_184133_a(null, new BlockPos((Entity)player), ESound.pegunjam, SoundCategory.PLAYERS, 0.5F, 1.0F);
      return new ActionResult(EnumActionResult.FAIL, itemStackIn);
    } 
    float f = MathHelper.func_76134_b((player.field_70759_as + (180 * (((hand == EnumHand.OFF_HAND) ? 1 : 2) - 1))) * 0.017453292F);
    float f1 = MathHelper.func_76126_a((player.field_70759_as + (180 * (((hand == EnumHand.OFF_HAND) ? 1 : 2) - 1))) * 0.017453292F);
    if (!player.field_71075_bZ.field_75098_d)
      consumeEnergy(itemStackIn, 1.0F); 
    double d22 = 64.0D;
    Vec3d vec3 = player.func_70676_i(1.0F);
    double d2 = player.field_70165_t + vec3.field_72450_a * d22 - player.field_70165_t + f * 0.35D + vec3.field_72450_a;
    double d3 = player.field_70163_u + 1.25D + vec3.field_72448_b * d22 - player.field_70163_u + 1.25D + vec3.field_72448_b;
    double d4 = player.field_70161_v + vec3.field_72449_c * d22 - player.field_70161_v + f1 * 0.35D + vec3.field_72449_c;
    EntityPEGunPellet entitywitherskull = new EntityPEGunPellet(worldIn, (EntityLivingBase)player, d2, d3, d4);
    entitywitherskull.field_70165_t = player.field_70165_t + f * 0.35D + vec3.field_72450_a;
    entitywitherskull.field_70163_u = player.field_70163_u + 1.25D + vec3.field_72448_b;
    entitywitherskull.field_70161_v = player.field_70161_v + f1 * 0.35D + vec3.field_72449_c;
    entitywitherskull.damage = getProjectileDamage(itemStackIn);
    if (!worldIn.field_72995_K)
      worldIn.func_72838_d((Entity)entitywitherskull); 
    player.field_70170_p.func_184133_a(null, new BlockPos((Entity)player), SoundEvents.field_187539_bB, SoundCategory.PLAYERS, 0.5F, 0.5F + player.func_70681_au().nextFloat() * 0.25F);
    player.field_70170_p.func_184133_a(null, new BlockPos((Entity)player), ESound.pegunfire, SoundCategory.PLAYERS, 0.5F, 0.6F + player.func_70681_au().nextFloat() * 0.2F + entitywitherskull.damage / 100.0F);
    return new ActionResult(EnumActionResult.PASS, itemStackIn);
  }
  
  public float getContainedEnergy(ItemStack stack) {
    float energy;
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    if (stack.func_77978_p().func_74764_b("PotEnergy")) {
      energy = stack.func_77978_p().func_74760_g("PotEnergy");
    } else {
      energy = 0.0F;
      stack.func_77978_p().func_74776_a("PotEnergy", energy);
    } 
    return energy;
  }
  
  public EnumAction func_77661_b(ItemStack stack) {
    return EnumAction.NONE;
  }
  
  public void setDamage(ItemStack stack, int damage) {}
  
  public int getMaxEnergy(ItemStack stack) {
    return (stack.func_77973_b() == EItem.peGunLevel5) ? 2500 : ((stack.func_77973_b() == EItem.peGunLevel4) ? 2000 : ((stack.func_77973_b() == EItem.peGunLevel3) ? 1500 : ((stack.func_77973_b() == EItem.peGunLevel2) ? 1000 : 500)));
  }
  
  public float getProjectileDamage(ItemStack stack) {
    return (stack.func_77973_b() == EItem.peGunLevel5) ? 10.0F : ((stack.func_77973_b() == EItem.peGunLevel4) ? 8.0F : ((stack.func_77973_b() == EItem.peGunLevel3) ? 6.0F : ((stack.func_77973_b() == EItem.peGunLevel2) ? 4.0F : 2.0F)));
  }
  
  public void addEnergy(ItemStack stack, float energy) {
    float contained = getContainedEnergy(stack);
    if (contained + energy >= getMaxEnergy(stack)) {
      stack.func_77978_p().func_74776_a("PotEnergy", getMaxEnergy(stack));
    } else {
      stack.func_77978_p().func_74776_a("PotEnergy", contained += energy);
    } 
  }
  
  public float consumeEnergy(ItemStack stack, float energy) {
    float contained = getContainedEnergy(stack);
    if (energy < contained) {
      stack.func_77978_p().func_74776_a("PotEnergy", contained -= energy);
      return energy;
    } 
    stack.func_77978_p().func_74776_a("PotEnergy", 0.0F);
    return contained;
  }
  
  public boolean canAcceptPE(ItemStack stack) {
    return (getContainedEnergy(stack) < getMaxEnergy(stack));
  }
  
  public boolean canTransferPE(ItemStack stack) {
    return false;
  }
}
