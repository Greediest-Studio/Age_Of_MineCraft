package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityShadowCreature extends EntityTameBase implements Light, Undead {
  public EntityShadowCreature(World par1World) {
    super(par1World);
    setSize(0.5F, 1.0F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 32.0F, 6.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIFleeSun((EntityCreature)this, 0.8D));
    this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.isOffensive = true;
    this.isImmuneToFire = true;
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public int getNextLevelRequirement() {
    return 50;
  }
  
  public float getBonusVSLight() {
    return 1.1F;
  }
  
  public float getBonusVSMassive() {
    return 0.1F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityShadowCreature(this.world);
  }
  
  public boolean isEntityImmuneToCoralium() {
    return true;
  }
  
  public boolean isEntityImmuneToDread() {
    return true;
  }
  
  public boolean isEntityImmuneToAntiMatter() {
    return true;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(12.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
    } 
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return super.attackEntityAsMob(par1Entity);
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() + 0.3F;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.shadow_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.shadow_death;
  }
  
  protected Item getDropItem() {
    return ACItems.shadow_fragment;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_SHADOW_CREATURE;
  }
  
  public void onLivingUpdate() {
    for (int i = 0; i < 2 && ACConfig.particleEntity && this.world.provider.getDimension() != ACLib.dark_realm_id; i++)
      this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
    super.onLivingUpdate();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
      Calendar calendar = this.world.getCurrentDate();
      if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    return par1EntityLivingData;
  }
}
