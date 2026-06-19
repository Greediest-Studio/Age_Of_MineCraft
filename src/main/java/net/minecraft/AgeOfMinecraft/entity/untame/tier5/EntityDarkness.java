package net.minecraft.AgeOfMinecraft.entity.untame.tier5;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.EngenderMod;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityBodyHelperDragon;
import net.minecraft.AgeOfMinecraft.entity.tame.ExtendMultiPartEntityPart;
import net.minecraft.AgeOfMinecraft.entity.untame.EntityUntameBase;
import net.minecraft.AgeOfMinecraft.entity.untame.ai.darkness.AIPhase1;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.util.Maths;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityDragonFireball;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntitySelectors;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityDarkness extends EntityUntameBase implements IEntityMultiPart, IMob {
  private final AIPhase1 AI_1 = new AIPhase1(this);
  
  public boolean shouldMove = true;
  
  public boolean shouldChangeAI = true;
  
  public int wingPos = 0;
  
  public double targetYRelative;
  
  private static final DataParameter<Float> maxEnergy = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.FLOAT);
  
  private static final DataParameter<Float> energy = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.FLOAT);
  
  public static final DataParameter<Boolean> isHardmode = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.BOOLEAN);
  
  public static final DataParameter<Integer> phase = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.VARINT);
  
  public static final DataParameter<Integer> cooldown = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.VARINT);
  
  public static final DataParameter<Integer> attackTicks = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.VARINT);
  
  public static final DataParameter<Integer> attack = EntityDataManager.createKey(EntityDarkness.class, DataSerializers.VARINT);
  
  public int innerRotation;
  
  public double[][] ringBuffer = new double[64][3];
  
  public int ringBufferIndex = -1;
  
  public ExtendMultiPartEntityPart[] dragonPartArray;
  
  public ExtendMultiPartEntityPart dragonPartHead;
  
  public ExtendMultiPartEntityPart dragonPartNeck;
  
  public ExtendMultiPartEntityPart dragonPartBody;
  
  public ExtendMultiPartEntityPart dragonPartTail1;
  
  public ExtendMultiPartEntityPart dragonPartTail2;
  
  public ExtendMultiPartEntityPart dragonPartTail3;
  
  public ExtendMultiPartEntityPart dragonPartWing1;
  
  public ExtendMultiPartEntityPart dragonPartWing2;
  
  public float prevAnimTime;
  
  public float animTime;
  
  public boolean slowed;
  
  public final List<EntityEnderCrystal> crystals = Lists.newArrayList();
  
  public EntityDarkness(World worldIn) {
    super(worldIn);
    setEnergy(getMaxEnergy());
    setSize(16.0F, 3.6F);
    this.dragonPartArray = new ExtendMultiPartEntityPart[] { this.dragonPartHead = new ExtendMultiPartEntityPart(this, "head", 6.0F, 6.0F), this.dragonPartNeck = new ExtendMultiPartEntityPart(this, "neck", 6.0F, 6.0F), this.dragonPartBody = new ExtendMultiPartEntityPart(this, "body", 8.0F, 8.0F), this.dragonPartTail1 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail2 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartTail3 = new ExtendMultiPartEntityPart(this, "tail", 4.0F, 4.0F), this.dragonPartWing1 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F), this.dragonPartWing2 = new ExtendMultiPartEntityPart(this, "wing", 4.0F, 4.0F) };
    this.noClip = true;
    this.isImmuneToFire = true;
    this.ignoreFrustumCheck = true;
    this.forceSpawn = true;
  }
  
  public int playMusic() {
    return (getPhase() < 4) ? 15 : ((getPhase() < 13) ? 16 : 0);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(750.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(0.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.0D);
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(maxEnergy, 20.0F);
    getDataManager().register(energy, 20.0F);
    getDataManager().register(isHardmode, Boolean.FALSE);
    getDataManager().register(phase, 0);
    getDataManager().register(attack, 0);
    getDataManager().register(attackTicks, 0);
    getDataManager().register(cooldown, 0);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Phase", getPhase());
    tagCompound.setFloat("Energy", getEnergy());
    tagCompound.setBoolean("Hardmode", getHardmode());
    tagCompound.setFloat("Energy", getEnergy());
    tagCompound.setFloat("MaxEnergy", getMaxEnergy());
    if (EngenderConfig.debugMode) {
      tagCompound.setInteger("Move", getAttackID());
      tagCompound.setInteger("Cooldown", getCooldown());
    } 
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setHardmode(tagCompund.getBoolean("Hardmode"));
    setPhase(tagCompund.getInteger("Phase"));
    setEnergy(tagCompund.getFloat("Energy"));
    setMaxEnergy(tagCompund.getFloat("MaxEnergy"));
    if (EngenderConfig.debugMode) {
      setAttackID(tagCompund.getInteger("Move"));
      setCooldown(tagCompund.getInteger("Cooldown"));
    } 
  }
  
  @Nullable
  public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    livingdata = super.onInitialSpawn(difficulty, livingdata);
    setMaxEnergy(750.0F * (this.world.playerEntities.size() * 0.33F + 1.0F));
    setEnergy(getMaxEnergy());
    setAttackID(1);
    this.forceNewTarget = true;
    sendMessage("spawn", true);
    return livingdata;
  }
  
  public void onKillCommand() {
    setPhase(18);
  }
  
  public void onUpdate() {
    super.onUpdate();
    if (getPhase() > 9 && getPhase() < 14 && getEnergy() <= 0.0F) {
      setPhase(14);
    } else if (getPhase() < 10 && getEnergy() <= 0.0F) {
      setPhase(19);
    } 
    if (this.world.isRemote) {
      float f12 = 0.2F / (MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 10.0F + 1.0F);
      f12 *= (float)Math.pow(2.0D, this.motionY);
      if (this.slowed) {
        this.animTime += f12 * 0.5F;
      } else if (this.wingPos == 1) {
        this.animTime = 1.25F;
      } else if (this.wingPos == 2) {
        this.animTime = 0.45F;
      } else {
        this.animTime += f12;
      } 
    } 
    double targetX = this.targetX - this.posX;
    double targetY = this.targetY - this.posY;
    double targetZ = this.targetZ - this.posZ;
    double distance = targetX * targetX + targetY * targetY + targetZ * targetZ;
    if (getCooldown() > 0)
      setCooldown(getCooldown() - 1); 
    if (getAttackTime() > 0)
      setAttackTime(getAttackTime() - 1); 
    onPhaseUpdate();
    onAIUpdate(distance);
    this.innerRotation++;
  }
  
  public void onLivingUpdate() {
    if (this.isJumping)
      this.isJumping = false; 
    if (!this.onGround)
      this.onGround = true; 
    if (this.isAirBorne)
      this.isAirBorne = false; 
    if (!this.noClip)
      this.noClip = true; 
    if (isAIDisabled() && !isSilent())
      setSilent(true); 
    onCrystalUpdate();
    if (getMaxHealth() != getMaxEnergy())
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(getMaxEnergy()); 
    if (getHealth() != getEnergy())
      setHealth(getEnergy()); 
    this.dragonPartHead.width = this.dragonPartHead.height = 2.5F;
    this.dragonPartNeck.width = this.dragonPartNeck.height = 2.5F;
    this.dragonPartTail1.width = this.dragonPartTail1.height = 2.0F;
    this.dragonPartTail2.width = this.dragonPartTail2.height = 2.0F;
    this.dragonPartTail3.width = this.dragonPartTail3.height = 2.0F;
    this.dragonPartBody.height = 3.5F;
    this.dragonPartBody.width = 5.0F;
    this.dragonPartWing1.height = 3.0F;
    this.dragonPartWing1.width = 4.0F;
    this.dragonPartWing2.height = 3.0F;
    this.dragonPartWing2.width = 4.0F;
    Vec3d[] avec3d = new Vec3d[this.dragonPartArray.length];
    for (int j = 0; j < this.dragonPartArray.length; j++)
      avec3d[j] = new Vec3d((this.dragonPartArray[j]).posX, (this.dragonPartArray[j]).posY, (this.dragonPartArray[j]).posZ); 
    float f14 = (float)(getMovementOffsets(5, 1.0F)[1] - getMovementOffsets(10, 1.0F)[1]) * 10.0F * 0.017453292F;
    float f16 = MathHelper.cos(f14);
    float f18 = MathHelper.sin(f14);
    float f2 = this.rotationYaw * 0.017453292F;
    float f19 = MathHelper.sin(f2);
    float f3 = MathHelper.cos(f2);
    double[] adouble = getMovementOffsets(5, 1.0F);
    double[] adouble1 = getMovementOffsets(14, 1.0F);
    getMovementOffsets(16, 1.0F);
    this.dragonPartBody.onUpdate();
    this.dragonPartBody.setLocationAndAngles(this.posX, this.posY - (MathHelper.sin(this.animTime * 6.2831855F - 0.5F) * 0.1F), this.posZ, 0.0F, 0.0F);
    this.dragonPartWing1.onUpdate();
    this.dragonPartWing1.setLocationAndAngles(this.posX + (f3 * 4.5F), this.posY + 1.0D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ + (f19 * 4.5F), 0.0F, 0.0F);
    this.dragonPartWing2.onUpdate();
    this.dragonPartWing2.setLocationAndAngles(this.posX - (f3 * 4.5F), this.posY + 1.0D + (MathHelper.sin(this.animTime * 6.2831855F) * 3.0F), this.posZ - (f19 * 4.5F), 0.0F, 0.0F);
    this.dragonPartNeck.onUpdate();
    this.dragonPartNeck.setLocationAndAngles(this.posX + (f19 * 3.5F), this.posY + 1.0D - (MathHelper.sin(this.animTime * 6.2831855F + 1.0F) * 0.1F) + (f18 * 2.0F) - (this.rotationPitch / 90.0F) * Math.PI * 0.25D, this.posZ - (f3 * 3.5F), 0.0F, 0.0F);
    this.dragonPartHead.onUpdate();
    this.dragonPartHead.setLocationAndAngles(this.posX + (f19 * 6.0F), this.posY + 1.0D - (MathHelper.sin(this.animTime * 6.2831855F) * 0.1F) + (f18 * 4.0F) - (this.rotationPitch / 90.0F) * Math.PI * 1.0D, this.posZ - (f3 * 6.0F), 0.0F, 0.0F);
    for (int i = 0; i < 3; i++) {
      ExtendMultiPartEntityPart MultiPartEntityPart = null;
      if (i == 0)
        MultiPartEntityPart = this.dragonPartTail1; 
      if (i == 1)
        MultiPartEntityPart = this.dragonPartTail2; 
      if (i == 2)
        MultiPartEntityPart = this.dragonPartTail3; 
      adouble1 = getMovementOffsets(12 + i * 2, 1.0F);
      float f21 = this.rotationYaw * 0.017453292F + simplifyAngle(adouble1[0] - adouble[0]) * 0.017453292F;
      float f22 = MathHelper.sin(f21);
      float f7 = MathHelper.cos(f21);
      float f23 = 1.5F;
      float f24 = (i + 1) * 2.0F;
      MultiPartEntityPart.onUpdate();
      MultiPartEntityPart.setLocationAndAngles(this.posX - ((f19 * f23 + f22 * f24) * f16), this.posY - (MathHelper.sin(this.animTime * 6.2831855F + i) * 0.2F * (i + 1)) - (f18 * (2.0F + (1 * i))) + f23, this.posZ + ((f3 * f23 + f7 * f24) * f16), 0.0F, 0.0F);
    } 
    for (int l = 0; l < this.dragonPartArray.length; l++) {
      (this.dragonPartArray[l]).prevPosX = (avec3d[l]).x;
      (this.dragonPartArray[l]).prevPosY = (avec3d[l]).y;
      (this.dragonPartArray[l]).prevPosZ = (avec3d[l]).z;
    } 
    if (this.ticksExisted % 5 == 0 && this.slowed)
      this.slowed = false; 
    if (this.world.isRemote)
      if (!isSilent()) {
        float f = MathHelper.cos(this.animTime * 6.2831855F);
        float f1 = MathHelper.cos(this.prevAnimTime * 6.2831855F);
        if (f1 <= -0.3F && f >= -0.3F)
          this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, getSoundCategory(), 5.0F, 0.8F + this.rand.nextFloat() * 0.3F, false); 
      }  
    this.prevAnimTime = this.animTime;
    if (isAIDisabled()) {
      this.animTime = 0.0F;
    } else if (isEntityAlive()) {
      if (EngenderConfig.mobs.regeneration && this.ticksExisted % 20 == 0)
        change(1.0F); 
      this.targetTasks.onUpdateTasks();
      if (this.ticksExisted % 40 == 0 && !this.world.isRemote && (getAttackTarget() == null || Maths.chance(75))) {
        List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().grow(getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()), Predicates.and(EntitySelectors.IS_ALIVE, EntitySelectors.NOT_SPECTATING));
        for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
          EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
          if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase)) {
            if ((entitylivingbase instanceof EntityPlayer && !((EntityPlayer)entitylivingbase).isCreative() && !((EntityPlayer)entitylivingbase).isSpectator()) || !(entitylivingbase instanceof EntityPlayer)) {
              setAttackTarget(entitylivingbase);
              break;
            } 
          } else {
            list.remove(entitylivingbase);
          } 
        } 
      } 
      if (!this.world.isRemote)
        if (isEntityAlive()) {
          ChunkLoadingEvent.updateLoaded((Entity)this);
        } else {
          ChunkLoadingEvent.stopLoading((Entity)this);
        }  
      if (this.ringBufferIndex < 0)
        for (int k = 0; k < this.ringBuffer.length; k++) {
          this.ringBuffer[k][0] = this.rotationYaw;
          this.ringBuffer[k][1] = this.posY;
        }  
      if (++this.ringBufferIndex == this.ringBuffer.length)
        this.ringBufferIndex = 0; 
      this.ringBuffer[this.ringBufferIndex][0] = this.rotationYaw;
      this.ringBuffer[this.ringBufferIndex][1] = this.posY;
      if (this.world.isRemote) {
        if (this.newPosRotationIncrements > 0) {
          double targetX = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
          double targetY = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
          double targetZ = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
          double distance = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
          this.rotationYaw = (float)(this.rotationYaw + distance / this.newPosRotationIncrements);
          this.rotationPitch = (float)(this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
          this.newPosRotationIncrements--;
          setPosition(targetX, targetY, targetZ);
          setRotation(this.rotationYaw, this.rotationPitch);
        } 
      } else {
        double targetX = this.targetX - this.posX;
        double targetY = this.targetY - this.posY;
        double targetZ = this.targetZ - this.posZ;
        targetY /= MathHelper.sqrt(targetX * targetX + targetZ * targetZ);
        float f12 = 0.6F;
        if (targetY < -f12)
          targetY = -f12; 
        if (targetY > f12)
          targetY = f12; 
        this.motionY += targetY * 0.10000000149011612D;
        this.rotationYaw = MathHelper.wrapDegrees(this.rotationYaw);
        double d4 = 180.0D - Math.atan2(targetX, targetZ) * 180.0D / Math.PI;
        double d6 = MathHelper.wrapDegrees(d4 - this.rotationYaw);
        if (d6 > 50.0D)
          d6 = 50.0D; 
        if (d6 < -50.0D)
          d6 = -50.0D; 
        Vec3d vec3 = (new Vec3d(this.targetX - this.posX, this.targetY - this.posY, this.targetZ - this.posZ)).normalize();
        Vec3d vec32 = (new Vec3d(MathHelper.sin(this.rotationYaw * 3.1415927F / 180.0F), this.motionY, -MathHelper.cos(this.rotationYaw * 3.1415927F / 180.0F))).normalize();
        float f5 = (float)(vec32.dotProduct(vec3) + 0.5D) / 1.5F;
        if (f5 < 0.0F)
          f5 = 0.0F; 
        this.randomYawVelocity *= 0.8F;
        float f6 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0F + 1.0F;
        double d9 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ) * 1.0D + 1.0D;
        if (d9 > 40.0D)
          d9 = 40.0D; 
        this.randomYawVelocity = (float)(this.randomYawVelocity + d6 * 0.699999988079071D / d9 / f6);
        this.rotationYaw += this.randomYawVelocity * 0.1F;
        float f7 = (float)(2.0D / (d9 + 1.0D));
        float f8 = 0.06F;
        moveRelative(0.0F, 0.0F, -1.0F, 0.06F * (f7 * f8 + 1.0F - f8));
        if (this.shouldMove)
          move(MoverType.SELF, this.motionX * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue(), this.motionY * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue(), this.motionZ * getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue()); 
        Vec3d vec31 = (new Vec3d(this.motionX, this.motionY, this.motionZ)).normalize();
        float f9 = (float)(vec31.dotProduct(vec32) + 1.0D) / 2.0F;
        f9 = 0.8F + 0.15F * f9;
        this.motionX *= f9;
        this.motionZ *= f9;
        this.motionY *= 0.9100000262260437D;
      } 
    } else {
      clearActivePotions();
    } 
    if (getEnergy() <= 0.0F && this.deathTicks > 0) {
      for (int k = 0; k < this.dragonPartArray.length; k++) {
        avec3d[k] = new Vec3d((this.dragonPartArray[k]).posX, (this.dragonPartArray[k]).posY, (this.dragonPartArray[k]).posZ);
        double d1 = (this.rand.nextFloat() * (this.dragonPartArray[k]).width - (this.dragonPartArray[k]).width / 2.0F);
        double d2 = (this.rand.nextFloat() * (this.dragonPartArray[k]).height - (this.dragonPartArray[k]).height / 2.0F);
        double d3 = (this.rand.nextFloat() * (this.dragonPartArray[k]).width - (this.dragonPartArray[k]).width / 2.0F);
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, (this.dragonPartArray[k]).posX + d1, (this.dragonPartArray[k]).posY + d2, (this.dragonPartArray[k]).posZ + d3, 0.0D, 0.10000000149011612D, 0.0D);
      } 
      float f13 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f15 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f17 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f13, this.posY + f15, this.posZ + f17, 0.0D, 0.0D, 0.0D);
    } else {
      if (getPhase() > 2) {
        damageEntities(this.dragonPartHead, (getAttackID() == 4) ? 0.5F : 1.0F, "darkness.bite", (getAttackID() == 3) ? 8.0D : 0.0D);
        damageEntities(this.dragonPartNeck, 0.5F, "mob", 8.0D);
        damageEntities(this.dragonPartBody, 0.5F, "mob", 8.0D);
        damageEntities(this.dragonPartTail1, 0.5F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail2, 0.5F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail3, 0.5F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartWing1, 0.35F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
        damageEntities(this.dragonPartWing2, 0.35F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
      } else if (getPhase() == 1) {
        damageEntities(this.dragonPartHead, (getAttackID() == 4) ? 0.5F : 1.0F, "darkness.bite", (getAttackID() == 3) ? 8.0D : 0.0D);
        damageEntities(this.dragonPartNeck, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartBody, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartTail1, 0.35F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail2, 0.35F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartTail3, 0.35F, (getAttackID() == 5) ? "darkness.tail.slash" : "darkness.tail", (getAttackID() == 5) ? 1.0D : 8.0D);
        damageEntities(this.dragonPartWing1, 0.25F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
        damageEntities(this.dragonPartWing2, 0.25F, (getAttackID() == 6) ? "darkness.wing.slash" : "darkness.wing", (getAttackID() == 6) ? 0.0D : 24.0D);
      } else if (getPhase() == 0) {
        damageEntities(this.dragonPartHead, (getAttackID() == 4) ? 0.5F : 1.0F, "darkness.bite", (getAttackID() == 3) ? 8.0D : 0.0D);
        damageEntities(this.dragonPartNeck, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartBody, 0.35F, "mob", 8.0D);
        damageEntities(this.dragonPartTail1, 0.35F, "darkness.tail", 8.0D);
        damageEntities(this.dragonPartTail2, 0.35F, "darkness.tail", 8.0D);
        damageEntities(this.dragonPartTail3, 0.35F, "darkness.tail", 8.0D);
        damageEntities(this.dragonPartWing1, 0.25F, "darkness.wing", 24.0D);
        damageEntities(this.dragonPartWing2, 0.25F, "darkness.wing", 24.0D);
      } else {
        damageEntities(this.dragonPartHead, 1.0F, "generic", 24.0D);
        damageEntities(this.dragonPartNeck, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartBody, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartTail1, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartTail2, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartTail3, 0.35F, "generic", 24.0D);
        damageEntities(this.dragonPartWing1, 0.25F, "generic", 24.0D);
        damageEntities(this.dragonPartWing2, 0.25F, "generic", 24.0D);
      } 
      destroyBlocksInAABB(this.dragonPartHead.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartNeck.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartBody.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartWing1.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartWing2.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartTail1.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartTail2.getEntityBoundingBox());
      destroyBlocksInAABB(this.dragonPartTail3.getEntityBoundingBox());
      if (getAttackTarget() != null && !getAttackTarget().isEntityAlive())
        setAttackTarget(null); 
      this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw;
    } 
  }
  
  protected void onDeathUpdate() {
    if (this.deathTicks >= 180 && this.deathTicks <= 200) {
      float f = (this.rand.nextFloat() - 0.5F) * 8.0F;
      float f1 = (this.rand.nextFloat() - 0.5F) * 4.0F;
      float f2 = (this.rand.nextFloat() - 0.5F) * 8.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX + f, this.posY + 2.0D + f1, this.posZ + f2, 0.0D, 0.0D, 0.0D);
    } 
    if (!this.world.isRemote) {
      if (this.deathTicks > 150 && this.deathTicks % 5 == 0 && this.world.getGameRules().getBoolean("doMobLoot")) {
        int i = 5000;
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY + 8.0D, this.posZ, j));
        } 
      } 
      if (this.deathTicks == 1) {
        if (Loader.isModLoaded("draconicevolution")) {
          Entity var8 = EntityList.createEntityByIDFromName(new ResourceLocation("draconicevolution:dragonheartitem"), this.world);
          this.world.spawnEntity(var8);
        } 
        this.world.playBroadcastSound(1028, new BlockPos((Entity)this), 0);
      } 
      double d0 = 0.0D;
      double d1 = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION).getY();
      double d2 = 0.0D;
      double d3 = d0 * d0 + d2 * d2;
      if (this.ticksExisted > 20)
        if (d3 > 1.0D && this.deathTicks < 1) {
          double d5 = MathHelper.sqrt(d3);
          this.renderYawOffset = this.rotationYaw = this.rotationYawHead = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F + 180.0F;
          this.motionX += d0 / d5 * 0.75D - this.motionX;
          this.motionY += d1 / d5 * 0.75D - this.motionY;
          this.motionZ += d2 / d5 * 0.75D - this.motionZ;
          move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
        } else {
          move(MoverType.SELF, 0.0D, 0.10000000149011612D, 0.0D);
          this.renderYawOffset = this.rotationYawHead = this.rotationYaw += 10.0F;
          this.deathTicks++;
        }  
      if (this.deathTicks == 200 && !this.world.isRemote) {
        int i = 100000;
        while (i > 0) {
          int j = EntityXPOrb.getXPSplit(i);
          i -= j;
          this.world.spawnEntity((Entity)new EntityXPOrb(this.world, this.posX, this.posY + 8.0D, this.posZ, j));
        } 
        this.world.setBlockState(new BlockPos(this.posX, this.posY + 4.0D, this.posZ), Blocks.DRAGON_EGG.getDefaultState(), 3);
        setDead();
      } 
    } 
  }
  
  public void onPhaseUpdate() {
    if (getPhase() == 0 && getEnergy() <= getMaxEnergy() * 0.75F) {
      setPhase(2);
    } else if (getPhase() == 2 && getEnergy() <= getMaxEnergy() * 0.15F) {
      setPhase(4);
    } else if (getPhase() == 4 && getEnergy() < getMaxEnergy()) {
      change(getMaxEnergy() * 0.003F);
      if (getEnergy() >= getMaxEnergy())
        setPhase(6); 
    } else if (getPhase() == 6 && getEnergy() <= getMaxEnergy() * 0.6F) {
      setPhase(8);
    } else if (getPhase() == 8 && getEnergy() <= getMaxEnergy() * 0.2F) {
      setPhase(10);
    } 
    if (getAttackTarget() != null && getCooldown() <= 0 && getAttackTime() <= 0 && this.shouldChangeAI) {
      switch (getPhase()) {
        case 18:
          setAttack(0, 400, 100.0F);
          return;
        case 19:
          setAttack(0, 40, 100.0F);
          return;
      } 
      if (!setAttack(2, 140, 50.0F))
        if (!setAttack(6, 40, 60.0F))
          setAttack(1, 180, 100.0F);  
    } else if (this.shouldChangeAI) {
      setAttack(1, 180, 100.0F);
    } 
  }
  
  public void onAIUpdate(double distance) {
    if (this.world.getTopSolidOrLiquidBlock(getPosition()).getY() > 0) {
      this.targetYRelative = this.world.getTopSolidOrLiquidBlock(getPosition()).getY();
    } else {
      this.targetYRelative = 60.0D;
    } 
    if (this.ticksExisted % 200 == 0 && getAttackTarget() != null) {
      EngenderMod.console("Attacking: " + getAttackTarget().getName() + "\nCurrent Attack Mode: " + getAttackID() + "\nCurrent Phase: " + getPhase());
    } else if (this.ticksExisted % 200 == 0) {
      EngenderMod.console("Attacking: None\nCurrent Attack Mode: " + getAttackID() + "\nCurrent Phase: " + getPhase());
    } 
    switch (getPhase()) {
      case 0:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
          case 2:
            this.AI_1.swoop(distance);
            return;
          case 6:
            this.AI_1.dragonFireball();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 1:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
          case 2:
            this.AI_1.swoop(distance);
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 2:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 3:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 4:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 5:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 6:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 7:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 8:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 9:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.wander(distance);
        return;
      case 10:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.swoop(distance);
        return;
      case 11:
        switch (getAttackID()) {
          case 0:
            this.AI_1.stay();
            return;
        } 
        this.AI_1.swoop(distance);
        return;
      case 12:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 13:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 14:
        this.AI_1.instantdeath();
        return;
      case 15:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 16:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 17:
        switch (getAttackID()) {
        
        } 
        this.AI_1.stay();
        return;
      case 18:
        this.AI_1.leave();
        return;
      case 19:
        this.AI_1.instantdeath();
        return;
    } 
    this.AI_1.stay();
  }
  
  public void onCrystalDestroyed(EntityEnderCrystal crystal, BlockPos pos, DamageSource dmgSrc) {}
  
  private void onCrystalUpdate() {
    for (int i = 1; i <= this.crystals.size(); i++) {
      EntityEnderCrystal crystal = this.crystals.get(i - 1);
      if (crystal != null)
        if (crystal.isDead || getDistance((Entity)crystal) > 48.0D) {
          this.crystals.remove(crystal);
          i--;
        } else if (crystal.innerRotation % 10 == 0 && getEnergy() < getEnergyCap()) {
          setEnergy(getEnergy() + 2.0F);
        }  
    } 
    if (Maths.random(10) == 0) {
      List<EntityEnderCrystal> list = this.world.getEntitiesWithinAABB(EntityEnderCrystal.class, getEntityBoundingBox().grow(48.0D));
      for (EntityEnderCrystal entityendercrystal : list) {
        if (!this.crystals.contains(entityendercrystal))
          this.crystals.add(entityendercrystal); 
      } 
    } 
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setColor(BossInfo.Color.PURPLE);
  }
  
  public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {}
  
  public void addTrackingPlayer(EntityPlayerMP player) {
    super.addTrackingPlayer(player);
    this.bossInfo.addPlayer(player);
  }
  
  public void removeTrackingPlayer(EntityPlayerMP player) {
    super.removeTrackingPlayer(player);
    this.bossInfo.removePlayer(player);
  }
  
  public double[] getMovementOffsets(int p_70974_1_, float p_70974_2_) {
    if (getEnergy() <= 0.0F)
      p_70974_2_ = 0.0F; 
    p_70974_2_ = 1.0F - p_70974_2_;
    int i = this.ringBufferIndex - p_70974_1_ & 0x3F;
    int j = this.ringBufferIndex - p_70974_1_ - 1 & 0x3F;
    double[] adouble = new double[3];
    double d0 = this.ringBuffer[i][0];
    double d1 = MathHelper.wrapDegrees(this.ringBuffer[j][0] - d0);
    adouble[0] = d0 + d1 * p_70974_2_;
    d0 = this.ringBuffer[i][1];
    d1 = this.ringBuffer[j][1] - d0;
    adouble[1] = d0 + d1 * p_70974_2_;
    adouble[2] = this.ringBuffer[i][2] + (this.ringBuffer[j][2] - this.ringBuffer[i][2]) * p_70974_2_;
    return adouble;
  }
  
  public double getMountedYOffset() {
    return 3.25D;
  }
  
  protected boolean canFitPassenger(Entity passenger) {
    return (getPassengers().size() < 5);
  }
  
  public void updatePassenger(Entity passenger) {
    if (isPassenger(passenger)) {
      int i = getPassengers().indexOf(passenger);
      float f3 = this.renderYawOffset * 3.1415927F / 180.0F;
      float f11 = MathHelper.sin(f3);
      float f4 = MathHelper.cos(f3);
      if (i == 5)
        passenger.setPosition(this.dragonPartTail3.posX, this.dragonPartTail3.posY + 1.0D, this.dragonPartTail3.posZ); 
      if (i == 4)
        passenger.setPosition(this.dragonPartTail2.posX, this.dragonPartTail2.posY + 1.0D, this.dragonPartTail2.posZ); 
      if (i == 3)
        passenger.setPosition(this.dragonPartTail1.posX, this.dragonPartTail1.posY + 1.0D, this.dragonPartTail1.posZ); 
      if (i == 2)
        passenger.setPosition(this.posX + (f11 * 1.0F), this.posY + getEyeHeight() + 1.0D, this.posZ - (f4 * 1.0F)); 
      if (i == 1)
        passenger.setPosition(this.posX + (f11 * -1.0F), this.posY + getEyeHeight() + 1.0D, this.posZ - (f4 * -1.0F)); 
      if (i == 0)
        passenger.setPosition(this.dragonPartNeck.posX, this.dragonPartNeck.posY + 1.0D, this.dragonPartNeck.posZ); 
    } 
  }
  
  public void onKillEntity(EntityLivingBase entityLivingIn) {
    super.onKillEntity(entityLivingIn);
    if (!this.world.isRemote && this.deathTicks <= 0 && entityLivingIn instanceof EntityPlayer)
      if (getPhase() > 3 && getPhase() < 12) {
        sendMessage("kill.final", 10);
      } else {
        sendMessage("kill.generic", 10);
      }  
  }
  
  protected void despawnEntity() {}
  
  protected void collideWithNearbyEntities() {}
  
  protected void damageEntities(ExtendMultiPartEntityPart bodypart, float percentage, String damagetype, double velocity) {
    List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, bodypart.getEntityBoundingBox().grow(3.0D));
    for (EntityLivingBase entity : list) {
      if (entity != this && isEntityAlive() && (!(entity instanceof EntityPlayer) || (entity instanceof EntityPlayer && !((EntityPlayer)entity).isCreative() && !((EntityPlayer)entity).isSpectator()))) {
        entity.attackEntityFrom((DamageSource)new EntityDamageSource(damagetype, (Entity)this), (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * percentage);
        this.slowed = true;
        if (velocity > 0.0D && !(entity instanceof IEntityMultiPart) && entity.ticksExisted % 10 == 0) {
          double d2 = entity.posX - bodypart.posX;
          double d3 = entity.posZ - bodypart.posX;
          double d4 = d2 * d2 + d3 * d3;
          entity.addVelocity(d2 / d4 * velocity, velocity * 0.025D, d3 / d4 * velocity);
        } 
      } 
    } 
  }
  
  private float simplifyAngle(double p_70973_1_) {
    return (float)MathHelper.wrapDegrees(p_70973_1_);
  }
  
  protected boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_) {
    int i = MathHelper.floor(p_70972_1_.minX);
    int j = MathHelper.floor(p_70972_1_.minY);
    int k = MathHelper.floor(p_70972_1_.minZ);
    int l = MathHelper.floor(p_70972_1_.maxX);
    int i1 = MathHelper.floor(p_70972_1_.maxY);
    int j1 = MathHelper.floor(p_70972_1_.maxZ);
    boolean flag1 = false;
    for (int k1 = i; k1 <= l; k1++) {
      for (int l1 = j; l1 <= i1; l1++) {
        for (int i2 = k; i2 <= j1; i2++) {
          BlockPos blockpos = new BlockPos(k1, l1, i2);
          IBlockState iblockstate = this.world.getBlockState(blockpos);
          Block block = iblockstate.getBlock();
          if (EngenderConfig.mobs.grief && isEntityAlive() && !block.isAir(iblockstate, (IBlockAccess)this.world, blockpos) && 
            block.canEntityDestroy(iblockstate, (IBlockAccess)this.world, blockpos, (Entity)this) && 
            block != Blocks.END_PORTAL && block != Blocks.DRAGON_EGG && block != Blocks.BEDROCK && block != Blocks.END_STONE && block != Blocks.OBSIDIAN && block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && block != Blocks.END_GATEWAY)
            if (!this.world.isRemote) {
              flag1 = (this.world.setBlockToAir(blockpos) || flag1);
            } else {
              this.slowed = true;
            }  
        } 
      } 
    } 
    return EngenderConfig.mobs.grief;
  }
  
  public boolean attackEntityFromPart(MultiPartEntityPart dragonPart, DamageSource source, float damage) {
    if (isEntityInvulnerable(source))
      damage = 0.0F; 
    if (dragonPart != this.dragonPartHead)
      damage = damage / 5.0F + Math.min(damage, 1.0F); 
    if (isEntityInvulnerable(source) || damage < 0.01F || source == DamageSource.WITHER || source == DamageSource.IN_WALL || source == DamageSource.DROWN || source == DamageSource.CRAMMING || source == DamageSource.CACTUS)
      return false; 
    return super.attackEntityFrom(source, damage);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return attackEntityFromPart((MultiPartEntityPart)this.dragonPartBody, source, amount);
  }
  
  public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease) {
    double d1, d0 = entityIn.posX - this.posX;
    double d2 = entityIn.posZ - this.posZ;
    if (entityIn instanceof EntityLivingBase) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
      d1 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - this.posY + getEyeHeight();
    } else {
      d1 = ((entityIn.getEntityBoundingBox()).minY + (entityIn.getEntityBoundingBox()).maxY) / 2.0D - this.posY + getEyeHeight();
    } 
    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
    float f = (float)(MathHelper.atan2(d2, d0) * 57.29577951308232D) - 90.0F;
    float f1 = (float)-(MathHelper.atan2(d1, d3) * 57.29577951308232D);
    this.rotationPitch = updateRotation(this.rotationPitch, f1, maxPitchIncrease);
    this.rotationYawHead = updateRotation(this.rotationYawHead, f, maxYawIncrease);
  }
  
  private float updateRotation(float angle, float targetAngle, float maxIncrease) {
    float f = MathHelper.wrapDegrees(targetAngle - angle);
    if (f > maxIncrease)
      f = maxIncrease; 
    if (f < -maxIncrease)
      f = -maxIncrease; 
    return angle + f;
  }
  
  protected EntityBodyHelper createBodyHelper() {
    return (EntityBodyHelper)new EntityBodyHelperDragon((EntityLivingBase)this);
  }
  
  public World getWorld() {
    return this.world;
  }
  
  public boolean isEntityInvulnerable(DamageSource source) {
    return isPhase(4, 5) ? true : super.isEntityInvulnerable(source);
  }
  
  public boolean canBeCollidedWith() {
    return (this.world.getClosestPlayerToEntity((Entity)this, this.width) != null && isEntityAlive());
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadPartYOffset(int p_184667_1_, double[] p_184667_2_, double[] p_184667_3_) {
    BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
    float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
    return p_184667_1_ / f;
  }
  
  public Vec3d getHeadLookVec(float p_184665_1_) {
    BlockPos blockpos = this.world.getTopSolidOrLiquidBlock(WorldGenEndPodium.END_PODIUM_LOCATION);
    float f = Math.max(MathHelper.sqrt(getDistanceSqToCenter(blockpos)) / 4.0F, 1.0F);
    float f1 = 6.0F / f;
    float f2 = this.rotationPitch;
    float f3 = 1.5F;
    this.rotationPitch = -f1 * f3 * 5.0F;
    Vec3d vec3d = getLook(p_184665_1_);
    this.rotationPitch = f2;
    return vec3d;
  }
  
  public Entity[] getParts() {
    return (Entity[])this.dragonPartArray;
  }
  
  protected SoundEvent getAmbientSound() {
    return SoundEvents.ENTITY_ENDERDRAGON_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_ENDERDRAGON_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_ENDERMEN_STARE;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 0.1F : 10.0F;
  }
  
  public float getEyeHeight() {
    return 2.325F;
  }
  
  public int getMaxSpawnedInChunk() {
    return 1;
  }
  
  public boolean isPhase(int phase) {
    return (phase == getPhase());
  }
  
  public boolean isPhase(int phase, int phaseHard) {
    return (phase == getPhase()) ? true : ((phaseHard == getPhase()));
  }
  
  public int getPhase() {
    return (Integer) this.dataManager.get(phase);
  }
  
  public void setPhase(int newPhase) {
    if (!this.world.isRemote)
      switch (newPhase) {
        case 1:
          changeStats(15.0D, 2.0D, 0.5D, 25.0D, 1.2D, 256.0D);
          sendMessage("respawn", 5);
          break;
        case 2:
          changeStats(15.0D, 10.0D, 1.0D, 40.0D, 1.2D, 256.0D);
          sendMessage("phase2", 5);
          break;
        case 3:
          changeStats(20.0D, 15.0D, 1.0D, 60.0D, 1.55D, 256.0D);
          sendMessage("rephase2", 5);
          break;
        case 4:
          sendMessage("phase3", 5);
          sendMessage("phase3.1", true);
          break;
        case 5:
          sendMessage("rephase3", 5);
          sendMessage("rephase3.1", true);
          break;
        case 6:
          changeStats(10.0D, 10.0D, 1.0D, 200.0D, 1.5D, 256.0D);
          sendMessage("phase3.2", true);
          break;
        case 7:
          changeStats(20.0D, 10.0D, 1.0D, 235.0D, 2.0D, 256.0D);
          sendMessage("phase3.2", true);
          break;
        case 8:
          sendMessage("phase3.comment1", 5);
          break;
        case 9:
          sendMessage("rephase3.comment1", 5);
          break;
        case 10:
          changeStats(20.0D, 20.0D, 1.0D, 200.0D, 1.55D, 512.0D);
          sendMessage("phase3.comment2", 5);
          break;
        case 11:
          changeStats(30.0D, 30.0D, 1.0D, 235.0D, 2.25D, 512.0D);
          sendMessage("rephase3.comment2", 5);
          break;
        case 12:
          sendMessage("phase3.move.finalcannon", true);
          sendMessage("phase3.move.finalcannon", 5);
          break;
        case 13:
          changeStats(0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 128.0D);
          sendMessage("mercy", 10);
          break;
        case 14:
          sendMessage("death", 10);
          break;
        case 15:
          sendMessage("redeath", 10);
          break;
        case 16:
          sendMessage("save", 10);
          break;
        case 17:
          sendMessage("win", 10);
          break;
        case 18:
          setEnergy(getMaxEnergy());
          changeStats(30.0D, 30.0D, 1.0D, 0.0D, 0.5D, 512.0D);
          sendMessage("cheat.leave", 5);
          break;
        case 19:
          sendMessage("cheat.instakill", 5);
          break;
        default:
          changeStats(10.0D, 0.0D, 0.5D, 20.0D, 1.02D, 128.0D);
          sendMessage("spawn", 5);
          break;
      }  
    this.forceNewTarget = true;
    setAttack(1, 80, 100.0F);
    getDataManager().set(phase, newPhase);
  }
  
  public boolean isAttackID(int id) {
    return (getAttackID() == id);
  }
  
  public int getAttackID() {
    return (Integer) this.dataManager.get(attack);
  }
  
  public void setAttackID(int newAttack) {
    getDataManager().set(attack, newAttack);
  }
  
  public int getAttackTime() {
    return (Integer) this.dataManager.get(attackTicks);
  }
  
  public void setAttackTime(int ticks) {
    getDataManager().set(attackTicks, ticks);
  }
  
  public int getCooldown() {
    return (Integer) this.dataManager.get(cooldown);
  }
  
  public void setCooldown(int newCooldown) {
    getDataManager().set(cooldown, newCooldown);
  }
  
  public boolean setAttack(int id, int length, float chance) {
    if (Maths.chance(chance)) {
      setAttackID(id);
      if (length > 0)
        setAttackTime(length); 
      return true;
    } 
    return false;
  }
  
  public boolean getHardmode() {
    return (Boolean) this.dataManager.get(isHardmode);
  }
  
  public void setHardmode(boolean hardmode) {
    getDataManager().set(isHardmode, hardmode);
  }
  
  protected void change(float energy) {
    if (getEnergy() < getMaxEnergy())
      setEnergy(getEnergy() + MathHelper.clamp(energy, 0.0F, getMaxEnergy())); 
  }
  
  protected float getEnergy() {
    return (Float) this.dataManager.get(energy);
  }
  
  protected void setEnergy(float newEnergy) {
    getDataManager().set(energy, newEnergy);
  }
  
  protected float getMaxEnergy() {
    return (Float) this.dataManager.get(maxEnergy);
  }
  
  protected void setMaxEnergy(float newEnergy) {
    getDataManager().set(maxEnergy, newEnergy);
  }
  
  public boolean isNonBoss() {
    return EngenderMod.sensorsShowJzahars(this.world);
  }
  
  private float getEnergyCap() {
    switch (getPhase()) {
      case 1:
        return getMaxEnergy() * 0.75F;
      case 5:
        return getMaxEnergy() * 0.5F;
    } 
    return getMaxEnergy();
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.ENDER;
  }
  
  public float getDamageCap() {
    return EngenderConfig.nightmareMode ? 25.0F : 50.0F;
  }
  
  public float getDamageReduction(DamageSource source, Entity entity) {
    return (entity == null) ? 1.0F : (!entity.isNonBoss() ? 0.9F : (!(entity instanceof EntityPlayer) ? ((entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase) ? 0.85F : ((getPhase() < 2) ? 0.8F : 0.75F)) : ((getPhase() > 3) ? 0.98F : 1.0F)));
  }
  
  protected void damageEntity(DamageSource source, float amount) {
    if (!isEntityInvulnerable(source)) {
      amount = ForgeHooks.onLivingHurt((EntityLivingBase)this, source, amount);
      if (amount <= 0.0F)
        return; 
      amount = applyArmorCalculations(source, amount);
      amount = applyPotionDamageCalculations(source, amount);
      float f = amount;
      amount = Math.max(amount - getAbsorptionAmount(), 0.0F);
      setAbsorptionAmount(getAbsorptionAmount() - f - amount);
      amount = ForgeHooks.onLivingDamage((EntityLivingBase)this, source, amount);
      if (amount != 0.0F) {
        float f1 = getEnergy();
        getCombatTracker().trackDamage(source, f1, amount);
        setEnergy(f1 - amount);
        setAbsorptionAmount(getAbsorptionAmount() - amount);
      } 
    } 
  }
  
  public boolean isEntityAlive() {
    return (getEnergy() >= 0.0F);
  }
  
  public boolean shootBall(Entity entity) {
    if (entity != null) {
      shootBall(entity.posX, entity.posY, entity.posZ);
      return true;
    } 
    return false;
  }
  
  public void shootBall(double posX, double posY, double posZ) {
    if (!this.world.isRemote) {
      double d6 = this.dragonPartHead.posX;
      double d7 = this.dragonPartHead.posY + 0.5D;
      double d8 = this.dragonPartHead.posZ;
      double d9 = posX - d6;
      double d10 = posY + 1.0D - d7;
      double d11 = posZ - d8;
      this.world.playEvent((EntityPlayer)null, 1016, new BlockPos((Entity)this), 0);
      EntityDragonFireball entitydragonfireball = new EntityDragonFireball(this.world, (EntityLivingBase)this, d9, d10, d11);
      entitydragonfireball.posX = d6;
      entitydragonfireball.posY = d7;
      entitydragonfireball.posZ = d8;
      this.world.spawnEntity((Entity)entitydragonfireball);
    } 
  }
  
  private void sendMessage(String key, boolean isStatus) {
    sendMessage(key, 0, isStatus);
  }
  
  private void sendMessage(String key, int iter) {
    sendMessage(key, iter, false);
  }
  
  private void sendMessage(String key, int iter, boolean isStatus) {
    if (!this.world.isRemote) {
      String iteration = (iter > 1) ? ("." + Maths.random(1, iter)) : "";
      String status = isStatus ? "status." : "";
      for (EntityPlayer entityplayer : this.world.playerEntities) {
        entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation(TextFormatting.LIGHT_PURPLE + I18n.format("entity.darkness." + status + key + iteration, new Object[] { entityplayer.getName() }), new Object[0]), isStatus);
      } 
    } 
  }
  
  public void changeStats(double defense, double toughness, double kbr, double attack, double speed, double followRange) {
    if (getEntityAttribute(SharedMonsterAttributes.ARMOR).getBaseValue() != defense)
      getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(defense); 
    if (getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getBaseValue() != toughness)
      getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(toughness); 
    if (getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getBaseValue() != kbr)
      getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(kbr); 
    if (getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() != attack)
      getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attack); 
    if (getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() != speed)
      getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(speed); 
    if (getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() != followRange)
      getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(followRange); 
  }
}
