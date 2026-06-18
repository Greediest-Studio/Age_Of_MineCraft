package net.minecraft.AgeOfMinecraft.entity.tame;

import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;

public class EntityBodyHelperDragon extends EntityBodyHelper {
  private final EntityLivingBase theLiving;
  
  private int rotationTickCounter;
  
  private float prevRenderYawHead;
  
  public EntityBodyHelperDragon(EntityLivingBase livingIn) {
    super(livingIn);
    this.theLiving = livingIn;
  }
  
  public void func_75664_a() {
    double d0 = this.theLiving.field_70165_t - this.theLiving.field_70169_q;
    double d1 = this.theLiving.field_70161_v - this.theLiving.field_70166_s;
    if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
      this.theLiving.field_70761_aq = this.theLiving.field_70177_z;
      this.theLiving.field_70759_as = computeAngleWithBound(this.theLiving.field_70761_aq, this.theLiving.field_70759_as, 360.0F);
      this.prevRenderYawHead = this.theLiving.field_70759_as;
      this.rotationTickCounter = 0;
    } else if (this.theLiving.func_184188_bt().isEmpty() || !(this.theLiving.func_184188_bt().get(0) instanceof net.minecraft.entity.EntityLiving)) {
      float f = 360.0F;
      if (Math.abs(this.theLiving.field_70759_as - this.prevRenderYawHead) > 5.0F) {
        this.rotationTickCounter = 0;
        this.prevRenderYawHead = this.theLiving.field_70759_as;
      } else {
        this.rotationTickCounter++;
        int i = 10;
        if (this.rotationTickCounter > i)
          f = Math.max(1.0F - (this.rotationTickCounter - i) / 10.0F, 0.0F) * 360.0F; 
      } 
      this.theLiving.field_70761_aq = computeAngleWithBound(this.theLiving.field_70759_as, this.theLiving.field_70761_aq, f);
    } 
  }
  
  private float computeAngleWithBound(float angle, float targetAngle, float maxIncrease) {
    float f = MathHelper.func_76142_g(targetAngle - angle);
    if (f > maxIncrease)
      f = maxIncrease; 
    if (f < -maxIncrease)
      f = -maxIncrease; 
    return angle + f;
  }
}
