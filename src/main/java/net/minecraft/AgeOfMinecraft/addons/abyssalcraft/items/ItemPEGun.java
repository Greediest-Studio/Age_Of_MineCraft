package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items;

import com.shinoow.abyssalcraft.api.energy.IEnergyContainerItem;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.items.ItemCompat;
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
    ItemCompat.setMaxDamage(this, 0);
    ItemCompat.setup(this, string, ETab.abyssal);
    ItemCompat.setMaxStackSize(this, 1);
  }
  
  public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
    if (isInCreativeTab(tab)) {
      ItemStack stack = new ItemStack(this);
      addEnergy(stack, getMaxEnergy(stack));
      items.add(stack);
    } 
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    tooltip.add("Blast your enemies with the power of the Great Ones");
  }
  
  public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand hand) {
    ItemStack itemStackIn = player.getHeldItem(hand);
    if (itemStackIn.isEmpty() || (!itemStackIn.isEmpty() && getContainedEnergy(itemStackIn) <= 0.0F)) {
      net.minecraft.AgeOfMinecraft.util.EntityCompat.world(player).playSound(null, new BlockPos(player), ESound.pegunjam, SoundCategory.PLAYERS, 0.5F, 1.0F);
      return new ActionResult<>(EnumActionResult.FAIL, itemStackIn);
    } 
    float f = MathHelper.cos((player.rotationYawHead + (180 * (((hand == EnumHand.OFF_HAND) ? 1 : 2) - 1))) * 0.017453292F);
    float f1 = MathHelper.sin((player.rotationYawHead + (180 * (((hand == EnumHand.OFF_HAND) ? 1 : 2) - 1))) * 0.017453292F);
    if (!player.capabilities.isCreativeMode)
      consumeEnergy(itemStackIn, 1.0F); 
    double d22 = 64.0D;
    Vec3d vec3 = player.getLook(1.0F);
    double d2 = player.posX + vec3.x * d22 - player.posX + f * 0.35D + vec3.x;
    double d3 = player.posY + 1.25D + vec3.y * d22 - player.posY + 1.25D + vec3.y;
    double d4 = player.posZ + vec3.z * d22 - player.posZ + f1 * 0.35D + vec3.z;
    EntityPEGunPellet entitywitherskull = new EntityPEGunPellet(worldIn, player, d2, d3, d4);
    entitywitherskull.posX = player.posX + f * 0.35D + vec3.x;
    entitywitherskull.posY = player.posY + 1.25D + vec3.y;
    entitywitherskull.posZ = player.posZ + f1 * 0.35D + vec3.z;
    entitywitherskull.damage = getProjectileDamage(itemStackIn);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(worldIn))
      worldIn.spawnEntity(entitywitherskull);
    net.minecraft.AgeOfMinecraft.util.EntityCompat.world(player).playSound(null, new BlockPos(player), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.PLAYERS, 0.5F, 0.5F + player.getRNG().nextFloat() * 0.25F);
    net.minecraft.AgeOfMinecraft.util.EntityCompat.world(player).playSound(null, new BlockPos(player), ESound.pegunfire, SoundCategory.PLAYERS, 0.5F, 0.6F + player.getRNG().nextFloat() * 0.2F + entitywitherskull.damage / 100.0F);
    return new ActionResult<>(EnumActionResult.PASS, itemStackIn);
  }
  
  public float getContainedEnergy(ItemStack stack) {
    float energy;
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    if (stack.getTagCompound().hasKey("PotEnergy")) {
      energy = stack.getTagCompound().getFloat("PotEnergy");
    } else {
      energy = 0.0F;
      stack.getTagCompound().setFloat("PotEnergy", energy);
    } 
    return energy;
  }
  
  public EnumAction getItemUseAction(ItemStack stack) {
    return EnumAction.NONE;
  }
  
  public void setDamage(ItemStack stack, int damage) {}
  
  public int getMaxEnergy(ItemStack stack) {
    return (stack.getItem() == EItem.peGunLevel5) ? 2500 : ((stack.getItem() == EItem.peGunLevel4) ? 2000 : ((stack.getItem() == EItem.peGunLevel3) ? 1500 : ((stack.getItem() == EItem.peGunLevel2) ? 1000 : 500)));
  }
  
  public float getProjectileDamage(ItemStack stack) {
    return (stack.getItem() == EItem.peGunLevel5) ? 10.0F : ((stack.getItem() == EItem.peGunLevel4) ? 8.0F : ((stack.getItem() == EItem.peGunLevel3) ? 6.0F : ((stack.getItem() == EItem.peGunLevel2) ? 4.0F : 2.0F)));
  }
  
  public void addEnergy(ItemStack stack, float energy) {
    float contained = getContainedEnergy(stack);
    if (contained + energy >= getMaxEnergy(stack)) {
      stack.getTagCompound().setFloat("PotEnergy", getMaxEnergy(stack));
    } else {
      stack.getTagCompound().setFloat("PotEnergy", contained += energy);
    } 
  }
  
  public float consumeEnergy(ItemStack stack, float energy) {
    float contained = getContainedEnergy(stack);
    if (energy < contained) {
      stack.getTagCompound().setFloat("PotEnergy", contained -= energy);
      return energy;
    } 
    stack.getTagCompound().setFloat("PotEnergy", 0.0F);
    return contained;
  }
  
  public boolean canAcceptPE(ItemStack stack) {
    return (getContainedEnergy(stack) < getMaxEnergy(stack));
  }
  
  public boolean canTransferPE(ItemStack stack) {
    return false;
  }
}
