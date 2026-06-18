package net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity;

import chumbanotz.mutantbeasts.util.MBSoundEvents;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntitySpiderPig extends EntityTameBase implements IJumpingMount {
  private static final DataParameter<Boolean> CLIMBING = EntityDataManager.func_187226_a(EntitySpiderPig.class, DataSerializers.field_187198_h);
  
  private int leapCooldown;
  
  private int leapTick;
  
  private boolean isLeaping;
  
  private float chargePower;
  
  private int chargingTick;
  
  private int chargeExhaustion;
  
  private boolean chargeExhausted;
  
  private final List<WebPos> webList = new ArrayList<>(12);
  
  public EntitySpiderPig(World worldIn) {
    super(worldIn);
    func_70105_a(1.4F, 0.9F);
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.1D, 64.0F, 12.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.1D, true));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
    this.field_70714_bg.func_75776_a(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(40.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(3.0D);
  }
  
  public String getDescName() {
    return "spiderpig";
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  public boolean canBeMatedWith() {
    return false;
  }
  
  public boolean canBeMarried() {
    return false;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(CLIMBING, Boolean.valueOf(false));
  }
  
  public boolean isBesideClimbableBlock() {
    return ((Boolean)this.field_70180_af.func_187225_a(CLIMBING)).booleanValue();
  }
  
  private void setBesideClimbableBlock(boolean climbing) {
    this.field_70180_af.func_187227_b(CLIMBING, Boolean.valueOf(climbing));
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ARTHROPOD;
  }
  
  public float func_70047_e() {
    return this.field_70131_O * 0.75F;
  }
  
  protected PathNavigate func_175447_b(World worldIn) {
    return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
  }
  
  public boolean func_70687_e(PotionEffect potioneffectIn) {
    return (potioneffectIn.func_188419_a() != MobEffects.field_76436_u && super.func_70687_e(potioneffectIn));
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && func_184187_bx() == null) {
      if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K)
        player.func_184220_m((Entity)this); 
      return true;
    } 
    return super.interact(player, hand);
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    setBesideClimbableBlock(this.field_70123_F);
    if (this.chargeExhaustion >= 120)
      this.chargeExhausted = true; 
    if (this.chargeExhaustion <= 0)
      this.chargeExhausted = false; 
    this.chargeExhaustion = Math.max(0, this.chargeExhaustion - 1);
    if (!this.field_70170_p.field_72995_K) {
      this.field_70715_bh.func_188527_a(1, !func_70631_g_());
      this.leapCooldown = Math.max(0, this.leapCooldown - 1);
      if (this.leapTick > 10 && this.field_70122_E)
        this.isLeaping = false; 
      updateWebList(false);
      updateChargeState();
      if (this.field_70173_aa % 600 == 0)
        func_70691_i(1.0F); 
    } 
  }
  
  private void updateWebList(boolean onlyCheckSize) {
    if (!onlyCheckSize) {
      for (int i = 0; i < this.webList.size(); i++) {
        WebPos coord = this.webList.get(i);
        if (this.field_70170_p.func_180495_p(coord) != Blocks.field_150321_G.func_176223_P()) {
          this.webList.remove(i);
          i--;
        } else {
          --coord.timeLeft;
        } 
      } 
      if (!this.webList.isEmpty()) {
        WebPos first = this.webList.get(0);
        if (first.timeLeft < 0) {
          this.webList.remove(0);
          removeWeb(first);
        } 
      } 
    } 
    while (this.webList.size() > 12) {
      WebPos first = this.webList.remove(0);
      removeWeb(first);
    } 
  }
  
  private void removeWeb(BlockPos pos) {
    if (this.field_70170_p.func_180495_p(pos) == Blocks.field_150321_G.func_176223_P()) {
      this.field_70170_p.func_175718_b(2001, pos, Block.func_176210_f(Blocks.field_150321_G.func_176223_P()));
      this.field_70170_p.func_175656_a(pos, Blocks.field_150350_a.func_176223_P());
    } 
  }
  
  private void updateChargeState() {
    if (this.chargingTick > 0)
      for (EntityLivingBase entityLivingBase : this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ(), EntitySelectors.func_191324_b((Entity)this))) {
        if (entityLivingBase != this && entityLivingBase != getOwner())
          func_70652_k((Entity)entityLivingBase); 
      }  
    this.chargingTick = Math.max(0, this.chargingTick - 1);
  }
  
  public boolean func_70652_k(Entity entityIn) {
    this.isLeaping = false;
    boolean spiderType = (entityIn instanceof EntitySpider || entityIn instanceof EntitySpiderPig);
    float damage = (float)func_110148_a(SharedMonsterAttributes.field_111264_e).func_111126_e();
    if (entityIn.field_70170_p.func_180495_p(entityIn.func_180425_c()).func_185904_a() == Material.field_151569_G && !spiderType)
      damage += 4.0F; 
    boolean flag = entityIn.func_70097_a(DamageSource.func_76358_a((EntityLivingBase)this), damage);
    if ((!func_184207_aI() || flag) && this.field_70146_Z.nextInt(2) == 0 && ForgeEventFactory.getMobGriefingEvent(this.field_70170_p, (Entity)this)) {
      double dx = entityIn.field_70165_t - entityIn.field_70169_q;
      double dz = entityIn.field_70161_v - entityIn.field_70166_s;
      BlockPos pos = new BlockPos((int)(entityIn.field_70165_t + dx * 0.5D), MathHelper.func_76128_c((func_174813_aQ()).field_72338_b), (int)(entityIn.field_70161_v + dz * 0.5D));
      Material material = this.field_70170_p.func_180495_p(pos).func_185904_a();
      if (!material.func_76220_a() && !material.func_76224_d() && material != Material.field_151569_G && !spiderType) {
        this.field_70170_p.func_175656_a(pos, Blocks.field_150321_G.func_176223_P());
        this.webList.add(new WebPos(pos, func_184207_aI() ? 600 : 1200));
        updateWebList(true);
        this.field_70181_x = Math.max(0.25D, this.field_70181_x);
        this.field_70143_R = 0.0F;
      } 
    } 
    return flag;
  }
  
  public boolean func_184776_b() {
    return !this.chargeExhausted;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_110206_u(int jumpPowerIn) {
    this.chargeExhaustion += 50 * jumpPowerIn / 100;
    this.chargePower = 1.0F * jumpPowerIn / 100.0F;
  }
  
  public void func_184775_b(int jumpPowerIn) {
    this.chargingTick = 8 * jumpPowerIn / 100;
  }
  
  public void func_184777_r_() {}
  
  protected boolean func_70610_aX() {
    return (super.func_70610_aX() || func_184207_aI());
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI() && func_82171_bF()) {
      EntityLivingBase livingentity = (EntityLivingBase)func_184179_bs();
      this.field_70138_W = 1.0F;
      this.field_70126_B = this.field_70177_z = this.field_70759_as = livingentity.field_70177_z;
      this.field_70127_C = this.field_70125_A = livingentity.field_70125_A * 0.4F;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      while (this.field_70761_aq > this.field_70759_as + 180.0F)
        this.field_70761_aq -= 360.0F; 
      while (this.field_70761_aq < this.field_70759_as - 180.0F)
        this.field_70761_aq += 360.0F; 
      if (!this.chargeExhausted && this.chargePower > 0.0F && (this.field_70122_E || this.field_70170_p.func_72953_d(func_174813_aQ()))) {
        float pitch = this.field_70125_A;
        this.field_70125_A = 0.0F;
        this.field_70125_A = pitch;
        double power = 1.600000023841858D * this.chargePower;
        this.field_70159_w = (func_70040_Z()).field_72450_a * power;
        this.field_70181_x = 0.30000001192092896D;
        this.field_70179_y = (func_70040_Z()).field_72449_c * power;
        this.chargePower = 0.0F;
      } 
      this.field_70747_aH = func_70689_ay() * 0.1F;
      if (func_184186_bw()) {
        strafe = livingentity.field_70702_br * 0.8F;
        forward = livingentity.field_191988_bg * 0.6F;
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (livingentity instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } else {
        this.field_184618_aE = this.field_70721_aZ;
        double d1 = this.field_70165_t - this.field_70169_q;
        double d0 = this.field_70161_v - this.field_70166_s;
        float f2 = MathHelper.func_76133_a(d1 * d1 + d0 * d0) * 4.0F;
        if (f2 > 1.0F)
          f2 = 1.0F; 
        this.field_70721_aZ += (f2 - this.field_70721_aZ) * 0.4F;
        this.field_184619_aG += this.field_70721_aZ;
      } 
    } else {
      this.field_70138_W = 0.6F;
      this.field_70747_aH = 0.02F;
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public void func_70074_a(EntityLivingBase entityLivingIn) {
    if (!this.field_70170_p.field_72995_K && 
      isPigOrSpider(entityLivingIn)) {
      EntitySpiderPig spiderPigEntity = new EntitySpiderPig(this.field_70170_p);
      entityLivingIn.func_70106_y();
      this.field_70170_p.func_72838_d((Entity)spiderPigEntity);
    } 
    super.func_70074_a(entityLivingIn);
  }
  
  public boolean func_70617_f_() {
    return isBesideClimbableBlock();
  }
  
  public void func_70110_aj() {}
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntitySpiderPig(this.field_70170_p);
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    if (!this.webList.isEmpty()) {
      NBTTagList nbtTagList = new NBTTagList();
      for (WebPos coord : this.webList) {
        NBTTagCompound compound1 = NBTUtil.func_186859_a(coord);
        compound1.func_74768_a("TimeLeft", coord.timeLeft);
        nbtTagList.func_74742_a((NBTBase)compound1);
      } 
      compound.func_74782_a("Webs", (NBTBase)nbtTagList);
    } 
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    NBTTagList listnbt = compound.func_150295_c("Webs", 10);
    for (int i = 0; i < listnbt.func_74745_c(); i++) {
      NBTTagCompound compound1 = listnbt.func_150305_b(i);
      this.webList.add(i, new WebPos(NBTUtil.func_186861_c(compound1), compound1.func_74762_e("TimeLeft")));
    } 
  }
  
  protected SoundEvent func_184639_G() {
    return MBSoundEvents.ENTITY_SPIDER_PIG_AMBIENT;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return MBSoundEvents.ENTITY_SPIDER_PIG_HURT;
  }
  
  protected SoundEvent func_184615_bR() {
    return MBSoundEvents.ENTITY_SPIDER_PIG_DEATH;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187709_dP, 0.15F, 1.0F);
    func_184185_a(SoundEvents.field_187823_fN, 0.15F, 1.0F);
  }
  
  public static boolean isPigOrSpider(EntityLivingBase livingEntity) {
    return (livingEntity.getClass() == EntityPig.class || livingEntity.getClass() == EntitySpider.class);
  }
  
  protected ResourceLocation func_184647_J() {
    return new ResourceLocation("mutantbeasts", "entities/spider_pig");
  }
  
  class LeapAttackGoal extends EntityAIBase {
    public boolean func_75250_a() {
      EntityLivingBase target = EntitySpiderPig.this.func_70638_az();
      return (target != null && EntitySpiderPig.this.leapCooldown <= 0 && (EntitySpiderPig.this.field_70122_E || EntitySpiderPig.this.func_70090_H()) && ((EntitySpiderPig.this.func_70068_e((Entity)target) < 64.0D && EntitySpiderPig.this.field_70146_Z.nextInt(8) == 0) || EntitySpiderPig.this.func_70068_e((Entity)target) < 6.25D));
    }
    
    public void func_75249_e() {
      EntitySpiderPig.this.isLeaping = true;
      EntitySpiderPig.this.leapCooldown = 15;
      EntityLivingBase target = EntitySpiderPig.this.func_70638_az();
      double x = target.field_70165_t - EntitySpiderPig.this.field_70165_t;
      double y = target.field_70163_u - EntitySpiderPig.this.field_70163_u;
      double z = target.field_70161_v - EntitySpiderPig.this.field_70161_v;
      double d = MathHelper.func_76133_a(x * x + y * y + z * z);
      double scale = (2.0F + 0.2F * EntitySpiderPig.this.field_70146_Z.nextFloat() * EntitySpiderPig.this.field_70146_Z.nextFloat());
      EntitySpiderPig.this.field_70159_w = x / d * scale;
      EntitySpiderPig.this.field_70181_x = y / d * scale * 0.5D + 0.3D;
      EntitySpiderPig.this.field_70179_y = z / d * scale;
    }
    
    public boolean func_75253_b() {
      return (EntitySpiderPig.this.isLeaping && EntitySpiderPig.this.leapTick < 40);
    }
    
    public void func_75246_d() {
      ++EntitySpiderPig.this.leapTick;
    }
    
    public void func_75251_c() {
      EntitySpiderPig.this.leapTick = 0;
    }
  }
  
  static class WebPos extends BlockPos {
    private int timeLeft;
    
    public WebPos(BlockPos pos, int timeLeft) {
      super((Vec3i)pos);
      this.timeLeft = timeLeft;
    }
  }
}
