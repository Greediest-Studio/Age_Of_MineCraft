package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicates;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Flying;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIBabyMobGirlFollowParent;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIMobGirlMate;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStormHead extends EntityTameBase implements IRangedAttackMob, Massive, Armored, Flying, Undead {
  public EntityWitherStorm residentWitherStorm;
  
  public int openMouthCounter;
  
  public EntityWitherStormHead(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.isImmuneToFire = true;
    setSize(9.0F, 9.0F);
    this.tasks.addTask(0, new EntityAIAttackRangedAlly(this, 0.0D, 60, 128.0F));
    this.experienceValue = 500;
    setLevel(300);
    this.ignoreFrustumCheck = true;
    this.forceSpawn = true;
    ((PathNavigateGround)getNavigator()).setBreakDoors(false);
    ((PathNavigateGround)getNavigator()).setEnterDoors(false);
    this.tasks.removeTask(new EntityAIOpenDoor(this, true));
    this.tasks.removeTask(new EntityAIMobGirlMate(this, 1.2D));
    this.tasks.removeTask(new EntityAIBabyMobGirlFollowParent(this, 1.2D));
    this.tasks.removeTask(new EntityAIWatchClosest(this, EntityLivingBase.class, 8.0F));
    this.tasks.removeTask(new EntityAIWatchClosest2(this, EntityGolem.class, 3.0F, 1.0F));
    this.tasks.removeTask(new EntityAIWatchClosest2(this, EntityVillager.class, 3.0F, 1.0F));
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "wither_storm_head";
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public void addTrackingPlayer(EntityPlayerMP player) {}
  
  public void removeTrackingPlayer(EntityPlayerMP player) {}
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(20.0D);
  }
  
  public boolean attackEntityAsMob(Entity entityIn) {
    if (super.attackEntityAsMob(entityIn)) {
      List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, entityIn.getEntityBoundingBox().grow(3.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list1 != null && !list1.isEmpty())
          for (EntityLivingBase entity1 : list1) {
              if (!false)
                  super.attackEntityAsMob(entity1);
          }
      return true;
    } 
    return false;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.WITHER_STORM;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 32.0F + this.rand.nextFloat() * 32.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 64.0F + this.rand.nextFloat() * 24.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 16.0F + this.rand.nextFloat() * 16.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public void outOfWorld() {}
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public float getEyeHeight() {
    return 5.0F;
  }
  
  public boolean getAlwaysRenderNameTag() {
    return false;
  }
  
  public void setInWeb() {}
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void fall(float distance, float damageMultiplier) {
    playSound(ESound.witherStormFall, 10.0F, 1.0F);
  }
  
  protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    if (this.residentWitherStorm == null)
      super.updateFallState(y, onGroundIn, state, pos); 
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
  
  public boolean isEntityImmuneToDarkness() {
    return true;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    return "Wither Storm Head";
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (this.residentWitherStorm != null) {
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
        float f = 0.8F;
        float f1 = 0.16277136F / f * f * f;
        moveRelative(strafe, vertical, forward, 0.02F);
        f = 0.8F;
        move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        this.motionX *= f;
        this.motionY *= f;
        this.motionZ *= f;
      } 
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d1 = this.posX - this.prevPosX;
      double d0 = this.posZ - this.prevPosZ;
      float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
      if (f2 > 1.0F)
        f2 = 1.0F; 
      this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public boolean isOnLadder() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    this.openMouthCounter = 30;
    return ESound.witherStormRoar;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return ESound.witherStormHurt;
  }
  
  protected SoundEvent getDeathSound() {
    return ESound.witherStormHurt;
  }
  
  protected float getSoundVolume() {
    return (isSneaking() || this.residentWitherStorm == null) ? 1.0F : 100.0F;
  }
  
  protected float getSoundPitch() {
    return 1.0F;
  }
  
  public boolean canBePushed() {
    return false;
  }
  
  public boolean canBeCollidedWith() {
    return (!this.isDead && isEntityAlive());
  }
  
  protected void despawnEntity() {
    this.idleTime = 0;
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
  }
  
  public float getBrightness() {
    return 1.0F;
  }
  
  public void addPotionEffect(PotionEffect p_70690_1_) {
    if (this.residentWitherStorm == null || (this.residentWitherStorm != null && !this.residentWitherStorm.isEntityAlive()))
      super.addPotionEffect(p_70690_1_); 
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_WITHER_STORM_HEAD;
  }
  
  public void setDead() {
    super.setDead();
    if (this.residentWitherStorm != null) {
      if (this == this.residentWitherStorm.centerHead)
        this.residentWitherStorm.centerHead = null; 
      if (this == this.residentWitherStorm.rightHead)
        this.residentWitherStorm.rightHead = null; 
      if (this == this.residentWitherStorm.leftHead)
        this.residentWitherStorm.leftHead = null; 
    } 
  }
  
  public double getMountedYOffset() {
    return this.height * 0.75D;
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      double d8 = 6.0D;
      Vec3d vec3 = getLook(1.0F);
      double dx = vec3.x * d8;
      double dy = vec3.y * d8;
      double dz = vec3.z * d8;
      passenger.setPosition(this.posX + dx, this.posY + dy + getMountedYOffset(), this.posZ + dz);
    } 
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  public void onEntityUpdate() {
    int i = getAir();
    super.onEntityUpdate();
    if (isEntityAlive() && this.residentWitherStorm == null) {
      i--;
      setAir(i);
      if (getAir() == -10) {
        setAir(0);
        attackEntityFrom((new DamageSource("sever")).setDamageBypassesArmor().setDamageIsAbsolute(), 10.0F);
      } 
    } else {
      setAir(100);
    } 
  }
  
  public void onLivingUpdate() {
    if (this.residentWitherStorm != null && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      if (isEntityAlive()) {
        ChunkLoadingEvent.updateLoaded(this);
      } else {
        ChunkLoadingEvent.stopLoading(this);
      } 
      setOwnerId(this.residentWitherStorm.getOwnerId());
    } 
    if (this.residentWitherStorm != null && this.residentWitherStorm.isDead) {
      setDead();
      return;
    } 
    if (this.residentWitherStorm != null) {
      float rot = this.residentWitherStorm.rotationYawHead * 0.017453292F;
      float oned = MathHelper.sin(rot);
      float twod = MathHelper.cos(rot);
      if (this.residentWitherStorm.doesntContainACommandBlock()) {
        if (this.residentWitherStorm.centerHead != null && this == this.residentWitherStorm.centerHead)
          setLocationAndAngles(this.residentWitherStorm.posX + (oned * -7.0F), this.residentWitherStorm.posY, this.residentWitherStorm.posZ - (twod * -7.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.rightHead != null && this == this.residentWitherStorm.rightHead)
          setLocationAndAngles(this.residentWitherStorm.posX + (twod * -7.0F) + (oned * -7.0F), this.residentWitherStorm.posY, this.residentWitherStorm.posZ + (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.leftHead != null && this == this.residentWitherStorm.leftHead)
          setLocationAndAngles(this.residentWitherStorm.posX - (twod * -7.0F) + (oned * -7.0F), this.residentWitherStorm.posY, this.residentWitherStorm.posZ - (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F); 
      } else {
        if (this.residentWitherStorm.centerHead != null && this == this.residentWitherStorm.centerHead)
          setLocationAndAngles(this.residentWitherStorm.posX + (oned * -7.0F), this.residentWitherStorm.posY, this.residentWitherStorm.posZ - (twod * -7.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.rightHead != null && this == this.residentWitherStorm.rightHead)
          setLocationAndAngles(this.residentWitherStorm.posX + (twod * -20.0F) + (oned * -4.0F), this.residentWitherStorm.posY + 10.0D, this.residentWitherStorm.posZ + (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F); 
        if (this.residentWitherStorm.leftHead != null && this == this.residentWitherStorm.leftHead)
          setLocationAndAngles(this.residentWitherStorm.posX - (twod * -20.0F) + (oned * -4.0F), this.residentWitherStorm.posY + 10.0D, this.residentWitherStorm.posZ - (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F); 
      } 
    } 
    if (this.ticksExisted == 25)
      this.ticksExisted += 40 + this.rand.nextInt(120); 
    if (isBeingRidden() && getControllingPassenger() != null && getControllingPassenger() instanceof EntityPlayer) {
      EntityLivingBase passenger = (EntityLivingBase)getControllingPassenger();
      passenger.fallDistance *= 0.0F;
      passenger.hurtResistantTime = 40;
      passenger.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 40, 4));
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead = passenger.rotationYaw;
      this.rotationPitch = passenger.rotationPitch;
      setAttackTarget(null);
    } 
    if (this.residentWitherStorm != null) {
      this.onGround = false;
      this.isAirBorne = true;
    } 
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (getHealth() <= 0.0F) {
      this.residentWitherStorm = null;
      float f13 = (this.rand.nextFloat() - 0.5F) * 9.0F;
      float f15 = (this.rand.nextFloat() - 0.5F) * 9.0F;
      float f17 = (this.rand.nextFloat() - 0.5F) * 9.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f13, this.posY + 2.0D + f15, this.posZ + f17, 0.0D, 0.0D, 0.0D);
    } 
    if (this.residentWitherStorm != null && this.residentWitherStorm.hurtTime <= 0)
      this.residentWitherStorm.hurtTime = 10; 
    if (this.ticksExisted % 10 == 0 && this.residentWitherStorm != null)
      heal(1.0F); 
    if (isInWater() && this.residentWitherStorm == null)
      this.motionY += 0.25D; 
    this.openMouthCounter--;
    EntityLivingBase entity = getAttackTarget();
    if (!isInvisible() && entity != null)
      faceEntity(entity, 20.0F, 180.0F);
    if (entity != null && !false && entity.getHealth() > 0.0F && (!(entity instanceof EntityTameBase) || (entity instanceof EntityTameBase && !((EntityTameBase)entity).isABoss()))) {
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && entity.isNonBoss())
        entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 2147483647, 1)); 
      double d01 = this.posX - entity.posX;
      double d11 = this.posY + getEyeHeight() - entity.posY;
      double d21 = this.posZ - entity.posZ;
      float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
      if (entity instanceof net.minecraft.entity.monster.EntityEnderman || entity instanceof EntityEnderman) {
        ((EntityLiving)entity).setAttackTarget((this.residentWitherStorm != null) ? (EntityLivingBase)this.residentWitherStorm : (EntityLivingBase)this);
        if (this.rand.nextInt(500) == 0) {
          if (entity instanceof net.minecraft.entity.monster.EntityEnderman && ((net.minecraft.entity.monster.EntityEnderman)entity).getHeldBlockState() == null) {
            attackEntityFrom(DamageSource.causeMobDamage(entity), 2500.0F);
            ((net.minecraft.entity.monster.EntityEnderman)entity).setHeldBlockState(Blocks.OBSIDIAN.getDefaultState());
          } 
          if (entity instanceof net.minecraft.entity.monster.EntityEnderman && ((net.minecraft.entity.monster.EntityEnderman)entity).getHeldBlockState() == null) {
            attackEntityFrom(DamageSource.causeMobDamage(entity), 2500.0F);
            ((net.minecraft.entity.monster.EntityEnderman)entity).setHeldBlockState(Blocks.OBSIDIAN.getDefaultState());
          } 
        } 
      } else if (entity instanceof net.minecraft.entity.boss.EntityDragon && entity.getHealth() <= 1.0F) {
        ((EntityLiving)entity).spawnExplosionParticle();
      } else if (entity instanceof EntityTameBase && (entity instanceof EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar || !entity.isNonBoss() || ((EntityTameBase)entity).isHero())) {
        ((EntityLiving)entity).setAttackTarget(this);
      } else {
        if (entity instanceof EntityLiving && !entity.isNonBoss())
          ((EntityLiving)entity).setAttackTarget(this);
        entity.motionX = d01 / f2 * 0.5D * 0.5D + entity.motionX * 0.5D;
        entity.motionY = d11 / f2 * 0.5D * 0.5D + entity.motionY * 0.5D;
        entity.motionZ = d21 / f2 * 0.5D * 0.5D + entity.motionZ * 0.5D;
      } 
      int in = 1;
      if (this.residentWitherStorm != null) {
        if (this.residentWitherStorm.getPhase() == EnumWitherStormPhase.Devourer)
          in *= 2; 
        if (this.residentWitherStorm.getPhase() == EnumWitherStormPhase.ThunderStorm)
          in *= 4; 
      } 
      for (int i1 = 0; i1 < 1500 * in; i1++) {
        int i11 = MathHelper.floor(entity.posY) + MathHelper.getInt(this.rand, 2, 3 * in) * MathHelper.getInt(this.rand, -1, 1);
        int l1 = MathHelper.floor(entity.posX) + MathHelper.getInt(this.rand, 2, 3 * in) * MathHelper.getInt(this.rand, -1, 1);
        int i2 = MathHelper.floor(entity.posZ) + MathHelper.getInt(this.rand, 2, 3 * in) * MathHelper.getInt(this.rand, -1, 1);
        BlockPos blockpos = new BlockPos(l1, i11, i2);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        if (this.residentWitherStorm != null && getHealth() <= 0.0F && this.world.canBlockSeeSky(blockpos) && !block.isAir(iblockstate, this.world, blockpos) && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.world.isAreaLoaded(blockpos, blockpos) && block.getBlockHardness(iblockstate, this.world, new BlockPos(l1, i11, i2)) != -1.0F)
          if (EngenderConfig.mobs.grief)
            if (block.getMaterial(iblockstate).isLiquid()) {
              this.world.setBlockToAir(new BlockPos(l1, i11, i2));
            } else {
              this.world.spawnEntity(new EntityFallingBlock(this.world, l1, i11, i2, block.getDefaultState()));
            }   
      } 
      if (getDistanceSq(entity) < 512.0D) {
        this.openMouthCounter = 5;
        if ((this.ticksExisted + getEntityId()) % 10 == 0)
          attackEntityAsMob(entity);
      } 
      List<EntityLiving> list1111 = this.world.getEntitiesWithinAABB(EntityLiving.class, entity.getEntityBoundingBox().grow((this.residentWitherStorm != null) ? (this.residentWitherStorm.getSize() / 25000.0D + 4.0D) : 4.0D), Predicates.and(EntitySelectors.IS_ALIVE));
      if (list1111 != null && !list1111.isEmpty())
          for (EntityLiving entity1 : list1111) {
              if (entity1 != null && entity1.isEntityAlive() && (!false || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer)) {
                  double d011 = entity.posX - entity1.posX;
                  double d111 = entity.posY - entity1.posY;
                  double d211 = entity.posZ - entity1.posZ;
                  float f21 = MathHelper.sqrt(d011 * d011 + d111 * d111 + d211 * d211);
                  if (entity1 instanceof EntityEnderman || (entity instanceof EntityTameBase && (entity instanceof EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityChagaroth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntitySacthoth || entity instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar || !entity.isNonBoss() || ((EntityTameBase) entity).isHero()))) {
                      entity1.setAttackTarget(this);
                      if (this.rand.nextInt(500) == 0 && (entity1 instanceof net.minecraft.entity.monster.EntityEnderman || entity1 instanceof EntityEnderman)) {
                          attackEntityFrom(DamageSource.causeMobDamage(entity1), 500.0F);
                          if (entity1 instanceof net.minecraft.entity.monster.EntityEnderman && ((net.minecraft.entity.monster.EntityEnderman) entity1).getHeldBlockState() == null)
                              ((net.minecraft.entity.monster.EntityEnderman) entity1).setHeldBlockState(Blocks.OBSIDIAN.getDefaultState());
                      }
                  } else {
                      entity1.motionX = d01 / f2 * 0.75D * 0.75D + entity1.motionX * 0.5D;
                      entity1.motionY = d11 / f2 * 0.75D * 0.75D + entity1.motionY * 0.5D;
                      entity1.motionZ = d21 / f2 * 0.75D * 0.75D + entity1.motionZ * 0.5D;
                  }
              }
          }
    } 
    List<EntityLiving> list11111 = this.world.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list11111 != null && !list11111.isEmpty())
        for (EntityLiving entity1 : list11111) {
            if (this.residentWitherStorm == null && entity1 != null && entity1.isEntityAlive() && (!false || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !(entity instanceof EntityEnderman) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer))
                attackEntityAsMob(entity1);
            if (this.residentWitherStorm != null && entity1 != null && getDistance(entity1) <= 10.0D && entity1.isEntityAlive() && (!false || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !(entity instanceof EntityEnderman) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer)) {
                if (!isWild() && EngenderConfig.general.useMessage)
                    getOwner().sendMessage(new TextComponentTranslation(entity1.getName() + " was eaten by The Wither Storm (" + getOwner().getName() + ")", new Object[0]));
                this.world.setEntityState(entity1, (byte) 3);
                if (!entity1.isNonBoss()) {
                    entity1.setHealth(0.0F);
                    entity1.motionY++;
                    entity1.motionY++;
                    entity1.motionY++;
                } else {
                    entity1.setDead();
                }
                this.residentWitherStorm.Grow(this.residentWitherStorm.getSize() + 1 + (int) entity1.getMaxHealth() + (int) entity1.height * (int) entity1.height + (int) entity1.width * (int) entity1.width);
                this.residentWitherStorm.heal((1 + (int) entity1.getMaxHealth() + (int) entity1.height * (int) entity1.height + (int) entity1.width * (int) entity1.width));
                this.openMouthCounter = 2;
            }
        }
    super.onLivingUpdate();
  }
  
  private void launchWitherSkullToEntity(EntityLivingBase p_82216_2_) {
    this.world.playEvent(null, 1024, new BlockPos(this), 0);
    double d1 = 2.0D;
    Vec3d vec3 = getLook(1.0F);
    double d2 = p_82216_2_.posX - this.posX + vec3.x;
    double d3 = p_82216_2_.posY - p_82216_2_.motionY - this.posY + 2.0D;
    double d4 = p_82216_2_.posZ - this.posZ + vec3.z;
    playSound(SoundEvents.ENTITY_WITHER_SHOOT, 10.0F, 0.8F);
    playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 10.0F, (1.0F + (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.2F) * 0.7F);
    EntityWitherStormSkull entitylargefireball = new EntityWitherStormSkull(this.world, this, d2, d3, d4);
    entitylargefireball.posX = this.posX + vec3.x * d1;
    entitylargefireball.posY = this.posY + 2.0D;
    entitylargefireball.posZ = this.posZ + vec3.z * d1;
    this.world.spawnEntity(entitylargefireball);
    this.openMouthCounter = 5;
    float dm = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    entitylargefireball.damage = dm;
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
    if (!isInvisible() && !false)
      launchWitherSkullToEntity(p_82196_1_); 
  }
  
  public boolean processInteract(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return (this.residentWitherStorm != null) ? this.residentWitherStorm.processInitialInteract(player, hand) : false;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.getDamageType() == "chaosImplosion" || source.getDamageType() == "de.GuardianFireball" || source.getDamageType() == "de.GuardianEnergyBall" || source.getDamageType() == "de.GuardianChaosBall")
      amount *= 0.2F; 
    if (isEntityInvulnerable(source))
      return false; 
    if (source.getTrueSource() instanceof EntityWitherStormSkull)
      return false; 
    if (this.residentWitherStorm != null && this.residentWitherStorm.isEntityAlive())
      this.residentWitherStorm.attackEntityFrom(source, amount * 0.3F); 
    return super.attackEntityFrom(source, amount);
  }
  
  public EnumPushReaction getPushReaction() {
    return EnumPushReaction.IGNORE;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
}

