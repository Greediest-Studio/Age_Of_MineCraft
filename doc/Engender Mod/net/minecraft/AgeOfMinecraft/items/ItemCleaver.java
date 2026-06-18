package net.minecraft.AgeOfMinecraft.items;

import com.shinoow.abyssalcraft.api.block.ACBlocks;
import com.shinoow.abyssalcraft.common.entity.EntityDepthsGhoul;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityWitherSkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Loader;

public class ItemCleaver extends ItemSword {
  public ItemCleaver(Item.ToolMaterial material, String name, CreativeTabs tab) {
    this(material, name, tab, (TextFormatting)null);
  }
  
  public ItemCleaver(Item.ToolMaterial material, String name, CreativeTabs tab, TextFormatting format) {
    super(material);
    setRegistryName(name);
    func_77655_b(name);
    func_77637_a(tab);
  }
  
  public boolean func_111207_a(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    try {
      if (!player.field_70170_p.field_72995_K && target.func_70089_S() && target instanceof EntityTameBase) {
        EntityTameBase mob = (EntityTameBase)target;
        if (!mob.func_70631_g_() && mob.canBeButchered()) {
          player.func_184609_a(hand);
          func_77644_a(stack, (EntityLivingBase)mob, (EntityLivingBase)player);
          mob.cleave((int)func_150931_i() + ForgeHooks.getLootingLevel((Entity)target, (Entity)player, DamageSource.func_76365_a(player)) * 3 + 3, DamageSource.func_76365_a(player));
        } 
        return true;
      } 
      if (target.func_70089_S() && target instanceof EntityAgeable && target instanceof EntityAnimal && !(target instanceof net.minecraft.entity.passive.EntityTameable)) {
        EntityAgeable mob = (EntityAgeable)target;
        if (!mob.func_70631_g_() && !((EntityAnimal)mob).func_70880_s() && mob.func_90011_a(mob) != null && mob.func_90011_a(mob).getClass() == mob.getClass()) {
          player.func_184609_a(hand);
          mob.func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
          func_77644_a(stack, (EntityLivingBase)mob, (EntityLivingBase)player);
          mob.func_70097_a(DamageSource.func_76365_a(player), mob.func_110138_aP());
          mob.func_94061_f(true);
          for (int ai = 0; ai <= (int)func_150931_i() + ForgeHooks.getLootingLevel((Entity)target, (Entity)player, DamageSource.func_76365_a(player)) * 3 + 3; ai++) {
            EntityAgeable addon = mob.func_90011_a(mob);
            addon.func_82149_j((Entity)mob);
            addon.field_70761_aq = addon.field_70177_z = addon.field_70759_as = mob.field_70759_as;
            mob.field_70170_p.func_72838_d((Entity)addon);
            addon.func_94061_f(true);
            addon.func_70645_a(DamageSource.func_76365_a(player));
            addon.func_70106_y();
          } 
          mob.func_70645_a(DamageSource.func_76365_a(player));
          mob.func_70606_j(0.0F);
        } 
        return true;
      } 
      return false;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean func_77644_a(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    stack.func_77972_a(1, attacker);
    target.func_184185_a(ESound.slashflesh, 1.0F, (target.func_70681_au().nextFloat() - target.func_70681_au().nextFloat()) * 0.2F + 1.0F);
    if (!target.func_70089_S() && !target.field_70170_p.field_72995_K) {
      if (Loader.isModLoaded("abyssalcraft") && target.getClass() == EntityDepthsGhoul.class) {
        EntityItem depths_ghoul_head;
        EntityItem pete_head;
        EntityItem mr_wilson_head;
        EntityItem dr_orange_head;
        switch (((EntityDepthsGhoul)target).getGhoulType()) {
          case 0:
            depths_ghoul_head = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(ACBlocks.depths_ghoul_head));
            depths_ghoul_head.func_174869_p();
            depths_ghoul_head.func_174873_u();
            depths_ghoul_head.field_70181_x++;
            target.field_70170_p.func_72838_d((Entity)depths_ghoul_head);
            break;
          case 1:
            pete_head = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(ACBlocks.pete_head));
            pete_head.func_174869_p();
            pete_head.func_174873_u();
            pete_head.field_70181_x++;
            target.field_70170_p.func_72838_d((Entity)pete_head);
            break;
          case 2:
            mr_wilson_head = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(ACBlocks.mr_wilson_head));
            mr_wilson_head.func_174869_p();
            mr_wilson_head.func_174873_u();
            mr_wilson_head.field_70181_x++;
            target.field_70170_p.func_72838_d((Entity)mr_wilson_head);
            break;
          case 3:
            dr_orange_head = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(ACBlocks.dr_orange_head));
            dr_orange_head.func_174869_p();
            dr_orange_head.func_174873_u();
            dr_orange_head.field_70181_x++;
            target.field_70170_p.func_72838_d((Entity)dr_orange_head);
            break;
        } 
      } 
      if (target.getClass() == EntitySkeleton.class) {
        EntityItem entityitem = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(Items.field_151144_bL, 1, 0));
        entityitem.func_174869_p();
        entityitem.func_174873_u();
        entityitem.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem);
      } 
      if (target.getClass() == EntityWitherSkeleton.class) {
        EntityItem entityitem = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(Items.field_151144_bL, 1, 1));
        entityitem.func_174869_p();
        entityitem.func_174873_u();
        entityitem.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem);
      } 
      if (target.getClass() == EntityZombie.class) {
        EntityItem entityitem = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(Items.field_151144_bL, 1, 2));
        entityitem.func_174869_p();
        entityitem.func_174873_u();
        entityitem.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem);
      } 
      if (target.getClass() == EntityPlayer.class) {
        EntityItem entityitem = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(Items.field_151144_bL, 1, 3));
        entityitem.func_174869_p();
        entityitem.func_174873_u();
        entityitem.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem);
      } 
      if (target.getClass() == EntityCreeper.class) {
        EntityItem entityitem = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + target.func_70047_e(), target.field_70161_v, new ItemStack(Items.field_151144_bL, 1, 4));
        entityitem.func_174869_p();
        entityitem.func_174873_u();
        entityitem.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem);
      } 
      if (target.getClass() == EntityWither.class) {
        float f = MathHelper.func_76134_b((target.field_70761_aq + 0.0F) * 0.017453292F);
        float f1 = MathHelper.func_76134_b((target.field_70761_aq + 180.0F) * 0.017453292F);
        float f2 = MathHelper.func_76126_a((target.field_70761_aq + 0.0F) * 0.017453292F);
        float f3 = MathHelper.func_76126_a((target.field_70761_aq + 180.0F) * 0.017453292F);
        EntityItem entityitem = new EntityItem(target.field_70170_p, target.field_70165_t, target.field_70163_u + 3.0D, target.field_70161_v, new ItemStack(Items.field_151144_bL, 1, 1));
        entityitem.func_174869_p();
        entityitem.func_174873_u();
        entityitem.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem);
        EntityItem entityitem1 = new EntityItem(target.field_70170_p, target.field_70165_t + f * 1.3D, target.field_70163_u + 2.2D, target.field_70161_v + f2 * 1.3D, new ItemStack(Items.field_151144_bL, 1, 1));
        entityitem1.func_174869_p();
        entityitem1.func_174873_u();
        entityitem1.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem1);
        EntityItem entityitem11 = new EntityItem(target.field_70170_p, target.field_70165_t + f1 * 1.3D, target.field_70163_u + 2.2D, target.field_70161_v + f3 * 1.3D, new ItemStack(Items.field_151144_bL, 1, 1));
        entityitem11.func_174869_p();
        entityitem11.func_174873_u();
        entityitem11.field_70181_x++;
        target.field_70170_p.func_72838_d((Entity)entityitem11);
      } 
    } 
    return true;
  }
  
  public void func_77624_a(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Gain extra animal drops and chop off any head");
    tooltip.add(TextFormatting.GOLD + "Right click to butcher animals");
  }
}
