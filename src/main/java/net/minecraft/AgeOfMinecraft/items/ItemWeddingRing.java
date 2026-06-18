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
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    if (target instanceof EntityTameBase && player.isSneaking()) {
      EntityTameBase mob = (EntityTameBase)target;
      if (!stack.getTagCompound().hasKey("Entity") && mob != null && !mob.isWild() && mob.getOwner() == player && mob.canBeMarried()) {
        mob.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
        mob.world.setEntityState((Entity)mob, (byte)20);
        player.swingArm(hand);
        if (!player.world.isRemote) {
          mob.setMarried(true);
          if (player instanceof EntityPlayerMP)
            ESetup.MARRY_MOB.trigger((EntityPlayerMP)player, mob); 
          if (stack.hasDisplayName())
            mob.setCustomNameTag(stack.getDisplayName()); 
          if (EngenderConfig.general.useMessage)
            player.sendMessage((ITextComponent)new TextComponentTranslation("Congradulations, " + player.getName() + "! Your marrige with " + mob.getName() + " has been successful! Till death due you two part...or will it?", new Object[0])); 
          stack.shrink(1);
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = player.getHeldItem(hand);
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    if (!stack.getTagCompound().hasKey("Entity"))
      return EnumActionResult.FAIL; 
    pos = pos.offset(facing);
    Entity entity = EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag("Entity"), world);
    if (entity instanceof EntityTameBase) {
      EntityTameBase entityliving = (EntityTameBase)entity;
      entityliving.writeToNBT(stack.getTagCompound().getCompoundTag("Entity"));
      entityliving.world.setEntityState((Entity)entityliving, (byte)20);
      entityliving.deathTime = 0;
      if (!world.isRemote) {
        entityliving.copyLocationAndAnglesFrom((Entity)player);
        entityliving.setLocationAndAngles(pos.getX(), pos.getY(), pos.getZ(), player.rotationYawHead + 180.0F, 40.0F);
        entityliving.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
        entityliving.renderYawOffset = entityliving.rotationYawHead = entityliving.rotationYaw;
        entityliving.playLivingSound();
        entityliving.setEnergy(0.0F);
        entityliving.setGrowingAge(0);
        entityliving.setHealth(1.0F);
        entityliving.setMarried(true);
        world.spawnEntity((Entity)entityliving);
        stack.shrink(1);
      } 
      return EnumActionResult.SUCCESS;
    } 
    return EnumActionResult.PASS;
  }
  
  public static Entity spawnMob(World worldIn, ItemStack stack, double x, double y, double z) {
    if (!stack.hasTagCompound())
      stack.setTagCompound(new NBTTagCompound()); 
    Entity entity = null;
    if (stack.getTagCompound().getCompoundTag("Entity") != null) {
      entity = EntityList.createEntityFromNBT(stack.getTagCompound().getCompoundTag("Entity"), worldIn);
      if (entity != null && entity instanceof EntityTameBase) {
        EntityTameBase entityliving = (EntityTameBase)entity;
        entityliving.writeToNBT(stack.getTagCompound().getCompoundTag("Entity"));
        entityliving.world.setEntityState((Entity)entityliving, (byte)20);
        entityliving.playSound(SoundEvents.BLOCK_GLASS_BREAK, 1.0F, 1.0F);
        if (!worldIn.isRemote) {
          entityliving.setLocationAndAngles(x, y, z, MathHelper.wrapDegrees(worldIn.rand.nextFloat() * 360.0F), 0.0F);
          entityliving.rotationYawHead = entityliving.rotationYaw;
          entityliving.renderYawOffset = entityliving.rotationYaw;
          entityliving.playLivingSound();
          entityliving.setEnergy(0.0F);
          entityliving.setGrowingAge(0);
          entityliving.setMarried(true);
          worldIn.spawnEntity((Entity)entityliving);
          stack.shrink(1);
        } 
      } 
    } 
    return entity;
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
