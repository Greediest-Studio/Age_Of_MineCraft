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
    func_77625_d(1);
  }
  
  public BaubleType getBaubleType(ItemStack itemstack) {
    return BaubleType.RING;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    return stack.func_77978_p().func_74764_b("Entity") ? ESetup.SUPEREPIC : EnumRarity.RARE;
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    if (target instanceof EntityTameBase && player.func_70093_af()) {
      EntityTameBase mob = (EntityTameBase)target;
      if (!stack.func_77978_p().func_74764_b("Entity") && mob != null && !mob.isWild() && mob.getOwner() == player && mob.canBeMarried()) {
        mob.func_184185_a(SoundEvents.field_187802_ec, 1.0F, 1.0F);
        mob.field_70170_p.func_72960_a((Entity)mob, (byte)20);
        player.func_184609_a(hand);
        if (!player.field_70170_p.field_72995_K) {
          mob.setMarried(true);
          if (player instanceof EntityPlayerMP)
            ESetup.MARRY_MOB.trigger((EntityPlayerMP)player, mob); 
          if (stack.func_82837_s())
            mob.func_96094_a(stack.func_82833_r()); 
          if (EngenderConfig.general.useMessage)
            player.func_145747_a((ITextComponent)new TextComponentTranslation("Congradulations, " + player.func_70005_c_() + "! Your marrige with " + mob.func_70005_c_() + " has been successful! Till death due you two part...or will it?", new Object[0])); 
          stack.func_190918_g(1);
        } 
      } 
      return true;
    } 
    return false;
  }
  
  public EnumActionResult func_180614_a(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack stack = player.func_184586_b(hand);
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    if (!stack.func_77978_p().func_74764_b("Entity"))
      return EnumActionResult.FAIL; 
    pos = pos.func_177972_a(facing);
    Entity entity = EntityList.func_75615_a(stack.func_77978_p().func_74775_l("Entity"), world);
    if (entity instanceof EntityTameBase) {
      EntityTameBase entityliving = (EntityTameBase)entity;
      entityliving.func_189511_e(stack.func_77978_p().func_74775_l("Entity"));
      entityliving.field_70170_p.func_72960_a((Entity)entityliving, (byte)20);
      entityliving.field_70725_aQ = 0;
      if (!world.field_72995_K) {
        entityliving.func_82149_j((Entity)player);
        entityliving.func_70012_b(pos.func_177958_n(), pos.func_177956_o(), pos.func_177952_p(), player.field_70759_as + 180.0F, 40.0F);
        entityliving.func_184185_a(SoundEvents.field_187561_bM, 1.0F, 1.0F);
        entityliving.field_70761_aq = entityliving.field_70759_as = entityliving.field_70177_z;
        entityliving.func_70642_aH();
        entityliving.setEnergy(0.0F);
        entityliving.setGrowingAge(0);
        entityliving.func_70606_j(1.0F);
        entityliving.setMarried(true);
        world.func_72838_d((Entity)entityliving);
        stack.func_190918_g(1);
      } 
      return EnumActionResult.SUCCESS;
    } 
    return EnumActionResult.PASS;
  }
  
  public static Entity spawnMob(World worldIn, ItemStack stack, double x, double y, double z) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    Entity entity = null;
    if (stack.func_77978_p().func_74775_l("Entity") != null) {
      entity = EntityList.func_75615_a(stack.func_77978_p().func_74775_l("Entity"), worldIn);
      if (entity != null && entity instanceof EntityTameBase) {
        EntityTameBase entityliving = (EntityTameBase)entity;
        entityliving.func_189511_e(stack.func_77978_p().func_74775_l("Entity"));
        entityliving.field_70170_p.func_72960_a((Entity)entityliving, (byte)20);
        entityliving.func_184185_a(SoundEvents.field_187561_bM, 1.0F, 1.0F);
        if (!worldIn.field_72995_K) {
          entityliving.func_70012_b(x, y, z, MathHelper.func_76142_g(worldIn.field_73012_v.nextFloat() * 360.0F), 0.0F);
          entityliving.field_70759_as = entityliving.field_70177_z;
          entityliving.field_70761_aq = entityliving.field_70177_z;
          entityliving.func_70642_aH();
          entityliving.setEnergy(0.0F);
          entityliving.setGrowingAge(0);
          entityliving.setMarried(true);
          worldIn.func_72838_d((Entity)entityliving);
          stack.func_190918_g(1);
        } 
      } 
    } 
    return entity;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    tooltip.add("Give this ring to one of your mob girls to marry her.");
    tooltip.add("New children from her are stronger");
    tooltip.add(TextFormatting.GREEN + "When she is slain, pick this up and right click on the ground to revive her");
    tooltip.add(TextFormatting.GOLD + "Shift right click on one of your mob girls to marry them");
    tooltip.add("");
    if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("EntityName")) {
      tooltip.add(TextFormatting.BLUE + "Bound to: " + stack.func_77978_p().func_74779_i("EntityName"));
    } else {
      tooltip.add(TextFormatting.BLUE + "Bound to: Nobody");
    } 
  }
  
  public boolean func_77651_p() {
    return true;
  }
}
