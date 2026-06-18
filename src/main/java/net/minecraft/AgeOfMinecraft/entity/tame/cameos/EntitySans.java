package net.minecraft.AgeOfMinecraft.entity.tame.cameos;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.EnderTeleportEvent;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EntitySans extends EntityTameBase implements IRangedAttackMob, Light, Undead {
  protected static final DataParameter<EnumFacing> DIRECTION = EntityDataManager.createKey(EntitySans.class, DataSerializers.FACING);
  
  private static final DataParameter<Integer> ATTACKID = EntityDataManager.createKey(EntitySans.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> EYEID = EntityDataManager.createKey(EntitySans.class, DataSerializers.VARINT);
  
  private int attacks;
  
  private int attackSwitchTimer;
  
  public int attackInterval = 2;
  
  private boolean tookTurn;
  
  private SansSpeech talker;
  
  public EntitySans(World worldIn) {
    super(worldIn);
    this.attackSwitchTimer = 1;
    this.isOffensive = true;
    this.isImmuneToFire = true;
    this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 0.8D, 32.0F, 8.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 0.0D, 10, 64.0F));
    setSize(0.5F, 1.65F);
  }
  
  public int playMusic() {
    return isHero() ? 101 : 100;
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.ITALIC;
  }
  
  public String getDescName() {
    return "sans";
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    if (this.attackSwitchTimer <= 0) {
      double d10;
      double d9;
      double d8;
      float rot;
      double d7;
      double dI;
      double d0;
      double d01;
      float f1;
      double d15;
      double d14;
      double d13;
      float f2;
      double d12;
      double dII;
      double d1;
      double d3;
      double d11;
      double d20;
      double d19;
      double d18;
      double d17;
      double d2;
      EntityBoneAttack bone1;
      double d16;
      double d23;
      double d22;
      EntityGasterBlaster entityGasterBlaster1;
      double d21;
      EntityGasterBlaster blaster;
      double d31;
      double d4;
      EntityBoneAttack entityBoneAttack2;
      double d24;
      EntityBoneAttack entityBoneAttack1;
      EntityBoneAttack bone2;
      double d5;
      double d25;
      EntityBoneAttack bone3;
      EntityBoneAttack bone4;
      EntityBoneAttack bone;
      double d26;
      double d6;
      EntityBoneAttack entityBoneAttack3;
      EntityBoneAttack entityBoneAttack4;
      switch (getAttackType()) {
        case 1:
          d10 = target.posX + this.rand.nextGaussian() * 8.0D;
          d15 = target.posY;
          d20 = target.posZ + this.rand.nextGaussian() * 8.0D;
          d23 = target.posX - d10;
          d4 = target.posY - d15;
          d5 = target.posZ - d20;
          bone = new EntityBoneAttack(this.world, d10, d15, d20, d23, d4, d5);
          this.world.spawnEntity(bone);
          bone.setBoneType(1);
          bone.setBlue(true);
          bone.shootingEntity = this;
          bone.motionX *= 0.75D;
          bone.motionZ *= 0.75D;
          break;
        case 2:
          d10 = target.posX + this.rand.nextDouble() * 16.0D - 8.0D;
          d15 = target.posY + 16.0D;
          d20 = target.posZ + this.rand.nextDouble() * 16.0D - 8.0D;
          d23 = target.posX - d10;
          d4 = target.posY + ((target.height > 8.0F) ? 4.0D : (target.height * 0.5D)) - d15;
          d5 = target.posZ - d20;
          bone = new EntityBoneAttack(this.world, d10, d15, d20, d23, d4, d5);
          this.world.spawnEntity(bone);
          bone.setBlue((this.rand.nextInt(3) == 0));
          bone.shootingEntity = this;
          bone.playSound(ESound.bonelaunch, 1.0F, 1.0F);
          break;
        case 3:
          if (this.rand.nextBoolean()) {
            double d27 = target.posX + 12.0D;
            double d28 = target.posX + 10.0D;
            double d29 = target.posX - d28;
            double d30 = target.posX - d27;
            EntityBoneAttack entityBoneAttack5 = new EntityBoneAttack(this.world, d28, target.posY - target.motionY - 0.5D, target.posZ, d29, target.posY, target.posZ);
            this.world.spawnEntity(entityBoneAttack5);
            entityBoneAttack5.shootingEntity = this;
            entityBoneAttack5.setBoneType(2);
            entityBoneAttack5.accelerationZ = target.posZ - entityBoneAttack5.posZ;
            entityBoneAttack5.accelerationY = entityBoneAttack5.posY - entityBoneAttack5.posY;
            entityBoneAttack5.motionX -= 0.6D;
            EntityBoneAttack entityBoneAttack6 = new EntityBoneAttack(this.world, d27, target.posY - target.motionY, target.posZ, d30, target.posY, target.posZ);
            this.world.spawnEntity(entityBoneAttack6);
            entityBoneAttack6.shootingEntity = this;
            entityBoneAttack6.setBoneType(2);
            entityBoneAttack6.setBlue(true);
            entityBoneAttack6.accelerationZ = target.posZ - entityBoneAttack6.posZ;
            entityBoneAttack6.accelerationY = entityBoneAttack6.posY - entityBoneAttack6.posY;
            entityBoneAttack6.motionX -= 0.6D;
            break;
          } 
          d9 = target.posX - 12.0D;
          d14 = target.posX - 10.0D;
          d19 = target.posX - d14;
          d22 = target.posX - d9;
          entityBoneAttack2 = new EntityBoneAttack(this.world, d14, target.posY - target.motionY - 0.5D, target.posZ, d19, target.posY, target.posZ);
          this.world.spawnEntity(entityBoneAttack2);
          entityBoneAttack2.shootingEntity = this;
          entityBoneAttack2.setBoneType(2);
          entityBoneAttack2.accelerationZ = target.posZ - entityBoneAttack2.posZ;
          entityBoneAttack2.accelerationY = entityBoneAttack2.posY - entityBoneAttack2.posY;
          entityBoneAttack2.motionX += 0.6D;
          bone2 = new EntityBoneAttack(this.world, d9, target.posY - target.motionY, target.posZ, d22, target.posY, target.posZ);
          this.world.spawnEntity(bone2);
          bone2.shootingEntity = this;
          bone2.setBoneType(2);
          bone2.setBlue(true);
          bone2.accelerationZ = target.posZ - bone2.posZ;
          bone2.accelerationY = bone2.posY - bone2.posY;
          bone2.motionX += 0.6D;
          break;
        case 4:
          if (getEyeType() != 1)
            setEyeType(1); 
          d8 = target.posX + MathHelper.sin(this.rand.nextFloat() * 360.0F) * 16.0D;
          d13 = target.posY + 0.5D;
          d18 = target.posZ + MathHelper.cos(this.rand.nextFloat() * 360.0F) * 16.0D;
          entityGasterBlaster1 = new EntityGasterBlaster(this.world, d8, d13, d18, target.posX, target.posY, target.posZ);
          this.world.spawnEntity((Entity)entityGasterBlaster1);
          entityGasterBlaster1.targetpointx = target.posX;
          entityGasterBlaster1.targetpointy = entityGasterBlaster1.posY + entityGasterBlaster1.getEyeHeight() + target.motionY * 10.0D;
          entityGasterBlaster1.targetpointz = target.posZ;
          entityGasterBlaster1.shootingEntity = this;
          entityGasterBlaster1.playSound(ESound.gasterblasterspawn, 3.0F, 0.9F);
          break;
        case 5:
          setEnergy(getEnergy() - 0.1F);
          if (getEyeType() != 2) {
            setEyeType(2);
            playSound(ESound.sansblink, 5.0F, 1.0F);
          } 
          rot = this.rotationYawHead * 0.017453292F;
          f1 = MathHelper.sin(rot);
          f2 = MathHelper.cos(rot);
          switch (this.rand.nextInt(6)) {
            case 1:
              setHandDirection(EnumFacing.DOWN);
              target.motionX = 0.0D;
              target.motionY = -2.0D;
              target.motionZ = 0.0D;
              target.fallDistance += 10.0F;
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 2:
              setHandDirection(EnumFacing.NORTH);
              target.motionY += 0.5D;
              target.motionX = -(f1 * 2.0F);
              target.motionZ = -(f2 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 3:
              setHandDirection(EnumFacing.SOUTH);
              target.motionY += 0.5D;
              target.motionX = (f1 * 2.0F);
              target.motionZ = (f2 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 4:
              setHandDirection(EnumFacing.EAST);
              target.motionY += 0.5D;
              target.motionX = (f2 * 2.0F);
              target.motionZ = (f1 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 5:
              setHandDirection(EnumFacing.WEST);
              target.motionY += 0.5D;
              target.motionX = -(f2 * 2.0F);
              target.motionZ = -(f1 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
          } 
          setHandDirection(EnumFacing.UP);
          target.motionX = 0.0D;
          target.motionY = 2.0D;
          target.motionZ = 0.0D;
          if (target instanceof EntityPlayerMP)
            ((EntityPlayerMP)target).connection.sendPacket((Packet)new SPacketEntityVelocity((Entity)target)); 
          break;
        case 6:
          d7 = target.posX + MathHelper.sin(this.rand.nextFloat() * 360.0F) * 16.0D;
          d12 = target.posY + 0.5D;
          d18 = target.posZ + MathHelper.cos(this.rand.nextFloat() * 360.0F) * 16.0D;
          entityGasterBlaster1 = new EntityGasterBlaster(this.world, d7, d12, d18, target.posX, target.posY, target.posZ);
          this.world.spawnEntity((Entity)entityGasterBlaster1);
          entityGasterBlaster1.targetpointx = target.posX;
          entityGasterBlaster1.targetpointy = entityGasterBlaster1.posY + entityGasterBlaster1.getEyeHeight() + target.motionY * 10.0D;
          entityGasterBlaster1.targetpointz = target.posZ;
          entityGasterBlaster1.shootingEntity = this;
          entityGasterBlaster1.playSound(ESound.gasterblasterspawn, 3.0F, 1.0F);
          entityGasterBlaster1.setSmall(true);
          break;
        case 7:
          dI = 3.0D + MathHelper.sin(this.ticksExisted * 0.1F) * 3.0D;
          dII = 3.0D - MathHelper.sin(this.ticksExisted * 0.1F) * 3.0D;
          d17 = target.posX + 15.0D;
          d21 = target.posX - d17;
          d24 = target.posZ + dI;
          d25 = target.posZ + dI - d24;
          d26 = target.posZ - dII;
          d6 = target.posZ - dII - d26;
          entityBoneAttack3 = new EntityBoneAttack(this.world, d17, target.posY - 1.0D, d24, d21, target.posY, d25);
          this.world.spawnEntity(entityBoneAttack3);
          entityBoneAttack3.shootingEntity = this;
          entityBoneAttack3.setBoneType(3);
          entityBoneAttack3.accelerationY = entityBoneAttack3.posY - entityBoneAttack3.posY;
          entityBoneAttack3.accelerationZ = entityBoneAttack3.posZ - entityBoneAttack3.posZ;
          entityBoneAttack3.accelerationX *= 0.5D;
          entityBoneAttack4 = new EntityBoneAttack(this.world, d17, target.posY - 1.0D, d26, d21, target.posY, d6);
          this.world.spawnEntity(entityBoneAttack4);
          entityBoneAttack4.shootingEntity = this;
          entityBoneAttack4.setBoneType(3);
          entityBoneAttack4.accelerationY = entityBoneAttack4.posY - entityBoneAttack4.posY;
          entityBoneAttack4.accelerationZ = entityBoneAttack4.posZ - entityBoneAttack4.posZ;
          entityBoneAttack4.accelerationX *= 0.5D;
          break;
        case 10:
          d0 = target.posX + MathHelper.cos(this.ticksExisted * 0.05F) * 8.0D;
          d1 = target.posY + 0.5D;
          d2 = target.posZ + MathHelper.sin(this.ticksExisted * 0.05F) * 8.0D;
          blaster = new EntityGasterBlaster(this.world, d0, d1, d2, target.posX, target.posY, target.posZ);
          this.world.spawnEntity((Entity)blaster);
          blaster.targetpointx = target.posX;
          blaster.targetpointy = d1 + 0.5D + target.motionY * 10.0D;
          blaster.targetpointz = target.posZ;
          blaster.shootingEntity = this;
          blaster.playSound(ESound.gasterblasterspawn, 3.0F, 1.0F);
          blaster.setSmall(true);
          break;
        case 11:
          d0 = target.posX + 5.0D;
          d3 = target.posX - d0;
          bone1 = new EntityBoneAttack(this.world, d0, target.posY - 1.5D + target.motionY, target.posZ, d3, target.posY, target.posZ);
          this.world.spawnEntity(bone1);
          bone1.shootingEntity = this;
          bone1.setBoneType(4);
          bone1.accelerationZ = target.posZ - bone1.posZ;
          bone1.accelerationY = bone1.posY - bone1.posY;
          break;
        default:
          d01 = target.posX - 10.0D;
          d11 = target.posX + 10.0D;
          d16 = target.posX - d11;
          d31 = target.posX - d01;
          entityBoneAttack1 = new EntityBoneAttack(this.world, d11, target.posY - 1.5D + target.motionY, target.posZ, d16, target.posY, target.posZ);
          this.world.spawnEntity(entityBoneAttack1);
          entityBoneAttack1.shootingEntity = this;
          entityBoneAttack1.setBoneType(1);
          entityBoneAttack1.accelerationZ = target.posZ - entityBoneAttack1.posZ;
          entityBoneAttack1.accelerationY = entityBoneAttack1.posY - entityBoneAttack1.posY;
          entityBoneAttack1.motionX -= 0.5D;
          bone2 = new EntityBoneAttack(this.world, d01, target.posY - 1.5D + target.motionY, target.posZ, d31, target.posY, target.posZ);
          this.world.spawnEntity(bone2);
          bone2.shootingEntity = this;
          bone2.setBoneType(1);
          bone2.accelerationZ = target.posZ - bone2.posZ;
          bone2.accelerationY = bone2.posY - bone2.posY;
          bone2.motionX += 0.5D;
          bone3 = new EntityBoneAttack(this.world, d11, target.posY + target.height + 0.5D + target.motionY, target.posZ, d16, target.posY, target.posZ);
          this.world.spawnEntity(bone3);
          bone3.shootingEntity = this;
          bone3.setBoneType(1);
          bone3.accelerationZ = target.posZ - bone3.posZ;
          bone3.accelerationY = bone3.posY - bone3.posY;
          bone3.motionX -= 0.5D;
          bone4 = new EntityBoneAttack(this.world, d01, target.posY + target.height + 0.5D + target.motionY, target.posZ, d31, target.posY, target.posZ);
          this.world.spawnEntity(bone4);
          bone4.shootingEntity = this;
          bone4.setBoneType(1);
          bone4.accelerationZ = target.posZ - bone4.posZ;
          bone4.accelerationY = bone4.posY - bone4.posY;
          bone4.motionX += 0.5D;
          break;
      } 
    } 
    this.attacks++;
    switch (getAttackType()) {
      case 0:
        if (this.attacks >= (isHero() ? 16 : 8)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
        } 
        break;
      case 3:
        if (this.attacks >= (isHero() ? 12 : 6)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
        } 
        break;
      case 4:
        if (this.attacks >= (isHero() ? 24 : 12)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
          setEyeType(0);
        } 
        break;
      case 5:
        if (this.attacks >= (isHero() ? 16 : 8)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
          setEyeType(0);
          playSound(ESound.sansblink, 5.0F, 1.0F);
        } 
        break;
      case 6:
        if (this.attacks >= (isHero() ? 32 : 16)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
          setEyeType(0);
        } 
        break;
      case 7:
        if (this.attacks >= (isHero() ? 200 : 100)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
        } 
        break;
      case 10:
        if (this.attacks >= (isHero() ? 720 : 360)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
        } 
        break;
      case 11:
        if (this.attacks >= 1) {
          this.tookTurn = true;
          this.attackSwitchTimer = 200;
          this.attacks = 0;
        } 
      default:
        if (this.attacks >= (isHero() ? 40 : 20)) {
          this.tookTurn = true;
          this.attackSwitchTimer = 20;
          this.attacks = 0;
        } 
        break;
    } 
    if (!target.isEntityAlive() && target instanceof EntityPlayer)
      setEnergy(100.0F); 
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    entity.hurtResistantTime = 0;
    entity.playSound(ESound.bonehit, 0.1F, 1.0F);
  }
  
  public void karmicRetribution(EntityLivingBase entity, int karmaAmount) {
    if (this.world.getDifficulty() == EnumDifficulty.EASY)
      karmaAmount = Math.min(karmaAmount / 2, karmaAmount); 
    if (this.world.getDifficulty() == EnumDifficulty.HARD)
      karmaAmount = karmaAmount * 3 / 2; 
    if (entity.isEntityUndead())
      karmaAmount *= 2; 
    if (entity instanceof EntityTameBase && ((EntityTameBase)entity).isABoss())
      karmaAmount *= 3; 
    if (!entity.isNonBoss())
      karmaAmount *= 3; 
    if (entity instanceof EntityTameBase && ((EntityTameBase)entity).getTier() == EnumTier.TIER6)
      karmaAmount *= 5; 
    if (entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal || false)
      karmaAmount = 0; 
    if (entity.isEntityAlive() && entity.attackable() && !false && !(entity instanceof EntitySans)) {
      NBTTagCompound karma = entity.getEntityData();
      if (karma.hasKey("KR", 99)) {
        karma.setInteger("KR", karma.getInteger("KR") + karmaAmount);
      } else {
        karma.setInteger("KR", karmaAmount);
      } 
    } 
  }
  
  protected void entityInit() {
    super.entityInit();
    getDataManager().register(DIRECTION, EnumFacing.SOUTH);
    getDataManager().register(ATTACKID, Integer.valueOf(-1));
    getDataManager().register(EYEID, Integer.valueOf(0));
  }
  
  public int getAttackType() {
    return ((Integer)this.dataManager.get(ATTACKID)).intValue();
  }
  
  public void setAttackType(int id) {
    this.dataManager.set(ATTACKID, Integer.valueOf(id));
    setHandDirection(EnumFacing.SOUTH);
    setEyeType(0);
    switch (id) {
      case 2:
        this.attackInterval = isHero() ? 2 : 5;
        return;
      case 3:
        this.attackInterval = isHero() ? 7 : 15;
        return;
      case 4:
        this.attackInterval = isHero() ? 10 : 20;
        return;
      case 5:
        this.attackInterval = (getEnergy() <= 50.0F) ? (isHero() ? 5 : 10) : (isHero() ? 10 : 20);
        return;
      case 6:
        this.attackInterval = isHero() ? 5 : 10;
        return;
      case 7:
        this.attackInterval = 2;
        return;
      case 10:
        this.attackInterval = 1;
        return;
      case 11:
        this.attackInterval = 400;
        return;
    } 
    this.attackInterval = isHero() ? 5 : 10;
  }
  
  public int getEyeType() {
    return ((Integer)this.dataManager.get(EYEID)).intValue();
  }
  
  public void setEyeType(int age) {
    this.dataManager.set(EYEID, Integer.valueOf(age));
  }
  
  public EnumFacing getHandDirection() {
    return (EnumFacing)this.dataManager.get(DIRECTION);
  }
  
  public void setHandDirection(EnumFacing direction) {
    this.dataManager.set(DIRECTION, direction);
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(FITTNESS).setBaseValue(1.0D);
    getEntityAttribute(STRENGTH).setBaseValue(1.0D);
    getEntityAttribute(STAMINA).setBaseValue(100.0D);
    getEntityAttribute(INTELLIGENCE).setBaseValue(100.0D);
    getEntityAttribute(DEXTERITY).setBaseValue(100.0D);
    getEntityAttribute(AGILITY).setBaseValue(100.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setAttackType(tagCompund.getInteger("Attack"));
    setEyeType(tagCompund.getInteger("Eye"));
    this.dataManager.set(DIRECTION, EnumFacing.byIndex(tagCompund.getByte("Direction")));
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Attack", getAttackType());
    tagCompound.setInteger("Eye", getEyeType());
    tagCompound.setByte("Direction", (byte)((EnumFacing)this.dataManager.get(DIRECTION)).getIndex());
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public boolean isCameo() {
    return true;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setPercent(getEnergy() / 100.0F);
    this.bossInfo.setColor(BossInfo.Color.WHITE);
  }
  
  public String getName() {
    return "sans";
  }
  
  public boolean attemptTeleport(double x, double y, double z) {
    double d0 = this.posX;
    double d1 = this.posY;
    double d2 = this.posZ;
    this.posX = x;
    this.posY = y;
    this.posZ = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos((Entity)this);
    World world = this.world;
    if (world.isBlockLoaded(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.getY() > 0) {
        BlockPos blockpos1 = blockpos.down();
        IBlockState iblockstate = world.getBlockState(blockpos1);
        if (iblockstate.isOpaqueCube()) {
          flag1 = true;
          continue;
        } 
        this.posY--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        setPositionAndUpdate(this.posX, this.posY, this.posZ);
        if (isBeingRidden())
          getControllingPassenger().setPositionAndUpdate(this.posX, this.posY, this.posZ); 
        if (world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty())
          flag = true; 
      } 
    } 
    if (!flag) {
      setPositionAndUpdate(d0, d1, d2);
      return false;
    } 
    if (this instanceof net.minecraft.entity.EntityCreature)
      getNavigator().clearPath(); 
    return true;
  }
  
  protected boolean teleportRandomly() {
    double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 16.0D;
    double d1 = this.posY + (this.rand.nextDouble() - 0.5D) * 16.0D;
    double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 16.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportToEntity(Entity p_70816_1_) {
    double d1 = p_70816_1_.posX + (this.rand.nextDouble() - 0.5D) * 16.0D;
    double d2 = p_70816_1_.posY + (this.rand.nextDouble() - 0.5D) * 16.0D;
    double d3 = p_70816_1_.posZ + (this.rand.nextDouble() - 0.5D) * 16.0D;
    return teleportTo(d1, d2, d3);
  }
  
  protected boolean teleportTo(double x, double y, double z) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    boolean flag = attemptTeleport(event.getTargetX(), event.getTargetY(), event.getTargetZ());
    if (flag) {
      this.world.playSound((EntityPlayer)null, this.prevPosX, this.prevPosY, this.prevPosZ, ESound.sansblink, getSoundCategory(), 1.0F, 1.0F);
      playSound(ESound.sansblink, 1.0F, 1.0F);
      this.motionX = this.motionY = this.motionZ = 0.0D;
      this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    } 
    return flag;
  }
  
  public void onLivingUpdate() {
    super.onLivingUpdate();
    getEntityAttribute(FITTNESS).setBaseValue(1.0D);
    getEntityAttribute(STRENGTH).setBaseValue(1.0D);
    getEntityAttribute(STAMINA).setBaseValue(100.0D);
    getEntityAttribute(INTELLIGENCE).setBaseValue(100.0D);
    getEntityAttribute(DEXTERITY).setBaseValue(100.0D);
    getEntityAttribute(AGILITY).setBaseValue(100.0D);
    if (getEnergy() <= 50.0F)
      spawnStressParticle(); 
    if (getEnergy() <= 20.0F)
      spawnStressParticle(); 
    if (this.talker == null && this.ticksExisted == 800 && getAttackType() < 0)
      this.talker = new SansSpeech(this, "idlequote", 23, ESound.sanstalk, 2); 
    if (this.ticksExisted % 100 == 0)
      setEnergy(getEnergy() + 1.0F); 
    if (getAttackType() == -1)
      setEnergy(getEnergy() + 0.05F); 
    if (!this.world.isRemote && getAttackTarget() != null && !(getAttackTarget() instanceof EntityPlayer) && this.tookTurn) {
      this.tookTurn = false;
      setAttackType(this.rand.nextInt(8));
    } 
    if (!this.world.isRemote && getAttackTarget() != null && getAttackTarget() instanceof EntityTameBase && ((EntityTameBase)getAttackTarget()).getFakeHealth() > 0.0F)
      setAttackType(11); 
    if (!this.world.isRemote && getAttackTarget() == null)
      setAttackType(-1); 
    if (getAttackType() != 10 && getEnergy() <= 1.0F)
      setAttackType(10); 
    if (this.posY < -5.0D)
      teleportRandomly(); 
    if (!this.tookTurn && this.attackSwitchTimer > 0)
      this.attackSwitchTimer--; 
    if (this.motionY > 1.5D)
      teleportRandomly(); 
    if (getAttackTarget() != null && getDistance((Entity)getAttackTarget()) > 32.0D && this.rand.nextInt(500) == 0)
      teleportToEntity((Entity)getAttackTarget()); 
    if (this.talker != null)
      if (this.talker.ended) {
        this.talker = null;
      } else {
        this.talker.updateSpeech();
      }  
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (source.getDamageType() == "sans")
      return true; 
    if (isEntityInvulnerable(source) || source.getTrueSource() == null) {
      if (!this.world.isRemote)
        teleportRandomly(); 
      return false;
    } 
    if (getEnergy() > 0.0F && getFakeHealth() <= 0.0F) {
      float cantkeepdodgingforever = 1.0F;
      if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityPlayer) {
        cantkeepdodgingforever = 5.0F;
        if (this.talker == null && getEnergy() >= 98.0F)
          this.talker = new SansSpeech(this, "firstattacked", 44, ESound.sanstalk, 2); 
        setEnergy(getEnergy() - 3.0F);
      } 
      if (!this.world.isRemote && source.getTrueSource() != null && this.tookTurn) {
        this.tookTurn = false;
        setAttackType(this.rand.nextInt(8));
      } 
      if (!this.world.isRemote)
        if (teleportRandomly() && this.rand.nextInt(5) == 0) {
          setEnergy(getEnergy() - cantkeepdodgingforever);
        } else {
          setEnergy(getEnergy() - 1.0F);
          this.motionY--;
          this.motionX = this.rand.nextDouble() * 2.0D - 1.0D;
          this.motionZ = this.rand.nextDouble() * 2.0D - 1.0D;
          if (this.motionX > 0.0D)
            this.motionX++; 
          if (this.motionX < 0.0D)
            this.motionX--; 
          if (this.motionZ > 0.0D)
            this.motionZ++; 
          if (this.motionZ < 0.0D)
            this.motionZ--; 
        }  
      return false;
    } 
    return super.attackEntityFrom(source, amount);
  }
  
  public class SansSpeech {
    EntitySans sans;
    
    String name;
    
    int length;
    
    SoundEvent soundbite;
    
    int delay;
    
    int timer;
    
    int id;
    
    public boolean ended;
    
    public SansSpeech(EntitySans speaker, String thename, int thelength, SoundEvent bite, int timedilation) {
      this.sans = speaker;
      this.name = thename;
      this.length = thelength;
      this.soundbite = bite;
      this.delay = timedilation;
    }
    
    public void updateSpeech() {
      this.timer++;
      if (!this.sans.world.isRemote && this.timer >= this.delay && !this.ended) {
        for (EntityPlayer player : this.sans.world.playerEntities) {
          player.sendStatusMessage((ITextComponent)new TextComponentTranslation("speech.sans." + this.name + "." + this.id, new Object[0]), true);
          if (this.soundbite != null)
            this.sans.world.playSound(null, player.getPosition(), this.soundbite, this.sans.getSoundCategory(), 1.0F, 1.0F); 
          this.timer = 0;
          this.id++;
        } 
        if (this.id >= this.length)
          this.ended = true; 
      } 
    }
  }
}

