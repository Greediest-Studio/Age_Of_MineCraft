package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityLargeFireballOther;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGhasther extends EntityTameBase implements Massive, Flying, Armored {
  private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntityGhasther.class, DataSerializers.BOOLEAN);
  
  private int explosionStrength = 5;
  
  public EnumBehaviour behaviour = EnumBehaviour.REGULAR;
  
  private int damageTillNextScream;
  
  public EntityGhasther(World worldIn) {
    super(worldIn);
    setSize(9.0F, 9.0F);
    this.experienceValue = 100;
    this.isImmuneToFire = true;
    this.isOffensive = true;
    this.moveHelper = new GhastMoveHelper(this);
    this.tasks.addTask(0, new AIFireballAttack(this));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.0D, EngenderConfig.mobs.useMobTalkerModels ? 64.0F : 100.0F, EngenderConfig.mobs.useMobTalkerModels ? 9.0F : 16.0F));
    this.tasks.addTask(2, new AIRandomFly(this));
    this.tasks.addTask(3, new AILookAround());
    this.tasks.addTask(4, new EntityAILookIdle(this));
  }
  
  public String getDescName() {
    return "ghather";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.LIGHT_PURPLE;
  }
  
  public int playMusic() {
    return 4;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setColor(BossInfo.Color.YELLOW);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGhasther(this.world);
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 0.5F;
  }
  
  public float getBonusVSArmored() {
    return 3.0F;
  }
  
  public float getBonusVSFlying() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 2.0F;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isAttacking() {
    return this.dataManager.get(ATTACKING);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public void setAttacking(boolean attacking) {
    this.dataManager.set(ATTACKING, attacking);
  }
  
  public void performSpecialAttack() {
    playSound(getHurtSound(null), getSoundVolume(), getSoundPitch());
    setSpecialAttackTimer(1200);
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() - 0.25F;
  }
  
  public void setInWeb() {}
  
  public int getSpawnTimer() {
    return 80;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    if (!this.world.isRemote)
      if (!this.world.isRemote) {
        if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false)
          if (getDistanceSq(getAttackTarget()) < (this.reachWidth * this.reachWidth + ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width) * ((getAttackTarget() instanceof EntityTameBase) ? ((EntityTameBase)getAttackTarget()).reachWidth : (getAttackTarget()).width)) + 9.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
            attackEntityAsMob(getAttackTarget());
        if (isEntityAlive()) {
          ChunkLoadingEvent.updateLoaded(this);
        } else {
          ChunkLoadingEvent.stopLoading(this);
        } 
      }  
    this.onGround = false;
    this.isAirBorne = true;
    if (isHero() && getSpecialAttackTimer() > 1100) {
      playSound(getHurtSound(null), getSoundVolume(), getSoundPitch());
      List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(64.0D, 64.0D, 64.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list != null && !list.isEmpty())
          for (EntityLivingBase entity : list) {
              if (entity != null)
                  if (!false) {
                      if (getSpecialAttackTimer() > 1190 && entity instanceof EntityCreature && !(entity instanceof EntityTameBase))
                          ((EntityCreature) entity).tasks.addTask(0, new EntityAIAvoidEntitySPC((EntityCreature) entity, EntityGhasther.class, 128.0F, 1.5D, 1.5D));
                      entity.hurtResistantTime = 0;
                      inflictEngenderMobDamage(entity, "'s ears exploded thanks to ", DamageSource.WITHER, 0.25F);
                      entity.addVelocity(this.rand.nextGaussian() * 0.05D, this.rand.nextGaussian() * 0.05D, this.rand.nextGaussian() * 0.05D);
                  }
          }
    } 
    if (getAttackTarget() != null && getDistanceSq(getAttackTarget()) < 2048.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (getAttackTarget() != null && getOwner() != null && !canEntityBeSeen(getAttackTarget()) && this.rand.nextInt(80) == 0)
      playSound(getHurtSound(null), getSoundVolume(), getSoundPitch() + 0.25F);
    if (getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)getControllingPassenger();
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = passenger.rotationYaw;
      this.rotationPitch = EngenderConfig.mobs.useMobTalkerModels ? passenger.rotationPitch : 0.0F;
      double d1 = 0.4D;
      if (this.moralRaisedTimer > 200)
        d1 *= 2.0D; 
      Vec3d vec3 = passenger.getLook(1.0F);
      if (passenger.moveForward > 0.0F)
        move(MoverType.SELF, vec3.x * d1, vec3.y * d1, vec3.z * d1); 
      if (passenger.moveForward < 0.0F)
        move(MoverType.SELF, -(vec3.x * d1), -(vec3.y * d1), -(vec3.z * d1)); 
    } 
    if (getOwner() != null)
      if (getAttackTarget() == null && this.ticksExisted % 10 == 0) {
        double d0 = getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        List<Entity> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(d0, d0, d0), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            Entity entity = list.get(i1);
            if (entity != null && entity.isEntityAlive() && canEntityBeSeen(entity) && !false && entity.getDistanceSq(getOwner()) <= 256.0D) {
              setAttackTarget((EntityLivingBase)entity);
            } else {
              list.remove(entity);
            } 
          }  
      }  
  }
  
  public int getFireballStrength() {
    return this.explosionStrength;
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public void travel(float strafe, float vertical, float forward) {
    if (this.deathTicks >= 300)
      super.travel(strafe, vertical, forward); 
    if (!isAIDisabled() || isBeingRidden() || this.deathTicks < 300) {
      if (isInWater()) {
        moveRelative(strafe, vertical, forward, 0.02F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.800000011920929D;
        this.motionY *= 0.800000011920929D;
        this.motionZ *= 0.800000011920929D;
      } else if (isInLava()) {
        moveRelative(strafe, vertical, forward, 0.02F);
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.5D;
        this.motionY *= 0.5D;
        this.motionZ *= 0.5D;
      } else {
        float f = 0.95F;
        float f1 = 0.16277136F / f * f * f;
        moveRelative(strafe, vertical, forward, 0.02F);
        f = 0.95F;
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= f;
        this.motionY *= f;
        this.motionZ *= f;
      } 
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public boolean isOnLadder() {
    return false;
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (amount > 50.0F)
      amount = 50.0F; 
    if (isEntityInvulnerable(source))
      return false; 
    if (source.getImmediateSource() instanceof net.minecraft.entity.projectile.EntityLargeFireball && source.getTrueSource() instanceof EntityPlayer) {
      super.attackEntityFrom(source, 1000.0F);
      return true;
    } 
    float f = getHealth();
    super.attackEntityFrom(source, amount);
    this.damageTillNextScream = (int)(this.damageTillNextScream + f - getHealth());
    return true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(ATTACKING, Boolean.FALSE);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(35.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(40.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_GHAST_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_GHAST_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_GHAST_DEATH;
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_GHASTHER;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 10.0F;
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("ExplosionPower", this.explosionStrength);
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    if (tagCompund.hasKey("ExplosionPower", 99))
      this.explosionStrength = tagCompund.getInteger("ExplosionPower"); 
  }
  
  public float getEyeHeight() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.height * 0.84F) : (this.height * 0.66F);
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
  
  public float getBrightness() {
    return 1.0F;
  }
  
  public double getMountedYOffset() {
    return this.height * (EngenderConfig.mobs.useMobTalkerModels ? 0.75D : 0.95D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.fleshHitCrushHeavy;
  }
  
  public void launchFireball(EntityGhasther ghast, double d2, double d3, double d4, double d5, double d6, double d7) {
    EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(this.world, ghast, d2, d3, d4);
    entitylargefireball.explosionPower = ghast.getFireballStrength();
    entitylargefireball.posX = d5;
    entitylargefireball.posY = d6;
    entitylargefireball.posZ = d7;
    float dm = (float)ghast.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    entitylargefireball.damage = dm;
    ghast.playSound(SoundEvents.ENTITY_GHAST_SHOOT, 10.0F, 0.875F);
    this.world.spawnEntity(entitylargefireball);
  }
  
  public void chooseNewAttack() {
    if (this.damageTillNextScream > 20.0F) {
      if (getHealth() <= getMaxHealth() / 3.0F) {
        this.behaviour = EnumBehaviour.RANDOM;
      } else {
        this.behaviour = EnumBehaviour.GHASTCALL;
      } 
    } else {
      switch (this.rand.nextInt(15)) {
        case 1:
        case 2:
        case 3:
          this.behaviour = EnumBehaviour.SPREAD;
          return;
        case 4:
        case 5:
        case 6:
          this.behaviour = EnumBehaviour.PEPPER;
          return;
        case 7:
        case 8:
          this.behaviour = EnumBehaviour.TRISHOT;
          return;
        case 9:
          this.behaviour = EnumBehaviour.BOMBARD;
          return;
        case 10:
          this.behaviour = EnumBehaviour.MACHINEGUN;
          return;
      } 
      this.behaviour = EnumBehaviour.REGULAR;
    } 
  }
  
  protected void onDeathUpdate() {
    this.deathTicks++;
    this.world.spawnParticle((this.rand.nextFloat() <= 0.2F) ? EnumParticleTypes.EXPLOSION_HUGE : EnumParticleTypes.EXPLOSION_LARGE, this.posX + (this.rand.nextFloat() * 9.0F - 4.5F), this.posY + (this.rand.nextFloat() * 9.0F - 4.5F), this.posZ + (this.rand.nextFloat() * 9.0F - 4.5F), 0.0D, 0.0D, 0.0D);
    if (this.deathTicks >= 300) {
      this.rotationPitch--;
      this.deathTime++;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + (this.rand.nextFloat() * 9.0F - 4.5F), this.posY + (this.rand.nextFloat() * 9.0F - 4.5F), this.posZ + (this.rand.nextFloat() * 9.0F - 4.5F), 0.0D, 0.0D, 0.0D);
    } 
    if (!this.world.isRemote) {
      if (this.deathTicks == 340) {
        if (!this.world.isRemote && this.world.getGameRules().getBoolean("doMobLoot")) {
          int i = getExperiencePoints(this.attackingPlayer);
          i = ForgeEventFactory.getExperienceDrop(this, this.attackingPlayer, i);
          while (i > 0) {
            int j = EntityXPOrb.getXPSplit(i);
            i -= j;
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
            this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
          } 
        } 
        setDead();
        for (int k = 0; k < 10; k++)
          spawnExplosionParticle(); 
      } 
      if (this.deathTicks % 60 == 0)
        playHurtSound(null); 
      if (this.deathTicks == 1) {
        this.motionY++;
        this.motionY++;
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.world.playerEntities) {
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
            entityplayer.sendStatusMessage(new TextComponentTranslation("§4" + getOwner().getName() + "'s " + getName() + " has been killed!!!", new Object[0]), true);
          } 
          getOwner().sendMessage(new TextComponentTranslation("Your Ghasther has been destroyed!", new Object[0]));
        } 
      } 
      if (this.deathTicks > 80)
        move(MoverType.SELF, 0.0D, -0.2D, 0.0D); 
      this.renderYawOffset = this.rotationYawHead = this.rotationYaw += 5.0F;
    } 
  }
  
  static class AIFireballAttack extends EntityAIBase {
    private EntityGhasther ghast;
    
    public int attackTimer;
    
    public AIFireballAttack(EntityGhasther ghast) {
      this.ghast = ghast;
    }
    
    public boolean shouldExecute() {
      return (this.ghast.getAttackTarget() != null && !this.ghast.isSneaking());
    }
    
    public void startExecuting() {
      this.attackTimer = 0;
      this.ghast.setArmsRaised(true);
      this.ghast.chooseNewAttack();
    }
    
    public void resetTask() {
      this.ghast.setAttacking(false);
      this.ghast.setArmsRaised(false);
    }
    
    public void updateTask() {
      double d1 = this.ghast.isChild() ? 1.5D : 3.0D;
      Vec3d vec3 = this.ghast.getLook(1.0F);
      double mark1 = this.ghast.posX + vec3.x * d1;
      double mark2 = this.ghast.posY + 1.0D + vec3.y * d1;
      double mark3 = this.ghast.posZ + vec3.z * d1;
      EntityLivingBase entitylivingbase = this.ghast.getAttackTarget();
      double d0 = 100.0D;
      if (entitylivingbase != null && entitylivingbase.getDistanceSq(this.ghast) < d0 * d0) {
        World world = this.ghast.world;
        this.attackTimer++;
        if (this.ghast.moralRaisedTimer > 200)
          this.attackTimer++; 
        switch (this.ghast.behaviour) {
          case PEPPER:
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.6F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer > 30 && this.attackTimer % 10 == 0) {
              double d2 = entitylivingbase.posX - mark1;
              double d3 = entitylivingbase.posY + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - mark2;
              double d4 = entitylivingbase.posZ - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 80) {
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case TRISHOT:
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.6F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer >= 20 && this.attackTimer % 20 == 0) {
              double d2 = entitylivingbase.posX - mark1;
              double d3 = entitylivingbase.posY + ((entitylivingbase.height > 6.0F) ? 6.0D : (entitylivingbase.height * 0.5D)) - mark2;
              double d4 = entitylivingbase.posZ - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 60) {
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case CATCH:
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.5F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer == 40)
              for (int i = 0; i <= 6; i++) {
                double d2 = entitylivingbase.posX + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) - mark1;
                double d3 = entitylivingbase.posY + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - mark2;
                double d4 = entitylivingbase.posZ + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) - mark3;
                this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              }  
            if (this.attackTimer == 50)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.5F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer == 80) {
              for (int i = 0; i <= 6; i++) {
                double d2 = entitylivingbase.posX + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) - mark1;
                double d3 = entitylivingbase.posY + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - mark2;
                double d4 = entitylivingbase.posZ + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) - mark3;
                this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              } 
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case SPREAD:
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.5F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer == 40) {
              for (int i = 0; i <= 6 + this.ghast.getRNG().nextInt(3); i++) {
                double d2 = entitylivingbase.posX + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) - mark1;
                double d3 = entitylivingbase.posY + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - mark2;
                double d4 = entitylivingbase.posZ + ((i == 1) ? 0.0D : (this.ghast.getRNG().nextDouble() * 8.0D - 4.0D)) - mark3;
                this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              } 
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case BOMBARD:
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.4F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer > 20 && this.attackTimer % 3 == 0) {
              double d2 = entitylivingbase.posX + this.ghast.getRNG().nextDouble() * 2.0D - 1.0D - mark1;
              double d3 = entitylivingbase.posY + this.ghast.getRNG().nextDouble() * 2.0D - 1.0D + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - mark2;
              double d4 = entitylivingbase.posZ + this.ghast.getRNG().nextDouble() * 2.0D - 1.0D - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 140) {
              this.attackTimer = -60;
              this.ghast.chooseNewAttack();
            } 
            break;
          case MACHINEGUN:
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.4F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer > 20) {
              double d2 = entitylivingbase.posX - mark1;
              double d3 = entitylivingbase.posY + ((entitylivingbase.height > 6.0F) ? 6.0D : (entitylivingbase.height * 0.5D)) - mark2;
              double d4 = entitylivingbase.posZ - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 80) {
              this.attackTimer = -80;
              this.ghast.chooseNewAttack();
            } 
            break;
          case GHASTCALL:
            if (this.attackTimer == 40) {
              this.ghast.playHurtSound(null);
              this.ghast.playHurtSound(null);
              this.ghast.playHurtSound(null);
              this.ghast.playHurtSound(null);
              this.ghast.playHurtSound(null);
            } 
            if (this.attackTimer >= 80) {
              int i = MathHelper.floor(this.ghast.posX);
              int k = MathHelper.floor(this.ghast.posZ);
              for (int h = (int)this.ghast.getHealth(); h <= this.ghast.getMaxHealth(); h++) {
                for (int l = 0; l < 10; l++) {
                  int i1 = i + MathHelper.getInt(this.ghast.rand, 16, 64) * MathHelper.getInt(this.ghast.rand, -1, 1);
                  int k1 = k + MathHelper.getInt(this.ghast.rand, 16, 64) * MathHelper.getInt(this.ghast.rand, -1, 1);
                  int j1 = this.ghast.world.getTopSolidOrLiquidBlock(new BlockPos(i1, MathHelper.floor(this.ghast.posY), k1)).getY();
                  if (this.ghast.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(this.ghast.world, new BlockPos(i1, j1 - 1, k1), EnumFacing.UP)) {
                    EntityGhast entityzombie = new EntityGhast(this.ghast.world);
                    entityzombie.setPosition(i1, j1, k1);
                    if (this.ghast.rand.nextInt(100) == 0 && !this.ghast.world.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.ghast.world.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), entityzombie) && this.ghast.world.getCollisionBoxes(entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.ghast.world.containsAnyLiquid(entityzombie.getEntityBoundingBox())) {
                      this.ghast.world.spawnEntity(entityzombie);
                      entityzombie.onInitialSpawn(this.ghast.world.getDifficultyForLocation(new BlockPos(entityzombie)), null);
                      entityzombie.setOwnerId(this.ghast.getOwnerId());
                      entityzombie.setIsAntiMob(this.ghast.isAntiMob());
                      entityzombie.setGrowingAge(this.ghast.getGrowingAge());
                      break;
                    } 
                  } 
                } 
              } 
              this.attackTimer = -80;
              this.ghast.chooseNewAttack();
              this.ghast.damageTillNextScream = 0;
            } 
            break;
          case RANDOM:
            if (this.attackTimer == 10)
              this.ghast.playHurtSound(null); 
            if (this.attackTimer > 20 && this.attackTimer % 10 == 0) {
              double d2 = this.ghast.getRNG().nextDouble() * 64.0D - 32.0D;
              double d3 = this.ghast.getRNG().nextDouble() * 64.0D - 32.0D;
              double d4 = this.ghast.getRNG().nextDouble() * 64.0D - 32.0D;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 140) {
              this.attackTimer = -60;
              this.ghast.chooseNewAttack();
              this.ghast.damageTillNextScream = 0;
            } 
            break;
          default:
            if (this.ghast.behaviour == EntityGhasther.EnumBehaviour.GHASTCALL || this.ghast.behaviour == EntityGhasther.EnumBehaviour.RANDOM)
              this.attackTimer = 0; 
            if (this.attackTimer == 10)
              this.ghast.playSound(SoundEvents.ENTITY_GHAST_WARN, 10.0F, 0.6F + this.ghast.getRNG().nextFloat() * 0.4F); 
            if (this.attackTimer == 20) {
              double d2 = entitylivingbase.posX - mark1;
              double d3 = entitylivingbase.posY + ((entitylivingbase.height > 8.0F) ? 7.0D : (entitylivingbase.height * 0.5D)) - mark2;
              double d4 = entitylivingbase.posZ - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
              if (this.ghast.getDistance(entitylivingbase) <= 10.0D)
                this.ghast.attackEntityAsMob(entitylivingbase);
            } 
            break;
        } 
      } else if (this.attackTimer > 0) {
        this.attackTimer--;
      } 
      this.ghast.setAttacking((this.attackTimer > 10 || this.ghast.getSpecialAttackTimer() > 1100));
    }
  }
  
  class AILookAround extends EntityAIBase {
    private EntityGhasther parentEntity = EntityGhasther.this;
    
    public AILookAround() {
      setMutexBits(2);
    }
    
    public boolean shouldExecute() {
      return true;
    }
    
    public void updateTask() {
      if (this.parentEntity.getControllingPassenger() != null) {
        this.parentEntity.prevRotationYaw = this.parentEntity.rotationYaw = (this.parentEntity.getControllingPassenger()).rotationYaw;
        this.parentEntity.rotationPitch = 0.0F;
        this.parentEntity.setRotation(this.parentEntity.rotationYaw, this.parentEntity.rotationPitch);
        this.parentEntity.rotationYawHead = this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
      } else if (this.parentEntity.getAttackTarget() == null) {
        this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = this.parentEntity.rotationYawHead = -((float)Math.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * 180.0F / 3.1415927F;
      } else {
        EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
        this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw = this.parentEntity.rotationYawHead;
        this.parentEntity.getLookHelper().setLookPositionWithEntity(entitylivingbase, 180.0F, EngenderConfig.mobs.useMobTalkerModels ? 40.0F : 180.0F);
      } 
    }
  }
  
  static class AIRandomFly extends EntityAIBase {
    private EntityGhasther ghast;
    
    public AIRandomFly(EntityGhasther ghast) {
      this.ghast = ghast;
      setMutexBits(1);
    }
    
    public boolean shouldExecute() {
      EntityMoveHelper entitymovehelper = this.ghast.getMoveHelper();
      if (!entitymovehelper.isUpdating())
        return true; 
      double d0 = entitymovehelper.getX() - this.ghast.posX;
      double d1 = entitymovehelper.getY() - this.ghast.posY;
      double d2 = entitymovehelper.getZ() - this.ghast.posZ;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return (d3 < 1.0D || d3 > 6400.0D);
    }
    
    public boolean shouldContinueExecuting() {
      return false;
    }
    
    public void startExecuting() {
      Random random = this.ghast.getRNG();
      if (this.ghast.getOwner() != null) {
        if (this.ghast.getOwner().isSneaking()) {
          double d0 = (this.ghast.getOwner()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d1 = (this.ghast.getOwner()).posY + 8.0D + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d2 = (this.ghast.getOwner()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } else if (!this.ghast.getCurrentBook().isEmpty()) {
          double d0 = (this.ghast.getOwner()).posX;
          double d1 = (this.ghast.getOwner()).posY + 8.0D;
          double d2 = (this.ghast.getOwner()).posZ;
          this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } else {
          double d0 = (this.ghast.getOwner()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = (this.ghast.getOwner()).posY + ((EngenderConfig.mobs.useMobTalkerModels || this.ghast.isChild()) ? 16.0D : 32.0D) + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = (this.ghast.getOwner()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } 
      } else {
        double d0 = this.ghast.posX + random.nextGaussian() * 32.0D;
        double d1 = this.ghast.posY + random.nextGaussian() * 32.0D;
        double d2 = this.ghast.posZ + random.nextGaussian() * 32.0D;
        this.ghast.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
      } 
    }
  }
  
  static class GhastMoveHelper extends EntityMoveHelper {
    private EntityGhasther parentEntity;
    
    private int courseChangeCooldown;
    
    public GhastMoveHelper(EntityGhasther ghast) {
      super(ghast);
      this.parentEntity = ghast;
    }
    
    public void setMoveTo(double x, double y, double z, double speedIn) {
      super.setMoveTo(x, Flying.clampFlightY(y), z, speedIn);
    }
    
    public void onUpdateMoveHelper() {
      if (this.action == EntityMoveHelper.Action.MOVE_TO && this.parentEntity.getJukeboxToDanceTo() == null) {
        double d0 = this.posX - this.parentEntity.posX;
        double d1 = this.posY - this.parentEntity.posY;
        double d2 = this.posZ - this.parentEntity.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (this.courseChangeCooldown-- <= 0) {
          this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
          d3 = MathHelper.sqrt(d3);
          if (isNotColliding(this.posX, this.posY, this.posZ, d3)) {
            this.parentEntity.motionX += d0 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.15D : 0.075D);
            this.parentEntity.motionY += d1 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.15D : 0.075D);
            this.parentEntity.motionZ += d2 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.15D : 0.075D);
          } else {
            this.action = EntityMoveHelper.Action.WAIT;
          } 
        } 
      } 
    }
    
    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
      double d0 = (x - this.parentEntity.posX) / p_179926_7_;
      double d1 = (y - this.parentEntity.posY) / p_179926_7_;
      double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
      AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();
      for (int i = 1; i < p_179926_7_; i++) {
        axisalignedbb = axisalignedbb.offset(d0, d1, d2);
        if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty())
          return false; 
      } 
      return true;
    }
  }
  
  private enum EnumBehaviour {
    REGULAR, SPREAD, BOMBARD, CATCH, MACHINEGUN, PEPPER, TRISHOT, GHASTCALL, ENRAGED, RANDOM, FINALE
  }
}

