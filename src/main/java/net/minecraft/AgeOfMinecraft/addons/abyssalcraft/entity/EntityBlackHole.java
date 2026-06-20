package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity;

import java.util.List;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBlackHole extends Entity {
  public EntityTameBase shootingEntity;
  
  public EntityBlackHole(World worldIn) {
    super(worldIn);
    this.preventEntitySpawning = true;
    setSize(3.0F, 3.0F);
  }
  
  public EntityBlackHole(World worldIn, EntityTameBase entity) {
    this(worldIn);
    this.shootingEntity = entity;
    copyLocationAndAnglesFrom(entity);
  }
  
  protected boolean canTriggerWalking() {
    return false;
  }
  
  protected void entityInit() {}
  
  public double getYOffset() {
    return isRiding() ? (-(getRidingEntity()).height * 0.5D) : 0.0D;
  }
  
  public void onUpdate() {
    this.prevPosX = this.posX;
    this.prevPosY = this.posY;
    this.prevPosZ = this.posZ;
    if (this.ticksExisted >= (isRiding() ? 199 : 400)) {
      setDead();
      if (isRiding()) {
        this.world.playEvent(3000, getPosition(), 0);
        playSound(ESound.jzaharshout, 10.0F, 0.5F);
        playSound(ESound.blast, 10.0F, 1.0F);
        getRidingEntity().setDead();
      } 
    } 
    if (isRiding() && getRidingEntity() != null && getRidingEntity() instanceof EntityLivingBase && ((EntityLivingBase)getRidingEntity()).deathTime > 0) {
      ((EntityLivingBase)getRidingEntity()).deathTime = 0;
      ((EntityLivingBase)getRidingEntity()).hurtTime = 10;
    } 
    List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(64.0D));
    if (!isRiding() && list != null && !list.isEmpty())
      for (int j = 0; j < list.size(); j++) {
        Entity entity = list.get(j);
        if (this.shootingEntity == null && entity instanceof EntityJzahar)
          this.shootingEntity = (EntityJzahar)entity; 
        if (this.shootingEntity != null && (entity == this.shootingEntity || (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage) || (entity instanceof EntityLivingBase && false))) {
          entity.hurtResistantTime = 100;
          if (entity instanceof EntityLivingBase)
            ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 100, 4)); 
        } 
        if (entity.isEntityAlive() && !(entity instanceof EntityJzahar) && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
          if (entity.posY < this.posY)
            entity.motionY += 0.02500000037252903D; 
          double d1 = 20.0D;
          float x = MathHelper.cos((this.ticksExisted + j * 2)) * (3.0F + (float)(this.rand.nextGaussian() * 6.0D));
          float z = MathHelper.sin((this.ticksExisted + j * 2)) * (3.0F + (float)(this.rand.nextGaussian() * 6.0D));
          double d2 = this.posX + x - entity.posX;
          double d3 = this.posY + 1.0D - entity.posY;
          double d4 = this.posZ + z - entity.posZ;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          if (entity.getDistanceSq(this) <= 64.0D && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
            entity.attackEntityFrom(DamageSource.OUT_OF_WORLD, (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian) ? 20.0F : 4.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.isEntityAlive() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " touched a Black Hole's event horizon thanks to " + this.shootingEntity.getName() + " (" + this.shootingEntity.getOwner().getName() + ")", new Object[0]));
          } 
          if (entity.getDistanceSq(this) <= 16.0D && this.shootingEntity != null && entity instanceof EntityLivingBase && !false) {
            if (entity instanceof EntityLivingBase)
              ((EntityLivingBase)entity).setHealth((entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm) ? (((EntityLivingBase)entity).getHealth() - 100.0F) : (((EntityLivingBase)entity).getHealth() - 10.0F)); 
            entity.attackEntityFrom(DamageSource.CRAMMING.setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute(), (entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier6.EntityWitherStorm || entity instanceof net.minecraft.AgeOfMinecraft.addons.draconicevolution.EntityChaosGuardian) ? 500.0F : 50.0F);
            if (EngenderConfig.general.useMessage && entity instanceof EntityLivingBase && !entity.isEntityAlive() && !this.shootingEntity.isWild())
              this.shootingEntity.getOwner().sendMessage(new TextComponentTranslation(entity.getName() + " was sucked into a Black Hole thanks to " + this.shootingEntity.getName() + " (" + this.shootingEntity.getOwner().getName() + ")", new Object[0]));
            if (!entity.isEntityAlive() && entity.isNonBoss() && !(entity instanceof EntityTameBase)) {
              if (entity instanceof EntityLiving)
                ((EntityLiving)entity).spawnExplosionParticle(); 
              entity.setDead();
            } 
          } 
        } 
        if (entity != null && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityBlackHole) && !(entity instanceof EntityImplosion)) {
          float x = MathHelper.cos((this.ticksExisted + j * 2)) * (3.0F + (float)(this.rand.nextGaussian() * 6.0D));
          float z = MathHelper.sin((this.ticksExisted + j * 2)) * (3.0F + (float)(this.rand.nextGaussian() * 6.0D));
          double d1 = 20.0D;
          double d2 = this.posX + x - entity.posX;
          double d3 = this.posY + 1.0D - entity.posY;
          double d4 = this.posZ + z - entity.posZ;
          double d5 = d2 * d2 + d3 * d3 + d4 * d4;
          entity.setPosition(entity.posX, entity.posY, entity.posZ);
          entity.addVelocity(d2 / d5 * d1, d3 / d5 * d1, d4 / d5 * d1);
          this.world.setEntityState(entity, (byte)2);
          if (entity.getDistanceSq(this) <= 16.0D)
            entity.setDead(); 
        } 
      }  
    int i = MathHelper.floor(this.posY);
    int i1 = MathHelper.floor(this.posX);
    int j1 = MathHelper.floor(this.posZ);
    for (int l1 = -6; l1 <= 6; l1++) {
      for (int i2 = -6; i2 <= 6; i2++) {
        for (int j = -6; j <= 6; j++) {
          int j2 = i1 + l1;
          int k = i + j;
          int l = j1 + i2;
          BlockPos pos = new BlockPos(j2, k, l);
          IBlockState state = this.world.getBlockState(pos);
          Block block = state.getBlock();
          if (!isRiding() && !block.isAir(state, this.world, pos) && this.rand.nextInt(10) == 0 && !net.minecraft.AgeOfMinecraft.util.EntityCompat.isRemote(this.world) && this.world.isAreaLoaded(getPosition().add(-32, -32, -32), getPosition().add(32, 32, 32)) && block.getBlockHardness(state, this.world, new BlockPos(j2, k, l)) != -1.0F)
            if (block.getMaterial(state).isLiquid()) {
              this.world.setBlockToAir(new BlockPos(j2, k, l));
            } else {
              this.world.spawnEntity(new EntityFallingBlock(this.world, j2, k + 0.5D, l, block.getDefaultState()));
            }  
        } 
      } 
    } 
  }
  
  protected void writeEntityToNBT(NBTTagCompound compound) {}
  
  protected void readEntityFromNBT(NBTTagCompound compound) {}
  
  public EnumPushReaction getPushReaction() {
    return EnumPushReaction.IGNORE;
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return false;
  }
  
  @SideOnly(Side.CLIENT)
  public boolean isInRangeToRenderDist(double distance) {
    return true;
  }
}


