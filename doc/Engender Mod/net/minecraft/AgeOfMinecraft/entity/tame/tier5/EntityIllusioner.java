package net.minecraft.AgeOfMinecraft.entity.tame.tier5;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityPEGunPellet;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.items.ItemPEGun;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityAbstractIllagers;
import net.minecraft.AgeOfMinecraft.entity.tame.EntitySpellcasterIllager;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAttackRangedBowAlly;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntityTippedArrowOther;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityIllusioner extends EntitySpellcasterIllager implements IRangedAttackMob {
  private static final DataParameter<Integer> DISGUISE_ID = EntityDataManager.func_187226_a(EntityIllusioner.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> DISGUSE_TIMER = EntityDataManager.func_187226_a(EntityIllusioner.class, DataSerializers.field_187192_b);
  
  private final EntityAIAttackRangedBowAlly<EntityIllusioner> aiArrowAttack = new EntityAIAttackRangedBowAlly((EntityTameBase)this, 0.5D, 20, 15.0F);
  
  private final EntityAIFriendlyAttackMelee aiAttackOnCollide = new EntityAIFriendlyAttackMelee((EntityTameBase)this, 1.0D, true);
  
  public EntityIllusioner(World worldIn) {
    super(worldIn);
    this.field_70728_aV = 50;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(1, (EntityAIBase)new EntityAIFollowLeader((EntityTameBase)this, 0.75D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntitySpellcasterIllager.AICastingSpell(this));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIIllusionFormSpell());
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new AIDisguiseSpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIMirriorSpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIFearSpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIBlindnessSpell());
    this.field_70714_bg.func_75776_a(4, (EntityAIBase)new AIReinforcingSpell());
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIMoveTowardsRestriction((EntityCreature)this, 0.5D));
    this.field_70714_bg.func_75776_a(6, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.5D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    setCombatTask();
  }
  
  public void setCombatTask() {
    if (this.field_70170_p != null && !this.field_70170_p.field_72995_K) {
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiAttackOnCollide);
      this.field_70714_bg.func_85156_a((EntityAIBase)this.aiArrowAttack);
      ItemStack itemstack = func_184614_ca();
      if (itemstack != null && itemstack.func_77973_b() instanceof net.minecraft.item.ItemBow) {
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)this.aiArrowAttack);
      } else {
        this.field_70714_bg.func_75776_a(4, (EntityAIBase)this.aiAttackOnCollide);
      } 
    } 
  }
  
  public String getDescName() {
    return "illusioner";
  }
  
  public TextFormatting getTextFormat() {
    return TextFormatting.AQUA;
  }
  
  public int getNextLevelRequirement() {
    return 500;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    func_184212_Q().func_187214_a(DISGUISE_ID, Integer.valueOf(0));
    func_184212_Q().func_187214_a(DISGUSE_TIMER, Integer.valueOf(0));
  }
  
  public int getDisguiseTime() {
    return ((Integer)this.field_70180_af.func_187225_a(DISGUSE_TIMER)).intValue();
  }
  
  public void setDisguiseTime(int age) {
    this.field_70180_af.func_187227_b(DISGUSE_TIMER, Integer.valueOf(age));
  }
  
  public int getDisguiseID() {
    return ((Integer)this.field_70180_af.func_187225_a(DISGUISE_ID)).intValue();
  }
  
  public void setDisguiseID(int age) {
    this.field_70180_af.func_187227_b(DISGUISE_ID, Integer.valueOf(age));
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER5;
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    tagCompound.func_74768_a("DiguiseID", getDisguiseID());
    tagCompound.func_74768_a("DiguiseTime", getDisguiseTime());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    setDisguiseID(tagCompund.func_74762_e("DiguiseID"));
    setDisguiseTime(tagCompund.func_74762_e("DiguiseTime"));
    setCombatTask();
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.5D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(32.0D);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a(20.0D);
    func_110148_a(SharedMonsterAttributes.field_189429_h).func_111128_a(5.0D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(8.0D);
    func_110148_a(SharedMonsterAttributes.field_111266_c).func_111128_a(1.0D);
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance difficulty, IEntityLivingData livingdata) {
    func_184201_a(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.field_151031_f));
    setCombatTask();
    return super.func_180482_a(difficulty, livingdata);
  }
  
  protected ResourceLocation func_184647_J() {
    return LootTableList.field_186419_a;
  }
  
  protected SoundEvent func_184639_G() {
    if (getDisguiseID() > 0 && getDisguiseTime() > 0) {
      func_184185_a(SoundEvents.field_193783_dc, 0.05F, func_70647_i());
      switch (getDisguiseID()) {
        case 2:
          return SoundEvents.field_191268_hm;
        case 3:
          return SoundEvents.field_191243_bm;
      } 
      return SoundEvents.field_187910_gj;
    } 
    return SoundEvents.field_193783_dc;
  }
  
  protected SoundEvent func_184615_bR() {
    return SoundEvents.field_193786_de;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource damageSourceIn) {
    return SoundEvents.field_193787_df;
  }
  
  protected SoundEvent getSpellSound() {
    return SoundEvents.field_193784_dd;
  }
  
  public void func_82196_d(EntityLivingBase target, float distanceFactor) {
    if (func_184614_ca().func_77973_b() instanceof ItemPEGun) {
      if (((ItemPEGun)func_184614_ca().func_77973_b()).getContainedEnergy(func_184614_ca()) > 0.0F) {
        float f = MathHelper.func_76134_b((this.field_70759_as + (180 * ((func_184638_cS() ? 1 : 2) - 1))) * 0.017453292F);
        float f1 = MathHelper.func_76126_a((this.field_70759_as + (180 * ((func_184638_cS() ? 1 : 2) - 1))) * 0.017453292F);
        ((ItemPEGun)func_184614_ca().func_77973_b()).consumeEnergy(func_184614_ca(), 1.0F);
        Vec3d vec3 = func_70676_i(1.0F);
        double d2 = target.field_70165_t - this.field_70165_t + f * 0.35D + vec3.field_72450_a;
        double d3 = target.field_70163_u + 0.5D - this.field_70163_u + func_70047_e() - 0.10000000149011612D + vec3.field_72448_b;
        double d4 = target.field_70161_v - this.field_70161_v + f1 * 0.35D + vec3.field_72449_c;
        EntityPEGunPellet entitywitherskull = new EntityPEGunPellet(this.field_70170_p, (EntityLivingBase)this, d2, d3, d4);
        entitywitherskull.field_70165_t = this.field_70165_t + f * 0.35D + vec3.field_72450_a;
        entitywitherskull.field_70163_u = this.field_70163_u + func_70047_e() - 0.10000000149011612D + vec3.field_72448_b;
        entitywitherskull.field_70161_v = this.field_70161_v + f1 * 0.35D + vec3.field_72449_c;
        entitywitherskull.damage = ((ItemPEGun)func_184614_ca().func_77973_b()).getProjectileDamage(func_184614_ca());
        if (!this.field_70170_p.field_72995_K)
          this.field_70170_p.func_72838_d((Entity)entitywitherskull); 
        func_184185_a(SoundEvents.field_187539_bB, 0.5F, 0.5F + func_70681_au().nextFloat() * 0.25F);
        func_184185_a(ESound.pegunfire, 0.5F, 0.6F + func_70681_au().nextFloat() * 0.2F + entitywitherskull.damage / 100.0F);
      } else {
        func_184185_a(ESound.pegunjam, 0.5F, 1.0F);
        func_70099_a(func_184614_ca(), 1.4F);
        func_184201_a(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
      } 
    } else {
      EntityTippedArrowOther entityarrow = new EntityTippedArrowOther(this.field_70170_p, (EntityLivingBase)this);
      double d0 = target.field_70165_t - this.field_70165_t;
      double d1 = target.field_70163_u + target.func_70047_e() - 0.5D - this.field_70163_u + func_70047_e() - 0.10000000149011612D;
      double d2 = target.field_70161_v - this.field_70161_v;
      double d3 = MathHelper.func_76133_a(d0 * d0 + d2 * d2);
      entityarrow.func_70186_c(d0, d1 + d3 * func_70032_d((Entity)target) * 0.013D, d2, 1.4F, 1.0F);
      int i = EnchantmentHelper.func_185284_a(Enchantments.field_185309_u, (EntityLivingBase)this);
      int j = EnchantmentHelper.func_185284_a(Enchantments.field_185310_v, (EntityLivingBase)this);
      if (func_184187_bx() != null) {
        entityarrow.func_70239_b((distanceFactor * 3.0F) + this.field_70146_Z.nextGaussian() * 0.25D + 0.5D);
      } else {
        entityarrow.func_70239_b((distanceFactor * 1.5F) + this.field_70146_Z.nextGaussian() * 0.25D + 0.5D);
      } 
      if (target instanceof net.minecraft.entity.boss.EntityDragon && this.field_70163_u < target.field_70163_u - 10.0D)
        entityarrow.field_70181_x++; 
      if (i > 0)
        entityarrow.func_70239_b(entityarrow.func_70242_d() + i * 0.5D + 0.5D); 
      if (isHero())
        entityarrow.func_70239_b(entityarrow.func_70242_d() * 2.0D); 
      if (this.moralRaisedTimer > 200)
        entityarrow.func_70239_b(entityarrow.func_70242_d() * 1.5D); 
      if (func_184187_bx() != null) {
        j += 2;
        entityarrow.func_70243_d(true);
      } 
      if (j > 0)
        entityarrow.func_70240_a(j); 
      if (EnchantmentHelper.func_185284_a(Enchantments.field_185311_w, (EntityLivingBase)this) > 0)
        entityarrow.func_70015_d(100); 
      if (func_184592_cb() != null && func_184592_cb().func_77973_b() == Items.field_185167_i)
        entityarrow.func_184555_a(func_184592_cb()); 
      func_184185_a(SoundEvents.field_187866_fi, 1.0F, 1.0F / (func_70681_au().nextFloat() * 0.4F + 0.8F));
      this.field_70170_p.func_72838_d((Entity)entityarrow);
    } 
  }
  
  protected EntityArrow createArrowEntity(float p_193097_1_) {
    EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.field_70170_p, (EntityLivingBase)this);
    entitytippedarrow.func_190547_a((EntityLivingBase)this, p_193097_1_);
    return (EntityArrow)entitytippedarrow;
  }
  
  @SideOnly(Side.CLIENT)
  public EntityAbstractIllagers.IllagerArmPose getArmPose() {
    if (isSpellcasting() && getDisguiseID() != 1)
      return EntityAbstractIllagers.IllagerArmPose.SPELLCASTING; 
    if (isArmsRaised() && getDisguiseID() != 1)
      return EntityAbstractIllagers.IllagerArmPose.BOW_AND_ARROW; 
    return EntityAbstractIllagers.IllagerArmPose.CROSSED;
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (getDisguiseTime() > 0)
      setDisguiseTime(getDisguiseTime() - 1); 
    if (getDisguiseID() != 0 && (getDisguiseTime() < 0 || (getDisguiseTime() > 0 && this.field_70172_ad > 0))) {
      func_184185_a(SoundEvents.field_193788_dg, 1.0F, 1.0F);
      func_70656_aK();
      setDisguiseID(0);
      setDisguiseTime(0);
    } 
  }
  
  public class EntityAIPanicFear extends EntityAIBase {
    protected final EntityCreature creature;
    
    protected double speed;
    
    protected double randPosX;
    
    protected double randPosY;
    
    protected double randPosZ;
    
    protected int feartimer;
    
    public EntityAIPanicFear(EntityCreature creature, double speedIn) {
      this.creature = creature;
      this.speed = speedIn;
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      return (this.feartimer < 400 && findRandomPosition());
    }
    
    protected boolean findRandomPosition() {
      Vec3d vec3d = RandomPositionGenerator.func_75463_a(this.creature, 5, 4);
      if (vec3d == null)
        return false; 
      this.randPosX = vec3d.field_72450_a;
      this.randPosY = vec3d.field_72448_b;
      this.randPosZ = vec3d.field_72449_c;
      return true;
    }
    
    public void func_75249_e() {
      this.creature.func_70661_as().func_75492_a(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }
    
    public boolean func_75253_b() {
      return (this.feartimer < 400);
    }
    
    public void func_75246_d() {
      this.creature.func_70604_c(null);
      this.creature.func_70624_b(null);
      if (!this.creature.func_70661_as().func_75492_a(this.randPosX, this.randPosY, this.randPosZ, this.speed)) {
        Vec3d vec3d = RandomPositionGenerator.func_75463_a(this.creature, 5, 4);
        if (vec3d != null) {
          this.randPosX = vec3d.field_72450_a;
          this.randPosY = vec3d.field_72448_b;
          this.randPosZ = vec3d.field_72449_c;
        } 
      } 
      this.feartimer++;
    }
    
    public void func_75251_c() {
      this.creature.field_70714_bg.func_85156_a(this);
    }
  }
  
  public class AIFearSpell extends EntitySpellcasterIllager.AIUseSpell {
    private EntityIllusioner.EntityAIPanicFear fear;
    
    private AIFearSpell() {
      super(EntityIllusioner.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      return (!EntityIllusioner.this.func_70638_az().func_70662_br() && EntityIllusioner.this.func_70638_az().func_184222_aU() && (EntityIllusioner.this.func_70638_az() instanceof EntityCreature || (EntityIllusioner.this.func_70638_az() instanceof EntityTameBase && !((EntityTameBase)EntityIllusioner.this.func_70638_az()).isABoss() && ((EntityTameBase)EntityIllusioner.this.func_70638_az()).getIntelligence() < 24.0F)));
    }
    
    public boolean func_75253_b() {
      return (EntityIllusioner.this.func_70638_az() != null && super.func_75253_b());
    }
    
    public void func_75249_e() {
      super.func_75249_e();
      EntityIllusioner.this.func_70638_az().func_145782_y();
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 500;
    }
    
    protected void castSpell() {
      List<EntityCreature> list = EntityIllusioner.this.field_70170_p.func_175647_a(EntityCreature.class, EntityIllusioner.this.func_70638_az().func_174813_aQ().func_186662_g(8.0D), Predicates.and(new Predicate[] { EntitySelectors.field_94557_a }));
      EntityIllusioner.this.field_70170_p.func_175669_a(1023, new BlockPos((Entity)EntityIllusioner.this.func_70638_az()), 0);
      ((EntityCreature)EntityIllusioner.this.func_70638_az()).func_184185_a(SoundEvents.field_187925_gy, 2.0F, 1.0F);
      for (EntityCreature entity : list) {
        this.fear = new EntityIllusioner.EntityAIPanicFear(entity, 1.5D);
        if (!entity.field_70714_bg.field_75782_a.contains(this.fear) && !EntityIllusioner.this.func_184191_r((Entity)entity))
          entity.field_70714_bg.func_75776_a(0, this.fear); 
      } 
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_193789_dh;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.FEAR;
    }
  }
  
  public class AIBlindnessSpell extends EntitySpellcasterIllager.AIUseSpell {
    private int lastTargetId;
    
    private AIBlindnessSpell() {
      super(EntityIllusioner.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      if (EntityIllusioner.this.func_70638_az().func_145782_y() == this.lastTargetId)
        return false; 
      return (EntityIllusioner.this.func_70638_az() instanceof net.minecraft.entity.player.EntityPlayer || (EntityIllusioner.this.func_70638_az() instanceof EntityTameBase && EntityIllusioner.this.func_70638_az().func_184222_aU()));
    }
    
    public boolean func_75253_b() {
      return (EntityIllusioner.this.func_70638_az() != null && super.func_75253_b());
    }
    
    public void func_75249_e() {
      super.func_75249_e();
      this.lastTargetId = EntityIllusioner.this.func_70638_az().func_145782_y();
    }
    
    protected int getCastingTime() {
      return 20;
    }
    
    protected int getCastingInterval() {
      return 180;
    }
    
    protected void castSpell() {
      EntityIllusioner.this.func_70638_az().func_70690_d(new PotionEffect(MobEffects.field_76440_q, 400));
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_193789_dh;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.BLINDNESS;
    }
  }
  
  public class AIMirriorSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIMirriorSpell() {
      super(EntityIllusioner.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      return (EntityIllusioner.this.getGhostTime() <= 0);
    }
    
    protected int getCastingTime() {
      return 20;
    }
    
    protected int getCastingInterval() {
      return 340;
    }
    
    protected void castSpell() {
      EntityIllusioner.this.func_70656_aK();
      EntityIllusioner.this.setGhostTime(1200);
    }
    
    @Nullable
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_193790_di;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.DISAPPEAR;
    }
  }
  
  public class AIDisguiseSpell extends EntitySpellcasterIllager.AIUseSpell {
    private AIDisguiseSpell() {
      super(EntityIllusioner.this);
    }
    
    public boolean func_75250_a() {
      if (EntityIllusioner.this.isWild())
        return false; 
      if (EntityIllusioner.this.func_70638_az() != null)
        return false; 
      if (EntityIllusioner.this.isSpellcasting())
        return false; 
      if (EntityIllusioner.this.field_70173_aa < this.spellCooldown)
        return false; 
      return (EntityIllusioner.this.getDisguiseTime() <= 0);
    }
    
    public boolean func_75253_b() {
      return (EntityIllusioner.this.func_70638_az() == null && super.func_75253_b());
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 100;
    }
    
    protected void castSpell() {
      EntityIllusioner.this.func_184185_a(SoundEvents.field_193788_dg, 1.0F, 1.0F);
      EntityIllusioner.this.func_70656_aK();
      EntityIllusioner.this.setGhostTime(0);
      EntityIllusioner.this.setDisguiseTime(12000);
      if (EntityIllusioner.this.field_70170_p.func_72935_r() && EntityIllusioner.this.field_70170_p.func_175678_i(EntityIllusioner.this.func_180425_c())) {
        EntityIllusioner.this.setDisguiseID(1);
      } else if (EntityIllusioner.this.getLevel() < 50) {
        EntityIllusioner.this.setDisguiseID(2);
      } else {
        EntityIllusioner.this.setDisguiseID(3);
      } 
    }
    
    @Nullable
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_193790_di;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.CHANGE_SELF;
    }
  }
  
  public class AIReinforcingSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = new Predicate<EntityTameBase>() {
        public boolean apply(EntityTameBase p_apply_1_) {
          return !p_apply_1_.isWild();
        }
      };
    
    public AIReinforcingSpell() {
      super(EntityIllusioner.this);
    }
    
    public boolean func_75250_a() {
      if (!super.func_75250_a())
        return false; 
      List<EntityTameBase> list = EntityIllusioner.this.field_70170_p.func_175647_a(EntityTameBase.class, EntityIllusioner.this.func_174813_aQ().func_186662_g(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityIllusioner.this.field_70146_Z.nextInt(list.size()));
      if (entity.getGhostTime() > 0 || !entity.func_70089_S() || entity == EntityIllusioner.this) {
        list.remove(entity);
        return false;
      } 
      EntityIllusioner.this.setAllyTarget(entity);
      return true;
    }
    
    public boolean func_75253_b() {
      return (EntityIllusioner.this.getAllyTarget() != null && EntityIllusioner.this.getAllyTarget().func_70089_S() && EntityIllusioner.this.func_184191_r((Entity)EntityIllusioner.this.getAllyTarget()) && EntityIllusioner.this.getAllyTarget().getGhostTime() <= 0 && this.spellWarmup > 0);
    }
    
    public void func_75251_c() {
      super.func_75251_c();
      EntityIllusioner.this.setAllyTarget((EntityTameBase)null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityIllusioner.this.getAllyTarget();
      if (entitysheep != null && entitysheep.func_70089_S() && EntityIllusioner.this.func_184191_r((Entity)entitysheep))
        if (entitysheep.getGhostTime() <= 0) {
          entitysheep.func_70656_aK();
          entitysheep.setGhostTime(1200);
          entitysheep.field_70172_ad = 10;
          func_75251_c();
        }  
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 40;
    }
    
    protected int getCastingInterval() {
      return 100;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_193790_di;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.BUFFER_ILLUSIONER;
    }
  }
  
  public class AIIllusionFormSpell extends EntitySpellcasterIllager.AIUseSpell {
    final Predicate<EntityTameBase> wololoSelector = new Predicate<EntityTameBase>() {
        public boolean apply(EntityTameBase p_apply_1_) {
          return !p_apply_1_.isWild();
        }
      };
    
    public AIIllusionFormSpell() {
      super(EntityIllusioner.this);
    }
    
    public boolean func_75250_a() {
      if (EntityIllusioner.this.func_70638_az() != null)
        return false; 
      if (EntityIllusioner.this.isSpellcasting())
        return false; 
      if (EntityIllusioner.this.field_70173_aa < this.spellCooldown)
        return false; 
      List<EntityTameBase> list = EntityIllusioner.this.field_70170_p.func_175647_a(EntityTameBase.class, EntityIllusioner.this.func_174813_aQ().func_186662_g(32.0D), this.wololoSelector);
      if (list.isEmpty())
        return false; 
      EntityTameBase entity = list.get(EntityIllusioner.this.field_70146_Z.nextInt(list.size()));
      if (entity.isABoss() || entity.getIllusionFormTime() > 0 || !entity.func_70089_S() || entity == EntityIllusioner.this || entity instanceof EntityIllusioner || !EntityIllusioner.this.func_184191_r((Entity)entity)) {
        list.remove(entity);
        return false;
      } 
      EntityIllusioner.this.setAllyTarget(entity);
      return true;
    }
    
    public boolean func_75253_b() {
      return (EntityIllusioner.this.getAllyTarget() != null && EntityIllusioner.this.getAllyTarget().func_70089_S() && EntityIllusioner.this.func_184191_r((Entity)EntityIllusioner.this.getAllyTarget()) && this.spellWarmup > 0);
    }
    
    public void func_75251_c() {
      super.func_75251_c();
      EntityIllusioner.this.setAllyTarget((EntityTameBase)null);
    }
    
    protected void castSpell() {
      EntityTameBase entitysheep = EntityIllusioner.this.getAllyTarget();
      if (entitysheep != null && entitysheep.func_70089_S() && EntityIllusioner.this.func_184191_r((Entity)entitysheep))
        if (entitysheep.getIllusionFormTime() <= 0) {
          entitysheep.func_184185_a(ESound.bugSpecial, 1.0F, 1.0F);
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.func_70656_aK();
          entitysheep.setIllusionFormTime(12000);
          entitysheep.field_70172_ad = 10;
          entitysheep.field_70173_aa = -10;
          func_75251_c();
        }  
    }
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected int getCastingTime() {
      return 60;
    }
    
    protected int getCastingInterval() {
      return 200;
    }
    
    protected SoundEvent getSpellPrepareSound() {
      return SoundEvents.field_193789_dh;
    }
    
    protected EntitySpellcasterIllager.SpellType getSpellType() {
      return EntitySpellcasterIllager.SpellType.ILLUSION_FORM;
    }
  }
}
