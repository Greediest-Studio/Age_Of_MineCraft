package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.shinoow.abyssalcraft.api.AbyssalCraftAPI;
import com.shinoow.abyssalcraft.api.item.ACItems;
import com.shinoow.abyssalcraft.lib.ACConfig;
import com.shinoow.abyssalcraft.lib.ACLib;
import com.shinoow.abyssalcraft.lib.ACSounds;
import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
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
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.BossInfo;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySacthoth extends EntityTameBase implements Armored, Undead {
  private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntitySacthoth.class, DataSerializers.BYTE);
  
  private static final UUID attackDamageBoostUUID = UUID.fromString("648D7064-6A60-4F59-8ABE-C2C23A6DD7A9");
  
  private static final AttributeModifier attackDamageBoost = new AttributeModifier(attackDamageBoostUUID, "Halloween Attack Damage Boost", 8.0D, 0);
  
  private int shadowFlameShootTimer;
  
  public EntitySacthoth(World par1World) {
    super(par1World);
    setSize(1.1F, 3.8F);
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 12.0F));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.8D));
    this.tasks.addTask(4, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D));
    this.tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    this.tasks.addTask(5, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
    this.ignoreFrustumCheck = true;
    this.isImmuneToFire = true;
    this.isOffensive = true;
  }
  
  public int playMusic() {
    return 2;
  }
  
  public int getNextLevelRequirement() {
    return 2500;
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
  
  public float getBonusVSLight() {
    return 0.75F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public boolean affectedByCommandingStaff() {
    return false;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  protected PathNavigate createNavigator(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean canBreatheUnderwater() {
    return true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(CLIMBING, new Byte((byte)0));
  }
  
  public String getName() {
    return TextFormatting.DARK_RED + super.getName() + TextFormatting.WHITE;
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (!this.world.isRemote)
      setBesideClimbableBlock(this.collidedHorizontally); 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(25.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(15.0D);
    if (ACConfig.hardcoreMode) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(600.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(30.0D);
    } else {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15.0D);
    } 
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected boolean canDespawn() {
    return false;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  protected void updateAITasks() {
    super.updateAITasks();
    if (this.ticksExisted % 20 == 0)
      heal(1.0F); 
  }
  
  public boolean isOnLadder() {
    return isBesideClimbableBlock();
  }
  
  public boolean attackEntityAsMob(Entity par1Entity) {
    boolean flag = super.attackEntityAsMob(par1Entity);
    if (flag && 
      par1Entity instanceof EntityLivingBase) {
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.WITHER, 15));
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 60));
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120));
      ((EntityLivingBase)par1Entity).addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, 240));
    } 
    if (ACConfig.hardcoreMode && par1Entity instanceof EntityPlayer)
      par1Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this).setDamageBypassesArmor().setDamageIsAbsolute(), 4.5F * (float)((ACConfig.damageAmpl > 1.0D) ? ACConfig.damageAmpl : 1.0D)); 
    return flag;
  }
  
  protected float getSoundPitch() {
    return super.getSoundPitch() - 0.3F;
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
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_BLAZE_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_BLAZE_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return ACSounds.sacthoth_death;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.5F : 5.0F;
  }
  
  public boolean isBesideClimbableBlock() {
    return ((((Byte)this.dataManager.get(CLIMBING)).byteValue() & 0x1) != 0);
  }
  
  public void setBesideClimbableBlock(boolean par1) {
    byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();
    if (par1) {
      b0 = (byte)(b0 | 0x1);
    } else {
      b0 = (byte)(b0 & 0xFFFFFFFE);
    } 
    this.dataManager.set(CLIMBING, Byte.valueOf(b0));
  }
  
  public int getDamageCap() {
    return 50;
  }
  
  public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
    if (par1DamageSource == DamageSource.IN_WALL) {
      teleportRandomly();
      return false;
    } 
    if (par1DamageSource.isExplosion()) {
      if (this.world.isRemote && ACConfig.showBossDialogs)
        SpecialTextUtil.SacthothText(new String[] { I18n.translateToLocal("message.sacthoth.damage.explosion") }); 
      return false;
    } 
    if (par1DamageSource.isProjectile()) {
      if (this.world.isRemote && ACConfig.showBossDialogs)
        SpecialTextUtil.SacthothText(new String[] { I18n.translateToLocal("message.sacthoth.damage.projectile") }); 
      return false;
    } 
    return super.attackEntityFrom(par1DamageSource, par2);
  }
  
  protected boolean teleportRandomly() {
    double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
    double d1 = this.posY + (this.rand.nextInt(64) - 32);
    double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportTo(double par1, double par3, double par5) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, par1, par3, par5, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    double d3 = this.posX;
    double d4 = this.posY;
    double d5 = this.posZ;
    this.posX = event.getTargetX();
    this.posY = event.getTargetY();
    this.posZ = event.getTargetZ();
    boolean flag = false;
    BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
    if (this.world.isBlockLoaded(pos)) {
      boolean flag1 = false;
      while (!flag1 && pos.getY() > 0) {
        BlockPos pos1 = pos.down();
        IBlockState block = this.world.getBlockState(pos1);
        if (block.getMaterial().blocksMovement()) {
          flag1 = true;
          continue;
        } 
        this.posY--;
        pos = pos1;
      } 
      if (flag1) {
        setPosition(this.posX, this.posY, this.posZ);
        if (this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox()))
          flag = true; 
      } 
    } 
    if (!flag) {
      setPosition(d3, d4, d5);
      return false;
    } 
    short short1 = 128;
    for (int l = 0; l < short1; l++) {
      double d6 = l / (short1 - 1.0D);
      float f = (this.rand.nextFloat() - 0.5F) * 0.2F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 0.2F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 0.2F;
      double d7 = d3 + (this.posX - d3) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
      double d8 = d4 + (this.posY - d4) * d6 + this.rand.nextDouble() * this.height;
      double d9 = d5 + (this.posZ - d5) * d6 + (this.rand.nextDouble() - 0.5D) * this.width * 2.0D;
      if (ACConfig.particleEntity)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d7, d8, d9, f, f1, f2, new int[0]); 
    } 
    this.world.playSound(d3, d4, d5, SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F, false);
    playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
    return true;
  }
  
  protected void onDeathUpdate() {
    this.deathTicks++;
    if (!this.world.isRemote)
      if (this.deathTicks == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.world.playerEntities) {
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
            entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().getName() + "'s Sacthoth has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).sendMessage((ITextComponent)new TextComponentTranslation("Your Sacthoth has been destroyed!", new Object[0]));
        }   
    if (this.deathTicks <= 200) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      if (ACConfig.particleEntity) {
        this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]);
        if (this.deathTicks >= 190 && this.deathTicks <= 200)
          this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D, new int[0]); 
      } 
    } 
    if (!this.world.isRemote) {
      if (this.deathTicks > 100 && this.deathTicks % 5 == 0) {
        int i = 500;
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
        } 
        this.world.spawnEntity((Entity)new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.shadow_fragment, 4)));
        this.world.spawnEntity((Entity)new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.shadow_shard, 2)));
        this.world.spawnEntity((Entity)new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.shadow_gem)));
        this.world.spawnEntity((Entity)new EntityItem(this.world, this.posX + posneg(3), this.posY + this.rand.nextInt(3), this.posZ + posneg(3), new ItemStack(ACItems.shard_of_oblivion)));
      } 
      if (this.deathTicks >= 150 && this.deathTicks <= 200) {
        if (this.deathTicks >= 150) {
          EntityShadowCreature shadowCreature = new EntityShadowCreature(this.world);
          shadowCreature.copyLocationAndAnglesFrom((Entity)this);
          shadowCreature.setOwnerId(getOwnerId());
          this.world.spawnEntity((Entity)shadowCreature);
        } 
        if (this.deathTicks >= 175) {
          EntityShadowMonster shadowMonster = new EntityShadowMonster(this.world);
          shadowMonster.copyLocationAndAnglesFrom((Entity)this);
          shadowMonster.setOwnerId(getOwnerId());
          this.world.spawnEntity((Entity)shadowMonster);
        } 
        if (this.deathTicks >= 195) {
          EntityShadowBeast shadowBeast = new EntityShadowBeast(this.world);
          shadowBeast.copyLocationAndAnglesFrom((Entity)this);
          shadowBeast.setOwnerId(getOwnerId());
          this.world.spawnEntity((Entity)shadowBeast);
        } 
      } 
      if (this.deathTicks == 200 && !this.world.isRemote) {
        List<Entity> list = this.world.loadedEntityList;
        if (list != null)
          for (int k2 = 0; k2 < list.size(); k2++) {
            Entity entity = list.get(k2);
            if (entity instanceof EntityJzahar && entity.isEntityAlive())
              SpecialTextUtil.JzaharGroup(this.world, new String[] { false ? I18n.translateToLocal("message.jzaharhelpful.snidecomment.sacthoth") : I18n.translateToLocal("message.jzahar.snidecomment.sacthoth") }); 
          }  
        setDead();
        this.world.spawnEntity((Entity)new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(ACItems.sacthoths_soul_harvesting_blade)));
      } 
    } 
  }
  
  private int posneg(int num) {
    return this.rand.nextBoolean() ? this.rand.nextInt(num) : (-1 * this.rand.nextInt(num));
  }
  
  protected void collideWithEntity(Entity par1Entity) {
    if (this.deathTicks == 0)
      par1Entity.applyEntityCollision((Entity)this); 
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.getHeldItem(hand);
    ItemStack heldItem = new ItemStack(stack.getItem());
    if (stack.isEmpty() && getRidingEntity() == null) {
      if (!isWild() && false && !isChild() && !this.world.isRemote)
        player.startRiding((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 3);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (i == 2)
        passenger.setPosition(this.posX - (f4 * 0.75F), this.posY + (2.25F * getFittness()), this.posZ - (f11 * 0.75F)); 
      if (i == 1)
        passenger.setPosition(this.posX + (f4 * 0.75F), this.posY + (2.25F * getFittness()), this.posZ + (f11 * 0.75F)); 
      if (i == 0)
        passenger.setPosition(this.posX + (f11 * 0.25F), this.posY + (2.25F * getFittness()), this.posZ - (f4 * 0.25F)); 
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
        setBesideClimbableBlock(this.collidedHorizontally);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
      } 
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
  
  protected SoundEvent getRegularHurtSound() {
    return ESound.metalHit;
  }
  
  protected SoundEvent getPierceHurtSound() {
    return ESound.metalHitPierce;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.metalHitCrush;
  }
  
  public int getVerticalFaceSpeed() {
    return 180;
  }
  
  public void onLivingUpdate() {
    if (!isInvisible())
      for (int i = 0; i < 3 && ACConfig.particleEntity && this.world.provider.getDimension() != ACLib.dark_realm_id; i++)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);  
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().grow(30.0D, 30.0D, 30.0D));
    if (list != null)
      for (int k2 = 0; k2 < list.size(); k2++) {
        Entity entity = list.get(k2);
        if (entity instanceof EntityPlayer && !false && !entity.isDead && this.deathTicks == 0 && !((EntityPlayer)entity).capabilities.isCreativeMode)
          ((EntityPlayer)entity).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40)); 
      }  
    EntityPlayer player = this.world.getClosestPlayerToEntity((Entity)this, 160.0D);
    if (player != null && !false && player.getDistance((Entity)this) >= 50.0D && !player.capabilities.isCreativeMode) {
      if (player.posX - this.posX > 50.0D)
        teleportTo(player.posX + 30.0D, player.posY, player.posZ); 
      if (player.posX - this.posX < -50.0D)
        teleportTo(player.posX - 30.0D, player.posY, player.posZ); 
      if (player.posZ - this.posZ > 50.0D)
        teleportTo(player.posX, player.posY, player.posZ - 30.0D); 
      if (player.posZ - this.posZ < -50.0D)
        teleportTo(player.posX, player.posY, player.posZ + 30.0D); 
      if (player.posY - this.posY > 50.0D)
        teleportTo(player.posX, player.posY, player.posZ); 
      if (player.posY - this.posY < -50.0D)
        teleportTo(player.posX, player.posY, player.posZ); 
    } 
    if (getAttackTarget() != null && getDistanceSq((Entity)getAttackTarget()) <= 100.0D && this.shadowFlameShootTimer <= (isHero() ? -100 : -300))
      this.shadowFlameShootTimer = 100; 
    if (getAttackTarget() != null)
      faceEntity((Entity)getAttackTarget(), 10.0F, 180.0F); 
    if (this.shadowFlameShootTimer > 0) {
      this.motionX *= 0.05D;
      this.motionZ *= 0.05D;
      this.world.setEntityState((Entity)this, (byte)23);
      if (this.ticksExisted % 5 == 0)
        this.world.playSound(null, new BlockPos(this.posX + 0.5D, this.posY + getEyeHeight(), this.posZ + 0.5D), SoundEvents.ENTITY_GHAST_SHOOT, getSoundCategory(), 1.5F + getRNG().nextFloat(), getRNG().nextFloat() * 0.5F + 0.3F); 
      Entity target = getHeadLookTarget();
      if (target != null) {
        List<EntityLivingBase> list1 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(2.0D, 2.0D, 2.0D), Predicates.and(new Predicate[] { EntitySelectors.IS_ALIVE }));
        if (list1 != null && !list1.isEmpty())
          for (int i1 = 0; i1 < list1.size(); i1++) {
            EntityLivingBase entity = list1.get(i1);
            if (entity != null && !false && this.rand.nextInt(3) == 0)
              if (entity.attackEntityFrom((new DamageSource("shadow")).setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage(), (float)(15.0D - getDistance((Entity)entity)) * 3.0F)) {
                entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 1600));
                entity.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1600));
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 800, 1));
                entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 800));
                entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 400, 1));
              } else {
                attackEntityAsMob((Entity)entity);
                entity.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
                entity.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400));
                entity.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200));
                entity.addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
                entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 100));
              }  
          }  
        if (target.attackEntityFrom(AbyssalCraftAPI.antimatter, (float)(15.0D - getDistance(target)) * 3.0F)) {
          if (target instanceof EntityLivingBase) {
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 1600));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 1600));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 800, 1));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 800));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 400, 1));
          } 
        } else {
          attackEntityAsMob(target);
          if (target instanceof EntityLivingBase) {
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 400));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 200));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.NAUSEA, 200));
            ((EntityLivingBase)target).addPotionEffect(new PotionEffect(MobEffects.WITHER, 100));
          } 
        } 
      } 
    } 
    this.shadowFlameShootTimer--;
    super.onLivingUpdate();
  }
  
  public void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
    super.writeEntityToNBT(par1NBTTagCompound);
    if (this.deathTicks > 0)
      par1NBTTagCompound.setInteger("DeathTicks", this.deathTicks); 
  }
  
  public void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
    super.readEntityFromNBT(par1NBTTagCompound);
    this.deathTicks = par1NBTTagCompound.getInteger("DeathTicks");
  }
  
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData par1EntityLivingData) {
    par1EntityLivingData = super.onInitialSpawn(difficulty, par1EntityLivingData);
    if (this.world.isDaytime())
      this.world.setWorldTime(14000L); 
    IAttributeInstance attribute = getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    Calendar calendar = this.world.getCurrentDate();
    attribute.removeModifier(attackDamageBoost);
    if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31)
      attribute.applyModifier(attackDamageBoost); 
    return par1EntityLivingData;
  }
  
  private Entity getHeadLookTarget() {
    Entity pointedEntity = null;
    double range = 4.0D + this.rand.nextDouble() * 16.0D;
    Vec3d srcVec = new Vec3d(this.posX, this.posY + getEyeHeight(), this.posZ);
    Vec3d lookVec = getLook(1.0F);
    RayTraceResult raytrace = this.world.rayTraceBlocks(srcVec, srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range));
    BlockPos hitpos = (raytrace != null) ? raytrace.getBlockPos() : null;
    double rx = (hitpos == null) ? range : Math.min(range, Math.abs(this.posX - hitpos.getX()));
    double ry = (hitpos == null) ? range : Math.min(range, Math.abs(this.posY - hitpos.getY()));
    double rz = (hitpos == null) ? range : Math.min(range, Math.abs(this.posZ - hitpos.getZ()));
    Vec3d destVec = srcVec.add(lookVec.x * range, lookVec.y * range, lookVec.z * range);
    float var9 = 8.0F;
    List<Entity> possibleList = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, getEntityBoundingBox().offset(lookVec.x * rx, lookVec.y * ry, lookVec.z * rz).grow(var9, var9, var9));
    double hitDist = 0.0D;
    for (Entity possibleEntity : possibleList) {
      if (possibleEntity != this && possibleEntity instanceof EntityLivingBase && !false) {
        float borderSize = possibleEntity.getCollisionBorderSize();
        AxisAlignedBB collisionBB = possibleEntity.getEntityBoundingBox().grow(borderSize, borderSize, borderSize);
        RayTraceResult interceptPos = collisionBB.calculateIntercept(srcVec, destVec);
        if (collisionBB.contains(srcVec)) {
          if (0.0D < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = 0.0D;
          } 
          continue;
        } 
        if (interceptPos != null) {
          double possibleDist = srcVec.distanceTo(interceptPos.hitVec);
          if (possibleDist < hitDist || hitDist == 0.0D) {
            pointedEntity = possibleEntity;
            hitDist = possibleDist;
          } 
        } 
      } 
    } 
    return pointedEntity;
  }
  
  protected void addMouthParticles() {
    if (this.world.isRemote) {
      Vec3d vector = getLookVec();
      double px = this.posX;
      double py = this.posY + getEyeHeight();
      double pz = this.posZ;
      for (int i = 0; i < 45; i++) {
        double dx = vector.x;
        double dy = vector.y;
        double dz = vector.z;
        double spread = 10.0D + getRNG().nextDouble() * 5.0D;
        double velocity = 1.0D + getRNG().nextDouble();
        dx += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dy += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dz += getRNG().nextGaussian() * 0.007499999832361937D * spread;
        dx *= velocity;
        dy *= velocity;
        dz *= velocity;
        this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, px + getRNG().nextDouble() - 0.5D, py + getRNG().nextDouble() - 0.5D, pz + getRNG().nextDouble() - 0.5D, dx, dy, dz, new int[0]);
      } 
    } else {
      this.world.setEntityState((Entity)this, (byte)23);
    } 
  }
  
  @SideOnly(Side.CLIENT)
  public void handleStatusUpdate(byte id) {
    if (id == 23) {
      addMouthParticles();
    } else {
      super.handleStatusUpdate(id);
    } 
  }
}


