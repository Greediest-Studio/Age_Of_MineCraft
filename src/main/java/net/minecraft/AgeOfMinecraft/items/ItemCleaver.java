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
    this(material, name, tab, null);
  }
  
  public ItemCleaver(Item.ToolMaterial material, String name, CreativeTabs tab, TextFormatting format) {
    super(material);
    setRegistryName(name);
    setTranslationKey(name);
    setCreativeTab(tab);
  }
  
  public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
    try {
      if (!player.world.isRemote && target.isEntityAlive() && target instanceof EntityTameBase) {
        EntityTameBase mob = (EntityTameBase)target;
        if (!mob.isChild() && mob.canBeButchered()) {
          player.swingArm(hand);
          hitEntity(stack, (EntityLivingBase)mob, (EntityLivingBase)player);
          mob.cleave((int)getAttackDamage() + ForgeHooks.getLootingLevel((Entity)target, (Entity)player, DamageSource.causePlayerDamage(player)) * 3 + 3, DamageSource.causePlayerDamage(player));
        } 
        return true;
      } 
      if (target.isEntityAlive() && target instanceof EntityAgeable && target instanceof EntityAnimal && !(target instanceof net.minecraft.entity.passive.EntityTameable)) {
        EntityAgeable mob = (EntityAgeable)target;
        if (!mob.isChild() && !((EntityAnimal)mob).isInLove() && mob.createChild(mob) != null && mob.createChild(mob).getClass() == mob.getClass()) {
          player.swingArm(hand);
          mob.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
          hitEntity(stack, mob, player);
          mob.attackEntityFrom(DamageSource.causePlayerDamage(player), mob.getMaxHealth());
          mob.setNoAI(true);
          for (int ai = 0; ai <= (int)getAttackDamage() + ForgeHooks.getLootingLevel((Entity)target, (Entity)player, DamageSource.causePlayerDamage(player)) * 3 + 3; ai++) {
            EntityAgeable addon = mob.createChild(mob);
            addon.copyLocationAndAnglesFrom((Entity)mob);
            addon.renderYawOffset = addon.rotationYaw = addon.rotationYawHead = mob.rotationYawHead;
            mob.world.spawnEntity((Entity)addon);
            addon.setNoAI(true);
            addon.onDeath(DamageSource.causePlayerDamage(player));
            addon.setDead();
          } 
          mob.onDeath(DamageSource.causePlayerDamage(player));
          mob.setHealth(0.0F);
        } 
        return true;
      } 
      return false;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
    stack.damageItem(1, attacker);
    target.playSound(ESound.slashflesh, 1.0F, (target.getRNG().nextFloat() - target.getRNG().nextFloat()) * 0.2F + 1.0F);
    if (!target.isEntityAlive() && !target.world.isRemote) {
      if (Loader.isModLoaded("abyssalcraft") && target.getClass() == EntityDepthsGhoul.class) {
        EntityItem depths_ghoul_head;
        EntityItem pete_head;
        EntityItem mr_wilson_head;
        EntityItem dr_orange_head;
        switch (((EntityDepthsGhoul)target).getGhoulType()) {
          case 0:
            depths_ghoul_head = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(ACBlocks.depths_ghoul_head));
            depths_ghoul_head.setDefaultPickupDelay();
            depths_ghoul_head.setNoDespawn();
            depths_ghoul_head.motionY++;
            target.world.spawnEntity((Entity)depths_ghoul_head);
            break;
          case 1:
            pete_head = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(ACBlocks.pete_head));
            pete_head.setDefaultPickupDelay();
            pete_head.setNoDespawn();
            pete_head.motionY++;
            target.world.spawnEntity((Entity)pete_head);
            break;
          case 2:
            mr_wilson_head = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(ACBlocks.mr_wilson_head));
            mr_wilson_head.setDefaultPickupDelay();
            mr_wilson_head.setNoDespawn();
            mr_wilson_head.motionY++;
            target.world.spawnEntity((Entity)mr_wilson_head);
            break;
          case 3:
            dr_orange_head = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(ACBlocks.dr_orange_head));
            dr_orange_head.setDefaultPickupDelay();
            dr_orange_head.setNoDespawn();
            dr_orange_head.motionY++;
            target.world.spawnEntity((Entity)dr_orange_head);
            break;
        } 
      } 
      if (target.getClass() == EntitySkeleton.class) {
        EntityItem entityitem = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(Items.SKULL, 1, 0));
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
        entityitem.motionY++;
        target.world.spawnEntity((Entity)entityitem);
      } 
      if (target.getClass() == EntityWitherSkeleton.class) {
        EntityItem entityitem = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(Items.SKULL, 1, 1));
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
        entityitem.motionY++;
        target.world.spawnEntity((Entity)entityitem);
      } 
      if (target.getClass() == EntityZombie.class) {
        EntityItem entityitem = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(Items.SKULL, 1, 2));
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
        entityitem.motionY++;
        target.world.spawnEntity((Entity)entityitem);
      } 
      if (target.getClass() == EntityPlayer.class) {
        EntityItem entityitem = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(Items.SKULL, 1, 3));
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
        entityitem.motionY++;
        target.world.spawnEntity((Entity)entityitem);
      } 
      if (target.getClass() == EntityCreeper.class) {
        EntityItem entityitem = new EntityItem(target.world, target.posX, target.posY + target.getEyeHeight(), target.posZ, new ItemStack(Items.SKULL, 1, 4));
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
        entityitem.motionY++;
        target.world.spawnEntity((Entity)entityitem);
      } 
      if (target.getClass() == EntityWither.class) {
        float f = MathHelper.cos((target.renderYawOffset + 0.0F) * 0.017453292F);
        float f1 = MathHelper.cos((target.renderYawOffset + 180.0F) * 0.017453292F);
        float f2 = MathHelper.sin((target.renderYawOffset + 0.0F) * 0.017453292F);
        float f3 = MathHelper.sin((target.renderYawOffset + 180.0F) * 0.017453292F);
        EntityItem entityitem = new EntityItem(target.world, target.posX, target.posY + 3.0D, target.posZ, new ItemStack(Items.SKULL, 1, 1));
        entityitem.setDefaultPickupDelay();
        entityitem.setNoDespawn();
        entityitem.motionY++;
        target.world.spawnEntity((Entity)entityitem);
        EntityItem entityitem1 = new EntityItem(target.world, target.posX + f * 1.3D, target.posY + 2.2D, target.posZ + f2 * 1.3D, new ItemStack(Items.SKULL, 1, 1));
        entityitem1.setDefaultPickupDelay();
        entityitem1.setNoDespawn();
        entityitem1.motionY++;
        target.world.spawnEntity(entityitem1);
        EntityItem entityitem11 = new EntityItem(target.world, target.posX + f1 * 1.3D, target.posY + 2.2D, target.posZ + f3 * 1.3D, new ItemStack(Items.SKULL, 1, 1));
        entityitem11.setDefaultPickupDelay();
        entityitem11.setNoDespawn();
        entityitem11.motionY++;
        target.world.spawnEntity(entityitem11);
      } 
    } 
    return true;
  }
  
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    tooltip.add("Gain extra animal drops and chop off any head");
    tooltip.add(TextFormatting.GOLD + "Right click to butcher animals");
  }
}
