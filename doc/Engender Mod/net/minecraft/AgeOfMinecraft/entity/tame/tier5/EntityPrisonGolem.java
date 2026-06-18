package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.Armored;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
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
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
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
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPrisonGolem extends EntityTameBase implements Armored {
  private static final DataParameter<Boolean> ALTERNATE_SKIN = EntityDataManager.func_187226_a(EntityPrisonGolem.class, DataSerializers.field_187198_h);
  
  public EntityPrisonGolem(World worldIn) {
    super(worldIn);
    func_70105_a(1.25F, 2.68F);
    this.isOffensive = true;
    this.field_70728_aV = 20;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 48.0F, 8.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.0D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.55D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
  }
  
  public String getDescName() {
    return "prison_golem";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityPrisonGolem(this.field_70170_p);
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return ESetup.CONSTRUCT;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(ALTERNATE_SKIN, Boolean.valueOf(false));
  }
  
  public boolean isAlternateSkin() {
    return ((Boolean)func_184212_Q().func_187225_a(ALTERNATE_SKIN)).booleanValue();
  }
  
  public void setAlternateSkin(boolean childZombie) {
    func_184212_Q().func_187227_b(ALTERNATE_SKIN, Boolean.valueOf(childZombie));
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setAlternateSkin(tagCompund.func_74767_n("AltSkin"));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    if (((Boolean)this.field_70180_af.func_187225_a(ALTERNATE_SKIN)).booleanValue())
      tagCompound.func_74757_a("AltSkin", true); 
  }
  
  public float getBonusVSLight() {
    return 5.0F;
  }
  
  public float getBonusVSArmored() {
    return 0.75F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public int func_70641_bl() {
    return 1;
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(100.0D);
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.3D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
    func_110148_a(SharedMonsterAttributes.field_111265_b).func_111128_a(48.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(15.0D);
  }
  
  public double getKnockbackResistance() {
    return 1.0D;
  }
  
  public void performSpecialAttack() {
    this.field_70181_x = 1.0D;
    func_184185_a(ESound.golemSpecial, 10.0F, 1.0F);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (stack.func_190926_b() && !isWild() && func_184191_r((Entity)player) && !func_70631_g_() && !player.func_70093_af() && !this.field_70170_p.field_72995_K) {
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
      if (EngenderConfig.mobs.useMobTalkerModels) {
        if (i == 1)
          passenger.func_70107_b(this.field_70165_t - f4 * (0.5D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.field_70163_u + 1.1D, this.field_70161_v - f11 * (0.5D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
        if (i == 0)
          passenger.func_70107_b(this.field_70165_t + f4 * (0.5D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.field_70163_u + 1.1D, this.field_70161_v + f11 * (0.5D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
      } else {
        if (i == 1)
          passenger.func_70107_b(this.field_70165_t - f4 * (0.6499999761581421D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.field_70163_u + 1.375D, this.field_70161_v - f11 * (0.6499999761581421D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
        if (i == 0)
          passenger.func_70107_b(this.field_70165_t + f4 * (0.6499999761581421D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D)), this.field_70163_u + 1.375D, this.field_70161_v + f11 * (0.6499999761581421D + ((this.field_70721_aZ >= 0.01D) ? (sho1 * 0.2D) : 0.0D))); 
      } 
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
    if (func_70638_az() != null && func_70068_e((Entity)func_70638_az()) < 64.0D && getSpecialAttackTimer() <= 0 && this.field_70122_E && isHero())
      performSpecialAttack(); 
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
    AttributeModifier irongolemrandomizer = (new AttributeModifier(UUID.fromString("B9766B59-8566-4402-BC1F-3EE2A276D839"), "Iron Golem randomizer", this.field_70146_Z.nextDouble() * 1.5D, 1)).func_111168_a(false);
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
    entity.field_70159_w = 0.0D;
    entity.field_70181_x = 0.0D;
    entity.field_70179_y = 0.0D;
    double amount = 0.6D;
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
      entity.field_70159_w /= 2.0D;
      entity.field_70179_y /= 2.0D;
      entity.field_70159_w -= xRatio / f * 2.0D;
      entity.field_70179_y -= zRatio / f * 2.0D;
      entity.field_70181_x /= 2.0D;
      entity.field_70181_x += amount;
      if (entity instanceof EntityPlayerMP)
        ((EntityPlayerMP)entity).field_71135_a.func_147359_a((Packet)new SPacketEntityVelocity(entity)); 
    } 
    entity.field_70181_x += amount;
    if (entity instanceof EntityLivingBase) {
      inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)entity, MobEffects.field_76421_d, 5, 1);
      if (entity instanceof EntityLiving && ((EntityLiving)entity).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
        ((EntityLiving)entity).func_70624_b(null); 
    } 
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
    return ELoot.ENTITIES_PRISON_GOLEM;
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
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
    if (!this.field_70170_p.field_72995_K && this.field_70170_p.field_73012_v.nextBoolean())
      setAlternateSkin(true); 
    return super.func_180482_a(difficulty, livingdata);
  }
}
