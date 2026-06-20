package net.minecraft.AgeOfMinecraft.entity.tame.tier6;

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
import net.minecraft.AgeOfMinecraft.entity.tame.Undead;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.events.ChunkLoadingEvent;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.AgeOfMinecraft.util.EntityAICompat;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
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
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.WorldInfo;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityWitherStorm extends EntityTameBase implements Massive, Armored, Flying, Undead {
  private static final DataParameter<Boolean> DOESNT_HAVE_COMMAND_BLOCK = EntityDataManager.createKey(EntityWitherStorm.class, DataSerializers.BOOLEAN);
  
  private static final DataParameter<Integer> SIZE = EntityDataManager.createKey(EntityWitherStorm.class, DataSerializers.VARINT);
  
  public EntityWitherStormHead centerHead;
  
  public EntityWitherStormHead rightHead;
  
  public EntityWitherStormHead leftHead;
  
  public EntityWitherStormTentacle tentacle1;
  
  public EntityWitherStormTentacle tentacle2;
  
  public EntityWitherStormTentacle tentacle3;
  
  public EntityWitherStormTentacle tentacle4;
  
  public EntityWitherStormTentacle tentacle5;
  
  public EntityWitherStormTentacleDevourer tentacledevourer1;
  
  public EntityWitherStormTentacleDevourer tentacledevourer2;
  
  private boolean witherStormDeathFinished;
  
  public EntityWitherStorm(World worldIn) {
    super(worldIn);
    setSize(9.0F, 32.0F);
    this.isOffensive = true;
    this.isImmuneToFire = true;
    this.noClip = true;
    ((PathNavigateGround)getNavigator()).setCanSwim(true);
    this.moveHelper = new WitherStormMoveHelper(this);
    this.tasks.addTask(5, new AIRandomFly(this));
    this.tasks.addTask(7, new AILookAround());
    this.experienceValue = 50000;
    setLevel(300);
    this.ignoreFrustumCheck = true;
    Grow(12500);
    setHealth(getMaxHealth());
    for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(worldIn))
      worldIn.playSound(null, entityplayer.getPosition(), ESound.witherStormFinish, getSoundCategory(), Float.MAX_VALUE, 1.0F); 
  }
  
  public TextFormatting getTextFormat() {
    return rainbowText();
  }
  
  public String getDescName() {
    return doesntContainACommandBlock() ? "wither_storm_severed" : ((getSize() >= 250000) ? "wither_storm_thunderstorm" : ((getSize() < 250000 && getSize() >= 50000) ? "wither_storm_devourer" : "wither_storm_destroyer"));
  }
  
  public int playMusic() {
    return (getSize() >= 250000) ? 68 : ((getSize() < 250000 && getSize() >= 50000) ? 13 : 12);
  }
  
  public boolean leavesNoCorpse() {
    return true;
  }
  
  public boolean isChild() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.setColor(BossInfo.Color.PURPLE);
    this.bossInfo.setDarkenSky(true);
  }
  
  public int getSpawnTimer() {
    return 0;
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return ESetup.WITHER_STORM;
  }
  
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
  
  public boolean canUseGuardBlock() {
    return false;
  }
  
  public boolean canWearEasterEggs() {
    return false;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER6;
  }
  
  public void onKillCommand() {
    super.onKillCommand();
    killParts();
  }
  
  public void setDead() {
    killParts();
    ChunkLoadingEvent.stopLoading(this);
    super.setDead();
  }
  
  private void killParts() {
    killPart(this.centerHead);
    killPart(this.rightHead);
    killPart(this.leftHead);
    killPart(this.tentacle1);
    killPart(this.tentacle2);
    killPart(this.tentacle3);
    killPart(this.tentacle4);
    killPart(this.tentacle5);
    killPart(this.tentacledevourer1);
    killPart(this.tentacledevourer2);
    this.centerHead = null;
    this.rightHead = null;
    this.leftHead = null;
    this.tentacle1 = null;
    this.tentacle2 = null;
    this.tentacle3 = null;
    this.tentacle4 = null;
    this.tentacle5 = null;
    this.tentacledevourer1 = null;
    this.tentacledevourer2 = null;
  }
  
  private void killPart(Entity part) {
    if (part != null && !part.isDead)
      part.setDead(); 
  }
  
  protected void applyEntityAttributes() {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(12500.0D);
    getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.8D);
    getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(128.0D);
    getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(100.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(30.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(20.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public String getName() {
    if (hasCustomName())
      return getCustomNameTag(); 
    return doesntContainACommandBlock() ? "Severed Wither Storm" : "The Wither Storm";
  }
  
  protected void entityInit() {
    super.entityInit();
    this.getDataManager().register(SIZE, 0);
    this.getDataManager().register(DOESNT_HAVE_COMMAND_BLOCK, Boolean.FALSE);
  }
  
  public void outOfWorld() {}
  
  public void writeEntityToNBT(NBTTagCompound tagCompound) {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Growth", getSize());
    tagCompound.setBoolean("NoCommandBlock", doesntContainACommandBlock());
  }
  
  public void readEntityFromNBT(NBTTagCompound tagCompund) {
    super.readEntityFromNBT(tagCompund);
    Grow(tagCompund.getInteger("Growth"));
    setNotContainingCommandBlock(tagCompund.getBoolean("NoCommandBlock"));
  }
  
  protected SoundEvent getAmbientSound() {
    return ESound.witherStormAmbient;
  }
  
  protected SoundEvent getHurtSound(DamageSource source) {
    return doesntContainACommandBlock() ? ESound.witherStormHurt : ESound.witherStormHurtCommandBlock;
  }
  
  protected SoundEvent getDeathSound() {
    return doesntContainACommandBlock() ? ESound.witherStormHurt : ESound.witherStormDeath;
  }
  
  protected float getSoundVolume() {
    return isSneaking() ? 1.0F : 100.0F;
  }
  
  protected float getSoundPitch() {
    return 1.0F;
  }
  
  public float getEyeHeight() {
    return 0.5F;
  }
  
  public boolean canBePushed() {
    return false;
  }

  public boolean canBeCollidedWith() {
    return !this.isDead && isEntityAlive();
  }

  @Nullable
  public AxisAlignedBB getCollisionBoundingBox() {
    return canBeCollidedWith() ? getEntityBoundingBox() : null;
  }

  public float getCollisionBorderSize() {
    if (doesntContainACommandBlock())
      return 8.0F;
    switch (getPhase()) {
      case ThunderStorm:
        return 24.0F;
      case Devourer:
        return 16.0F;
      default:
        return 10.0F;
    }
  }

  private void clampStormMotion() {
    if (this.motionX > 1.0D)
      this.motionX = 1.0D;
    if (this.motionX < -1.0D)
      this.motionX = -1.0D;
    if (this.motionY > 1.0D)
      this.motionY = 1.0D;
    if (this.motionY < -1.0D)
      this.motionY = -1.0D;
    if (this.motionZ > 1.0D)
      this.motionZ = 1.0D;
    if (this.motionZ < -1.0D)
      this.motionZ = -1.0D;
  }

  private void syncHealthAfterFormChange() {
    float maxHealth = getMaxHealth();
    if (getHealth() <= 20.0F || getHealth() > maxHealth)
      setHealth(maxHealth);
  }

  public static boolean isWitherStormFamily(Entity entity) {
    return entity instanceof EntityWitherStorm || entity instanceof EntityWitherStormHead || entity instanceof EntityWitherStormTentacle || entity instanceof EntityWitherStormTentacleDevourer || entity instanceof EntityCommandBlockWither;
  }

  public static boolean isWitherStormDamageSource(DamageSource source) {
    return source != null && (isWitherStormFamily(source.getTrueSource()) || isWitherStormFamily(source.getImmediateSource()));
  }

  public static boolean shouldIgnoreStormTarget(@Nullable Entity entity) {
    if (!(entity instanceof EntityLivingBase))
      return true; 
    if (isWitherStormFamily(entity))
      return true; 
    ResourceLocation id = EntityList.getKey(entity);
    if (id == null)
      return false; 
    String path = id.getPath();
    return (path != null && path.toLowerCase(java.util.Locale.ROOT).contains("wither"));
  }

  public static boolean canLiftStormBlock(World world, BlockPos pos, IBlockState state) {
    if (world == null || pos == null || state == null)
      return false; 
    Block block = state.getBlock();
    if (block == null || block.isAir(state, world, pos))
      return false; 
    if (block.hasTileEntity(state))
      return false; 
    if (state.getRenderType() != net.minecraft.util.EnumBlockRenderType.MODEL)
      return false; 
    if (block instanceof net.minecraft.block.BlockContainer)
      return false; 
    if (block instanceof net.minecraft.block.BlockFalling)
      return false; 
    if (block instanceof net.minecraft.block.BlockLiquid || block instanceof net.minecraftforge.fluids.BlockFluidBase || block instanceof net.minecraftforge.fluids.BlockFluidClassic)
      return false; 
    if (block instanceof net.minecraft.block.BlockDoor || block instanceof net.minecraft.block.BlockTrapDoor || block instanceof net.minecraft.block.BlockFenceGate)
      return false; 
    if (block instanceof net.minecraft.block.BlockSlab || block instanceof net.minecraft.block.BlockStairs || block instanceof net.minecraft.block.BlockPane || block instanceof net.minecraft.block.BlockWall || block instanceof net.minecraft.block.BlockFence)
      return false; 
    if (block instanceof net.minecraft.block.BlockTorch || block instanceof net.minecraft.block.BlockRedstoneWire || block instanceof net.minecraft.block.BlockRailBase || block instanceof net.minecraft.block.BlockLever || block instanceof net.minecraft.block.BlockButton || block instanceof net.minecraft.block.BlockSign)
      return false; 
    if (block instanceof net.minecraft.block.BlockBush || block instanceof IPlantable)
      return false; 
    return !state.getMaterial().isLiquid();
  }
  
  public EnumWitherStormPhase getPhase() {
    return (getSize() <= 250000 && getSize() > 50000) ? EnumWitherStormPhase.Devourer : ((getSize() > 250000) ? EnumWitherStormPhase.ThunderStorm : EnumWitherStormPhase.Destroyer);
  }
  
  public void onLivingUpdate() {
    float rot = this.rotationYawHead * 0.017453292F;
    float oned = MathHelper.sin(rot);
    float twod = MathHelper.cos(rot);
    EntityLivingBase owner = getOwner();
    if (owner != null && getDistanceSq(owner) >= 48400.0D) {
      setPositionAndUpdate(owner.posX, owner.posY + 40.0D, owner.posZ);
      this.motionX = 0.0D;
      this.motionY = 0.0D;
      this.motionZ = 0.0D;
      this.fallDistance = 0.0F;
      getNavigator().clearPath();
    }
    if (!doesntContainACommandBlock() && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      if (isEntityAlive()) {
        ChunkLoadingEvent.updateLoaded(this);
      } else {
        ChunkLoadingEvent.stopLoading(this);
      }  
    getNavigator().clearPath();
    this.experienceValue = getSize();
    getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(doesntContainACommandBlock() ? 1000.0D : getSize());
    getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(doesntContainACommandBlock() ? 20.0D : 24.0D);
    getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(doesntContainACommandBlock() ? 10.0D : 20.0D);
    if (!doesntContainACommandBlock() && getSize() >= 300000 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.ticksExisted % 1000 == 0) {
      EntityWitherStorm entityzombie = new EntityWitherStorm(this.world);
      entityzombie.copyLocationAndAnglesFrom(this);
      entityzombie.setNoAI(isAIDisabled());
      entityzombie.setOwnerId(getOwnerId());
      entityzombie.setNotContainingCommandBlock(true);
      entityzombie.setHealth(entityzombie.getMaxHealth());
      entityzombie.motionX = this.rand.nextDouble() - 0.5D;
      entityzombie.motionZ = this.rand.nextDouble() - 0.5D;
      this.world.spawnEntity(entityzombie);
      Grow(getSize() - 12500);
    } 
    clampStormMotion();
    if (owner != null)
      owner.removeActivePotionEffect(MobEffects.WITHER);
    if (this.deathTicks <= 0)
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        if (this.centerHead != null) {
          if (this.centerHead.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.centerHead.setLocationAndAngles(this.posX + (oned * -7.0F), this.posY, this.posZ - (twod * -7.0F), 0.0F, 0.0F);
              this.centerHead.renderYawOffset = this.centerHead.rotationYaw = this.rotationYawHead;
            } else {
              this.centerHead.setLocationAndAngles(this.posX + (oned * -7.0F), this.posY, this.posZ - (twod * -7.0F), 0.0F, 0.0F);
              this.centerHead.renderYawOffset = this.centerHead.rotationYaw = this.rotationYawHead;
            }  
          if (getJukeboxToDanceTo() != null)
            this.centerHead.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.centerHead.isDead) {
            this.centerHead = null;
            attackEntityFrom(DamageSource.GENERIC, 1000.0F);
          } 
        } else {
          this.centerHead = new EntityWitherStormHead(this.world);
          this.centerHead.residentWitherStorm = this;
          this.centerHead.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.centerHead);
          this.centerHead.onUpdate();
        } 
        if (this.rightHead != null) {
          if (this.rightHead.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.rightHead.setLocationAndAngles(this.posX + (twod * -7.0F) + (oned * -7.0F), this.posY, this.posZ + (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F);
              this.rightHead.renderYawOffset = this.rightHead.rotationYaw = this.rotationYawHead + 10.0F;
            } else {
              this.rightHead.setLocationAndAngles(this.posX + (twod * -20.0F) + (oned * -4.0F), this.posY + 10.0D, this.posZ + (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F);
              this.rightHead.renderYawOffset = this.rightHead.rotationYaw = this.rotationYawHead + 30.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.rightHead.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.rightHead.isDead) {
            this.rightHead = null;
            attackEntityFrom(DamageSource.GENERIC, 1000.0F);
          } 
        } else {
          this.rightHead = new EntityWitherStormHead(this.world);
          this.rightHead.residentWitherStorm = this;
          this.rightHead.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.rightHead);
          this.rightHead.onUpdate();
        } 
        if (this.leftHead != null) {
          if (this.leftHead.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.leftHead.setLocationAndAngles(this.posX - (twod * -7.0F) + (oned * -7.0F), this.posY, this.posZ - (oned * -7.0F) - (twod * -7.0F), 0.0F, 0.0F);
              this.leftHead.renderYawOffset = this.leftHead.rotationYaw = this.rotationYawHead - 10.0F;
            } else {
              this.leftHead.setLocationAndAngles(this.posX - (twod * -20.0F) + (oned * -4.0F), this.posY + 10.0D, this.posZ - (oned * -20.0F) - (twod * -4.0F), 0.0F, 0.0F);
              this.leftHead.renderYawOffset = this.leftHead.rotationYaw = this.rotationYawHead - 30.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.leftHead.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.leftHead.isDead) {
            this.leftHead = null;
            attackEntityFrom(DamageSource.GENERIC, 1000.0F);
          } 
        } else {
          this.leftHead = new EntityWitherStormHead(this.world);
          this.leftHead.residentWitherStorm = this;
          this.leftHead.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.leftHead);
          this.leftHead.onUpdate();
        } 
        if (this.tentacle1 != null) {
          if (this.tentacle1.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacle1.setLocationAndAngles(this.posX, this.posY - 2.0D, this.posZ, 0.0F, 0.0F);
              this.tentacle1.rotationYawHead = this.rotationYawHead - 30.0F;
            } else {
              this.tentacle1.setLocationAndAngles(this.posX, this.posY - 12.0D, this.posZ, 0.0F, 0.0F);
              this.tentacle1.rotationYawHead = this.rotationYawHead - 60.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle1.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle1.isDead) {
            this.tentacle1 = null;
            attackEntityFrom(DamageSource.GENERIC, 100.0F);
          } 
        } else {
          this.tentacle1 = new EntityWitherStormTentacle(this.world);
          this.tentacle1.residentWitherStorm = this;
          this.tentacle1.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacle1);
          this.tentacle1.onUpdate();
        } 
        if (this.tentacle2 != null) {
          if (this.tentacle2.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacle2.setLocationAndAngles(this.posX + (twod * 4.0F), this.posY - 1.0D, this.posZ + (oned * 4.0F), 0.0F, 0.0F);
              this.tentacle2.rotationYawHead = this.rotationYawHead + 90.0F;
            } else {
              this.tentacle2.setLocationAndAngles(this.posX + (twod * 2.0F), this.posY - 8.0D, this.posZ + (oned * 2.0F), 0.0F, 0.0F);
              this.tentacle2.rotationYawHead = this.rotationYawHead + 60.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle2.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle2.isDead) {
            this.tentacle2 = null;
            attackEntityFrom(DamageSource.GENERIC, 100.0F);
          } 
        } else {
          this.tentacle2 = new EntityWitherStormTentacle(this.world);
          this.tentacle2.residentWitherStorm = this;
          this.tentacle2.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacle2);
          this.tentacle2.onUpdate();
        } 
        if (this.tentacle3 != null) {
          if (this.tentacle3.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacle3.setLocationAndAngles(this.posX - (twod * -2.0F), this.posY + 4.0D, this.posZ - (oned * -2.0F), 0.0F, 0.0F);
              this.tentacle3.rotationYawHead = this.rotationYawHead - 130.0F;
            } else {
              this.tentacle3.setLocationAndAngles(this.posX - (twod * -8.0F), this.posY + 8.0D, this.posZ - (oned * -8.0F), 0.0F, 0.0F);
              this.tentacle3.rotationYawHead = this.rotationYawHead - 120.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle3.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle3.isDead) {
            this.tentacle3 = null;
            attackEntityFrom(DamageSource.GENERIC, 100.0F);
          } 
        } else {
          this.tentacle3 = new EntityWitherStormTentacle(this.world);
          this.tentacle3.residentWitherStorm = this;
          this.tentacle3.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacle3);
          this.tentacle3.onUpdate();
        } 
        if (this.tentacle4 != null) {
          if (this.tentacle4.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacle4.setLocationAndAngles(this.posX + (twod * -5.0F), this.posY + 3.0D, this.posZ + (oned * -5.0F), 0.0F, 0.0F);
              this.tentacle4.rotationYawHead = this.rotationYawHead + 120.0F;
            } else {
              this.tentacle4.setLocationAndAngles(this.posX + (twod * -5.0F), this.posY + 4.0D, this.posZ + (oned * -5.0F), 0.0F, 0.0F);
              this.tentacle4.rotationYawHead = this.rotationYawHead + 105.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle4.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle4.isDead) {
            this.tentacle4 = null;
            attackEntityFrom(DamageSource.GENERIC, 100.0F);
          } 
        } else {
          this.tentacle4 = new EntityWitherStormTentacle(this.world);
          this.tentacle4.residentWitherStorm = this;
          this.tentacle4.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacle4);
          this.tentacle4.onUpdate();
        } 
        if (this.tentacle5 != null) {
          if (this.tentacle5.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacle5.setLocationAndAngles(this.posX, this.posY + 2.0D, this.posZ, 0.0F, 0.0F);
              this.tentacle5.rotationYawHead = this.rotationYawHead - 100.0F;
            } else {
              this.tentacle5.setLocationAndAngles(this.posX, this.posY + 3.0D, this.posZ, 0.0F, 0.0F);
              this.tentacle5.rotationYawHead = this.rotationYawHead - 180.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacle5.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacle5.isDead) {
            this.tentacle5 = null;
            attackEntityFrom(DamageSource.GENERIC, 100.0F);
          } 
        } else {
          this.tentacle5 = new EntityWitherStormTentacle(this.world);
          this.tentacle5.residentWitherStorm = this;
          this.tentacle5.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacle5);
          this.tentacle5.onUpdate();
        } 
        if (this.tentacledevourer1 != null) {
          if (this.tentacledevourer1.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacledevourer1.setLocationAndAngles(this.posX, this.posY + 10.0D, this.posZ - oned, 0.0F, 0.0F);
              this.tentacledevourer1.rotationYawHead = this.rotationYawHead - 180.0F;
            } else {
              this.tentacledevourer1.setLocationAndAngles(this.posX - (twod * -12.0F), this.posY + 10.0D, this.posZ - (oned * -12.0F), 0.0F, 0.0F);
              this.tentacledevourer1.setInvisible((getSize() < 50000));
              this.tentacledevourer1.rotationYawHead = this.rotationYawHead - 120.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacledevourer1.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacledevourer1.isDead) {
            this.tentacledevourer1 = null;
            attackEntityFrom(DamageSource.GENERIC, 200.0F);
          } 
        } else {
          this.tentacledevourer1 = new EntityWitherStormTentacleDevourer(this.world);
          this.tentacledevourer1.residentWitherStorm = this;
          this.tentacledevourer1.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacledevourer1);
          this.tentacledevourer1.onUpdate();
        } 
        if (this.tentacledevourer2 != null) {
          if (this.tentacledevourer2.isEntityAlive())
            if (doesntContainACommandBlock()) {
              this.tentacledevourer2.setLocationAndAngles(this.posX, this.posY + 10.0D, this.posZ + oned, 0.0F, 0.0F);
              this.tentacledevourer2.rotationYawHead = this.rotationYawHead + 180.0F;
            } else {
              this.tentacledevourer2.setLocationAndAngles(this.posX + (twod * -12.0F), this.posY + 10.0D, this.posZ + (oned * -12.0F), 0.0F, 0.0F);
              this.tentacledevourer2.setInvisible((getSize() < 50000));
              this.tentacledevourer2.rotationYawHead = this.rotationYawHead + 120.0F;
            }  
          if (getJukeboxToDanceTo() != null)
            this.tentacledevourer2.setJukeboxToDanceTo(getJukeboxToDanceTo()); 
          if (this.tentacledevourer2.isDead) {
            this.tentacledevourer2 = null;
            attackEntityFrom(DamageSource.GENERIC, 200.0F);
          } 
        } else {
          this.tentacledevourer2 = new EntityWitherStormTentacleDevourer(this.world);
          this.tentacledevourer2.residentWitherStorm = this;
          this.tentacledevourer2.copyLocationAndAnglesFrom(this);
          this.world.spawnEntity(this.tentacledevourer2);
          this.tentacledevourer2.onUpdate();
        } 
      }  
    if ((this.ticksExisted + getEntityId()) % 100 == 0)
      playSound(ESound.commandBlockWitherHum, 0.25F, 1.0F); 
    if (doesntContainACommandBlock()) {
      heal(1.0F);
      if (this.width != 12.0F && this.height != 12.0F)
        setSize(12.0F, 12.0F); 
    } else {
      heal(2.0F);
      if (this.width != 9.0F && this.height != 32.0F)
        setSize(9.0F, 32.0F); 
    } 
    if (getHealth() <= 0.0F) {
      float f13 = (this.rand.nextFloat() - 0.5F) * 12.0F;
      float f15 = (this.rand.nextFloat() - 0.5F) * 36.0F;
      float f17 = (this.rand.nextFloat() - 0.5F) * 12.0F;
      this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f13, this.posY - 4.0D + f15, this.posZ + f17, 0.0D, 0.0D, 0.0D);
    } 
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && getOwner() != null && this.posY <= (getOwner()).posY && this.posY >= (getOwner()).posY - 0.5D && !isSneaking())
      createEngenderModExplosion(this, this.posX, this.posY, this.posZ, 8.0F, false, EngenderConfig.mobs.grief);
    this.onGround = false;
    this.isAirBorne = true;
    this.noClip = true;
    if (getSize() < 12500)
      Grow(12500); 
    if (getSize() >= 250000) {
      WorldInfo worldinfo = this.world.getWorldInfo();
      worldinfo.setCleanWeatherTime(0);
      worldinfo.setRainTime(200);
      worldinfo.setThunderTime(200);
      worldinfo.setRaining(true);
      worldinfo.setThundering(true);
    } 
    if (this.motionY > 1.0D)
      this.motionY = 0.0D; 
    this.renderYawOffset = this.rotationYaw = this.rotationYawHead;
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && !doesntContainACommandBlock() && getHealth() <= 0.0F) {
      finishWitherStormDeath();
      return;
    } 
    if (this.deathTicks <= 0) {
      if (this.recentlyHit <= 50 && getHealth() <= 0.0F) {
        setHealth(1.0F);
      } else if (this.recentlyHit > 50 && getHealth() <= 0.0F) {
        setHealth(0.0F);
      } 
      if (this.ticksExisted % 20 == 0 && !isAIDisabled() && isEntityAlive() && EngenderConfig.mobs.grief && !doesntContainACommandBlock())
        for (int i1 = 0; i1 < getSize() / 100; i1++) {
          if (this.rand.nextInt(4) == 0) {
            int l1 = MathHelper.floor(this.posX) + MathHelper.getInt(this.rand, 2, 128) * MathHelper.getInt(this.rand, -1, 1);
            int i2 = MathHelper.floor(this.posZ) + MathHelper.getInt(this.rand, 2, 128) * MathHelper.getInt(this.rand, -1, 1);
            BlockPos blockpos = new BlockPos(l1, MathHelper.floor(this.posY), i2);
            blockpos = this.world.getTopSolidOrLiquidBlock(blockpos);
            IBlockState iblockstate = this.world.getBlockState(blockpos);
            Block block = iblockstate.getBlock();
            if (this.world.getBlockState(blockpos.up()).getBlock().isAir(this.world.getBlockState(blockpos.up()), this.world, blockpos.up()) && !block.isAir(iblockstate, this.world, blockpos) && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.world.isAreaLoaded(blockpos, blockpos) && block.getBlockHardness(iblockstate, this.world, new BlockPos(l1, blockpos.getY(), i2)) != -1.0F)
              if (block.getMaterial(iblockstate).isLiquid()) {
                this.world.setBlockState(new BlockPos(l1, blockpos.getY(), i2),Blocks.AIR.getDefaultState(),2);
              } else if (canLiftStormBlock(this.world, blockpos, iblockstate)) {
                this.world.spawnEntity(new EntityFallingBlock(this.world, l1, blockpos.getY(), i2, iblockstate));
              }  
          } 
        }  
      if (this.ticksExisted % 60 == 0) {
        List<EntityCreature> list2 = this.world.getEntitiesWithinAABB(EntityCreature.class, getEntityBoundingBox().grow(128.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (list2 != null && !list2.isEmpty())
          for (int i1 = 0; i1 < list2.size(); i1++) {
            EntityCreature entity = list2.get(i1);
            EntityAIAvoidEntitySPC ai = new EntityAIAvoidEntitySPC(entity, EntityWitherStorm.class, 128.0F, 1.5D, 1.5D);
            if (entity != null && entity.isEntityAlive() && entity.isNonBoss() && !(entity instanceof net.minecraft.entity.monster.EntityEnderman) && !(entity instanceof EntityTameBase) && !EntityAICompat.containsTask(entity, ai)) {
              EntityAICompat.addTask(entity, 0, ai);
            } else {
              list2.remove(entity);
            } 
          }  
      } 
      if (this.ticksExisted > 20 && !doesntContainACommandBlock()) {
        List<Entity> list2 = this.world.getEntitiesWithinAABB(Entity.class, getEntityBoundingBox().grow(128.0D, 128.0D, 128.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
        if (list2 != null && !list2.isEmpty() && isEntityAlive())
            for (Entity entity : list2) {
                if (!(entity instanceof EntityFallingBlock) && entity != null && entity.getEyeHeight() == 0.0F && entity.height == 0.98F && entity.width == 0.98F) {
                    entity.noClip = (entity.collidedHorizontally || entity.collidedVertically);
                    double d01 = this.posX - entity.posX;
                    double d11 = this.posY + getEyeHeight() - entity.posY;
                    double d21 = this.posZ - entity.posZ;
                    float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
                    entity.motionX = d01 / f2 * 0.5D * 0.5D + entity.motionX * 0.5D;
                    entity.motionY = d11 / f2 * 0.5D * 0.5D + entity.motionY * 0.5D;
                    entity.motionZ = d21 / f2 * 0.5D * 0.5D + entity.motionZ * 0.5D;
                }
            }
      } 
      List<EntityFallingBlock> list = this.world.getEntitiesWithinAABB(EntityFallingBlock.class, getEntityBoundingBox().grow(128.0D, 128.0D, 128.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list != null && !list.isEmpty() && isEntityAlive() && !doesntContainACommandBlock())
          for (EntityFallingBlock entity : list) {
              if (entity != null) {
                  double d01 = this.posX - entity.posX;
                  double d11 = this.posY + getEyeHeight() - entity.posY;
                  double d21 = this.posZ - entity.posZ;
                  float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
                  entity.motionX = d01 / f2 * 0.5D * 0.5D + entity.motionX * 0.5D;
                  entity.motionY = d11 / f2 * 0.5D * 0.5D + entity.motionY * 0.5D;
                  entity.motionZ = d21 / f2 * 0.5D * 0.5D + entity.motionZ * 0.5D;
                  List<EntityLivingBase> sublist = this.world.getEntitiesWithinAABB(EntityLivingBase.class, entity.getEntityBoundingBox(), Predicates.and(EntitySelectors.NOT_SPECTATING));
                  if (isEntityAlive() && sublist != null && !sublist.isEmpty())
                      for (EntityLivingBase subentity : sublist) {
                          if (subentity != null && !shouldIgnoreStormTarget(subentity))
                              subentity.attackEntityFrom(DamageSource.IN_WALL, 5.0F);
                      }
              }
          }
      List<EntityFallingBlock> list1 = this.world.getEntitiesWithinAABB(EntityFallingBlock.class, getEntityBoundingBox(), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list1 != null && !list1.isEmpty() && isEntityAlive() && !doesntContainACommandBlock())
          for (EntityFallingBlock entity : list1) {
              if (entity != null) {
                  entity.setDead();
                  Grow(getSize() + 3);
                  heal(2.0F);
              }
          }
      List<EntityItem> list11 = this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow(256.0D, 256.0D, 256.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list11 != null && !list11.isEmpty() && isEntityAlive() && !doesntContainACommandBlock())
          for (EntityItem entity : list11) {
              if (entity != null && entity.getItem().getItem() != EItem.witheredNetherStar) {
                  double d01 = this.posX - entity.posX;
                  double d11 = this.posY + 2.0D - entity.posY;
                  double d21 = this.posZ - entity.posZ;
                  float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
                  entity.motionX = d01 / f2 * 0.6D * 0.6D + entity.motionX * 0.6D;
                  entity.motionY = d11 / f2 * 0.6D * 0.6D + entity.motionY * 0.6D;
                  entity.motionZ = d21 / f2 * 0.6D * 0.6D + entity.motionZ * 0.6D;
              }
          }
      List<EntityItem> list111 = this.world.getEntitiesWithinAABB(EntityItem.class, getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list111 != null && !list111.isEmpty() && isEntityAlive() && !doesntContainACommandBlock())
          for (EntityItem entity : list111) {
              if (entity != null && entity.getItem().getItem() != EItem.witheredNetherStar) {
                  entity.setDead();
                  Grow(getSize() + 1 + entity.getItem().getCount());
                  heal((1 + entity.getItem().getCount()));
              }
          }
      List<EntityArrow> list1111 = this.world.getEntitiesWithinAABB(EntityArrow.class, getEntityBoundingBox().grow(256.0D, 256.0D, 256.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list1111 != null && !list1111.isEmpty() && isEntityAlive() && !doesntContainACommandBlock())
          for (EntityArrow entity : list1111) {
              if (entity != null && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther)) {
                  double d01 = this.posX - entity.posX;
                  double d11 = this.posY + 2.0D - entity.posY;
                  double d21 = this.posZ - entity.posZ;
                  float f2 = MathHelper.sqrt(d01 * d01 + d11 * d11 + d21 * d21);
                  entity.motionX = d01 / f2 * 0.6D * 0.6D + entity.motionX * 0.6D;
                  entity.motionY = d11 / f2 * 0.6D * 0.6D + entity.motionY * 0.6D;
                  entity.motionZ = d21 / f2 * 0.6D * 0.6D + entity.motionZ * 0.6D;
              }
          }
      List<EntityArrow> list11111 = this.world.getEntitiesWithinAABB(EntityArrow.class, getEntityBoundingBox().grow(4.0D, 4.0D, 4.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
      if (list11111 != null && !list11111.isEmpty() && isEntityAlive() && !doesntContainACommandBlock())
          for (EntityArrow entity : list11111) {
              if (entity != null && !(entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther)) {
                  entity.setDead();
                  Grow(getSize() + 1);
                  heal(1.0F);
              }
          }
    } 
    this.ignoreFrustumCheck = true;
    super.onLivingUpdate();
  }
  
  public void setInWeb() {}
  
  protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public void travel(float strafe, float vertical, float forward) {
    float f = doesntContainACommandBlock() ? 0.99F : ((getSize() > 250000) ? 0.85F : ((getSize() > 250000) ? 0.88F : 0.91F));
    float f1 = 0.16277136F / f * f * f;
    moveRelative(strafe, vertical, forward, 0.02F);
    move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
    this.motionX *= f;
    this.motionY *= f;
    this.motionZ *= f;
    this.prevLimbSwingAmount = this.limbSwingAmount;
    double d1 = this.posX - this.prevPosX;
    double d0 = this.posZ - this.prevPosZ;
    float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
    if (f2 > 1.0F)
      f2 = 1.0F; 
    this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
    this.limbSwing += this.limbSwingAmount;
  }
  
  public boolean isOnLadder() {
    return false;
  }
  
  private double getHeadX(int p_82214_1_) {
    if (p_82214_1_ <= 0)
      return this.posX; 
    float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) / 180.0F * 3.1415927F;
    float f1 = MathHelper.cos(f);
    return this.posX + f1 * 1.25D;
  }
  
  private double getHeadY(int p_82208_1_) {
    return (p_82208_1_ <= 0) ? (this.posY + 2.25D) : (this.posY + 2.25D);
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
  
  public void setPartAttackTarget(@Nullable EntityTameBase part, @Nullable EntityLivingBase entitylivingbaseIn) {
    if (shouldIgnoreStormTarget(entitylivingbaseIn))
      entitylivingbaseIn = null;
    if (part != null)
      part.setAttackTarget(entitylivingbaseIn); 
  }

  public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
    super.setAttackTarget(shouldIgnoreStormTarget(entitylivingbaseIn) ? null : entitylivingbaseIn);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    if (isWitherStormDamageSource(source))
      return false;
    if (getSize() > 250000 && amount < 20.0F)
      return false; 
    if (source.getDamageType() == "chaosImplosion")
      amount *= 0.2F; 
    if (source.isExplosion())
      amount *= 5.0F; 
    if (amount > 5000.0F && source.isExplosion() && !doesntContainACommandBlock() && getSize() >= 50000 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      EntityWitherStorm entityzombie = new EntityWitherStorm(this.world);
      entityzombie.copyLocationAndAnglesFrom(this);
      entityzombie.setNoAI(isAIDisabled());
      entityzombie.setOwnerId(getOwnerId());
      entityzombie.setNotContainingCommandBlock(true);
      entityzombie.setHealth(entityzombie.getMaxHealth());
      entityzombie.motionX = this.rand.nextDouble() - 0.5D;
      entityzombie.motionZ = this.rand.nextDouble() - 0.5D;
      this.world.spawnEntity(entityzombie);
      Grow(getSize() - 12500);
    } 
    if (isEntityInvulnerable(source))
      return false; 
    if (!source.isProjectile() && !source.isMagicDamage() && source != DamageSource.LAVA && source != DamageSource.ON_FIRE && source != DamageSource.IN_FIRE && source != DamageSource.IN_WALL && source != DamageSource.FALL && source != DamageSource.DROWN) {
      if (source.getTrueSource() != null && source.getTrueSource() instanceof EntityLivingBase && !shouldIgnoreStormTarget(source.getTrueSource())) {
        setAttackTarget((EntityLivingBase)source.getTrueSource());
        if (this.centerHead != null)
          this.centerHead.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.leftHead != null)
          this.leftHead.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.rightHead != null)
          this.rightHead.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacle1 != null)
          this.tentacle1.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacle2 != null)
          this.tentacle2.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacle3 != null)
          this.tentacle3.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacle4 != null)
          this.tentacle4.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacle5 != null)
          this.tentacle5.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacledevourer1 != null)
          this.tentacledevourer1.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
        if (this.tentacledevourer2 != null)
          this.tentacledevourer2.setAttackTarget((EntityLivingBase)source.getTrueSource()); 
      } 
      return super.attackEntityFrom(source, amount);
    } 
    return false;
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
  
  public int getSize() {
    return this.getDataManager().get(SIZE);
  }
  
  public void Grow(int p_82215_1_) {
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      this.getDataManager().set(SIZE, doesntContainACommandBlock() ? 12500 : p_82215_1_);
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(doesntContainACommandBlock() ? 1000.0D : p_82215_1_);
      syncHealthAfterFormChange();
      if (p_82215_1_ == 12500 && !isWild())
        for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world))
          entityplayer.sendStatusMessage(new TextComponentTranslation(doesntContainACommandBlock() ? "§5 A Wither Storm has fissioned!" : ("§5" + getOwner().getName() + "'s Wither Storm has grown to Destroyer form!!"), new Object[0]), true);
    } 
  }
  
  public boolean doesntContainACommandBlock() {
    return this.getDataManager().get(DOESNT_HAVE_COMMAND_BLOCK);
  }
  
  public void setNotContainingCommandBlock(boolean p_82215_1_) {
    if (p_82215_1_)
      this.lastDamage = Float.MAX_VALUE; 
    this.getDataManager().set(DOESNT_HAVE_COMMAND_BLOCK, p_82215_1_);
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
      getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(p_82215_1_ ? 1000.0D : getSize());
      syncHealthAfterFormChange();
    }
  }
  
  @Nullable
  protected ResourceLocation getLootTable() {
    return doesntContainACommandBlock() ? ELoot.ENTITIES_WITHER_STORM_MULAGEN : ELoot.ENTITIES_WITHER_STORM;
  }
  
  private void dropWitherStormLootTable() {
    ResourceLocation resourcelocation = getLootTable();
    if (resourcelocation == null)
      return; 
    LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(resourcelocation);
    LootContext lootcontext = (new LootContext.Builder((WorldServer)this.world)).withLootedEntity(this).withDamageSource(DamageSource.GENERIC).build();
    boolean droppedAny = false;
    for (ItemStack itemstack : loottable.generateLootForPools(this.rand, lootcontext)) {
      EntityItem entityitem = entityDropItem(itemstack, 0.0F);
      if (entityitem != null) {
        droppedAny = true;
        entityitem.setNoDespawn(); 
      } 
    } 
    if (!droppedAny && !doesntContainACommandBlock()) {
      EntityItem entityitem = entityDropItem(new ItemStack(EItem.witheredNetherStar), 0.0F);
      if (entityitem != null)
        entityitem.setNoDespawn(); 
    } 
  }
  
  protected SoundEvent getCrushHurtSound() {
    return ESound.fleshHitCrushHeavy;
  }
  
  private void finishWitherStormDeath() {
    if (net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) || this.witherStormDeathFinished)
      return; 
    this.witherStormDeathFinished = true;
    if (this.centerHead != null && this.centerHead.residentWitherStorm != null) {
      playSound(getHurtSound(null), getSoundVolume(), 2.0F);
      playSound(getHurtSound(null), getSoundVolume(), 2.0F);
      playSound(getHurtSound(null), getSoundVolume(), 2.0F);
      playSound(getHurtSound(null), getSoundVolume(), 2.0F);
      createEngenderModExplosionFireless(this, this.centerHead.posX, this.centerHead.posY, this.centerHead.posZ, 9.0F, EngenderConfig.mobs.grief);
      double d01 = this.centerHead.posX - this.posX;
      double d21 = this.centerHead.posZ - this.posZ;
      float f2 = MathHelper.sqrt(d01 * d01 + d21 * d21);
      if (f2 > 0.0F) {
        this.centerHead.motionX = d01 / f2 * 0.6D * 0.6D + this.centerHead.motionX;
        this.centerHead.motionZ = d21 / f2 * 0.6D * 0.6D + this.centerHead.motionZ;
      } 
    } 
    List<EntityFallingBlock> list = this.world.getEntitiesWithinAABB(EntityFallingBlock.class, getEntityBoundingBox().grow(128.0D, 128.0D, 128.0D), Predicates.and(EntitySelectors.NOT_SPECTATING));
    if (list != null && !list.isEmpty() && net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        for (EntityFallingBlock entity : list) {
            if (entity != null) {
                createEngenderModExplosionFireless(this, entity.posX, entity.posY, entity.posZ, 2.0F, false);
                entity.setDead();
            }
        }
    for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world))
      this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F); 
    int i = getSize();
    while (i > 0) {
      int j = EntityXPOrb.getXPSplit(i);
      i -= j;
      this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + 8.0D, this.posZ, j));
    } 
    dropWitherStormLootTable();
    if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
      for (EntityWitherStorm allmulegans : this.world.getEntitiesWithinAABB(EntityWitherStorm.class, getEntityBoundingBox().grow(256.0D))) {
        if (allmulegans.doesntContainACommandBlock())
          allmulegans.setHealth(0.0F); 
      }  
    for (int i1 = 0; i1 < getSize() / 10000; i1++)
      entityDropItem(new ItemStack(Blocks.OBSIDIAN, 64), 0.0F); 
    setDead();
  }
  
  protected void onDeathUpdate() {
    if (usesVanillaDeathUpdate()) {
      super.onDeathUpdate();
      return;
    }
    BlockPos blockpos = getPosition();
    if (getGrowingAge() < 50000) {
      if (this.tentacledevourer1 != null)
        this.tentacledevourer1.setDead(); 
      if (this.tentacledevourer2 != null)
        this.tentacledevourer2.setDead(); 
    } 
    if (doesntContainACommandBlock()) {
      this.deathTicks++;
      super.onDeathUpdate();
    } else {
      this.deathTicks++;
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world)) {
        finishWitherStormDeath();
        return;
      } 
      if (getSize() > 12500 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.world.isAreaLoaded(blockpos, blockpos))
        for (int j = 0; j < ((getSize() > 50000) ? 20 : 10); j++) {
          this.world.setBlockState(getPosition().up(j), Blocks.OBSIDIAN.getDefaultState());
          EntityFallingBlock deathBlocks = new EntityFallingBlock(this.world, this.posX, this.posY + 0.5D + j, this.posZ, this.world.getBlockState(getPosition().up(j)));
          deathBlocks.motionX += this.rand.nextDouble() * 4.0D - 2.0D;
          deathBlocks.motionY -= this.rand.nextDouble();
          deathBlocks.motionZ += this.rand.nextDouble() * 4.0D - 2.0D;
          deathBlocks.setHurtEntities(true);
          this.world.spawnEntity(deathBlocks);
          Grow(getSize() - 3);
        }  
      if (this.deathTicks % 20 == 0)
        this.deathTime++; 
      if (getHealth() <= 0.0F) {
        setAttackTarget(null);
        float f13 = (this.rand.nextFloat() - 0.5F) * 12.0F;
        float f15 = (this.rand.nextFloat() - 0.5F) * 36.0F;
        float f17 = (this.rand.nextFloat() - 0.5F) * 12.0F;
        this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX + f13, this.posY - 4.0D + f15, this.posZ + f17, 0.0D, 0.0D, 0.0D);
      } 
      if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        if (this.deathTicks == 1)
          if (getOwner() != null) {
            for (EntityPlayer entityplayer : net.minecraft.AgeOfMinecraft.util.EntityCompat.playerEntities(this.world)) {
              this.world.playSound(null, entityplayer.getPosition(), getDeathSound(), getSoundCategory(), getSoundVolume(), 1.0F);
              entityplayer.sendStatusMessage(new TextComponentTranslation("§4" + getOwner().getName() + "'s Wither Storm has been killed!!!", new Object[0]), true);
            } 
            getOwner().sendMessage(new TextComponentTranslation("Your Wither Storm has been destroyed!", new Object[0]));
          }   
      if (this.deathTicks == 1 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        return; 
      if (this.deathTicks == 80)
        if (this.tentacle1 != null && this.tentacle1.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacle1.posX, this.tentacle1.posY, this.tentacle1.posZ, 6.0F, EngenderConfig.mobs.grief);
          this.tentacle1.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacle1.motionY = 0.800000011920929D;
          this.tentacle1.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 100)
        if (this.tentacle2 != null && this.tentacle2.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacle2.posX, this.tentacle2.posY, this.tentacle2.posZ, 6.0F, EngenderConfig.mobs.grief);
          this.tentacle2.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacle2.motionY = 0.800000011920929D;
          this.tentacle2.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 110)
        if (this.tentacledevourer1 != null && this.tentacledevourer1.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacledevourer1.posX, this.tentacledevourer1.posY, this.tentacledevourer1.posZ, 15.0F, EngenderConfig.mobs.grief);
          this.tentacledevourer1.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacledevourer1.motionY = 0.800000011920929D;
          this.tentacledevourer1.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 150) {
        if (this.tentacle4 != null && this.tentacle4.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacle4.posX, this.tentacle4.posY, this.tentacle4.posZ, 6.0F, EngenderConfig.mobs.grief);
          this.tentacle4.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacle4.motionY = 0.800000011920929D;
          this.tentacle4.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        } 
        if (this.tentacle3 != null && this.tentacle3.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacle3.posX, this.tentacle3.posY, this.tentacle3.posZ, 6.0F, EngenderConfig.mobs.grief);
          this.tentacle3.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacle3.motionY = 0.800000011920929D;
          this.tentacle3.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        } 
      } 
      if (this.deathTicks == 180) {
        if (this.tentacle5 != null && this.tentacle5.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacle5.posX, this.tentacle5.posY, this.tentacle5.posZ, 6.0F, EngenderConfig.mobs.grief);
          this.tentacle5.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacle5.motionY = 0.800000011920929D;
          this.tentacle5.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        } 
        if (this.tentacledevourer2 != null && this.tentacledevourer2.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.tentacledevourer2.posX, this.tentacledevourer2.posY, this.tentacledevourer2.posZ, 15.0F, EngenderConfig.mobs.grief);
          this.tentacledevourer2.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.tentacledevourer2.motionY = 0.800000011920929D;
          this.tentacledevourer2.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        } 
      } 
      if (this.deathTicks == 180)
        if (this.leftHead != null && this.leftHead.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.leftHead.posX, this.leftHead.posY, this.leftHead.posZ, 9.0F, EngenderConfig.mobs.grief);
          this.leftHead.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.leftHead.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks == 200)
        if (this.rightHead != null && this.rightHead.residentWitherStorm != null) {
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          playSound(getHurtSound(null), getSoundVolume(), 2.0F);
          if (!net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
            createEngenderModExplosionFireless(this, this.rightHead.posX, this.rightHead.posY, this.rightHead.posZ, 9.0F, EngenderConfig.mobs.grief);
          this.rightHead.motionX = ((this.rand.nextFloat() - 0.5F) * 3.0F);
          this.rightHead.motionZ = ((this.rand.nextFloat() - 0.5F) * 3.0F);
        }  
      if (this.deathTicks >= 300 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world))
        finishWitherStormDeath(); 
    } 
  }
  
  class AILookAround extends EntityAIBase {
    private EntityWitherStorm witherStorm = EntityWitherStorm.this;
    
    public AILookAround() {
      setMutexBits(2);
    }
    
    public boolean shouldExecute() {
      return true;
    }
    
    public void updateTask() {
      if (this.witherStorm.getAttackTarget() != null) {
        this.witherStorm.getLookHelper().setLookPositionWithEntity(this.witherStorm.getAttackTarget(), 3.0F, 0.0F);
      } else if (this.witherStorm.centerHead != null && this.witherStorm.centerHead.isBeingRidden()) {
        Vec3d vec3 = this.witherStorm.centerHead.getControllingPassenger().getLook(1.0F);
        this.witherStorm.getLookHelper().setLookPosition((this.witherStorm.centerHead.getControllingPassenger()).posX + vec3.x * 8.0D, (this.witherStorm.centerHead.getControllingPassenger()).posY + vec3.y * 8.0D, (this.witherStorm.centerHead.getControllingPassenger()).posZ + vec3.z * 8.0D, 180.0F, 0.0F);
      } else {
        this.witherStorm.getLookHelper().setLookPosition(this.witherStorm.getMoveHelper().getX(), this.witherStorm.posY, this.witherStorm.getMoveHelper().getZ(), 3.0F, 0.0F);
      } 
    }
  }
  
  static class AIRandomFly extends EntityAIBase {
    private EntityWitherStorm witherStorm;
    
    public AIRandomFly(EntityWitherStorm ghast) {
      this.witherStorm = ghast;
      setMutexBits(1);
    }
    
    public boolean shouldExecute() {
      EntityMoveHelper entitymovehelper = this.witherStorm.getMoveHelper();
      if (!entitymovehelper.isUpdating())
        return true; 
      double d0 = entitymovehelper.getX() - this.witherStorm.posX;
      double d1 = entitymovehelper.getY() - this.witherStorm.posY;
      double d2 = entitymovehelper.getZ() - this.witherStorm.posZ;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return (d3 < 1.0D || d3 > 3600.0D);
    }
    
    public boolean shouldContinueExecuting() {
      return false;
    }
    
    public void startExecuting() {
      Random random = this.witherStorm.getRNG();
      if (this.witherStorm.getOwner() != null) {
        double d0 = (this.witherStorm.getOwner()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 48.0F);
        double d1 = (this.witherStorm.getOwner()).posY + 40.0D + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
        double d2 = (this.witherStorm.getOwner()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 48.0F);
        if (this.witherStorm.isSneaking()) {
          d0 = (this.witherStorm.getOwner()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
          d1 = (this.witherStorm.getOwner()).posY - 60.0D - ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          d2 = (this.witherStorm.getOwner()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 2.0F);
        } 
        if (this.witherStorm.centerHead != null && this.witherStorm.centerHead.isBeingRidden()) {
          Vec3d vec3 = this.witherStorm.getOwner().getLook(1.0F);
          d0 = this.witherStorm.posX + vec3.x * 8.0D;
          d1 = this.witherStorm.posY + vec3.y * 8.0D;
          d2 = this.witherStorm.posZ + vec3.z * 8.0D;
        } 
        this.witherStorm.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
      } else {
        EntityPlayer player = this.witherStorm.world.getClosestPlayerToEntity(this.witherStorm, 256.0D);
        if (this.witherStorm.getAttackTarget() != null) {
          double d0 = (this.witherStorm.getAttackTarget()).posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = this.witherStorm.world.getTopSolidOrLiquidBlock(this.witherStorm.getPosition()).getY() + 48.0D + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = (this.witherStorm.getAttackTarget()).posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.witherStorm.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } else if (player != null && this.witherStorm.getDistanceSq(player) > 2304.0D) {
          double d0 = player.posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = player.posY + 48.0D + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = player.posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.witherStorm.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } else {
          double d0 = this.witherStorm.posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = this.witherStorm.posY + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = this.witherStorm.posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          if (d1 < 32.0D)
            d1 = 32.0D; 
          this.witherStorm.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
        } 
      } 
    }
  }
  
  static class WitherStormMoveHelper extends EntityMoveHelper {
    private EntityWitherStorm witherStorm;
    
    private int courseChangeCooldown;
    
    public WitherStormMoveHelper(EntityWitherStorm ghast) {
      super(ghast);
      this.witherStorm = ghast;
    }
    
    public void onUpdateMoveHelper() {
      if (this.action == EntityMoveHelper.Action.MOVE_TO) {
        double d0 = this.posX - this.witherStorm.posX;
        double d1 = this.posY - this.witherStorm.posY;
        double d2 = this.posZ - this.witherStorm.posZ;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (d3 < 1.0E-7D) {
          this.action = EntityMoveHelper.Action.WAIT;
          return;
        }
        if (this.courseChangeCooldown-- <= 0) {
          this.courseChangeCooldown += this.witherStorm.getRNG().nextInt(5) + 2;
          d3 = MathHelper.sqrt(d3);
          if (d3 < 1.0E-4D) {
            this.action = EntityMoveHelper.Action.WAIT;
            return;
          }
          if (this.witherStorm.getOwner() != null && this.witherStorm.getDistanceSq(this.witherStorm.getOwner()) > 5184.0D && this.witherStorm.getGuardBlock() == null) {
            this.witherStorm.motionX += d0 / d3 * 0.2D;
            this.witherStorm.motionY += d1 / d3 * 0.2D;
            this.witherStorm.motionZ += d2 / d3 * 0.2D;
          } else if (this.witherStorm.moralRaisedTimer > 200) {
            this.witherStorm.motionX += d0 / d3 * 0.2D;
            this.witherStorm.motionY += d1 / d3 * 0.2D;
            this.witherStorm.motionZ += d2 / d3 * 0.2D;
          } else {
            this.witherStorm.motionX += d0 / d3 * 0.1D;
            this.witherStorm.motionY += d1 / d3 * 0.1D;
            this.witherStorm.motionZ += d2 / d3 * 0.1D;
          } 
        } 
      } 
    }
    
    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
      return true;
    }
  }
}

