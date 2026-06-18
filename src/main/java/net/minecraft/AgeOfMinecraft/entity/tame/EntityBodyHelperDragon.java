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
  
  public void updateRenderAngles() {
    double d0 = this.theLiving.posX - this.theLiving.prevPosX;
    double d1 = this.theLiving.posZ - this.theLiving.prevPosZ;
    if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
      this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
      this.theLiving.rotationYawHead = computeAngleWithBound(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 360.0F);
      this.prevRenderYawHead = this.theLiving.rotationYawHead;
      this.rotationTickCounter = 0;
    } else if (this.theLiving.getPassengers().isEmpty() || !(this.theLiving.getPassengers().get(0) instanceof net.minecraft.entity.EntityLiving)) {
      float f = 360.0F;
      if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 5.0F) {
        this.rotationTickCounter = 0;
        this.prevRenderYawHead = this.theLiving.rotationYawHead;
      } else {
        this.rotationTickCounter++;
        int i = 10;
        if (this.rotationTickCounter > i)
          f = Math.max(1.0F - (this.rotationTickCounter - i) / 10.0F, 0.0F) * 360.0F; 
      } 
      this.theLiving.renderYawOffset = computeAngleWithBound(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, f);
    } 
  }
  
  private float computeAngleWithBound(float angle, float targetAngle, float maxIncrease) {
    float f = MathHelper.wrapDegrees(targetAngle - angle);
    if (f > maxIncrease)
      f = maxIncrease; 
    if (f < -maxIncrease)
      f = -maxIncrease; 
    return angle + f;
  }
}
