package net.minecraft.AgeOfMinecraft.entity.tame;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityAbstractIllagers extends EntityTameBase {
  protected static final DataParameter<Byte> AGGRESSIVE = EntityDataManager.func_187226_a(EntityAbstractIllagers.class, DataSerializers.field_187191_a);
  
  public EntityAbstractIllagers(World p_i47509_1_) {
    super(p_i47509_1_);
    func_70105_a(0.5F, 1.9F);
    this.isOffensive = true;
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(AGGRESSIVE, Byte.valueOf((byte)0));
  }
  
  @SideOnly(Side.CLIENT)
  protected boolean isAggressive(int mask) {
    int i = ((Byte)this.field_70180_af.func_187225_a(AGGRESSIVE)).byteValue();
    return ((i & mask) != 0);
  }
  
  protected void setAggressive(int mask, boolean value) {
    int i = ((Byte)this.field_70180_af.func_187225_a(AGGRESSIVE)).byteValue();
    if (value) {
      i |= mask;
    } else {
      i &= mask ^ 0xFFFFFFFF;
    } 
    this.field_70180_af.func_187227_b(AGGRESSIVE, Byte.valueOf((byte)(i & 0xFF)));
  }
  
  public EnumCreatureAttribute func_70668_bt() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  @SideOnly(Side.CLIENT)
  public IllagerArmPose getArmPose() {
    return IllagerArmPose.CROSSED;
  }
  
  @SideOnly(Side.CLIENT)
  public enum IllagerArmPose {
    CROSSED, ATTACKING, SPELLCASTING, BOW_AND_ARROW;
  }
}
