package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
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
  private static final DataParameter<Boolean> ATTACKING = EntityDataManager.func_187226_a(EntityGhasther.class, DataSerializers.field_187198_h);
  
  private int explosionStrength = 5;
  
  public EnumBehaviour behaviour = EnumBehaviour.REGULAR;
  
  private int damageTillNextScream;
  
  public EntityGhasther(World worldIn) {
    super(worldIn);
    func_70105_a(9.0F, 9.0F);
    this.field_70728_aV = 100;
    this.field_70178_ae = true;
    this.isOffensive = true;
    this.field_70765_h = new GhastMoveHelper(this);
    this.field_70714_bg.func_75776_a(0, new AIFireballAttack(this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, EngenderConfig.mobs.useMobTalkerModels ? 64.0F : 100.0F, EngenderConfig.mobs.useMobTalkerModels ? 9.0F : 16.0F));
    this.field_70714_bg.func_75776_a(2, new AIRandomFly(this));
    this.field_70714_bg.func_75776_a(3, new AILookAround());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
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
    this.bossInfo.func_186745_a(BossInfo.Color.YELLOW);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityGhasther(this.field_70170_p);
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
    return ((Boolean)this.field_70180_af.func_187225_a(ATTACKING)).booleanValue();
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  public void setAttacking(boolean attacking) {
    this.field_70180_af.func_187227_b(ATTACKING, Boolean.valueOf(attacking));
  }
  
  public void performSpecialAttack() {
    func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), func_70647_i());
    setSpecialAttackTimer(1200);
  }
  
  protected float func_70647_i() {
    return super.func_70647_i() - 0.25F;
  }
  
  public void func_70110_aj() {}
  
  public int getSpawnTimer() {
    return 80;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (!this.field_70170_p.field_72995_K)
      if (!this.field_70170_p.field_72995_K) {
        if (func_70089_S() && func_70638_az() != null && func_70638_az().func_70089_S() && this.isOffensive && !func_70631_g_() && !func_184191_r((Entity)func_70638_az()))
          if (func_70068_e((Entity)func_70638_az()) < (this.reachWidth * this.reachWidth + ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N) * ((func_70638_az() instanceof EntityTameBase) ? ((EntityTameBase)func_70638_az()).reachWidth : (func_70638_az()).field_70130_N)) + 9.0D && (this.field_70173_aa + func_145782_y()) % 10 == 0)
            func_70652_k((Entity)func_70638_az());  
        if (func_70089_S()) {
          ChunkLoadingEvent.updateLoaded((Entity)this);
        } else {
          ChunkLoadingEvent.stopLoading((Entity)this);
        } 
      }  
    this.field_70122_E = false;
    this.field_70160_al = true;
    if (isHero() && getSpecialAttackTimer() > 1100) {
      func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), func_70647_i());
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(64.0D, 64.0D, 64.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null)
            if (!func_184191_r((Entity)entity)) {
              if (getSpecialAttackTimer() > 1190 && entity instanceof EntityCreature && !(entity instanceof EntityTameBase))
                ((EntityCreature)entity).field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)entity, EntityGhasther.class, 128.0F, 1.5D, 1.5D)); 
              entity.field_70172_ad = 0;
              inflictEngenderMobDamage(entity, "'s ears exploded thanks to ", DamageSource.field_82727_n, 0.25F);
              entity.func_70024_g(this.field_70146_Z.nextGaussian() * 0.05D, this.field_70146_Z.nextGaussian() * 0.05D, this.field_70146_Z.nextGaussian() * 0.05D);
            }  
        }  
    } 
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 2048.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
    if (func_70638_az() != null && getOwner() != null && !func_70685_l((Entity)func_70638_az()) && this.field_70146_Z.nextInt(80) == 0)
      func_184185_a(func_184601_bQ((DamageSource)null), func_70599_aP(), func_70647_i() + 0.25F); 
    if (func_184179_bs() != null && func_184179_bs() instanceof EntityPlayer) {
      EntityPlayer passenger = (EntityPlayer)func_184179_bs();
      this.field_70761_aq = this.field_70177_z = this.field_70759_as = passenger.field_70177_z;
      this.field_70125_A = EngenderConfig.mobs.useMobTalkerModels ? passenger.field_70125_A : 0.0F;
      double d1 = 0.4D;
      if (this.moralRaisedTimer > 200)
        d1 *= 2.0D; 
      Vec3d vec3 = passenger.func_70676_i(1.0F);
      if (passenger.field_191988_bg > 0.0F)
        func_70091_d(MoverType.SELF, vec3.field_72450_a * d1, vec3.field_72448_b * d1, vec3.field_72449_c * d1); 
      if (passenger.field_191988_bg < 0.0F)
        func_70091_d(MoverType.SELF, -(vec3.field_72450_a * d1), -(vec3.field_72448_b * d1), -(vec3.field_72449_c * d1)); 
    } 
    if (getOwner() != null)
      if (func_70638_az() == null && this.field_70173_aa % 10 == 0) {
        double d0 = func_110148_a(SharedMonsterAttributes.field_111265_b).func_111126_e();
        List<Entity> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(d0, d0, d0), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
        if (list != null && !list.isEmpty())
          for (int i1 = 0; i1 < list.size(); i1++) {
            Entity entity = list.get(i1);
            if (entity != null && entity.func_70089_S() && func_70685_l(entity) && !func_184191_r(entity) && entity.func_70068_e((Entity)getOwner()) <= 256.0D) {
              func_70624_b((EntityLivingBase)entity);
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
  
  protected void func_184231_a(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (this.deathTicks >= 300)
      super.func_191986_a(strafe, vertical, forward); 
    if (!func_175446_cd() || func_184207_aI() || this.deathTicks < 300) {
      if (func_70090_H()) {
        func_191958_b(strafe, vertical, forward, 0.02F);
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.800000011920929D;
        this.field_70181_x *= 0.800000011920929D;
        this.field_70179_y *= 0.800000011920929D;
      } else if (func_180799_ab()) {
        func_191958_b(strafe, vertical, forward, 0.02F);
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= 0.5D;
        this.field_70181_x *= 0.5D;
        this.field_70179_y *= 0.5D;
      } else {
        float f = 0.95F;
        float f1 = 0.16277136F / f * f * f;
        func_191958_b(strafe, vertical, forward, 0.02F);
        f = 0.95F;
        func_70091_d(MoverType.SELF, this.field_70159_w, this.field_70181_x, this.field_70179_y);
        this.field_70159_w *= f;
        this.field_70181_x *= f;
        this.field_70179_y *= f;
      } 
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public boolean func_70617_f_() {
    return false;
  }
  
  protected boolean func_70041_e_() {
    return false;
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    if (amount > 50.0F)
      amount = 50.0F; 
    if (func_180431_b(source))
      return false; 
    if (source.func_76364_f() instanceof net.minecraft.entity.projectile.EntityLargeFireball && source.func_76346_g() instanceof EntityPlayer) {
      super.func_70097_a(source, 1000.0F);
      return true;
    } 
    float f = func_110143_aJ();
    super.func_70097_a(source, amount);
    this.damageTillNextScream = (int)(this.damageTillNextScream + f - func_110143_aJ());
    return true;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(ATTACKING, Boolean.valueOf(false));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(35.0D);
    func_110148_a(SharedMonsterAttributes.field_188790_f).func_111128_a(40.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  protected SoundEvent func_184639_G() {
    return SoundEvents.field_187551_bH;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187555_bJ;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187553_bI;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_GHASTHER;
  }
  
  protected float func_70599_aP() {
    return func_70093_af() ? 0.1F : 10.0F;
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("ExplosionPower", this.explosionStrength);
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    if (tagCompund.func_150297_b("ExplosionPower", 99))
      this.explosionStrength = tagCompund.func_74762_e("ExplosionPower"); 
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (this.field_70131_O * 0.84F) : (this.field_70131_O * 0.66F);
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
  }
  
  public double func_70042_X() {
    return this.field_70131_O * (EngenderConfig.mobs.useMobTalkerModels ? 0.75D : 0.95D);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return false;
  }
  
  protected SoundEvent getCrushHurtSound() {
    return EngenderConfig.mobs.useMobTalkerModels ? super.getCrushHurtSound() : ESound.fleshHitCrushHeavy;
  }
  
  public void launchFireball(EntityGhasther ghast, double d2, double d3, double d4, double d5, double d6, double d7) {
    EntityLargeFireballOther entitylargefireball = new EntityLargeFireballOther(this.field_70170_p, (EntityLivingBase)ghast, d2, d3, d4);
    entitylargefireball.field_92057_e = ghast.getFireballStrength();
    entitylargefireball.field_70165_t = d5;
    entitylargefireball.field_70163_u = d6;
    entitylargefireball.field_70161_v = d7;
    float dm = (float)ghast.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
    entitylargefireball.damage = dm;
    ghast.func_184185_a(SoundEvents.field_187557_bK, 10.0F, 0.875F);
    this.field_70170_p.func_72838_d((Entity)entitylargefireball);
  }
  
  public void chooseNewAttack() {
    if (this.damageTillNextScream > 20.0F) {
      if (func_110143_aJ() <= func_110138_aP() / 3.0F) {
        this.behaviour = EnumBehaviour.RANDOM;
      } else {
        this.behaviour = EnumBehaviour.GHASTCALL;
      } 
    } else {
      switch (this.field_70146_Z.nextInt(15)) {
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
  
  protected void func_70609_aI() {
    this.deathTicks++;
    this.field_70170_p.func_175688_a((this.field_70146_Z.nextFloat() <= 0.2F) ? EnumParticleTypes.EXPLOSION_HUGE : EnumParticleTypes.EXPLOSION_LARGE, this.field_70165_t + (this.field_70146_Z.nextFloat() * 9.0F - 4.5F), this.field_70163_u + (this.field_70146_Z.nextFloat() * 9.0F - 4.5F), this.field_70161_v + (this.field_70146_Z.nextFloat() * 9.0F - 4.5F), 0.0D, 0.0D, 0.0D, new int[0]);
    if (this.deathTicks >= 300) {
      this.field_70125_A--;
      this.field_70725_aQ++;
      this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_HUGE, this.field_70165_t + (this.field_70146_Z.nextFloat() * 9.0F - 4.5F), this.field_70163_u + (this.field_70146_Z.nextFloat() * 9.0F - 4.5F), this.field_70161_v + (this.field_70146_Z.nextFloat() * 9.0F - 4.5F), 0.0D, 0.0D, 0.0D, new int[0]);
    } 
    if (!this.field_70170_p.field_72995_K) {
      if (this.deathTicks == 340) {
        if (!this.field_70170_p.field_72995_K && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
          int i = func_70693_a(this.field_70717_bb);
          i = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.field_70717_bb, i);
          while (i > 0) {
            int j = EntityXPOrb.func_70527_a(i);
            i -= j;
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
            this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v, j));
          } 
        } 
        func_70106_y();
        for (int k = 0; k < 10; k++)
          func_70656_aK(); 
      } 
      if (this.deathTicks % 60 == 0)
        func_184581_c(null); 
      if (this.deathTicks == 1) {
        this.field_70181_x++;
        this.field_70181_x++;
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s " + func_70005_c_() + " has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your Ghasther has been destroyed!", new Object[0]));
        } 
      } 
      if (this.deathTicks > 80)
        func_70091_d(MoverType.SELF, 0.0D, -0.2D, 0.0D); 
      this.field_70761_aq = this.field_70759_as = this.field_70177_z += 5.0F;
    } 
  }
  
  static class AIFireballAttack extends EntityAIBase {
    private EntityGhasther ghast;
    
    public int attackTimer;
    
    public AIFireballAttack(EntityGhasther ghast) {
      this.ghast = ghast;
    }
    
    public boolean func_75250_a() {
      return (this.ghast.func_70638_az() != null && !this.ghast.func_70093_af());
    }
    
    public void func_75249_e() {
      this.attackTimer = 0;
      this.ghast.setArmsRaised(true);
      this.ghast.chooseNewAttack();
    }
    
    public void func_75251_c() {
      this.ghast.setAttacking(false);
      this.ghast.setArmsRaised(false);
    }
    
    public void func_75246_d() {
      double d1 = this.ghast.func_70631_g_() ? 1.5D : 3.0D;
      Vec3d vec3 = this.ghast.func_70676_i(1.0F);
      double mark1 = this.ghast.field_70165_t + vec3.field_72450_a * d1;
      double mark2 = this.ghast.field_70163_u + 1.0D + vec3.field_72448_b * d1;
      double mark3 = this.ghast.field_70161_v + vec3.field_72449_c * d1;
      EntityLivingBase entitylivingbase = this.ghast.func_70638_az();
      double d0 = 100.0D;
      if (entitylivingbase != null && entitylivingbase.func_70068_e((Entity)this.ghast) < d0 * d0) {
        World world = this.ghast.field_70170_p;
        this.attackTimer++;
        if (this.ghast.moralRaisedTimer > 200)
          this.attackTimer++; 
        switch (this.ghast.behaviour) {
          case PEPPER:
            if (this.attackTimer == 10)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.6F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer > 30 && this.attackTimer % 10 == 0) {
              double d2 = entitylivingbase.field_70165_t - mark1;
              double d3 = entitylivingbase.field_70163_u + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
              double d4 = entitylivingbase.field_70161_v - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 80) {
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case TRISHOT:
            if (this.attackTimer == 10)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.6F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer >= 20 && this.attackTimer % 20 == 0) {
              double d2 = entitylivingbase.field_70165_t - mark1;
              double d3 = entitylivingbase.field_70163_u + ((entitylivingbase.field_70131_O > 6.0F) ? 6.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
              double d4 = entitylivingbase.field_70161_v - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 60) {
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case CATCH:
            if (this.attackTimer == 10)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.5F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer == 40)
              for (int i = 0; i <= 6; i++) {
                double d2 = entitylivingbase.field_70165_t + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) - mark1;
                double d3 = entitylivingbase.field_70163_u + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
                double d4 = entitylivingbase.field_70161_v + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) - mark3;
                this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              }  
            if (this.attackTimer == 50)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.5F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer == 80) {
              for (int i = 0; i <= 6; i++) {
                double d2 = entitylivingbase.field_70165_t + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) - mark1;
                double d3 = entitylivingbase.field_70163_u + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
                double d4 = entitylivingbase.field_70161_v + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) - mark3;
                this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              } 
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case SPREAD:
            if (this.attackTimer == 10)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.5F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer == 40) {
              for (int i = 0; i <= 6 + this.ghast.func_70681_au().nextInt(3); i++) {
                double d2 = entitylivingbase.field_70165_t + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) - mark1;
                double d3 = entitylivingbase.field_70163_u + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
                double d4 = entitylivingbase.field_70161_v + ((i == 1) ? 0.0D : (this.ghast.func_70681_au().nextDouble() * 8.0D - 4.0D)) - mark3;
                this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              } 
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
            } 
            break;
          case BOMBARD:
            if (this.attackTimer == 10)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.4F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer > 20 && this.attackTimer % 3 == 0) {
              double d2 = entitylivingbase.field_70165_t + this.ghast.func_70681_au().nextDouble() * 2.0D - 1.0D - mark1;
              double d3 = entitylivingbase.field_70163_u + this.ghast.func_70681_au().nextDouble() * 2.0D - 1.0D + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
              double d4 = entitylivingbase.field_70161_v + this.ghast.func_70681_au().nextDouble() * 2.0D - 1.0D - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 140) {
              this.attackTimer = -60;
              this.ghast.chooseNewAttack();
            } 
            break;
          case MACHINEGUN:
            if (this.attackTimer == 10)
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.4F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer > 20) {
              double d2 = entitylivingbase.field_70165_t - mark1;
              double d3 = entitylivingbase.field_70163_u + ((entitylivingbase.field_70131_O > 6.0F) ? 6.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
              double d4 = entitylivingbase.field_70161_v - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
            } 
            if (this.attackTimer == 80) {
              this.attackTimer = -80;
              this.ghast.chooseNewAttack();
            } 
            break;
          case GHASTCALL:
            if (this.attackTimer == 40) {
              this.ghast.func_184581_c(null);
              this.ghast.func_184581_c(null);
              this.ghast.func_184581_c(null);
              this.ghast.func_184581_c(null);
              this.ghast.func_184581_c(null);
            } 
            if (this.attackTimer >= 80) {
              int i = MathHelper.func_76128_c(this.ghast.field_70165_t);
              int k = MathHelper.func_76128_c(this.ghast.field_70161_v);
              for (int h = (int)this.ghast.func_110143_aJ(); h <= this.ghast.func_110138_aP(); h++) {
                for (int l = 0; l < 10; l++) {
                  int i1 = i + MathHelper.func_76136_a(this.ghast.field_70146_Z, 16, 64) * MathHelper.func_76136_a(this.ghast.field_70146_Z, -1, 1);
                  int k1 = k + MathHelper.func_76136_a(this.ghast.field_70146_Z, 16, 64) * MathHelper.func_76136_a(this.ghast.field_70146_Z, -1, 1);
                  int j1 = this.ghast.field_70170_p.func_175672_r(new BlockPos(i1, MathHelper.func_76128_c(this.ghast.field_70163_u), k1)).func_177956_o();
                  if (this.ghast.field_70170_p.func_180495_p(new BlockPos(i1, j1 - 1, k1)).isSideSolid((IBlockAccess)this.ghast.field_70170_p, new BlockPos(i1, j1 - 1, k1), EnumFacing.UP)) {
                    EntityGhast entityzombie = new EntityGhast(this.ghast.field_70170_p);
                    entityzombie.func_70107_b(i1, j1, k1);
                    if (this.ghast.field_70146_Z.nextInt(100) == 0 && !this.ghast.field_70170_p.func_175636_b(i1, j1, k1, 7.0D) && this.ghast.field_70170_p.func_72917_a(entityzombie.func_174813_aQ(), (Entity)entityzombie) && this.ghast.field_70170_p.func_184144_a((Entity)entityzombie, entityzombie.func_174813_aQ()).isEmpty() && !this.ghast.field_70170_p.func_72953_d(entityzombie.func_174813_aQ())) {
                      this.ghast.field_70170_p.func_72838_d((Entity)entityzombie);
                      entityzombie.func_180482_a(this.ghast.field_70170_p.func_175649_E(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
                      entityzombie.setOwnerId(this.ghast.func_184753_b());
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
              this.ghast.func_184581_c(null); 
            if (this.attackTimer > 20 && this.attackTimer % 10 == 0) {
              double d2 = this.ghast.func_70681_au().nextDouble() * 64.0D - 32.0D;
              double d3 = this.ghast.func_70681_au().nextDouble() * 64.0D - 32.0D;
              double d4 = this.ghast.func_70681_au().nextDouble() * 64.0D - 32.0D;
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
              this.ghast.func_184185_a(SoundEvents.field_187559_bL, 10.0F, 0.6F + this.ghast.func_70681_au().nextFloat() * 0.4F); 
            if (this.attackTimer == 20) {
              double d2 = entitylivingbase.field_70165_t - mark1;
              double d3 = entitylivingbase.field_70163_u + ((entitylivingbase.field_70131_O > 8.0F) ? 7.0D : (entitylivingbase.field_70131_O * 0.5D)) - mark2;
              double d4 = entitylivingbase.field_70161_v - mark3;
              this.ghast.launchFireball(this.ghast, d2, d3, d4, mark1, mark2, mark3);
              this.attackTimer = -40;
              this.ghast.chooseNewAttack();
              if (this.ghast.func_70032_d((Entity)entitylivingbase) <= 10.0D)
                this.ghast.func_70652_k((Entity)entitylivingbase); 
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
      func_75248_a(2);
    }
    
    public boolean func_75250_a() {
      return true;
    }
    
    public void func_75246_d() {
      if (this.parentEntity.func_184179_bs() != null) {
        this.parentEntity.field_70126_B = this.parentEntity.field_70177_z = (this.parentEntity.func_184179_bs()).field_70177_z;
        this.parentEntity.field_70125_A = 0.0F;
        this.parentEntity.func_70101_b(this.parentEntity.field_70177_z, this.parentEntity.field_70125_A);
        this.parentEntity.field_70759_as = this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z;
      } else if (this.parentEntity.func_70638_az() == null) {
        this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z = this.parentEntity.field_70759_as = -((float)Math.atan2(this.parentEntity.field_70159_w, this.parentEntity.field_70179_y)) * 180.0F / 3.1415927F;
      } else {
        EntityLivingBase entitylivingbase = this.parentEntity.func_70638_az();
        this.parentEntity.field_70761_aq = this.parentEntity.field_70177_z = this.parentEntity.field_70759_as;
        this.parentEntity.func_70671_ap().func_75651_a((Entity)entitylivingbase, 180.0F, EngenderConfig.mobs.useMobTalkerModels ? 40.0F : 180.0F);
      } 
    }
  }
  
  static class AIRandomFly extends EntityAIBase {
    private EntityGhasther ghast;
    
    public AIRandomFly(EntityGhasther ghast) {
      this.ghast = ghast;
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      EntityMoveHelper entitymovehelper = this.ghast.func_70605_aq();
      if (!entitymovehelper.func_75640_a())
        return true; 
      double d0 = entitymovehelper.func_179917_d() - this.ghast.field_70165_t;
      double d1 = entitymovehelper.func_179919_e() - this.ghast.field_70163_u;
      double d2 = entitymovehelper.func_179918_f() - this.ghast.field_70161_v;
      double d3 = d0 * d0 + d1 * d1 + d2 * d2;
      return (d3 < 1.0D || d3 > 6400.0D);
    }
    
    public boolean func_75253_b() {
      return false;
    }
    
    public void func_75249_e() {
      Random random = this.ghast.func_70681_au();
      if (this.ghast.getOwner() != null) {
        if (this.ghast.getOwner().func_70093_af()) {
          double d0 = (this.ghast.getOwner()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d1 = (this.ghast.getOwner()).field_70163_u + 8.0D + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          double d2 = (this.ghast.getOwner()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 4.0F);
          this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } else if (!this.ghast.getCurrentBook().func_190926_b()) {
          double d0 = (this.ghast.getOwner()).field_70165_t;
          double d1 = (this.ghast.getOwner()).field_70163_u + 8.0D;
          double d2 = (this.ghast.getOwner()).field_70161_v;
          this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } else {
          double d0 = (this.ghast.getOwner()).field_70165_t + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d1 = (this.ghast.getOwner()).field_70163_u + ((EngenderConfig.mobs.useMobTalkerModels || this.ghast.func_70631_g_()) ? 16.0D : 32.0D) + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          double d2 = (this.ghast.getOwner()).field_70161_v + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
          this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
        } 
      } else {
        double d0 = this.ghast.field_70165_t + random.nextGaussian() * 32.0D;
        double d1 = this.ghast.field_70163_u + random.nextGaussian() * 32.0D;
        double d2 = this.ghast.field_70161_v + random.nextGaussian() * 32.0D;
        this.ghast.func_70605_aq().func_75642_a(d0, d1, d2, 1.0D);
      } 
    }
  }
  
  static class GhastMoveHelper extends EntityMoveHelper {
    private EntityGhasther parentEntity;
    
    private int courseChangeCooldown;
    
    public GhastMoveHelper(EntityGhasther ghast) {
      super((EntityLiving)ghast);
      this.parentEntity = ghast;
    }
    
    public void func_75641_c() {
      if (this.field_188491_h == EntityMoveHelper.Action.MOVE_TO && this.parentEntity.getJukeboxToDanceTo() == null) {
        double d0 = this.field_75646_b - this.parentEntity.field_70165_t;
        double d1 = this.field_75647_c - this.parentEntity.field_70163_u;
        double d2 = this.field_75644_d - this.parentEntity.field_70161_v;
        double d3 = d0 * d0 + d1 * d1 + d2 * d2;
        if (this.courseChangeCooldown-- <= 0) {
          this.courseChangeCooldown += this.parentEntity.func_70681_au().nextInt(5) + 2;
          d3 = MathHelper.func_76133_a(d3);
          if (isNotColliding(this.field_75646_b, this.field_75647_c, this.field_75644_d, d3)) {
            this.parentEntity.field_70159_w += d0 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.15D : 0.075D);
            this.parentEntity.field_70181_x += d1 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.15D : 0.075D);
            this.parentEntity.field_70179_y += d2 / d3 * ((this.parentEntity.moralRaisedTimer > 200) ? 0.15D : 0.075D);
          } else {
            this.field_188491_h = EntityMoveHelper.Action.WAIT;
          } 
        } 
      } 
    }
    
    private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
      double d0 = (x - this.parentEntity.field_70165_t) / p_179926_7_;
      double d1 = (y - this.parentEntity.field_70163_u) / p_179926_7_;
      double d2 = (z - this.parentEntity.field_70161_v) / p_179926_7_;
      AxisAlignedBB axisalignedbb = this.parentEntity.func_174813_aQ();
      for (int i = 1; i < p_179926_7_; i++) {
        axisalignedbb = axisalignedbb.func_72317_d(d0, d1, d2);
        if (!this.parentEntity.field_70170_p.func_184144_a((Entity)this.parentEntity, axisalignedbb).isEmpty())
          return false; 
      } 
      return true;
    }
  }
  
  private enum EnumBehaviour {
    REGULAR, SPREAD, BOMBARD, CATCH, MACHINEGUN, PEPPER, TRISHOT, GHASTCALL, ENRAGED, RANDOM, FINALE;
  }
}
