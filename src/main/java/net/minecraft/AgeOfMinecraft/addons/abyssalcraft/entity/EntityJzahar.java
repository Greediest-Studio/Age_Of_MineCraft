package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.shinoow.abyssalcraft.api.entity.EntityUtil;
import com.shinoow.abyssalcraft.common.entity.EntityGatekeeperEssence;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import com.shinoow.abyssalcraft.lib.world.TeleporterDarkRealm;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.util.AttributeCompat;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityJzahar extends EntityTameBase implements IRangedAttackMob, Armored, Massive, Undead {
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 10.0D, 0);
  
  private EntityAIAttackRangedAlly aiArrowAttack = new EntityAIAttackRangedAlly(this, 0.8D, 60, 64.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  private int talkTimer;
  
  private int shoutTicks;
  
  private int iframes;
  
  private static final DataParameter<Integer> EARTHQUAKETIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> BLACKHOLETIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> IMPLOSIONTIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> SHOUTTIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> COOLDOWNTIMER = EntityDataManager.createKey(EntityJzahar.class, DataSerializers.VARINT);
  
  private boolean doShout;
  
  double speed;
  
  public EntityJzahar(World par1World) {
    super(par1World);
    this.speed = 0.05D;
    setSize(1.5F, 6.0F);
    this.tasks.addTask(0, new EntityAISwimming(this));
    this.tasks.addTask(1, new EntityAIFollowLeader(this, 1.0D, 64.0F, 16.0F));
    this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 0.6D));
    this.tasks.addTask(5, new EntityAIWander(this, 0.6D));
    this.tasks.addTask(6, new EntityAILookIdle(this));
    this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    this.isOffensive = true;
    setLevel(300);
  }
  
  public int playMusic() {
    return 8;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setDarkenSky(true);
    if (getHealth() > getMaxHealth() * 0.75D && this.bossInfo.getColor() != BossInfo.Color.BLUE)
      this.bossInfo.setColor(BossInfo.Color.BLUE); 
    if (getHealth() < getMaxHealth() * 0.75D && getHealth() > getMaxHealth() / 2.0F && this.bossInfo.getColor() != BossInfo.Color.GREEN)
      this.bossInfo.setColor(BossInfo.Color.GREEN); 
    if (getHealth() < getMaxHealth() / 2.0F && getHealth() > getMaxHealth() / 4.0F && this.bossInfo.getColor() != BossInfo.Color.YELLOW)
      this.bossInfo.setColor(BossInfo.Color.YELLOW); 
    if (getHealth() < getMaxHealth() / 4.0F && getHealth() > 0.0F && this.bossInfo.getColor() != BossInfo.Color.RED)
      this.bossInfo.setColor(BossInfo.Color.RED); 
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public String getName() {
    return TextFormatting.BLUE + super.getName() + TextFormatting.WHITE;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public float getBonusVSLight() {
    return 1.25F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSFlying() {
    return 2.0F;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(100.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(500.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(50.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(EARTHQUAKETIMER, 0);
    this.getDataManager().register(BLACKHOLETIMER, 0);
    this.getDataManager().register(IMPLOSIONTIMER, 0);
    this.getDataManager().register(SHOUTTIMER, 0);
    this.getDataManager().register(COOLDOWNTIMER, 0);
  }
  
  public int getTimer(int timer) {
    switch (timer) {
      case 0:
        return this.getDataManager().get(EARTHQUAKETIMER);
      case 1:
        return this.getDataManager().get(BLACKHOLETIMER);
      case 2:
        return this.getDataManager().get(IMPLOSIONTIMER);
      case 3:
        return this.getDataManager().get(SHOUTTIMER);
      case 4:
        return this.getDataManager().get(COOLDOWNTIMER);
    } 
    return 0;
  }
  
  public void setTimer(int timer, int value) {
    switch (timer) {
      case 0:
        this.getDataManager().set(EARTHQUAKETIMER, value);
        break;
      case 1:
        this.getDataManager().set(BLACKHOLETIMER, value);
        break;
      case 2:
        this.getDataManager().set(IMPLOSIONTIMER, value);
        break;
      case 3:
        this.getDataManager().set(SHOUTTIMER, value);
        break;
      case 4:
        this.getDataManager().set(COOLDOWNTIMER, value);
        break;
    } 
  }
  
  public void decrementTimer(int timer) {
    setTimer(timer, getTimer(timer) - 1);
  }
  
  public float getDefaultStrengthStat() {
    return 100.0F;
  }
  
  public float getDefaultStaminaStat() {
    return 100.0F;
  }
  
  public float getDefaultIntelligenceStat() {
    return 100.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 100.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 100.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_BLAZE_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_ENDERDRAGON_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WITHER_DEATH;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.5F : 5.0F;
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && this.isOffensive && !isChild() && !false && getDistanceSq(getAttackTarget()) < (this.width * this.width + (getAttackTarget()).width * (getAttackTarget()).width) + 36.0D && (this.ticksExisted + getEntityId()) % 10 == 0)
      attackEntityAsMob(getAttackTarget());
    if (this.ticksExisted % 5 == 0)
      heal(2.0F); 
  }
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 3);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        player.startRiding(this);
      return true;
    } 
    return false;
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (i == 2)
        passenger.setPosition(this.posX - (f4 * 1.5F), this.posY + (4.0F * getFittness()) + (MathHelper.cos(this.ticksExisted * 0.3F) * 0.25F), this.posZ - (f11 * 1.5F)); 
      if (i == 1)
        passenger.setPosition(this.posX + (f4 * 1.5F), this.posY + (4.0F * getFittness()) + (MathHelper.cos(this.ticksExisted * 0.3F) * 0.25F), this.posZ + (f11 * 1.5F)); 
      if (i == 0)
        passenger.setPosition(this.posX + (f11 * 0.25F), this.posY + (5.25F * getFittness()), this.posZ - (f4 * 0.25F)); 
    } 
  }
  
  public void travel(float strafe, float vertical, float forward) {
    if (isBeingRidden()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
      this.prevRotationYaw = this.rotationYaw = entitylivingbase.rotationYaw;
      this.rotationPitch = entitylivingbase.rotationPitch;
      setRotation(this.rotationYaw, this.rotationPitch);
      this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
      strafe = entitylivingbase.moveStrafing;
      forward = entitylivingbase.moveForward;
      if (isInWater() || isInLava())
        this.motionY += 0.05D; 
      if (canPassengerSteer()) {
        setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * ((isInWater() || isInLava()) ? 5.0F : 1.0F));
        super.travel(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
      if (entitylivingbase.moveForward > 0.0F && this.ticksExisted % 7 == 0)
        playStepSound(new BlockPos(this), this.world.getBlockState(new BlockPos(this)).getBlock());
      this.prevLimbSwingAmount = this.limbSwingAmount;
      double d5 = this.posX - this.prevPosX;
      double d7 = this.posZ - this.prevPosZ;
      float f10 = MathHelper.sqrt(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
      this.limbSwing += this.limbSwingAmount;
    } else {
      super.travel(strafe, vertical, forward);
    } 
  }
  
  public void addPotionEffect(PotionEffect potioneffectIn) {
    if (!potioneffectIn.getPotion().isBadEffect())
      super.addPotionEffect(potioneffectIn); 
  }
  
  public boolean takesFallDamage() {
    return false;
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
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.5F * (float)(Math.max(ACConfig.damageAmpl, 1.0D)));
    return flag;
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.isFireDamage())
      return false; 
    if (source.isExplosion())
      return false; 
    if (source.isMagicDamage())
      return false; 
    if (source.getDamageType() == "chaosImplosion" || source.getDamageType() == "de.GuardianFireball" || source.getDamageType() == "de.GuardianEnergyBall" || source.getDamageType() == "de.GuardianChaosBall") {
      amount *= 0.25F;
    } else {
      if (this.iframes > 0)
        return false; 
      this.iframes = 10;
    } 
    return super.attackEntityFrom(source, amount);
  }
  
  public boolean isEntityUndead() {
    return true;
  }
  
  private double getHeadX(int par1) {
    if (par1 <= 0)
      return this.posX; 
    float f = (this.renderYawOffset + (180 * (par1 - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.3D;
  }
  
  private double getHeadY(int par1) {
    return (par1 <= 0) ? (this.posY + 3.75D) : (this.posY + 2.2D);
  }
  
  private double getHeadZ(int par1) {
    if (par1 <= 0)
      return this.posZ; 
    float f = (this.renderYawOffset + (180 * (par1 - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.3D;
  }
  
  public float getEyeHeight() {
    return this.height * 0.9F;
  }
  
  public void onLivingUpdate() {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (isEntityAlive()) {
        ChunkLoadingEvent.updateLoaded(this);
      } else {
        ChunkLoadingEvent.stopLoading(this);
      }  
    if (this.posY <= 0.0D) {
      setPosition(this.posX, 0.01D, this.posZ);
      this.onGround = true;
      this.isAirBorne = false;
    } 
    if (this.motionY > 0.42D)
      this.motionY = 0.42D; 
    this.isOffensive = true;
    if (this.talkTimer > 0)
      this.talkTimer--; 
    if (this.iframes > 0)
      this.iframes--; 
    float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
    float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
    float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(100.0D));
    if (list != null && this.ticksExisted > 20)
      for (int k2 = 0; k2 < list.size(); k2++) {
        Entity entity = list.get(k2);
        if (entity instanceof EntityLivingBase && entity.isEntityAlive())
          if (entity instanceof net.minecraft.entity.boss.EntityDragon || entity instanceof net.minecraft.entity.boss.EntityWither) {
            if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
              AttributeCompat.setBaseValue((EntityLivingBase)entity, SharedMonsterAttributes.MAX_HEALTH, 1.0D);
              ((EntityLivingBase)entity).setHealth(1.0F);
              attackEntityAsMob(entity);
              entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);
              if (!entity.isEntityAlive()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.world, this);
                entitywitherskull.copyLocationAndAnglesFrom(entity);
                entitywitherskull.posY++;
                this.world.spawnEntity(entitywitherskull);
                entitywitherskull.startRiding(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was super-banished by " + getName() + " (" + getOwner().getName() + ")", new Object[0]));
                list.remove(entity);
                SpecialTextUtil.JzaharGroup(this.world, !isWild() ? I18n.translateToLocal("message.jzaharhelpful.banish.vanilla") : I18n.translateToLocal("message.jzahar.banish.vanilla"));
              } 
            } else if (ACConfig.particleEntity) {
              this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
            } 
          } else if (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityDragonBoss || entity instanceof com.shinoow.abyssalcraft.common.entity.EntitySacthoth || entity instanceof com.shinoow.abyssalcraft.common.entity.EntityChagaroth) {
            if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
              AttributeCompat.setBaseValue((EntityLivingBase)entity, SharedMonsterAttributes.MAX_HEALTH, 1.0D);
              ((EntityLivingBase)entity).setHealth(1.0F);
              attackEntityAsMob(entity);
              entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);
              if (!entity.isEntityAlive()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.world, this);
                entitywitherskull.copyLocationAndAnglesFrom(entity);
                entitywitherskull.posY++;
                this.world.spawnEntity(entitywitherskull);
                entitywitherskull.startRiding(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was super-banished by " + getName() + " (" + getOwner().getName() + ")", new Object[0]));
                SpecialTextUtil.JzaharGroup(this.world, !isWild() ? I18n.translateToLocal("message.jzaharhelpful.banish.ac") : I18n.translateToLocal("message.jzahar.banish.ac"));
              } 
            } else if (ACConfig.particleEntity) {
              this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
            } 
          } else if (entity instanceof com.shinoow.abyssalcraft.common.entity.EntityJzahar) {
            if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
              AttributeCompat.setBaseValue((EntityLivingBase)entity, SharedMonsterAttributes.MAX_HEALTH, 1.0D);
              ((EntityLivingBase)entity).setHealth(1.0F);
              attackEntityAsMob(entity);
              entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);
              if (!entity.isEntityAlive()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.world, this);
                entitywitherskull.copyLocationAndAnglesFrom(entity);
                entitywitherskull.posY++;
                this.world.spawnEntity(entitywitherskull);
                entitywitherskull.startRiding(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was super-banished by " + getName() + " (" + getOwner().getName() + ")", new Object[0]));
                SpecialTextUtil.JzaharGroup(this.world, !isWild() ? I18n.translateToLocal("message.jzaharhelpful.banish.jzh") : I18n.translateToLocal("message.jzahar.banish.jzh"));
              } 
            } else if (ACConfig.particleEntity) {
              this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
            } 
          } else if (!entity.isNonBoss()) {
            if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
              AttributeCompat.setBaseValue((EntityLivingBase)entity, SharedMonsterAttributes.MAX_HEALTH, 1.0D);
              ((EntityLivingBase)entity).setHealth(1.0F);
              attackEntityAsMob(entity);
              entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, 1.0F);
              if (Loader.isModLoaded("draconicevolution") && entity instanceof com.brandon3055.draconicevolution.entity.EntityGuardianCrystal)
                entity.setDead(); 
              if (!entity.isEntityAlive()) {
                EntityBlackHole entitywitherskull = new EntityBlackHole(this.world, this);
                entitywitherskull.copyLocationAndAnglesFrom(entity);
                entitywitherskull.posY++;
                this.world.spawnEntity(entitywitherskull);
                entitywitherskull.startRiding(entity);
                if (EngenderConfig.general.useMessage && !isWild())
                  getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was super-banished by " + getName() + " (" + getOwner().getName() + ")", new Object[0]));
                SpecialTextUtil.JzaharGroup(this.world, !isWild() ? I18n.translateToLocal("message.jzaharhelpful.banish.other") : I18n.translateToLocal("message.jzahar.banish.other"));
              } 
            } else if (ACConfig.particleEntity) {
              this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, entity.posX + f, entity.posY + 2.0D + f1, entity.posZ + f2, 0.0D, 0.0D, 0.0D);
            } 
          } else if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode && this.talkTimer == 0 && getDistanceSq(entity) <= 8.0D) {
            this.talkTimer = 1200;
            if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
              if (EntityUtil.isPlayerCoralium((EntityPlayer)entity)) {
                SpecialTextUtil.JzaharText("Hello, old friend.");
              } else if (false) {
                SpecialTextUtil.JzaharText(String.format(I18n.translateToLocal("message.jzaharhelpful.creative.1"), entity.getName()));
                SpecialTextUtil.JzaharText(I18n.translateToLocal("message.jzaharhelpful.creative.2"));
              } else {
                SpecialTextUtil.JzaharText(String.format(I18n.translateToLocal("message.jzahar.creative.1"), entity.getName()));
                SpecialTextUtil.JzaharText(I18n.translateToLocal("message.jzahar.creative.2"));
              }  
          }  
      }  
    if (getAttackTarget() != null)
      if (getDistanceSq(getAttackTarget()) > 200.0D || getAttackTarget() instanceof net.minecraft.entity.EntityFlying || (getAttackTarget()).posY > this.posY + 4.0D) {
        this.tasks.addTask(2, this.aiArrowAttack);
        this.tasks.removeTask(this.aiAttackOnCollide);
      } else {
        this.tasks.addTask(2, this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
      }  
    super.onLivingUpdate();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen(this) && this.rand.nextInt(100) == 0) {
      double d1 = getHeadX(1);
      double d2 = getHeadY(1);
      double d3 = getHeadZ(1);
      fireLightning(getAttackTarget(), d1, d2, d3);
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && isEntityAlive() && getAttackTarget() != null && getAttackTarget().isEntityAlive() && getAttackTarget().canEntityBeSeen(this) && this.rand.nextInt(100) == 0) {
      double d1 = getHeadX(2);
      double d2 = getHeadY(2);
      double d3 = getHeadZ(2);
      fireLightning(getAttackTarget(), d1, d2, d3);
    } 
    if (this.deathTicks == 0) {
      decrementTimer(0);
      decrementTimer(1);
      decrementTimer(2);
      decrementTimer(3);
      decrementTimer(4);
      if (getTimer(0) > 600)
        for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(64.0D))) {
          if (entity.onGround && entity instanceof EntityLivingBase && !false) {
            entity.motionX += (float)(Math.random() * 0.1D - 0.05D);
            entity.motionY += (float)(Math.random() * 0.1D - 0.05D);
            entity.motionZ += (float)(Math.random() * 0.1D - 0.05D);
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 20, 3));
            if (this.rand.nextInt(5) == 0)
              ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 20, 3)); 
            entity.rotationPitch += (float)(Math.random() * 4.0D - 2.0D);
            entity.rotationYaw += (float)(Math.random() * 4.0D - 2.0D);
            if (this.rand.nextInt(5) == 0) {
              entity.motionY = 0.5D;
              if (entity instanceof EntityLivingBase)
                inflictEngenderMobDamage((EntityLivingBase)entity, " was shaken to bits by ", DamageSource.HOT_FLOOR, 1.0F); 
            } 
          } 
        }  
      if (getTimer(3) < 0 && this.rand.nextInt(20) == 0 && getAttackTarget() != null && getDistanceSq(getAttackTarget()) <= 9216.0D && !this.doShout && getTimer(4) < 0) {
        playSound(ACSounds.jzahar_shout, 5.0F, 1.0F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          SpecialTextUtil.JzaharGroup(this.world, "Uftoin...");
        this.shoutTicks = getTimer(3) - 30;
        this.doShout = true;
        setTimer(4, 100);
      } 
      if (getTimer(3) < this.shoutTicks && this.doShout) {
        this.doShout = false;
        this.world.setEntityState(this, (byte)23);
        setTimer(3, 400);
        playSound(ACSounds.jzahar_blast, 5.0F, 1.0F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          SpecialTextUtil.JzaharGroup(this.world, "...mglagln!");
        if (isEntityAlive()) {
          double size = 64.0D;
          Vec3d vector = getLookVec();
          for (Entity entity : this.world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(size).offset(vector.x * 32.0D, vector.y * 32.0D, vector.z * 32.0D))) {
            if (entity instanceof EntityLivingBase && !false) {
              double dx = vector.x;
              double dy = vector.z;
              double dz = vector.z;
              double spread = 10.0D;
              double velocity = 2.0D + getRNG().nextDouble() * 8.0D;
              dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
              dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
              dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
              dx *= velocity;
              dy *= velocity * 0.25D;
              dz *= velocity;
              entity.addVelocity(dx, dy, dz);
              if (entity instanceof EntityLivingBase)
                inflictEngenderMobDamage((EntityLivingBase)entity, " was shouted apart by ", DamageSource.FLY_INTO_WALL, 2.0F * (float)velocity); 
            } 
          } 
        } 
      } 
      if (getTimer(0) < 0 && this.rand.nextInt(400) == 0 && getAttackTarget() != null && (getAttackTarget()).onGround && getTimer(4) < 0) {
        swingArm(EnumHand.MAIN_HAND);
        setTimer(0, 1000);
        playSound(ACSounds.jzahar_earthquake, 5.0F, 1.0F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          SpecialTextUtil.JzaharGroup(this.world, "Shugnah throd!");
        setTimer(4, 100);
      } 
      if (getTimer(2) < 0 && getAttackTarget() != null && getTimer(4) < 0) {
        swingArm(EnumHand.MAIN_HAND);
        setTimer(2, 1200);
        playSound(ACSounds.jzahar_implosion, 5.0F, 1.0F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          SpecialTextUtil.JzaharGroup(this.world, "Nilgh'ri mtli!");
        EntityImplosion entitywitherskull = new EntityImplosion(this.world, this);
        BlockPos targetpos = getAttackTarget().getPosition();
        entitywitherskull.setPosition((targetpos.getX() + this.rand.nextInt(10) * (this.rand.nextBoolean() ? 1 : -1)), (targetpos.getY() + 2), (targetpos.getZ() + this.rand.nextInt(10) * (this.rand.nextBoolean() ? 1 : -1)));
        this.world.playEvent(3000, entitywitherskull.getPosition(), 0);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitywitherskull); 
        setTimer(4, 100);
      } 
      if (getTimer(1) < 0 && this.rand.nextInt(800) == 0 && getAttackTarget() != null && getTimer(4) < 0 && !ACConfig.no_black_holes) {
        swingArm(EnumHand.MAIN_HAND);
        setTimer(1, 1600);
        playSound(ACSounds.jzahar_black_hole, 5.0F, 1.0F);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          SpecialTextUtil.JzaharGroup(this.world, "Ph'nilgh'ri n'ghft!");
        EntityBlackHole entitywitherskull = new EntityBlackHole(this.world, this);
        entitywitherskull.copyLocationAndAnglesFrom(getAttackTarget());
        BlockPos targetpos = getAttackTarget().getPosition();
        entitywitherskull.setPosition((targetpos.getX() + (5 + this.rand.nextInt(10)) * (this.rand.nextBoolean() ? 1 : -1)), (targetpos.getY() + 2), (targetpos.getZ() + (5 + this.rand.nextInt(10)) * (this.rand.nextBoolean() ? 1 : -1)));
        this.world.playEvent(3000, entitywitherskull.getPosition(), 0);
        if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
          this.world.spawnEntity(entitywitherskull); 
        setTimer(4, 100);
      } 
    } 
  }
  
  protected void addShoutParticles() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      Vec3d vector = getLookVec();
      double px = this.posX + vector.x * 5.0D;
      double py = this.posY + getEyeHeight() + vector.y * 5.0D;
      double pz = this.posZ + vector.z * 5.0D;
      for (int i = 0; i < 2000; i++) {
        double dx = vector.x;
        double dy = vector.y;
        double dz = vector.z;
        double spread = 10.0D;
        double velocity = 2.0D + getRNG().nextDouble() * 18.0D;
        dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, true, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz);
      } 
    } else {
      this.world.setEntityState(this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 23) {
      addShoutParticles();
    } else {
      super.handleStatusUpdate(id);
    } 
  }
  
  public void writeEntityToNBT(NBTTagCompound nbttag) {
    super.writeEntityToNBT(nbttag);
    if (this.deathTicks > 0)
      nbttag.setInteger("DeathTicks", this.deathTicks); 
    nbttag.setInteger("EarthquakeTime", getTimer(0));
    nbttag.setInteger("BlackHoleTime", getTimer(1));
    nbttag.setInteger("ImplosionTime", getTimer(2));
    nbttag.setInteger("ShoutTime", getTimer(3));
    nbttag.setInteger("CooldownTime", getTimer(4));
  }
  
  public void readEntityFromNBT(NBTTagCompound nbttag) {
    super.readEntityFromNBT(nbttag);
    this.deathTicks = nbttag.getInteger("DeathTicks");
    setTimer(0, nbttag.getInteger("EarthquakeTime"));
    setTimer(1, nbttag.getInteger("BlackHoleTime"));
    setTimer(2, nbttag.getInteger("ImplosionTime"));
    setTimer(3, nbttag.getInteger("ShoutTime"));
    setTimer(4, nbttag.getInteger("CooldownTime"));
  }
  
  protected void onDeathUpdate() {
    this.motionX = this.motionY = this.motionZ = 0.0D;
    this.deathTicks++;
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (this.deathTicks == 1) {
        List<Entity> list = net.minecraft.AgeOfMinecraft.util.EntityCompat.loadedEntityList(this.world);
        if (list != null)
            for (Entity entity : list) {
                if (entity instanceof EntityJzahar && entity.isEntityAlive())
                    SpecialTextUtil.JzaharGroup(this.world, false ? I18n.translateToLocal("message.jzaharhelpful.snidecomment.jzahar") : I18n.translateToLocal("message.jzahar.snidecomment.jzahar"));
            }
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world)) {
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
            entityplayer.sendStatusMessage(new TextComponentTranslation("§4" + getOwner().getName() + "'s Jz'ahar has been destroyed!!!", new Object[0]), true);
          } 
          getOwner().sendMessage(new TextComponentTranslation("Your Jz'ahar has been destroyed!", new Object[0]));
        } 
      }  
    if (this.deathTicks <= 800) {
      if (this.deathTicks == 410)
        for (int a = 1; a < 10; a++)
          playSound(ACSounds.jzahar_charge, a, 1.0F);  
      if (this.deathTicks < 400)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 2.5D, this.posZ, 0.0D, 0.0D, 0.0D);
      float f = (this.rand.nextFloat() - 0.5F) * 3.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 3.0F;
      if (this.deathTicks >= 100 && this.deathTicks < 400)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
      if (this.deathTicks >= 200 && this.deathTicks < 400) {
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + f, this.posY + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
        this.world.spawnParticle(EnumParticleTypes.LAVA, this.posX, this.posY + 2.5D, this.posZ, 0.0D, 0.0D, 0.0D, 0);
      } 
      this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.0D, 0.0D);
      if (this.deathTicks >= 790 && this.deathTicks <= 800) {
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY + 1.5D, this.posZ, 0.0D, 0.0D, 0.0D);
        playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 4.0F, (1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F) * 0.7F);
      } 
      if (this.deathTicks > 400 && this.deathTicks < 800) {
        float size = 64.0F;
        List<Entity> list = this.world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(size, size, size));
        for (Entity entity : list) {
          double scale = (size - entity.getDistance(this.posX, this.posY, this.posZ)) / size;
          Vec3d dir = new Vec3d(entity.posX - this.posX, entity.posY - this.posY, entity.posZ - this.posZ);
          dir = dir.normalize();
          if (entity instanceof EntityLivingBase && false) {
            entity.addVelocity(dir.x * this.speed * scale, dir.y * this.speed * scale, dir.z * this.speed * scale);
            continue;
          } 
          entity.addVelocity(dir.x * -this.speed * scale, dir.y * -this.speed * scale, dir.z * -this.speed * scale);
        } 
        this.speed += 0.002D;
      } 
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && 
      this.deathTicks > 750 && this.deathTicks % 5 == 0) {
      int i = 500;
      while (i > 0) {
        int j = EntityXPOrb.getXPSplit(i);
        i -= j;
        this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
      } 
    } 
    if (this.deathTicks == 790 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      List<BlockPos> blocks = new ArrayList<>();
      for (int x = 0; x < 10; x++) {
        for (int y = 0; y < 10; y++) {
          for (int z = 0; z < 10; z++) {
            if (!this.world.isAirBlock(new BlockPos(this.posX + x, this.posY + y, this.posZ + z)))
              blocks.add(new BlockPos(this.posX + x, this.posY + y, this.posZ + z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX - x, this.posY + y, this.posZ + z)))
              blocks.add(new BlockPos(this.posX - x, this.posY + y, this.posZ + z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX + x, this.posY + y, this.posZ - z)))
              blocks.add(new BlockPos(this.posX + x, this.posY + y, this.posZ - z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX - x, this.posY + y, this.posZ - z)))
              blocks.add(new BlockPos(this.posX - x, this.posY + y, this.posZ - z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX + x, this.posY - y, this.posZ + z)))
              blocks.add(new BlockPos(this.posX + x, this.posY - y, this.posZ + z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX - x, this.posY - y, this.posZ + z)))
              blocks.add(new BlockPos(this.posX - x, this.posY - y, this.posZ + z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX + x, this.posY - y, this.posZ - z)))
              blocks.add(new BlockPos(this.posX + x, this.posY - y, this.posZ - z)); 
            if (!this.world.isAirBlock(new BlockPos(this.posX - x, this.posY - y, this.posZ - z)))
              blocks.add(new BlockPos(this.posX - x, this.posY - y, this.posZ - z)); 
          } 
        } 
      } 
      for (BlockPos pos : blocks) {
        if (this.world.getBlockState(pos).getBlock() != Blocks.BEDROCK)
          this.world.setBlockToAir(pos); 
      } 
      if (!this.world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(3.0D, 1.0D, 3.0D)).isEmpty()) {
        List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(3.0D, 1.0D, 3.0D));
        for (Entity entity : entities) {
          if (entity instanceof EntityPlayer && !false) {
            EntityPlayer player = (EntityPlayer)entity;
            player.setHealth(1.0F);
            player.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.HUNGER, 2400, 5));
            player.addPotionEffect(new PotionEffect(MobEffects.POISON, 2400, 5));
            if (player instanceof EntityPlayerMP) {
              WorldServer worldServer = (WorldServer)net.minecraft.AgeOfMinecraft.util.EntityCompat.world(player);
              EntityPlayerMP mp = (EntityPlayerMP)player;
              mp.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 80, 255));
              mp.server.getPlayerList().transferPlayerToDimension(mp, ACLib.dark_realm_id, new TeleporterDarkRealm(worldServer));
            } 
            continue;
          } 
          if ((entity instanceof EntityLivingBase && !false) || !(entity instanceof EntityLivingBase))
            entity.setDead(); 
        } 
      } 
    } 
    if (!isWild()) {
      if (this.deathTicks == 20 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.1"));
      if (this.deathTicks == 100 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.2"));
      if (this.deathTicks == 180 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.3"));
      if (this.deathTicks == 260 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.4"));
      if (this.deathTicks == 340 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.5"));
      if (this.deathTicks == 420 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.6"));
      if (this.deathTicks == 500 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.7"));
      if (this.deathTicks == 580 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.8"));
      if (this.deathTicks == 660 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.9"));
      if (this.deathTicks == 800 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzaharhelpful.death.10"));
        int i = 60000;
        this.world.spawnEntity(new EntityGatekeeperEssence(this.world, this.posX, this.posY, this.posZ));
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
        } 
        setDead();
      } 
    } else {
      if (this.deathTicks == 20 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.1"));
      if (this.deathTicks == 100 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.2"));
      if (this.deathTicks == 180 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.3"));
      if (this.deathTicks == 260 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.4"));
      if (this.deathTicks == 340 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.5"));
      if (this.deathTicks == 420 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.6"));
      if (this.deathTicks == 500 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.7"));
      if (this.deathTicks == 580 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.8"));
      if (this.deathTicks == 660 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.9"));
      if (this.deathTicks == 800 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        SpecialTextUtil.JzaharGroup(this.world, I18n.translateToLocal("message.jzahar.death.10"));
        int i = 60000;
        this.world.spawnEntity(new EntityGatekeeperEssence(this.world, this.posX, this.posY, this.posZ));
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
        } 
        setDead();
      } 
    } 
  }
  
  private void launchWitherSkullToEntity(int par1, EntityLivingBase par2EntityLivingBase) {
    launchWitherSkullToCoords(par1, par2EntityLivingBase.posX + par2EntityLivingBase.motionX * 10.0D, par2EntityLivingBase.posY + 1.0D + ((par2EntityLivingBase.height > 8.0F) ? 7.0D : (par2EntityLivingBase.height * 0.5D)) + par2EntityLivingBase.motionY * 10.0D, par2EntityLivingBase.posZ + par2EntityLivingBase.motionZ * 10.0D, (par1 == 0 && this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int par1, double par2, double par4, double par6, boolean par8) {
    this.world.playEvent(null, 1024, new BlockPos(this.posX, this.posY, this.posZ), 0);
    double d3 = getHeadX(par1);
    double d4 = getHeadY(par1);
    double d5 = getHeadZ(par1);
    double d6 = par2 - d3;
    double d7 = par4 - d4;
    double d8 = par6 - d5;
    EntityMiniBlackHole entitywitherskull = new EntityMiniBlackHole(this.world, this, d6, d7, d8);
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    this.world.spawnEntity(entitywitherskull);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase par1EntityLivingBase, float par2) {
    launchWitherSkullToEntity(0, par1EntityLivingBase);
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    SpecialTextUtil.JzaharGroup(this.world, "Allahu Akbar!");
    IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    Calendar calendar = this.world.getCurrentDate();
    attribute.removeModifier(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.applyModifier(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}
}


