package net.minecraft.AgeOfMinecraft.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional.Interface;

@Interface(iface = "baubles.api.IBauble", modid = "baubles")
public class ItemWeddingRing extends ItemBEItem implements IBauble {
  public ItemWeddingRing() {
    super("wedding_ring");
    setMaxStackSize(1);
  }
  
  public BaubleType getBaubleType(ItemStack itemstack) {
    return BaubleType.RING;
  }
  
  public EnumRarity getRarity(ItemStack stack) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    return stack.getTagCompound().hasKey("Entity") ? ESetup.SUPEREPIC : EnumRarity.RARE;
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    return false;
  }
  
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    return EnumActionResult.PASS;
  }
  
  public static Entity spawnMob(World worldIn, ItemStack stack, double x, double y, double z) {
    return null;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    tooltip.add("Give this ring to one of your mob girls to marry her.");
    tooltip.add("New children from her are stronger");
    tooltip.add(TextFormatting.GREEN + "When she is slain, pick this up and right click on the ground to revive her");
    tooltip.add(TextFormatting.GOLD + "Shift right click on one of your mob girls to marry them");
    tooltip.add("");
    if (stack.hasTagCompound() && stack.getTagCompound().hasKey("EntityName")) {
      tooltip.add(TextFormatting.BLUE + "Bound to: " + stack.getTagCompound().getString("EntityName"));
    } else {
      tooltip.add(TextFormatting.BLUE + "Bound to: Nobody");
    } 
  }
  
  public boolean getShareTag() {
    return true;
  }
}
