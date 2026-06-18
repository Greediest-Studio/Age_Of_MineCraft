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
  protected static final DataParameter<EnumFacing> DIRECTION = EntityDataManager.func_187226_a(EntitySans.class, DataSerializers.field_187202_l);
  
  private static final DataParameter<Integer> ATTACKID = EntityDataManager.func_187226_a(EntitySans.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> EYEID = EntityDataManager.func_187226_a(EntitySans.class, DataSerializers.field_187192_b);
  
  private int attacks;
  
  private int attackSwitchTimer;
  
  public int attackInterval = 2;
  
  private boolean tookTurn;
  
  private SansSpeech talker;
  
  public EntitySans(World worldIn) {
    super(worldIn);
    this.attackSwitchTimer = 1;
    this.isOffensive = true;
    this.field_70178_ae = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 0.8D, 32.0F, 8.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIAttackRangedAlly(this, 0.0D, 10, 64.0F));
    func_70105_a(0.5F, 1.65F);
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
  
  public boolean func_70631_g_() {
    return false;
  }
  
  public void setChild(boolean childZombie) {}
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
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
          d10 = target.field_70165_t + this.field_70146_Z.nextGaussian() * 8.0D;
          d15 = target.field_70163_u;
          d20 = target.field_70161_v + this.field_70146_Z.nextGaussian() * 8.0D;
          d23 = target.field_70165_t - d10;
          d4 = target.field_70163_u - d15;
          d5 = target.field_70161_v - d20;
          bone = new EntityBoneAttack(this.field_70170_p, d10, d15, d20, d23, d4, d5);
          this.field_70170_p.func_72838_d(bone);
          bone.setBoneType(1);
          bone.setBlue(true);
          bone.shootingEntity = this;
          bone.field_70159_w *= 0.75D;
          bone.field_70179_y *= 0.75D;
          break;
        case 2:
          d10 = target.field_70165_t + this.field_70146_Z.nextDouble() * 16.0D - 8.0D;
          d15 = target.field_70163_u + 16.0D;
          d20 = target.field_70161_v + this.field_70146_Z.nextDouble() * 16.0D - 8.0D;
          d23 = target.field_70165_t - d10;
          d4 = target.field_70163_u + ((target.field_70131_O > 8.0F) ? 4.0D : (target.field_70131_O * 0.5D)) - d15;
          d5 = target.field_70161_v - d20;
          bone = new EntityBoneAttack(this.field_70170_p, d10, d15, d20, d23, d4, d5);
          this.field_70170_p.func_72838_d(bone);
          bone.setBlue((this.field_70146_Z.nextInt(3) == 0));
          bone.shootingEntity = this;
          bone.func_184185_a(ESound.bonelaunch, 1.0F, 1.0F);
          break;
        case 3:
          if (this.field_70146_Z.nextBoolean()) {
            double d27 = target.field_70165_t + 12.0D;
            double d28 = target.field_70165_t + 10.0D;
            double d29 = target.field_70165_t - d28;
            double d30 = target.field_70165_t - d27;
            EntityBoneAttack entityBoneAttack5 = new EntityBoneAttack(this.field_70170_p, d28, target.field_70163_u - target.field_70181_x - 0.5D, target.field_70161_v, d29, target.field_70163_u, target.field_70161_v);
            this.field_70170_p.func_72838_d(entityBoneAttack5);
            entityBoneAttack5.shootingEntity = this;
            entityBoneAttack5.setBoneType(2);
            entityBoneAttack5.accelerationZ = target.field_70161_v - entityBoneAttack5.field_70161_v;
            entityBoneAttack5.accelerationY = entityBoneAttack5.field_70163_u - entityBoneAttack5.field_70163_u;
            entityBoneAttack5.field_70159_w -= 0.6D;
            EntityBoneAttack entityBoneAttack6 = new EntityBoneAttack(this.field_70170_p, d27, target.field_70163_u - target.field_70181_x, target.field_70161_v, d30, target.field_70163_u, target.field_70161_v);
            this.field_70170_p.func_72838_d(entityBoneAttack6);
            entityBoneAttack6.shootingEntity = this;
            entityBoneAttack6.setBoneType(2);
            entityBoneAttack6.setBlue(true);
            entityBoneAttack6.accelerationZ = target.field_70161_v - entityBoneAttack6.field_70161_v;
            entityBoneAttack6.accelerationY = entityBoneAttack6.field_70163_u - entityBoneAttack6.field_70163_u;
            entityBoneAttack6.field_70159_w -= 0.6D;
            break;
          } 
          d9 = target.field_70165_t - 12.0D;
          d14 = target.field_70165_t - 10.0D;
          d19 = target.field_70165_t - d14;
          d22 = target.field_70165_t - d9;
          entityBoneAttack2 = new EntityBoneAttack(this.field_70170_p, d14, target.field_70163_u - target.field_70181_x - 0.5D, target.field_70161_v, d19, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(entityBoneAttack2);
          entityBoneAttack2.shootingEntity = this;
          entityBoneAttack2.setBoneType(2);
          entityBoneAttack2.accelerationZ = target.field_70161_v - entityBoneAttack2.field_70161_v;
          entityBoneAttack2.accelerationY = entityBoneAttack2.field_70163_u - entityBoneAttack2.field_70163_u;
          entityBoneAttack2.field_70159_w += 0.6D;
          bone2 = new EntityBoneAttack(this.field_70170_p, d9, target.field_70163_u - target.field_70181_x, target.field_70161_v, d22, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(bone2);
          bone2.shootingEntity = this;
          bone2.setBoneType(2);
          bone2.setBlue(true);
          bone2.accelerationZ = target.field_70161_v - bone2.field_70161_v;
          bone2.accelerationY = bone2.field_70163_u - bone2.field_70163_u;
          bone2.field_70159_w += 0.6D;
          break;
        case 4:
          if (getEyeType() != 1)
            setEyeType(1); 
          d8 = target.field_70165_t + MathHelper.func_76126_a(this.field_70146_Z.nextFloat() * 360.0F) * 16.0D;
          d13 = target.field_70163_u + 0.5D;
          d18 = target.field_70161_v + MathHelper.func_76134_b(this.field_70146_Z.nextFloat() * 360.0F) * 16.0D;
          entityGasterBlaster1 = new EntityGasterBlaster(this.field_70170_p, d8, d13, d18, target.field_70165_t, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d((Entity)entityGasterBlaster1);
          entityGasterBlaster1.targetpointx = target.field_70165_t;
          entityGasterBlaster1.targetpointy = entityGasterBlaster1.field_70163_u + entityGasterBlaster1.func_70047_e() + target.field_70181_x * 10.0D;
          entityGasterBlaster1.targetpointz = target.field_70161_v;
          entityGasterBlaster1.shootingEntity = this;
          entityGasterBlaster1.func_184185_a(ESound.gasterblasterspawn, 3.0F, 0.9F);
          break;
        case 5:
          setEnergy(getEnergy() - 0.1F);
          if (getEyeType() != 2) {
            setEyeType(2);
            func_184185_a(ESound.sansblink, 5.0F, 1.0F);
          } 
          rot = this.field_70759_as * 0.017453292F;
          f1 = MathHelper.func_76126_a(rot);
          f2 = MathHelper.func_76134_b(rot);
          switch (this.field_70146_Z.nextInt(6)) {
            case 1:
              setHandDirection(EnumFacing.DOWN);
              target.field_70159_w = 0.0D;
              target.field_70181_x = -2.0D;
              target.field_70179_y = 0.0D;
              target.field_70143_R += 10.0F;
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 2:
              setHandDirection(EnumFacing.NORTH);
              target.field_70181_x += 0.5D;
              target.field_70159_w = -(f1 * 2.0F);
              target.field_70179_y = -(f2 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 3:
              setHandDirection(EnumFacing.SOUTH);
              target.field_70181_x += 0.5D;
              target.field_70159_w = (f1 * 2.0F);
              target.field_70179_y = (f2 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 4:
              setHandDirection(EnumFacing.EAST);
              target.field_70181_x += 0.5D;
              target.field_70159_w = (f2 * 2.0F);
              target.field_70179_y = (f1 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
            case 5:
              setHandDirection(EnumFacing.WEST);
              target.field_70181_x += 0.5D;
              target.field_70159_w = -(f2 * 2.0F);
              target.field_70179_y = -(f1 * 2.0F);
              if (target instanceof EntityPlayerMP)
                ((EntityPlayerMP)target).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)target)); 
              break;
          } 
          setHandDirection(EnumFacing.UP);
          target.field_70159_w = 0.0D;
          target.field_70181_x = 2.0D;
          target.field_70179_y = 0.0D;
          if (target instanceof EntityPlayerMP)
            ((EntityPlayerMP)target).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity((Entity)target)); 
          break;
        case 6:
          d7 = target.field_70165_t + MathHelper.func_76126_a(this.field_70146_Z.nextFloat() * 360.0F) * 16.0D;
          d12 = target.field_70163_u + 0.5D;
          d18 = target.field_70161_v + MathHelper.func_76134_b(this.field_70146_Z.nextFloat() * 360.0F) * 16.0D;
          entityGasterBlaster1 = new EntityGasterBlaster(this.field_70170_p, d7, d12, d18, target.field_70165_t, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d((Entity)entityGasterBlaster1);
          entityGasterBlaster1.targetpointx = target.field_70165_t;
          entityGasterBlaster1.targetpointy = entityGasterBlaster1.field_70163_u + entityGasterBlaster1.func_70047_e() + target.field_70181_x * 10.0D;
          entityGasterBlaster1.targetpointz = target.field_70161_v;
          entityGasterBlaster1.shootingEntity = this;
          entityGasterBlaster1.func_184185_a(ESound.gasterblasterspawn, 3.0F, 1.0F);
          entityGasterBlaster1.setSmall(true);
          break;
        case 7:
          dI = 3.0D + MathHelper.func_76126_a(this.field_70173_aa * 0.1F) * 3.0D;
          dII = 3.0D - MathHelper.func_76126_a(this.field_70173_aa * 0.1F) * 3.0D;
          d17 = target.field_70165_t + 15.0D;
          d21 = target.field_70165_t - d17;
          d24 = target.field_70161_v + dI;
          d25 = target.field_70161_v + dI - d24;
          d26 = target.field_70161_v - dII;
          d6 = target.field_70161_v - dII - d26;
          entityBoneAttack3 = new EntityBoneAttack(this.field_70170_p, d17, target.field_70163_u - 1.0D, d24, d21, target.field_70163_u, d25);
          this.field_70170_p.func_72838_d(entityBoneAttack3);
          entityBoneAttack3.shootingEntity = this;
          entityBoneAttack3.setBoneType(3);
          entityBoneAttack3.accelerationY = entityBoneAttack3.field_70163_u - entityBoneAttack3.field_70163_u;
          entityBoneAttack3.accelerationZ = entityBoneAttack3.field_70161_v - entityBoneAttack3.field_70161_v;
          entityBoneAttack3.accelerationX *= 0.5D;
          entityBoneAttack4 = new EntityBoneAttack(this.field_70170_p, d17, target.field_70163_u - 1.0D, d26, d21, target.field_70163_u, d6);
          this.field_70170_p.func_72838_d(entityBoneAttack4);
          entityBoneAttack4.shootingEntity = this;
          entityBoneAttack4.setBoneType(3);
          entityBoneAttack4.accelerationY = entityBoneAttack4.field_70163_u - entityBoneAttack4.field_70163_u;
          entityBoneAttack4.accelerationZ = entityBoneAttack4.field_70161_v - entityBoneAttack4.field_70161_v;
          entityBoneAttack4.accelerationX *= 0.5D;
          break;
        case 10:
          d0 = target.field_70165_t + MathHelper.func_76134_b(this.field_70173_aa * 0.05F) * 8.0D;
          d1 = target.field_70163_u + 0.5D;
          d2 = target.field_70161_v + MathHelper.func_76126_a(this.field_70173_aa * 0.05F) * 8.0D;
          blaster = new EntityGasterBlaster(this.field_70170_p, d0, d1, d2, target.field_70165_t, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d((Entity)blaster);
          blaster.targetpointx = target.field_70165_t;
          blaster.targetpointy = d1 + 0.5D + target.field_70181_x * 10.0D;
          blaster.targetpointz = target.field_70161_v;
          blaster.shootingEntity = this;
          blaster.func_184185_a(ESound.gasterblasterspawn, 3.0F, 1.0F);
          blaster.setSmall(true);
          break;
        case 11:
          d0 = target.field_70165_t + 5.0D;
          d3 = target.field_70165_t - d0;
          bone1 = new EntityBoneAttack(this.field_70170_p, d0, target.field_70163_u - 1.5D + target.field_70181_x, target.field_70161_v, d3, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(bone1);
          bone1.shootingEntity = this;
          bone1.setBoneType(4);
          bone1.accelerationZ = target.field_70161_v - bone1.field_70161_v;
          bone1.accelerationY = bone1.field_70163_u - bone1.field_70163_u;
          break;
        default:
          d01 = target.field_70165_t - 10.0D;
          d11 = target.field_70165_t + 10.0D;
          d16 = target.field_70165_t - d11;
          d31 = target.field_70165_t - d01;
          entityBoneAttack1 = new EntityBoneAttack(this.field_70170_p, d11, target.field_70163_u - 1.5D + target.field_70181_x, target.field_70161_v, d16, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(entityBoneAttack1);
          entityBoneAttack1.shootingEntity = this;
          entityBoneAttack1.setBoneType(1);
          entityBoneAttack1.accelerationZ = target.field_70161_v - entityBoneAttack1.field_70161_v;
          entityBoneAttack1.accelerationY = entityBoneAttack1.field_70163_u - entityBoneAttack1.field_70163_u;
          entityBoneAttack1.field_70159_w -= 0.5D;
          bone2 = new EntityBoneAttack(this.field_70170_p, d01, target.field_70163_u - 1.5D + target.field_70181_x, target.field_70161_v, d31, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(bone2);
          bone2.shootingEntity = this;
          bone2.setBoneType(1);
          bone2.accelerationZ = target.field_70161_v - bone2.field_70161_v;
          bone2.accelerationY = bone2.field_70163_u - bone2.field_70163_u;
          bone2.field_70159_w += 0.5D;
          bone3 = new EntityBoneAttack(this.field_70170_p, d11, target.field_70163_u + target.field_70131_O + 0.5D + target.field_70181_x, target.field_70161_v, d16, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(bone3);
          bone3.shootingEntity = this;
          bone3.setBoneType(1);
          bone3.accelerationZ = target.field_70161_v - bone3.field_70161_v;
          bone3.accelerationY = bone3.field_70163_u - bone3.field_70163_u;
          bone3.field_70159_w -= 0.5D;
          bone4 = new EntityBoneAttack(this.field_70170_p, d01, target.field_70163_u + target.field_70131_O + 0.5D + target.field_70181_x, target.field_70161_v, d31, target.field_70163_u, target.field_70161_v);
          this.field_70170_p.func_72838_d(bone4);
          bone4.shootingEntity = this;
          bone4.setBoneType(1);
          bone4.accelerationZ = target.field_70161_v - bone4.field_70161_v;
          bone4.accelerationY = bone4.field_70163_u - bone4.field_70163_u;
          bone4.field_70159_w += 0.5D;
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
          func_184185_a(ESound.sansblink, 5.0F, 1.0F);
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
    if (!target.func_70089_S() && target instanceof EntityPlayer)
      setEnergy(100.0F); 
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    entity.field_70172_ad = 0;
    entity.func_184185_a(ESound.bonehit, 0.1F, 1.0F);
  }
  
  public void karmicRetribution(EntityLivingBase entity, int karmaAmount) {
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY)
      karmaAmount = Math.min(karmaAmount / 2, karmaAmount); 
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
      karmaAmount = karmaAmount * 3 / 2; 
    if (entity.func_70662_br())
      karmaAmount *= 2; 
    if (entity instanceof EntityTameBase && ((EntityTameBase)entity).isABoss())
      karmaAmount *= 3; 
    if (!entity.func_184222_aU())
      karmaAmount *= 3; 
    if (entity instanceof EntityTameBase && ((EntityTameBase)entity).getTier() == EnumTier.TIER6)
      karmaAmount *= 5; 
    if (entity instanceof net.minecraft.entity.passive.EntityAnimal || entity instanceof net.minecraft.AgeOfMinecraft.entity.tame.Animal || func_184191_r((Entity)entity))
      karmaAmount = 0; 
    if (entity.func_70089_S() && entity.func_190631_cK() && !func_184191_r((Entity)entity) && !(entity instanceof EntitySans)) {
      NBTTagCompound karma = entity.getEntityData();
      if (karma.func_150297_b("KR", 99)) {
        karma.func_74768_a("KR", karma.func_74762_e("KR") + karmaAmount);
      } else {
        karma.func_74768_a("KR", karmaAmount);
      } 
    } 
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(DIRECTION, EnumFacing.SOUTH);
    func_184212_Q().func_187214_a(ATTACKID, Integer.valueOf(-1));
    func_184212_Q().func_187214_a(EYEID, Integer.valueOf(0));
  }
  
  public int getAttackType() {
    return ((Integer)this.field_70180_af.func_187225_a(ATTACKID)).intValue();
  }
  
  public void setAttackType(int id) {
    this.field_70180_af.func_187227_b(ATTACKID, Integer.valueOf(id));
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
    return ((Integer)this.field_70180_af.func_187225_a(EYEID)).intValue();
  }
  
  public void setEyeType(int age) {
    this.field_70180_af.func_187227_b(EYEID, Integer.valueOf(age));
  }
  
  public EnumFacing getHandDirection() {
    return (EnumFacing)this.field_70180_af.func_187225_a(DIRECTION);
  }
  
  public void setHandDirection(EnumFacing direction) {
    this.field_70180_af.func_187227_b(DIRECTION, direction);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(64.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(FITTNESS).func_111128_a(1.0D);
    func_110148_a(STRENGTH).func_111128_a(1.0D);
    func_110148_a(STAMINA).func_111128_a(100.0D);
    func_110148_a(INTELLIGENCE).func_111128_a(100.0D);
    func_110148_a(DEXTERITY).func_111128_a(100.0D);
    func_110148_a(AGILITY).func_111128_a(100.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setAttackType(tagCompund.func_74762_e("Attack"));
    setEyeType(tagCompund.func_74762_e("Eye"));
    this.field_70180_af.func_187227_b(DIRECTION, EnumFacing.func_82600_a(tagCompund.func_74771_c("Direction")));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("Attack", getAttackType());
    tagCompound.func_74768_a("Eye", getEyeType());
    tagCompound.func_74774_a("Direction", (byte)((EnumFacing)this.field_70180_af.func_187225_a(DIRECTION)).func_176745_a());
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
    this.bossInfo.func_186735_a(getEnergy() / 100.0F);
    this.bossInfo.func_186745_a(BossInfo.Color.WHITE);
  }
  
  public String func_70005_c_() {
    return "sans";
  }
  
  public boolean func_184595_k(double x, double y, double z) {
    double d0 = this.field_70165_t;
    double d1 = this.field_70163_u;
    double d2 = this.field_70161_v;
    this.field_70165_t = x;
    this.field_70163_u = y;
    this.field_70161_v = z;
    boolean flag = false;
    BlockPos blockpos = new BlockPos((Entity)this);
    World world = this.field_70170_p;
    if (world.func_175667_e(blockpos)) {
      boolean flag1 = false;
      while (!flag1 && blockpos.func_177956_o() > 0) {
        BlockPos blockpos1 = blockpos.func_177977_b();
        IBlockState iblockstate = world.func_180495_p(blockpos1);
        if (iblockstate.func_185914_p()) {
          flag1 = true;
          continue;
        } 
        this.field_70163_u--;
        blockpos = blockpos1;
      } 
      if (flag1) {
        func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v);
        if (func_184207_aI())
          func_184179_bs().func_70634_a(this.field_70165_t, this.field_70163_u, this.field_70161_v); 
        if (world.func_184144_a((Entity)this, func_174813_aQ()).isEmpty())
          flag = true; 
      } 
    } 
    if (!flag) {
      func_70634_a(d0, d1, d2);
      return false;
    } 
    if (this instanceof net.minecraft.entity.EntityCreature)
      func_70661_as().func_75499_g(); 
    return true;
  }
  
  protected boolean teleportRandomly() {
    double d0 = this.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 16.0D;
    double d1 = this.field_70163_u + (this.field_70146_Z.nextDouble() - 0.5D) * 16.0D;
    double d2 = this.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 16.0D;
    return teleportTo(d0, d1, d2);
  }
  
  protected boolean teleportToEntity(Entity p_70816_1_) {
    double d1 = p_70816_1_.field_70165_t + (this.field_70146_Z.nextDouble() - 0.5D) * 16.0D;
    double d2 = p_70816_1_.field_70163_u + (this.field_70146_Z.nextDouble() - 0.5D) * 16.0D;
    double d3 = p_70816_1_.field_70161_v + (this.field_70146_Z.nextDouble() - 0.5D) * 16.0D;
    return teleportTo(d1, d2, d3);
  }
  
  protected boolean teleportTo(double x, double y, double z) {
    EnderTeleportEvent event = new EnderTeleportEvent((EntityLivingBase)this, x, y, z, 0.0F);
    if (MinecraftForge.EVENT_BUS.post((Event)event))
      return false; 
    boolean flag = func_184595_k(event.getTargetX(), event.getTargetY(), event.getTargetZ());
    if (flag) {
      this.field_70170_p.func_184148_a((EntityPlayer)null, this.field_70169_q, this.field_70167_r, this.field_70166_s, ESound.sansblink, func_184176_by(), 1.0F, 1.0F);
      func_184185_a(ESound.sansblink, 1.0F, 1.0F);
      this.field_70159_w = this.field_70181_x = this.field_70179_y = 0.0D;
      this.field_70761_aq = this.field_70177_z = this.field_70759_as;
    } 
    return flag;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    func_110148_a(FITTNESS).func_111128_a(1.0D);
    func_110148_a(STRENGTH).func_111128_a(1.0D);
    func_110148_a(STAMINA).func_111128_a(100.0D);
    func_110148_a(INTELLIGENCE).func_111128_a(100.0D);
    func_110148_a(DEXTERITY).func_111128_a(100.0D);
    func_110148_a(AGILITY).func_111128_a(100.0D);
    if (getEnergy() <= 50.0F)
      spawnStressParticle(); 
    if (getEnergy() <= 20.0F)
      spawnStressParticle(); 
    if (this.talker == null && this.field_70173_aa == 800 && getAttackType() < 0)
      this.talker = new SansSpeech(this, "idlequote", 23, ESound.sanstalk, 2); 
    if (this.field_70173_aa % 100 == 0)
      setEnergy(getEnergy() + 1.0F); 
    if (getAttackType() == -1)
      setEnergy(getEnergy() + 0.05F); 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && !(func_70638_az() instanceof EntityPlayer) && this.tookTurn) {
      this.tookTurn = false;
      setAttackType(this.field_70146_Z.nextInt(8));
    } 
    if (!this.field_70170_p.field_72995_K && func_70638_az() != null && func_70638_az() instanceof EntityTameBase && ((EntityTameBase)func_70638_az()).getFakeHealth() > 0.0F)
      setAttackType(11); 
    if (!this.field_70170_p.field_72995_K && func_70638_az() == null)
      setAttackType(-1); 
    if (getAttackType() != 10 && getEnergy() <= 1.0F)
      setAttackType(10); 
    if (this.field_70163_u < -5.0D)
      teleportRandomly(); 
    if (!this.tookTurn && this.attackSwitchTimer > 0)
      this.attackSwitchTimer--; 
    if (this.field_70181_x > 1.5D)
      teleportRandomly(); 
    if (func_70638_az() != null && func_70032_d((Entity)func_70638_az()) > 32.0D && this.field_70146_Z.nextInt(500) == 0)
      teleportToEntity((Entity)func_70638_az()); 
    if (this.talker != null)
      if (this.talker.ended) {
        this.talker = null;
      } else {
        this.talker.updateSpeech();
      }  
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (source.func_76355_l() == "sans")
      return true; 
    if (func_180431_b(source) || source.func_76346_g() == null) {
      if (!this.field_70170_p.field_72995_K)
        teleportRandomly(); 
      return false;
    } 
    if (getEnergy() > 0.0F && getFakeHealth() <= 0.0F) {
      float cantkeepdodgingforever = 1.0F;
      if (source.func_76346_g() != null && source.func_76346_g() instanceof EntityPlayer) {
        cantkeepdodgingforever = 5.0F;
        if (this.talker == null && getEnergy() >= 98.0F)
          this.talker = new SansSpeech(this, "firstattacked", 44, ESound.sanstalk, 2); 
        setEnergy(getEnergy() - 3.0F);
      } 
      if (!this.field_70170_p.field_72995_K && source.func_76346_g() != null && this.tookTurn) {
        this.tookTurn = false;
        setAttackType(this.field_70146_Z.nextInt(8));
      } 
      if (!this.field_70170_p.field_72995_K)
        if (teleportRandomly() && this.field_70146_Z.nextInt(5) == 0) {
          setEnergy(getEnergy() - cantkeepdodgingforever);
        } else {
          setEnergy(getEnergy() - 1.0F);
          this.field_70181_x--;
          this.field_70159_w = this.field_70146_Z.nextDouble() * 2.0D - 1.0D;
          this.field_70179_y = this.field_70146_Z.nextDouble() * 2.0D - 1.0D;
          if (this.field_70159_w > 0.0D)
            this.field_70159_w++; 
          if (this.field_70159_w < 0.0D)
            this.field_70159_w--; 
          if (this.field_70179_y > 0.0D)
            this.field_70179_y++; 
          if (this.field_70179_y < 0.0D)
            this.field_70179_y--; 
        }  
      return false;
    } 
    return super.func_70097_a(source, amount);
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
      if (!this.sans.field_70170_p.field_72995_K && this.timer >= this.delay && !this.ended) {
        for (EntityPlayer player : this.sans.field_70170_p.field_73010_i) {
          player.func_146105_b((ITextComponent)new TextComponentTranslation("speech.sans." + this.name + "." + this.id, new Object[0]), true);
          if (this.soundbite != null)
            this.sans.field_70170_p.func_184133_a(null, player.func_180425_c(), this.soundbite, this.sans.func_184176_by(), 1.0F, 1.0F); 
          this.timer = 0;
          this.id++;
        } 
        if (this.id >= this.length)
          this.ended = true; 
      } 
    }
  }
}
