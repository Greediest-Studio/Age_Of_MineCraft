package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

import com.google.common.base.Predicate;
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
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
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
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCommandBlockWither extends EntityTameBase implements IRangedAttackMob, Massive, Armored, Flying, Undead {
  private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.createKey(EntityCommandBlockWither.class, DataSerializers.VARINT);
  
  private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(EntityCommandBlockWither.class, DataSerializers.VARINT);
  
  private float[] xRotationHeads = new float[2];
  
  private float[] yRotationHeads = new float[2];
  
  private float[] xRotOHeads = new float[2];
  
  private float[] yRotOHeads = new float[2];
  
  private int[] nextHeadUpdate = new int[2];
  
  private int[] idleHeadUpdates = new int[2];
  
  private int blockBreakCounter;
  
  public EntityCommandBlockWither(World worldIn) {
    super(worldIn);
    setHealth(getMaxHealth());
    setSize(1.0F, 3.3F);
    this.isOffensive = true;
    this.isImmuneToFire = true;
    ((PathNavigateGround)getNavigator()).setCanSwim(true);
    this.tasks.addTask(0, new AIDoNothing());
    this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowLeader(this, 3.0D, 64.0F, 12.0F));
    this.tasks.addTask(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 1.0D, 60, 32.0F));
    this.experienceValue = 500;
    setLevel(300);
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return "wither_notstorm";
  }
  
  public int playMusic() {
    return 11;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    if (getSize() >= 12250 && this.ticksExisted % 2 == 0) {
      this.bossInfo.setColor(BossInfo.Color.PURPLE);
    } else {
      this.bossInfo.setColor(BossInfo.Color.RED);
    } 
    this.bossInfo.setDarkenSky(true);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.WITHER_STORM;
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20.0D);
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    return "Wither (Wither Storm)";
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
    return 24.0F + this.rand.nextFloat() * 32.0F;
  }
  
  public float getDefaultDexterityStat() {
    return 48.0F + this.rand.nextFloat() * 32.0F;
  }
  
  public float getDefaultAgilityStat() {
    return 48.0F + this.rand.nextFloat() * 32.0F;
  }
  
  public float getDefaultFittnessStat() {
    return 1.0F;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(INVULNERABILITY_TIME, 0);
    this.dataManager.register(SIZE, 0);
  }
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Invul", getInvulTime());
    tagCompound.setInteger("Growth", getSize());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    setInvulTime(tagCompund.getInteger("Invul"));
    Grow(tagCompund.getInteger("Growth"));
  }
  
  public int getSize() {
    return (Integer) this.dataManager.get(SIZE);
  }
  
  public void Grow(int p_82215_1_) {
    this.dataManager.set(SIZE, p_82215_1_);
  }
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  protected SoundEvent getAmbientSound() {
    return (getSize() >= 5000) ? ESound.commandBlockWitherIdle : SoundEvents.ENTITY_WITHER_AMBIENT;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return SoundEvents.ENTITY_WITHER_HURT;
  }
  
  protected SoundEvent getDeathSound() {
    return SoundEvents.ENTITY_WITHER_DEATH;
  }
  
  public float getEyeHeight() {
    return 2.75F;
  }
  
  public double getMountedYOffset() {
    return this.height * 0.945D;
  }
  
  public boolean canBePushed() {
    return false;
  }
  
  protected void onDeathUpdate() {
    super.onDeathUpdate();
    if (!this.world.isRemote)
      if (this.deathTime == 1)
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.world.playerEntities) {
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.1F);
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
            this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 0.9F);
            entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation("§4" + getOwner().getName() + "'s Wither Storm has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).sendMessage((ITextComponent)new TextComponentTranslation("Your Wither Storm has been destroyed!", new Object[0]));
        }   
  }
  
  public void onLivingUpdate() {
    this.experienceValue = getSize();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((getSize() < 300) ? 300.0D : getSize());
    if (this.posY < 0.0D)
      this.motionY += (0.5D - this.motionY) * 0.6000000238418579D; 
    if (this.motionX > 1.0D)
      this.motionX = 1.0D; 
    if (this.motionZ < -1.0D)
      this.motionZ = -1.0D; 
    if (this.motionY > 1.0D)
      this.motionY = 1.0D; 
    if (this.motionY < -1.0D)
      this.motionY = -1.0D; 
    if (this.motionX > 1.0D)
      this.motionX = 1.0D; 
    if (this.motionZ < -1.0D)
      this.motionZ = -1.0D; 
    if ((this.ticksExisted + getEntityId()) % 100 == 0)
      playSound(ESound.commandBlockWitherHum, (getSize() >= 5250) ? 0.25F : 5.0F, 1.0F); 
    if (getSize() >= 12500 && !this.world.isRemote) {
      EntityWitherStorm witherstorm = new EntityWitherStorm(this.world);
      witherstorm.copyLocationAndAnglesFrom((Entity)this);
      witherstorm.setNoAI(isAIDisabled());
      this.world.spawnEntity((Entity)witherstorm);
      witherstorm.setOwnerId(getOwnerId());
      if (hasCustomName())
        witherstorm.setCustomNameTag(getCustomNameTag()); 
      witherstorm.Grow(12500);
      Grow(0);
      onKillCommand();
    } 
    if (this.ticksExisted % 160 == 0)
      playSound(ESound.commandBlockWitherGrow, 2.0F, 1.0F); 
    if (this.ticksExisted % 30 == 0 && getSize() > 5000)
      playLivingSound(); 
    if (this.ticksExisted % 60 == 0 && getSize() > 12000)
      playSound(ESound.witherStormFirstRoar, 100.0F, 1.0F); 
    for (int i = 0; i < 16; i++) {
      int in = MathHelper.floor(this.posX - 1.5D + this.rand.nextDouble() * 3.0D);
      int m = MathHelper.floor(this.posY + 3.0D - this.rand.nextDouble() * 9.0D);
      int n = MathHelper.floor(this.posZ - 1.5D + this.rand.nextDouble() * 3.0D);
      BlockPos blockpos = new BlockPos(in, m, n);
      IBlockState iblockstate = this.world.getBlockState(blockpos);
      Block block = iblockstate.getBlock();
      if (!this.world.isRemote && (block != Blocks.AIR || m < 0))
        this.motionY = 0.05000000074505806D; 
    } 
    for (int i1 = 0; i1 < 1 + getSize() && i1 <= 1500; i1++) {
      if (isEntityAlive() && this.rand.nextInt(50) == 0) {
        int i11 = MathHelper.floor(this.posY + 1.0D) + MathHelper.getInt(this.rand, 2, 24) * MathHelper.getInt(this.rand, -1, 1);
        int l1 = MathHelper.floor(this.posX) + MathHelper.getInt(this.rand, 2, 24) * MathHelper.getInt(this.rand, -1, 1);
        int i2 = MathHelper.floor(this.posZ) + MathHelper.getInt(this.rand, 2, 24) * MathHelper.getInt(this.rand, -1, 1);
        BlockPos blockpos = new BlockPos(l1, i11, i2);
        IBlockState iblockstate = this.world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        if (this.world.canBlockSeeSky(blockpos) && !block.isAir(iblockstate, (IBlockAccess)this.world, blockpos) && !this.world.isRemote && this.world.isAreaLoaded(blockpos, blockpos) && block.getBlockHardness(iblockstate, this.world, new BlockPos(l1, i11, i2)) != -1.0F)
          if (EngenderConfig.mobs.grief) {
            Grow(getSize() + 3);
            heal(3.0F);
            if (block.getMaterial(iblockstate).isLiquid()) {
              this.world.setBlockToAir(new BlockPos(l1, i11, i2));
            } else {
              this.world.spawnEntity((Entity)new EntityFallingBlock(this.world, l1, i11, i2, block.getActualState(iblockstate, (IBlockAccess)this.world, blockpos)));
            } 
          }  
      } 
    } 
    List<EntityFallingBlock> list = this.world.getEntitiesWithinAABB(EntityFallingBlock.class, getEntityBoundingBox().grow(64.0D, 64.0D, 64.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (isEntityAlive() && list != null && !list.isEmpty())
        for (EntityFallingBlock entityFallingBlock : list) {
            if (entityFallingBlock != null) {
                entityFallingBlock.noClip = true;
                double d01 = this.posX - entityFallingBlock.posX;
                double d11 = this.posY + 1.5D - entityFallingBlock.posY;
                double d21 = this.posZ - entityFallingBlock.posZ;
                float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
                entityFallingBlock.motionX = d01 / f2 * 0.5D * 0.5D + entityFallingBlock.motionX * 0.5D;
                entityFallingBlock.motionY = d11 / f2 * 0.5D * 0.5D + entityFallingBlock.motionY * 0.5D;
                entityFallingBlock.motionZ = d21 / f2 * 0.5D * 0.5D + entityFallingBlock.motionZ * 0.5D;
                List<EntityLivingBase> sublist = this.world.getEntitiesWithinAABB(EntityLivingBase.class, entityFallingBlock.getEntityBoundingBox(), Predicates.and(EntitySelectors.NOT_SPECTATING));
                if (isEntityAlive() && sublist != null && !sublist.isEmpty())
                    for (EntityLivingBase subentity : sublist) {
                        if (subentity != null && !false)
                            subentity.attackEntityFrom(DamageSource.IN_WALL, 1.0F);
                    }
            }
        }
    List<EntityFallingBlock> list1 = this.world.getEntitiesWithinAABB(EntityFallingBlock.class, getEntityBoundingBox(), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (isEntityAlive() && list1 != null && !list1.isEmpty())
        for (EntityFallingBlock entityFallingBlock : list1) {
            if (entityFallingBlock != null) {
                entityFallingBlock.setDead();
                Grow(getSize() + 3);
                heal(3.0F);
            }
        }
    List<EntityItem> list11 = this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow(256.0D, 256.0D, 256.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (isEntityAlive() && list11 != null && !list11.isEmpty())
        for (EntityItem entityItem : list11) {
            if (entityItem != null) {
                double d01 = this.posX - entityItem.posX;
                double d11 = this.posY + 1.5D - entityItem.posY;
                double d21 = this.posZ - entityItem.posZ;
                float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
                entityItem.motionX = d01 / f2 * 0.6D * 0.6D + entityItem.motionX * 0.6D;
                entityItem.motionY = d11 / f2 * 0.6D * 0.6D + entityItem.motionY * 0.6D;
                entityItem.motionZ = d21 / f2 * 0.6D * 0.6D + entityItem.motionZ * 0.6D;
            }
        }
    List<EntityItem> list111 = this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox(), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (isEntityAlive() && list111 != null && !list111.isEmpty())
        for (EntityItem entityItem : list111) {
            if (entityItem != null) {
                entityItem.setDead();
                Grow(getSize() + 1 + entityItem.getItem().getCount());
                heal(1.0F + entityItem.getItem().getCount());
            }
        }
    EntityLivingBase entity = getAttackTarget();
    if (entity != null && getSize() >= 6000 && !false && entity.getHealth() > 0.0F && !(entity instanceof EntityWitherStorm) && !(entity instanceof EntityWitherStormHead) && !(entity instanceof EntityWitherStormTentacle) && !(entity instanceof EntityWitherStormTentacleDevourer)) {
      if (!this.world.isRemote)
        entity.addPotionEffect(new PotionEffect(MobEffects.WITHER, 2147483647, 1)); 
      double d01 = this.posX - entity.posX;
      double d11 = this.posY + getEyeHeight() - entity.posY;
      double d21 = this.posZ - entity.posZ;
      float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
      if (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman || entity instanceof net.minecraft.entity.monster.EntityEnderman) {
        ((EntityLiving)entity).setAttackTarget((EntityLivingBase)this);
      } else if (entity instanceof net.minecraft.entity.boss.EntityDragon && entity.getHealth() <= 1.0F) {
        ((EntityLiving)entity).spawnExplosionParticle();
      } else if (entity instanceof EntityTameBase && (entity instanceof EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityWither || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || !((EntityTameBase)entity).isNonBoss() || ((EntityTameBase)entity).isHero())) {
        ((EntityLiving)entity).setAttackTarget((EntityLivingBase)this);
      } else {
        if (entity instanceof EntityLiving && !entity.isNonBoss())
          ((EntityLiving)entity).setAttackTarget((EntityLivingBase)this); 
        entity.motionX = d01 / f2 * 0.5D * 0.5D + entity.motionX * 0.5D;
        entity.motionY = d11 / f2 * 0.5D * 0.5D + entity.motionY * 0.5D;
        entity.motionZ = d21 / f2 * 0.5D * 0.5D + entity.motionZ * 0.5D;
      } 
      List<EntityLiving> list11111 = this.world.getEntitiesWithinAABB(EntityLiving.class, getEntityBoundingBox().grow(1.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list11111 != null && !list11111.isEmpty() && getSize() >= 6000)
          for (EntityLiving entity1 : list11111) {
              if (entity1 != null && entity1.isEntityAlive() && (!false || entity1 instanceof net.minecraft.entity.passive.EntityAnimal) && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityEnderman) && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !(entity1 instanceof EntityTameBase) && !(entity1 instanceof net.minecraft.entity.boss.EntityDragon) && !(entity1 instanceof EntityWitherStorm) && !(entity1 instanceof EntityWitherStormHead) && !(entity1 instanceof EntityWitherStormTentacle) && !(entity1 instanceof EntityWitherStormTentacleDevourer)) {
                  if (!isWild() && EngenderConfig.general.useMessage)
                      getOwner().sendMessage((ITextComponent) new TextComponentTranslation(entity1.getName() + " was eaten by The Wither Storm (" + getOwner().getName() + ")", new Object[0]));
                  this.world.setEntityState((Entity) entity1, (byte) 3);
                  if (!entity1.isNonBoss()) {
                      entity1.setHealth(0.0F);
                  } else {
                      entity1.setDead();
                  }
                  Grow(getSize() + 1 + (int) entity1.height * (int) entity1.height + (int) entity1.width * (int) entity1.width);
                  heal(1.0F + entity1.getMaxHealth() + ((int) entity1.height * (int) entity1.height) + ((int) entity1.width * (int) entity1.width));
              }
          }
    } 
    if (getSize() >= 1000 && this.height != 4.0F) {
      setSize(2.0F, 4.0F);
    } else if (this.height != 3.3F) {
      setSize(0.9F, 3.3F);
    } 
    if (this.motionY < 0.0D)
      this.motionY *= 0.6D; 
    if (!this.world.isRemote && getAttackTarget() != null)
      if (entity != null) {
        if (this.posY < Flying.MAX_FLIGHT_TARGET_Y && (this.posY < Flying.clampFlightY(entity.posY) || (!isArmored() && this.posY < Flying.clampFlightY(entity.posY + 4.0D + entity.getEyeHeight())))) {
          if (this.motionY < 0.0D)
            this.motionY = 0.0D; 
          this.motionY += (0.35D - this.motionY) * 0.6D;
        } 
        double d0 = entity.posX - this.posX;
        double d1 = entity.posZ - this.posZ;
        double d3 = d0 * d0 + d1 * d1;
        if (d3 > 64.0D) {
          if (this.motionX * this.motionX + this.motionZ * this.motionZ != 0.0D)
            this.renderYawOffset = this.rotationYaw = (float)Math.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F; 
          double d5 = MathHelper.sqrt(d3);
          if (this.moralRaisedTimer > 200) {
            this.motionX += (d0 / d5 * 0.35D - this.motionX) * 0.5D;
            this.motionZ += (d1 / d5 * 0.35D - this.motionZ) * 0.5D;
          } else {
            this.motionX += (d0 / d5 * 0.35D - this.motionX) * 0.6D;
            this.motionZ += (d1 / d5 * 0.35D - this.motionZ) * 0.6D;
          } 
        } 
      }  
    super.onLivingUpdate();
    int k;
    for (k = 0; k < 2; k++) {
      this.yRotOHeads[k] = this.yRotationHeads[k];
      this.xRotOHeads[k] = this.xRotationHeads[k];
    } 
    for (k = 0; k < 2; k++) {
      this.xRotationHeads[k] = rotlerp(this.xRotationHeads[k], this.rotationPitch, 40.0F);
      this.yRotationHeads[k] = rotlerp(this.yRotationHeads[k], this.renderYawOffset, 10.0F);
    } 
    if (this.world.isRemote)
      for (k = 0; k < 2; k++)
        this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
    int j;
    for (j = 0; j < 3; j++) {
      double d10 = getHeadX(j);
      double d2 = getHeadY(j);
      double d4 = getHeadZ(j);
      this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D);
    } 
    if (getInvulTime() > 0)
      for (j = 0; j < 3; j++)
        this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0D, this.posY + (this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian() * 1.0D, 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D);
  }
  
  protected void updateAITasks() {
    if (getInvulTime() > 0) {
      int i = getInvulTime() - 1;
      if (i <= 0) {
        createEngenderModExplosionFireless((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false);
        playSound(ESound.commandBlockWitherSpawn, Float.MAX_VALUE, 1.0F);
        for (EntityPlayer entityplayer : this.world.playerEntities) {
          this.world.playSound(null, entityplayer.getPosition(), ESound.commandBlockWitherSpawn, getSoundCategory(), Float.MAX_VALUE, 1.0F);
          entityplayer.sendStatusMessage((ITextComponent)new TextComponentTranslation("§5A Wither Storm has been summoned in " + this.world.provider.getDimensionType().getName() + "!", new Object[0]), true);
        } 
      } 
      setInvulTime(i);
      if (this.ticksExisted % 1 == 0)
        heal(1.0F); 
    } else {
      super.updateAITasks();
      if (this.blockBreakCounter > 0) {
        this.blockBreakCounter--;
        if (this.blockBreakCounter == 0 && EngenderConfig.mobs.grief) {
          int i11 = MathHelper.floor(this.posY);
          int l1 = MathHelper.floor(this.posX);
          int i2 = MathHelper.floor(this.posZ);
          boolean flag = false;
          for (int k2 = -1; k2 <= 1; k2++) {
            for (int l2 = -1; l2 <= 1; l2++) {
              for (int j = 0; j <= 3; j++) {
                int i3 = l1 + k2;
                int k = i11 + j;
                int l = i2 + l2;
                BlockPos blockpos = new BlockPos(i3, k, l);
                IBlockState iblockstate = this.world.getBlockState(blockpos);
                Block block = iblockstate.getBlock();
                if (!block.isAir(iblockstate, (IBlockAccess)this.world, blockpos) && block.canEntityDestroy(iblockstate, (IBlockAccess)this.world, blockpos, (Entity)this))
                  flag = (this.world.destroyBlock(blockpos, true) || flag); 
              } 
            } 
          } 
          if (flag)
            this.world.playEvent((EntityPlayer)null, 1022, new BlockPos((Entity)this), 0); 
        } 
      } 
      if (this.ticksExisted % 20 == 0)
        heal(1.0F); 
    } 
  }
  
  public static boolean canDestroyBlock(Block p_181033_0_) {
    return (p_181033_0_ != Blocks.BEDROCK && p_181033_0_ != Blocks.END_PORTAL && p_181033_0_ != Blocks.END_PORTAL_FRAME && p_181033_0_ != Blocks.COMMAND_BLOCK && p_181033_0_ != Blocks.REPEATING_COMMAND_BLOCK && p_181033_0_ != Blocks.CHAIN_COMMAND_BLOCK && p_181033_0_ != Blocks.BARRIER);
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public void ignite() {
    setInvulTime(220);
    setHealth(getMaxHealth() / 3.0F);
  }
  
  public void setInWeb() {}
  
  private double getHeadX(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.posX; 
    float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.25D;
  }
  
  private double getHeadY(int p_82208_1_) {
    return (p_82208_1_ <= 0) ? (this.posY + 1.75D) : (this.posY + 2.25D);
  }
  
  private double getHeadZ(int p_82213_1_) {
    if (p_82213_1_ <= 0)
      return this.posZ; 
    float f = (this.renderYawOffset + (180 * (p_82213_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.sin(f);
    return this.posZ + f1 * 1.25D;
  }
  
  private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
    float f3 = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);
    if (f3 > p_82204_3_)
      f3 = p_82204_3_; 
    if (f3 < -p_82204_3_)
      f3 = -p_82204_3_; 
    return p_82204_1_ + f3;
  }
  
  private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
    launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.height * 0.75D, p_82216_2_.posZ, (p_82216_1_ == 0 && this.rand.nextFloat() < 0.001F));
  }
  
  private void launchWitherSkullToCoords(int p_82209_1_, double p_82209_2_, double p_82209_4_, double p_82209_6_, boolean p_82209_8_) {
    this.world.playEvent((EntityPlayer)null, 1024, new BlockPos((Entity)this), 0);
    double d3 = getHeadX(p_82209_1_);
    double d4 = getHeadY(p_82209_1_);
    double d5 = getHeadZ(p_82209_1_);
    double d6 = p_82209_2_ - d3;
    double d7 = p_82209_4_ - d4;
    double d8 = p_82209_6_ - d5;
    EntityWitherStormSkull entitywitherskull = new EntityWitherStormSkull(this.world, (EntityLivingBase)this, d6, d7, d8);
    if (p_82209_8_)
      entitywitherskull.setInvulnerable(true); 
    float f = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    entitywitherskull.damage = f;
    entitywitherskull.posY = d4;
    entitywitherskull.posX = d3;
    entitywitherskull.posZ = d5;
    this.world.spawnEntity((Entity)entitywitherskull);
  }
  
  public void attackEntityWithRangedAttack(EntityLivingBase p_82196_1_, float p_82196_2_) {
    if (!false)
      launchWitherSkullToEntity(0, p_82196_1_); 
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (isEntityInvulnerable(source))
      return false; 
    if (!source.isExplosion() && source != DamageSource.DROWN) {
      if (getInvulTime() > 0 && source != DamageSource.OUT_OF_WORLD)
        return false; 
      if (getSize() >= 1000)
        if (source.isProjectile() || source.getTrueSource() instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySkeleton) {
          setArrowCountInEntity(0);
          return false;
        }  
      if (getSize() >= 5250)
        if (source.isMagicDamage())
          return false;  
      if (this.blockBreakCounter <= 0)
        this.blockBreakCounter = 20; 
      for (int i = 0; i < this.idleHeadUpdates.length; i++)
        this.idleHeadUpdates[i] = this.idleHeadUpdates[i] + 10; 
      if (source.isMagicDamage() && getSize() < 5000)
        amount *= 100.0F; 
      return super.attackEntityFrom(source, amount);
    } 
    return false;
  }
  
  protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
    EntityItem entityitem = entityDropItem(new ItemStack(Blocks.COMMAND_BLOCK, 1, 0), 0.0F);
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return ELoot.ENTITIES_COMMAND_BLOCK_WITHER;
  }
  
  protected void despawnEntity() {
    this.idleTime = 0;
  }
  
  @SideOnly(Side.CLIENT)
  public int getBrightnessForRender() {
    return 15728880;
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
  
  @SideOnly(Side.CLIENT)
  public float getHeadYRotation(int p_82207_1_) {
    return this.yRotationHeads[p_82207_1_];
  }
  
  @SideOnly(Side.CLIENT)
  public float getHeadXRotation(int p_82210_1_) {
    return this.xRotationHeads[p_82210_1_];
  }
  
  public int getInvulTime() {
    return (Integer) this.dataManager.get(INVULNERABILITY_TIME);
  }
  
  public void setInvulTime(int p_82215_1_) {
    this.dataManager.set(INVULNERABILITY_TIME, p_82215_1_);
  }
  
  public boolean isArmored() {
    return (getSize() >= 3000);
  }
  
  public boolean isEntityUndead() {
    return true;
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
  
  class AIDoNothing extends EntityAIBase {
    public AIDoNothing() {
      setMutexBits(7);
    }
    
    public boolean shouldExecute() {
      return false;
    }
  }
}

