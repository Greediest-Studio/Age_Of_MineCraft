package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLoot;
import com.shinoow.abyssalcraft.lib.ACSounds;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityGatekeeperMinion extends EntityTameBase implements Armored, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 5.0D, 0);
  
  public EntityGatekeeperMinion(World par1World) {
    super(par1World);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.tasks.addTask(2, (EntityAIBase)new AIFireballAttack(this));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    setSize(0.9F, 2.7F);
    this.isOffensive = true;
    this.isImmuneToFire = true;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public float getBonusVSArmored() {
    return 1.25F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGatekeeperMinion(this.world);
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
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
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
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(36.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    swingArm(EnumHand.MAIN_HAND);
    swingArm(EnumHand.OFF_HAND);
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 3.0F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.shadow_death;
  }
  
  protected void playStepSound(BlockPos pos, Block par4) {
    playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    EntityLivingBase enemy = null;
    if (par1DamageSource.getTrueSource() instanceof EntityLivingBase && !false)
      enemy = (EntityLivingBase)par1DamageSource.getTrueSource(); 
    if (this.rand.nextInt(10) == 0) {
      List<EntityRemnant> remnants = this.world.getEntitiesWithinAABB(EntityRemnant.class, getEntityBoundingBox().grow(16.0D, 16.0D, 16.0D));
      if (remnants != null && 
        enemy != null) {
        Iterator<EntityRemnant> iter = remnants.iterator();
        while (iter.hasNext())
          ((EntityRemnant)iter.next()).enrage(false, enemy); 
      } 
      playSound(ACSounds.remnant_scream, 3.0F, 1.0F);
    } 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  protected void dropFewItems(boolean par1, int par2) {
    ItemStack item = new ItemStack(ACItems.eldritch_scale);
    if (this.rand.nextInt(10) == 0)
      item = new ItemStack(ACItems.ethaxium_ingot); 
    if (item != null) {
      int i = this.rand.nextInt(3);
      if (par2 > 0)
        i += this.rand.nextInt(par2 + 1); 
      for (int j = 0; j < i; j++)
        entityDropItem(item, 0.0F); 
    } 
  }
  
  protected ResourceLocation getLootTable() {
    return ACLoot.ENTITY_MINION_OF_THE_GATEKEEPER;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).isEmpty()) {
      Calendar calendar1 = this.world.getCurrentDate();
      if (calendar1.get(2) + 1 == 10 && calendar1.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
        setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
        this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
      } 
    } 
    IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    Calendar calendar = this.world.getCurrentDate();
    attribute.removeModifier(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.applyModifier(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  static class AIFireballAttack extends EntityAIFriendlyAttackMelee {
    private final EntityGatekeeperMinion blaze;
    
    private int attackStep;
    
    private int attackTime;
    
    public AIFireballAttack(EntityGatekeeperMinion blazeIn) {
      super(blazeIn, 1.0D, true);
      this.blaze = blazeIn;
      setMutexBits(3);
    }
    
    public boolean shouldExecute() {
      EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
      return (entitylivingbase != null && entitylivingbase.isEntityAlive());
    }
    
    public void startExecuting() {
      this.attackStep = 0;
    }
    
    public void resetTask() {
      super.resetTask();
    }
    
    public void updateTask() {
      this.attackTime--;
      EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
      if (entitylivingbase != null) {
        double d0 = this.blaze.getDistanceSq((Entity)entitylivingbase);
        if (d0 < getAttackReachSqr(entitylivingbase)) {
          if (this.attackTime <= 0) {
            this.attackTime = 20;
            this.blaze.attackEntityAsMob((Entity)entitylivingbase);
          } 
        } else if (d0 < getFollowDistance() * getFollowDistance()) {
          double d1 = entitylivingbase.posX - this.blaze.posX;
          double d2 = entitylivingbase.posY + 0.5D - this.blaze.posY + (this.blaze.height / 2.0F) + 0.5D;
          double d3 = entitylivingbase.posZ - this.blaze.posZ;
          if (this.attackTime <= 0) {
            this.attackStep++;
            if (this.attackStep == 1) {
              this.attackTime = 40;
            } else if (this.attackStep <= 4) {
              this.attackTime = 20;
            } else {
              this.attackTime = 60;
              this.attackStep = 0;
            } 
            if (this.attackStep > 1) {
              float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
              this.blaze.world.playEvent((EntityPlayer)null, 1018, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);
              this.blaze.swingArm(EnumHand.OFF_HAND);
              for (int i = 0; i < 1; i++) {
                EntityOmotholChargeOther entitysmallfireball = new EntityOmotholChargeOther(this.blaze.world, (EntityLivingBase)this.blaze, d1, d2, d3);
                entitysmallfireball.posY = this.blaze.posY + (this.blaze.height / 2.0F) + 0.5D;
                entitysmallfireball.playSound(ACSounds.remnant_scream, 1.0F, 1.0F);
                this.blaze.world.spawnEntity((Entity)entitysmallfireball);
              } 
            } 
          } 
          this.blaze.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, this.blaze.getHorizontalFaceSpeed(), this.blaze.getVerticalFaceSpeed());
        } 
      } 
      super.updateTask();
    }
    
    private double getFollowDistance() {
      IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
      return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
    }
  }
}

