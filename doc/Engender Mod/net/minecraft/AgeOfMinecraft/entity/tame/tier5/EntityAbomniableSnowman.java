package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.Elemental;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Massive;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESetup;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityAbomniableSnowman extends EntityTameBase implements IRangedAttackMob, Armored, Massive, Elemental {
  private AISpecialRangedAttack aiArrowAttack = new AISpecialRangedAttack(this, 1.0D, 40, 32.0F);
  
  private EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee(this, 1.0D, true);
  
  public EntityAbomniableSnowman(World worldIn) {
    super(worldIn);
    func_70105_a(2.5F, 5.36F);
    this.field_70728_aV = 50;
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.67D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "abomniable_snowman";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.LIGHT_PURPLE;
  }
  
  public int playMusic() {
    return 2;
  }
  
  public int getNextLevelRequirement() {
    return 1500;
  }
  
  public void updateBossBar() {
    super.updateBossBar();
    this.bossInfo.func_186745_a(BossInfo.Color.WHITE);
  }
  
  public boolean isABoss() {
    return true;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityAbomniableSnowman(this.field_70170_p);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.CONSTRUCT;
  }
  
  public float getBonusVSLight() {
    return 3.0F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(200.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(10.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(48.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(25.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K) {
      player.func_184220_m((Entity)this);
      return true;
    } 
    return false;
  }
  
  protected boolean func_184219_q(Entity passenger) {
    return (func_184188_bt().size() < 2);
  }
  
  public void func_184232_k(Entity passenger) {
    if (func_184196_w(passenger)) {
      int i = func_184188_bt().indexOf(passenger);
      float f3 = this.field_70761_aq * 3.1415927F / 180.0F;
      float f11 = MathHelper.func_76126_a(f3);
      float f4 = MathHelper.func_76134_b(f3);
      float sho = this.field_184619_aG - this.field_70721_aZ * (1.0F - this.field_70173_aa * 0.001F) + 6.0F;
      float sho1 = (Math.abs(sho % 13.0F - 6.5F) - 3.25F) / 3.25F;
      if (i == 1)
        passenger.func_70107_b(this.field_70165_t - f4 * (1.2999999523162842D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.4D) : 0.0D)), this.field_70163_u + 3.5D, this.field_70161_v - f11 * (1.2999999523162842D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.4D) : 0.0D))); 
      if (i == 0)
        passenger.func_70107_b(this.field_70165_t + f4 * (1.2999999523162842D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.4D) : 0.0D)), this.field_70163_u + 3.5D, this.field_70161_v + f11 * (1.2999999523162842D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.4D) : 0.0D))); 
    } 
  }
  
  public void func_191986_a(float strafe, float vertical, float forward) {
    if (func_184207_aI()) {
      EntityLivingBase entitylivingbase = (EntityLivingBase)func_184179_bs();
      this.field_70126_B = this.field_70177_z = entitylivingbase.field_70177_z;
      this.field_70125_A = entitylivingbase.field_70125_A;
      func_70101_b(this.field_70177_z, this.field_70125_A);
      this.field_70759_as = this.field_70761_aq = this.field_70177_z;
      strafe = entitylivingbase.field_70702_br / 3.0F;
      forward = entitylivingbase.field_191988_bg / 3.0F;
      if (func_184186_bw()) {
        func_70659_e((float)func_110148_a(SharedMonsterAttributes.field_111263_d).func_111126_e());
        super.func_191986_a(strafe, vertical, forward);
      } else if (entitylivingbase instanceof EntityPlayer) {
        this.field_70159_w = 0.0D;
        this.field_70181_x = 0.0D;
        this.field_70179_y = 0.0D;
      } 
      List<Entity> list = this.field_70170_p.func_72839_b((Entity)this, func_174813_aQ().func_186662_g(1.0D));
      for (int i = 0; i < list.size(); i++) {
        Entity entity = list.get(i);
        if (entity instanceof EntityLivingBase && !func_184191_r(entity) && !this.field_70170_p.field_72995_K && this.field_70173_aa % 10 == 0)
          func_70652_k(entity); 
      } 
      this.field_184618_aE = this.field_70721_aZ;
      double d5 = this.field_70165_t - this.field_70169_q;
      double d7 = this.field_70161_v - this.field_70166_s;
      float f10 = MathHelper.func_76133_a(d5 * d5 + d7 * d7) * 4.0F;
      if (f10 > 1.0F)
        f10 = 1.0F; 
      this.field_70721_aZ += (f10 - this.field_70721_aZ) * 0.4F;
      this.field_184619_aG += this.field_70721_aZ;
    } else {
      super.func_191986_a(strafe, vertical, forward);
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70638_az() != null)
      if (func_70068_e((Entity)func_70638_az()) > 100.0D || func_70638_az() instanceof net.minecraft.entity.EntityFlying || (func_70638_az()).field_70163_u > this.field_70163_u + 4.0D) {
        this.field_70714_bg.func_75776_a(2, this.aiArrowAttack);
        this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      } else {
        this.field_70714_bg.func_75776_a(2, (EntityAIBase)this.aiAttackOnCollide);
        this.field_70714_bg.func_85156_a(this.aiArrowAttack);
      }  
    if (this.field_70159_w * this.field_70159_w + this.field_70179_y * this.field_70179_y != 0.0D && this.field_70146_Z.nextInt(5) == 0) {
      int i = MathHelper.func_76128_c(this.field_70165_t);
      int j = MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224D);
      int k = MathHelper.func_76128_c(this.field_70161_v);
      IBlockState iblockstate = this.field_70170_p.func_180495_p(new BlockPos(i, j, k));
      if (iblockstate.func_185904_a() != Material.field_151579_a)
        this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_CRACK, this.field_70165_t + (this.field_70146_Z.nextFloat() - 0.5D) * this.field_70130_N, (func_174813_aQ()).field_72338_b + 0.1D, this.field_70161_v + (this.field_70146_Z.nextFloat() - 0.5D) * this.field_70130_N, 4.0D * (this.field_70146_Z.nextFloat() - 0.5D), 0.5D, (this.field_70146_Z.nextFloat() - 0.5D) * 4.0D, new int[] { Block.func_176210_f(iblockstate) }); 
    } 
  }
  
  public boolean func_70652_k(Entity entityIn) {
    this.attackTimer = 10;
    this.field_70170_p.func_72960_a((Entity)this, (byte)4);
    func_184185_a(SoundEvents.field_187596_cD, 1.0F, 1.0F);
    AttributeModifier irongolemrandomizer = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Iron Golem randomizer", this.field_70146_Z.nextDouble() * 2.5D, 1)).func_111168_a(false);
    IAttributeInstance iattributeinstanceattack = func_110148_a(SharedMonsterAttributes.field_111264_e);
    if (!iattributeinstanceattack.func_180374_a(irongolemrandomizer))
      iattributeinstanceattack.func_111121_a(irongolemrandomizer); 
    if (super.func_70652_k(entityIn)) {
      if (iattributeinstanceattack.func_180374_a(irongolemrandomizer))
        iattributeinstanceattack.func_111124_b(irongolemrandomizer); 
      return true;
    } 
    return false;
  }
  
  public void attackWithAdditionalEffects(Entity entity) {
    double amount = 1.0D;
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.EASY)
      amount *= 0.75D; 
    if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
      amount *= 1.5D; 
    if (!entity.func_70089_S() && entity instanceof EntityLivingBase) {
      ((EntityLivingBase)entity).field_70760_ar = ((EntityLivingBase)entity).field_70761_aq = ((EntityLivingBase)entity).field_70126_B = ((EntityLivingBase)entity).field_70177_z = ((EntityLivingBase)entity).field_70758_at = ((EntityLivingBase)entity).field_70759_as = this.field_70759_as;
      float xRatio = MathHelper.func_76126_a(this.field_70759_as * 0.017453292F);
      float zRatio = -MathHelper.func_76134_b(this.field_70759_as * 0.017453292F);
      entity.field_70160_al = true;
      float f = MathHelper.func_76129_c(xRatio * xRatio + zRatio * zRatio);
      entity.field_70159_w /= 3.0D;
      entity.field_70179_y /= 3.0D;
      entity.field_70159_w -= xRatio / f * 2.0D;
      entity.field_70179_y -= zRatio / f * 2.0D;
      entity.field_70181_x /= 2.0D;
      entity.field_70181_x += amount;
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity(entity)); 
    } 
    entity.field_70181_x += amount;
  }
  
  @SideOnly(Side.CLIENT)
  public void func_70103_a(byte id) {
    if (id == 4) {
      this.attackTimer = 10;
      func_184185_a(SoundEvents.field_187596_cD, 1.0F, 1.0F);
    } else {
      super.func_70103_a(id);
    } 
  }
  
  public boolean takesFallDamage() {
    return false;
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    if (getSpecialAttackTimer() <= 0 && isHero()) {
      setSpecialAttackTimer(300);
      func_184185_a(ESound.golemSmash, 10.0F, 1.0F);
      createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u - 2.0D, this.field_70161_v, 3.0F, false);
      List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_72314_b(24.0D, 3.0D, 24.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      if (list != null && !list.isEmpty())
        for (int i1 = 0; i1 < list.size(); i1++) {
          EntityLivingBase entity = list.get(i1);
          if (entity != null) {
            if (!func_184191_r((Entity)entity)) {
              entity.field_70181_x += 1.5D;
              if (entity instanceof net.minecraft.entity.monster.IMob) {
                entity.func_70097_a(DamageSource.func_188405_b((EntityLivingBase)this), 24.0F);
              } else {
                entity.func_70097_a(DamageSource.func_188405_b((EntityLivingBase)this), 12.0F);
              } 
              entity.field_70160_al = true;
              float f = MathHelper.func_76129_c(MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) * MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) + -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) * -MathHelper.func_76134_b(this.field_70177_z * 0.017453292F));
              entity.field_70159_w /= 2.0D;
              entity.field_70179_y /= 2.0D;
              entity.field_70159_w -= (MathHelper.func_76126_a(this.field_70177_z * 0.017453292F) / f) * 1.0D;
              entity.field_70179_y -= (-MathHelper.func_76134_b(this.field_70177_z * 0.017453292F) / f) * 1.0D;
            } 
            if (EngenderConfig.general.useMessage && !entity.func_70089_S() && !isWild())
              getOwner().func_145747_a((ITextComponent)new TextComponentTranslation(entity.func_70005_c_() + " was blown up by " + func_70005_c_() + " (" + getOwner().func_70005_c_() + ")", new Object[0])); 
          } 
        }  
    } 
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    return SoundEvents.field_187602_cF;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_187599_cE;
  }
  
  protected void func_180429_a(BlockPos pos, Block blockIn) {
    func_184185_a(SoundEvents.field_187605_cG, 1.0F, (func_70631_g_() ? 1.5F : 1.0F) / getFittness());
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_IRON_GOLEM;
  }
  
  protected void func_70609_aI() {
    this.field_70737_aN = 10;
    this.deathTicks++;
    if (this.deathTicks == 100) {
      if (!this.field_70170_p.field_72995_K && func_146066_aG() && this.field_70170_p.func_82736_K().func_82766_b("doMobLoot")) {
        int j = func_70693_a(this.field_70717_bb);
        j = ForgeEventFactory.getExperienceDrop((EntityLivingBase)this, this.field_70717_bb, j);
        while (j > 0) {
          int m = EntityXPOrb.func_70527_a(j);
          j -= m;
          this.field_70170_p.func_72838_d((Entity)new EntityXPOrb(this.field_70170_p, this.field_70165_t, this.field_70163_u + 8.0D, this.field_70161_v, m));
        } 
      } 
      for (int i = 0; i < 20; i++) {
        double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
        double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
        this.field_70170_p.func_175688_a(EnumParticleTypes.EXPLOSION_NORMAL, this.field_70165_t + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + (this.field_70146_Z.nextFloat() * this.field_70130_N * 2.0F) - this.field_70130_N, d2, d0, d1, new int[0]);
      } 
      func_70106_y();
    } 
    func_184185_a(SoundEvents.field_187807_fF, 1.0F, 1.0F);
    for (int k = 0; k < this.deathTicks; k++) {
      double d2 = this.field_70146_Z.nextGaussian() * 0.02D;
      double d0 = this.field_70146_Z.nextGaussian() * 0.02D;
      double d1 = this.field_70146_Z.nextGaussian() * 0.02D;
      this.field_70170_p.func_175688_a(EnumParticleTypes.BLOCK_DUST, this.field_70165_t + this.field_70146_Z.nextGaussian() * this.field_70130_N * 0.5D, this.field_70163_u + (this.field_70146_Z.nextFloat() * this.field_70131_O), this.field_70161_v + this.field_70146_Z.nextGaussian() * this.field_70130_N * 0.5D, d2, d0, d1, new int[] { Block.func_176210_f(Blocks.field_150433_aE.func_176223_P()) });
    } 
    if (!this.field_70170_p.field_72995_K)
      if (this.deathTicks == 1) {
        func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i());
        if (getOwner() != null) {
          for (EntityPlayer entityplayer : this.field_70170_p.field_73010_i) {
            this.field_70170_p.func_184133_a(null, entityplayer.func_180425_c(), func_184615_bR(), func_184176_by(), func_70599_aP(), 1.0F);
            entityplayer.func_146105_b((ITextComponent)new TextComponentTranslation("\u00A74" + getOwner().func_70005_c_() + "'s " + func_70005_c_() + " has been killed!!!", new Object[0]), true);
          } 
          ((EntityPlayerMP)getOwner()).func_145747_a((ITextComponent)new TextComponentTranslation("Your " + func_70005_c_() + " has been destroyed!", new Object[0]));
        } 
      }  
  }
  
  public void throwSnowball(double x, double y, double z) {
    EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.field_70170_p, (EntityLivingBase)this);
    float f = MathHelper.func_76133_a(x * x + z * z) * 0.25F;
    entitysnowball.func_70186_c(x, y + f, z, 1.6F, 1.0F);
    this.attackTimer = 10;
    this.field_70170_p.func_72960_a((Entity)this, (byte)4);
    func_184185_a(SoundEvents.field_187596_cD, 1.0F, 1.0F);
    func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
    this.field_70170_p.func_72838_d((Entity)entitysnowball);
    entitysnowball.damage = 8.0F;
  }
  
  public void whirlSnowball(EntityLivingBase target) {
    EntitySnowballHarmful entitysnowball = new EntitySnowballHarmful(this.field_70170_p, (EntityLivingBase)this);
    float f2 = this.field_70761_aq * 0.017453292F;
    float f19 = MathHelper.func_76126_a(f2);
    float f3 = MathHelper.func_76134_b(f2);
    double d1 = target.field_70165_t + this.field_70146_Z.nextDouble() * 2.0D - 1.0D - this.field_70165_t + (f19 * 1.5F);
    double d2 = target.field_70163_u - 2.0D - this.field_70146_Z.nextDouble() * 4.0D + target.field_70131_O - this.field_70163_u + func_70047_e();
    double d3 = target.field_70161_v + this.field_70146_Z.nextDouble() * 2.0D - 1.0D - this.field_70161_v - (f3 * 1.5F);
    float f = MathHelper.func_76133_a(d1 * d1 + d3 * d3) * 0.25F;
    entitysnowball.func_70186_c(d1, d2 + f, d3, 1.6F, 1.0F);
    this.attackTimer = 10;
    this.field_70170_p.func_72960_a((Entity)this, (byte)4);
    func_184185_a(SoundEvents.field_187596_cD, 1.0F, 1.0F);
    func_184185_a(SoundEvents.field_187805_fE, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
    this.field_70170_p.func_72838_d((Entity)entitysnowball);
    entitysnowball.damage = 4.0F;
  }
  
  public void func_82196_d(EntityLivingBase target, float p_82196_2_) {
    float f2 = this.field_70761_aq * 0.017453292F;
    float f19 = MathHelper.func_76126_a(f2);
    float f3 = MathHelper.func_76134_b(f2);
    double d1 = target.field_70165_t - this.field_70165_t + (f19 * 1.5F);
    double d2 = target.field_70163_u - 3.0D + target.field_70131_O - this.field_70163_u + func_70047_e();
    double d3 = target.field_70161_v - this.field_70161_v - (f3 * 1.5F);
    throwSnowball(d1, d2, d3);
  }
  
  public class AISpecialRangedAttack extends EntityAIBase {
    private final EntityAbomniableSnowman entityHost;
    
    private final IRangedAttackMob rangedAttackEntityHost;
    
    private EntityLivingBase attackTarget;
    
    public AISpecialRangedAttack(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn) {
      this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }
    
    private int rangedAttackTime = -1;
    
    private final double entityMoveSpeed;
    
    private int seeTime;
    
    private final int attackIntervalMin;
    
    private final int maxRangedAttackTime;
    
    private final float attackRadius;
    
    private final float maxAttackDistance;
    
    public AISpecialRangedAttack(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn) {
      if (!(attacker instanceof EntityLivingBase))
        throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob"); 
      this.rangedAttackEntityHost = attacker;
      this.entityHost = (EntityAbomniableSnowman)attacker;
      this.entityMoveSpeed = movespeed;
      this.attackIntervalMin = p_i1650_4_;
      this.maxRangedAttackTime = maxAttackTime;
      this.attackRadius = maxAttackDistanceIn;
      this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.entityHost.func_70638_az();
      if (entitylivingbase == null)
        return false; 
      this.attackTarget = entitylivingbase;
      return true;
    }
    
    public boolean func_75253_b() {
      return (func_75250_a() || !this.entityHost.func_70661_as().func_75500_f());
    }
    
    public void func_75251_c() {
      this.attackTarget = null;
      this.seeTime = 0;
      this.rangedAttackTime = -1;
    }
    
    public void func_75246_d() {
      double d0 = this.entityHost.func_70092_e(this.attackTarget.field_70165_t, (this.attackTarget.func_174813_aQ()).field_72338_b, this.attackTarget.field_70161_v);
      boolean flag = this.entityHost.func_70635_at().func_75522_a((Entity)this.attackTarget);
      if (flag) {
        this.seeTime++;
      } else {
        this.seeTime = 0;
      } 
      if ((d0 <= this.maxAttackDistance && this.seeTime >= 20) || !this.entityHost.field_70122_E || this.entityMoveSpeed == 0.0D) {
        this.entityHost.func_70661_as().func_75499_g();
      } else {
        this.entityHost.func_70661_as().func_75497_a((Entity)this.attackTarget, this.entityMoveSpeed);
      } 
      this.entityHost.func_70671_ap().func_75651_a((Entity)this.attackTarget, 30.0F, 30.0F);
      this.rangedAttackTime--;
      if (this.rangedAttackTime <= 0) {
        if (!flag)
          return; 
        if (this.entityHost.func_110143_aJ() <= this.entityHost.func_110138_aP() / 2.0F) {
          if (this.rangedAttackTime % 2 == 0 && this.rangedAttackTime <= -20 && this.rangedAttackTime >= -100)
            this.entityHost.whirlSnowball(this.attackTarget); 
          if (this.rangedAttackTime <= -120)
            this.rangedAttackTime = 20; 
        } else {
          if (this.rangedAttackTime % 20 == 0) {
            float f = MathHelper.func_76133_a(d0) / this.attackRadius;
            float lvt_5_1_ = MathHelper.func_76131_a(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.func_82196_d(this.attackTarget, lvt_5_1_);
          } 
          if (this.rangedAttackTime <= -80)
            this.rangedAttackTime = 40; 
        } 
      } 
    }
  }
}
