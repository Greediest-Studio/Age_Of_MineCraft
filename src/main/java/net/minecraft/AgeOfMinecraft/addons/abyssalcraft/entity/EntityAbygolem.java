package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityAbygolem extends EntityTameBase implements Light {
  public int timeUntilNextEgg;
  
  public EntityAbygolem(World par1World) {
    super(par1World);
    setSize(0.5F, 1.95F);
    this.tasks.addTask(0, new EntityAIOpenDoor(this, true));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.1D, 32.0F, 9.0F));
    this.tasks.addTask(2, new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.8D));
    this.tasks.addTask(5, new EntityAIWander(this, 0.8D));
    this.tasks.addTask(6, new EntityAILookIdle(this));
    this.isOffensive = true;
    this.timeUntilNextEgg = 200 + this.rand.nextInt(200);
  }
  
  public String getDescName() {
    return "aby_golem";
  }
  
  public int getNextLevelRequirement() {
    return 30;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityAbygolem(this.world);
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    } 
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    if (ACConfig.hardcoreMode && par1Entity instanceof net.minecraft.entity.player.EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 1.5F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return super.attackEntityAsMob(par1Entity);
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (!this.world.isRemote && !isChild() && --this.timeUntilNextEgg <= 0) {
      playSound(ESound.woodHit, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
      entityDropItem(isHero() ? new ItemStack(ACItems.abyssalnite_ingot, 1) : new ItemStack(ACItems.crystal_fragment, 1, 12), this.rand.nextFloat() * this.height);
      this.timeUntilNextEgg = 400 + this.rand.nextInt(1200);
    } 
  }
  
  public float getEyeHeight() {
    return this.height * 0.92F;
  }
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.woodHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.woodHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.woodHitCrush;
  }
  
  protected SoundEvent getAmbientSound() {
    return ACSounds.golem_ambient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ACSounds.golem_hurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.golem_death;
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_ABYSSALNITE_GOLEM;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
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
