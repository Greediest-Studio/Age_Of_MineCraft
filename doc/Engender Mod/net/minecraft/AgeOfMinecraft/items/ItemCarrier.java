package net.minecraft.AgeOfMinecraft.items;

import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityItemCarrier;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCarrier extends ItemBEItem {
  public ItemCarrier() {
    super("carrier");
    func_77625_d(1);
    func_185043_a(new ResourceLocation("carrying"), new IItemPropertyGetter() {
          @SideOnly(Side.CLIENT)
          public float func_185085_a(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
            return ItemCarrier.this.getState(stack);
          }
        });
  }
  
  public float getState(ItemStack stack) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    if (stack.func_77978_p().func_74764_b("Entity")) {
      if (stack.func_77978_p().func_74764_b("WitherStorm"))
        return 1.0F; 
      if (stack.func_77978_p().func_74764_b("Jzahar"))
        return 0.99F; 
      if (stack.func_77978_p().func_74764_b("ChaosGuardian"))
        return 0.98F; 
      if (stack.func_77978_p().func_74764_b("IsHero")) {
        if (stack.func_77978_p().func_74764_b("IsBoss"))
          return 0.6F; 
        if (stack.func_77978_p().func_74764_b("IsMarried"))
          return 0.5F; 
        return 0.4F;
      } 
      if (stack.func_77978_p().func_74764_b("IsBoss"))
        return 0.3F; 
      if (stack.func_77978_p().func_74764_b("IsMarried"))
        return 0.2F; 
      return 0.1F;
    } 
    return 0.0F;
  }
  
  public EnumRarity func_77613_e(ItemStack stack) {
    return EnumRarity.RARE;
  }
  
  public boolean hasCustomEntity(ItemStack stack) {
    return true;
  }
  
  public Entity createEntity(World world, Entity location, ItemStack itemstack) {
    EntityItemCarrier newItem = new EntityItemCarrier(world);
    newItem.func_174867_a(40);
    newItem.func_174873_u();
    newItem.func_82149_j(location);
    newItem.field_70159_w = location.field_70159_w;
    newItem.field_70181_x = location.field_70181_x;
    newItem.field_70179_y = location.field_70179_y;
    newItem.func_92058_a(itemstack);
    return (Entity)newItem;
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    ItemStack item = player.func_184586_b(hand);
    if (target instanceof EntityTameBase) {
      EntityTameBase mob = (EntityTameBase)target;
      if (!stack.func_77978_p().func_74764_b("Entity") && mob != null && mob.func_70089_S() && mob.func_184188_bt().isEmpty() && mob.func_184187_bx() == null) {
        mob.func_184185_a(SoundEvents.field_187604_bf, 1.0F, 2.0F);
        mob.func_184185_a(SoundEvents.field_187927_ha, 1.0F, 2.0F);
        mob.field_70170_p.func_72960_a((Entity)mob, (byte)20);
        player.func_184609_a(hand);
        if (!player.field_70170_p.field_72995_K) {
          mob.setGuardBlock(null);
          if (mob instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
            SpecialTextUtil.JzaharGroup(player.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.collect") }); 
          NBTTagCompound tag = mob.serializeNBT();
          item.func_77978_p().func_74782_a("Entity", (NBTBase)tag);
          item.func_77978_p().func_74778_a("EntityName", mob.func_70005_c_());
          if (mob.isABoss())
            item.func_77978_p().func_74757_a("IsBoss", true); 
          if (mob.isHero())
            item.func_77978_p().func_74757_a("IsHero", true); 
          if (mob.isMarried())
            item.func_77978_p().func_74757_a("IsMarried", true); 
          if (mob instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) {
            item.func_77978_p().func_74757_a("WitherStorm", true);
            mob.func_174812_G();
          } else {
            if (Loader.isModLoaded("abyssalcraft"))
              if (mob instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
                item.func_77978_p().func_74757_a("Jzahar", true);  
            if (Loader.isModLoaded("draconicevolution"))
              if (mob instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian)
                item.func_77978_p().func_74757_a("ChaosGuardian", true);  
            mob.func_70106_y();
          } 
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
      if (!player.field_70170_p.field_72995_K && entityliving instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
        SpecialTextUtil.JzaharGroup(player.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.release") }); 
      entityliving.func_189511_e(stack.func_77978_p().func_74775_l("Entity"));
      entityliving.func_70642_aH();
      if (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss) {
        entity.func_70012_b(player.field_70165_t, player.field_70163_u + 4.0D, player.field_70161_v, entity.field_70177_z, entity.field_70125_A);
      } else {
        entity.func_70012_b(pos.func_177958_n() + 0.5D, pos.func_177956_o(), pos.func_177952_p() + 0.5D, entity.field_70177_z, entity.field_70125_A);
      } 
      if (!player.field_70170_p.field_72995_K && entityliving instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker)
        entityliving.func_184220_m((Entity)player); 
      entity.field_70170_p.func_72960_a(entity, (byte)20);
      entity.func_184185_a(SoundEvents.field_187539_bB, 1.0F, 2.0F);
      entity.func_184185_a(SoundEvents.field_187561_bM, 1.0F, 2.0F);
      entity.func_184185_a(ESound.createMob, 1.0F, 1.0F);
      stack.func_77978_p().func_82580_o("Entity");
      stack.func_77978_p().func_82580_o("EntityName");
      if (stack.func_77978_p().func_74764_b("IsBoss"))
        stack.func_77978_p().func_82580_o("IsBoss"); 
      if (stack.func_77978_p().func_74764_b("IsHero"))
        stack.func_77978_p().func_82580_o("IsHero"); 
      if (stack.func_77978_p().func_74764_b("IsMarried"))
        stack.func_77978_p().func_82580_o("IsMarried"); 
      if (stack.func_77978_p().func_74764_b("WitherStorm"))
        stack.func_77978_p().func_82580_o("WitherStorm"); 
      if (stack.func_77978_p().func_74764_b("Jzahar"))
        stack.func_77978_p().func_82580_o("Jzahar"); 
      if (stack.func_77978_p().func_74764_b("ChaosGuardian"))
        stack.func_77978_p().func_82580_o("ChaosGuardian"); 
      if (!world.field_72995_K)
        world.func_72838_d(entity); 
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
        if (!worldIn.field_72995_K && entityliving instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
          SpecialTextUtil.JzaharGroup(worldIn, new String[] { I18n.func_74838_a("message.jzahar.release") }); 
        entityliving.func_189511_e(stack.func_77978_p().func_74775_l("Entity"));
        entityliving.func_70012_b(x, y, z, MathHelper.func_76142_g(worldIn.field_73012_v.nextFloat() * 360.0F), 0.0F);
        entityliving.field_70759_as = entityliving.field_70177_z;
        entityliving.field_70761_aq = entityliving.field_70177_z;
        entityliving.func_70642_aH();
        entityliving.field_70170_p.func_72960_a((Entity)entityliving, (byte)20);
        entityliving.func_184185_a(SoundEvents.field_187539_bB, 1.0F, 2.0F);
        entityliving.func_184185_a(SoundEvents.field_187561_bM, 1.0F, 2.0F);
        entityliving.func_184185_a(ESound.createMob, 1.0F, 1.0F);
        stack.func_77978_p().func_82580_o("Entity");
        stack.func_77978_p().func_82580_o("EntityName");
        if (!worldIn.field_72995_K)
          worldIn.func_72838_d((Entity)entityliving); 
      } 
    } 
    return entity;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    tooltip.add("Pick up one of your engendered mobs and carry them around");
    tooltip.add(TextFormatting.GOLD + "Right click to pick up and place your engendered mob");
    tooltip.add("");
    if (stack.func_77942_o() && stack.func_77978_p().func_74764_b("EntityName")) {
      tooltip.add(TextFormatting.BLUE + "Holding: " + stack.func_77978_p().func_74779_i("EntityName") + TextFormatting.WHITE);
    } else {
      tooltip.add(TextFormatting.BLUE + "Holding: Nothing");
    } 
  }
  
  public boolean func_77651_p() {
    return true;
  }
}
