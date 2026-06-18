package net.minecraft.AgeOfMinecraft.entity.tame;

import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntitySpellcasterIllager extends EntityAbstractIllagers implements Light {
  private static final DataParameter<Byte> SPELL = EntityDataManager.func_187226_a(EntitySpellcasterIllager.class, DataSerializers.field_187191_a);
  
  protected int spellTicks;
  
  private EntityTameBase allyTarget;
  
  private EntitySheep wololoTarget;
  
  private EntityTameBase convertTarget;
  
  private SpellType activeSpell = SpellType.NONE;
  
  public EntitySpellcasterIllager(World p_i47506_1_) {
    super(p_i47506_1_);
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(SPELL, Byte.valueOf((byte)0));
  }
  
  protected void setWololoTarget(@Nullable EntitySheep p_190748_1_) {
    this.wololoTarget = p_190748_1_;
  }
  
  @Nullable
  protected EntitySheep getWololoTarget() {
    return this.wololoTarget;
  }
  
  protected void setConvertingTarget(@Nullable EntityTameBase p_190748_1_) {
    this.convertTarget = p_190748_1_;
  }
  
  @Nullable
  protected EntityTameBase getConvertingTarget() {
    return this.convertTarget;
  }
  
  protected void setAllyTarget(@Nullable EntityTameBase p_190748_1_) {
    this.allyTarget = p_190748_1_;
  }
  
  @Nullable
  protected EntityTameBase getAllyTarget() {
    return this.allyTarget;
  }
  
  public void func_70037_a(NBTTagCompound compound) {
    super.func_70037_a(compound);
    this.spellTicks = compound.func_74762_e("SpellTicks");
  }
  
  public void func_70014_b(NBTTagCompound compound) {
    super.func_70014_b(compound);
    compound.func_74768_a("SpellTicks", this.spellTicks);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityAbstractIllagers.IllagerArmPose getArmPose() {
    return isSpellcasting() ? EntityAbstractIllagers.IllagerArmPose.SPELLCASTING : EntityAbstractIllagers.IllagerArmPose.CROSSED;
  }
  
  public boolean isSpellcasting() {
    if (this.field_70170_p.field_72995_K)
      return (((Byte)this.field_70180_af.func_187225_a(SPELL)).byteValue() > 0); 
    return (this.spellTicks > 0);
  }
  
  public void setSpellType(SpellType spellType) {
    this.activeSpell = spellType;
    this.field_70180_af.func_187227_b(SPELL, Byte.valueOf((byte)spellType.id));
  }
  
  protected SpellType getSpellType() {
    return !this.field_70170_p.field_72995_K ? this.activeSpell : SpellType.getFromId(((Byte)this.field_70180_af.func_187225_a(SPELL)).byteValue());
  }
  
  protected void func_70619_bc() {
    super.func_70619_bc();
    if (this.spellTicks > 0) {
      this.spellTicks--;
      if (this.moralRaisedTimer > 0)
        this.spellTicks--; 
    } 
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (this.field_70170_p.field_72995_K && isSpellcasting()) {
      SpellType entityspellcasterillager$spelltype = getSpellType();
      double d0 = entityspellcasterillager$spelltype.particleSpeed[0];
      double d1 = entityspellcasterillager$spelltype.particleSpeed[1];
      double d2 = entityspellcasterillager$spelltype.particleSpeed[2];
      float f = this.field_70761_aq * 0.017453292F + MathHelper.func_76134_b(this.field_70173_aa * 0.6662F) * 0.25F;
      float f1 = MathHelper.func_76134_b(f);
      float f2 = MathHelper.func_76126_a(f);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t + f1 * 0.6D, this.field_70163_u + 1.8D, this.field_70161_v + f2 * 0.6D, d0, d1, d2, new int[0]);
      this.field_70170_p.func_175688_a(EnumParticleTypes.SPELL_MOB, this.field_70165_t - f1 * 0.6D, this.field_70163_u + 1.8D, this.field_70161_v - f2 * 0.6D, d0, d1, d2, new int[0]);
    } 
  }
  
  protected int getSpellTicks() {
    return this.spellTicks;
  }
  
  protected abstract SoundEvent getSpellSound();
  
  public class AICastingSpell extends EntityAIBase {
    public AICastingSpell() {
      func_75248_a(3);
    }
    
    public boolean func_75250_a() {
      return (!EntitySpellcasterIllager.this.func_70631_g_() && EntitySpellcasterIllager.this.getSpellTicks() > 0);
    }
    
    public void func_75249_e() {
      super.func_75249_e();
      EntitySpellcasterIllager.this.field_70699_by.func_75499_g();
    }
    
    public void func_75251_c() {
      super.func_75251_c();
      EntitySpellcasterIllager.this.setSpellType(EntitySpellcasterIllager.SpellType.NONE);
    }
    
    public void func_75246_d() {
      EntitySpellcasterIllager.this.field_70699_by.func_75499_g();
      if (EntitySpellcasterIllager.this.func_70638_az() != null) {
        EntitySpellcasterIllager.this.func_70671_ap().func_75651_a((Entity)EntitySpellcasterIllager.this.func_70638_az(), EntitySpellcasterIllager.this.func_184649_cE(), EntitySpellcasterIllager.this.func_70646_bf());
      } else if (EntitySpellcasterIllager.this.getAllyTarget() != null) {
        EntitySpellcasterIllager.this.func_70671_ap().func_75651_a((Entity)EntitySpellcasterIllager.this.getAllyTarget(), EntitySpellcasterIllager.this.func_184649_cE(), EntitySpellcasterIllager.this.func_70646_bf());
      } else if (EntitySpellcasterIllager.this.getConvertingTarget() != null) {
        EntitySpellcasterIllager.this.func_70671_ap().func_75651_a((Entity)EntitySpellcasterIllager.this.getConvertingTarget(), 180.0F, EntitySpellcasterIllager.this.func_70646_bf());
      } 
    }
  }
  
  public abstract class AIUseSpell extends EntityAIBase {
    protected int spellWarmup;
    
    protected int spellCooldown;
    
    public boolean func_75250_a() {
      if (EntitySpellcasterIllager.this.func_70638_az() == null)
        return false; 
      if (EntitySpellcasterIllager.this.isSpellcasting())
        return false; 
      return (EntitySpellcasterIllager.this.field_70173_aa >= this.spellCooldown);
    }
    
    public boolean func_75253_b() {
      return (this.spellWarmup > 0);
    }
    
    public void func_75249_e() {
      this.spellWarmup = getCastWarmupTime();
      EntitySpellcasterIllager.this.spellTicks = getCastingTime();
      this.spellCooldown = EntitySpellcasterIllager.this.field_70173_aa + getCastingInterval();
      SoundEvent soundevent = getSpellPrepareSound();
      if (soundevent != null)
        EntitySpellcasterIllager.this.func_184185_a(soundevent, 1.0F, 1.0F); 
      EntitySpellcasterIllager.this.setSpellType(getSpellType());
    }
    
    public void func_75246_d() {
      this.spellWarmup--;
      if (EntitySpellcasterIllager.this.moralRaisedTimer > 0)
        this.spellWarmup--; 
      EntitySpellcasterIllager.this.field_70699_by.func_75499_g();
      if (this.spellWarmup == 0) {
        if (EntitySpellcasterIllager.this.func_70638_az() != null)
          EntitySpellcasterIllager.this.field_70172_ad = 2; 
        EntitySpellcasterIllager.this.setCurrentStudy(EntityTameBase.EnumStudy.Mental, 5);
        EntitySpellcasterIllager.this.field_70761_aq = EntitySpellcasterIllager.this.field_70177_z = EntitySpellcasterIllager.this.field_70759_as;
        EntitySpellcasterIllager.this.func_184609_a(EnumHand.MAIN_HAND);
        castSpell();
        EntitySpellcasterIllager.this.func_184185_a(EntitySpellcasterIllager.this.getSpellSound(), 1.0F, 1.0F);
      } 
    }
    
    protected abstract void castSpell();
    
    protected int getCastWarmupTime() {
      return 20;
    }
    
    protected abstract int getCastingTime();
    
    protected abstract int getCastingInterval();
    
    @Nullable
    protected abstract SoundEvent getSpellPrepareSound();
    
    protected abstract EntitySpellcasterIllager.SpellType getSpellType();
  }
  
  public enum SpellType {
    NONE(0, 0.0D, 0.0D, 0.0D),
    SUMMON_VEX(1, 0.7D, 0.7D, 0.8D),
    FANGS(2, 0.4D, 0.3D, 0.35D),
    WOLOLO(3, 0.7D, 0.5D, 0.2D),
    CONVERT(4, 0.8D, 0.56D, 0.0D),
    LIGHTNING_BOLT(5, 0.8D, 0.56D, 0.0D),
    FIREBALL(6, 1.0D, 0.3D, 0.0D),
    FIREBOLT(7, 1.0D, 0.784D, 0.0D),
    POISON_SPRAY(8, 0.0D, 0.784D, 0.0D),
    MAGIC_MISSLE(9, 0.625D, 0.925D, 1.0D),
    DISINTIGRATION_RAY(10, 0.0D, 1.0D, 0.0D),
    FROST_RAY(11, 0.625D, 0.925D, 1.0D),
    METEOR_STORM(12, 1.0D, 0.5D, 0.0D),
    BUFFER_EVOKER(13, 1.0D, 0.0D, 1.0D),
    POLYMORPH(14, 0.23D, 0.85D, 0.94D),
    DISAPPEAR(15, 0.3D, 0.3D, 0.8D),
    BLINDNESS(16, 0.1D, 0.1D, 0.2D),
    BUFFER_ILLUSIONER(17, 0.3D, 0.3D, 0.8D),
    CHANGE_SELF(18, 0.5D, 0.5D, 0.5D),
    ILLUSION_FORM(19, 0.9D, 0.25D, 0.25D),
    FEAR(20, 0.83D, 0.36D, 0.76D);
    
    private final int id;
    
    private final double[] particleSpeed;
    
    SpellType(int idIn, double xParticleSpeed, double yParticleSpeed, double zParticleSpeed) {
      this.id = idIn;
      this.particleSpeed = new double[] { xParticleSpeed, yParticleSpeed, zParticleSpeed };
    }
    
    public static SpellType getFromId(int idIn) {
      for (SpellType entityspellcasterillager$spelltype : values()) {
        if (idIn == entityspellcasterillager$spelltype.id)
          return entityspellcasterillager$spelltype; 
      } 
      return NONE;
    }
  }
}
