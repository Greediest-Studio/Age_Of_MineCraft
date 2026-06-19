package net.minecraft.AgeOfMinecraft.entity.tame;

import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityAbstractIllagers extends EntityTameBase {
  protected static final DataParameter<Byte> AGGRESSIVE = EntityDataManager.createKey(EntityAbstractIllagers.class, DataSerializers.BYTE);
  
  public EntityAbstractIllagers(World p_i47509_1_) {
    super(p_i47509_1_);
    setSize(0.5F, 1.9F);
    this.isOffensive = true;
  }
  
  protected void entityInit() {
    super.entityInit();
    this.dataManager.register(AGGRESSIVE, (byte) 0);
  }
  
  @SideOnly(Side.CLIENT)
  protected boolean isAggressive(int mask) {
    int i = (Byte) this.dataManager.get(AGGRESSIVE);
    return ((i & mask) != 0);
  }
  
  protected void setAggressive(int mask, boolean value) {
    int i = this.dataManager.get(AGGRESSIVE);
    if (value) {
      i |= mask;
    } else {
      i &= ~mask;
    } 
    this.dataManager.set(AGGRESSIVE, (byte) (i & 0xFF));
  }
  
  public EnumCreatureAttribute getCreatureAttribute() {
    return EnumCreatureAttribute.ILLAGER;
  }
  
  @SideOnly(Side.CLIENT)
  public IllagerArmPose getArmPose() {
    return IllagerArmPose.CROSSED;
  }
  
  @SideOnly(Side.CLIENT)
  public enum IllagerArmPose {
    CROSSED, ATTACKING, SPELLCASTING, BOW_AND_ARROW
  }
}
