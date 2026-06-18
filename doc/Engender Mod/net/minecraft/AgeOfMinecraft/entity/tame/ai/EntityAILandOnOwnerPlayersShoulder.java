package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAILandOnOwnerPlayersShoulder extends EntityAIBase {
  private final EntityTameBase entity;
  
  private EntityPlayer owner;
  
  private boolean isSittingOnShoulder;
  
  public EntityAILandOnOwnerPlayersShoulder(EntityTameBase p_i47415_1_) {
    this.entity = p_i47415_1_;
  }
  
  public boolean func_75250_a() {
    EntityLivingBase entitylivingbase = this.entity.getOwner();
    boolean flag = (entitylivingbase != null && !((EntityPlayer)entitylivingbase).func_175149_v() && !((EntityPlayer)entitylivingbase).field_71075_bZ.field_75100_b && !entitylivingbase.func_70090_H());
    return (this.entity.canFollowOwner() && flag && this.entity.canSitOnShoulder());
  }
  
  public boolean func_75252_g() {
    return !this.isSittingOnShoulder;
  }
  
  public void func_75249_e() {
    this.owner = (EntityPlayer)this.entity.getOwner();
    this.isSittingOnShoulder = false;
  }
  
  public void func_75246_d() {
    if (!this.isSittingOnShoulder && this.entity.canFollowOwner() && !this.entity.func_110167_bD())
      if (this.entity.func_174813_aQ().func_72326_a(this.owner.func_174813_aQ()))
        this.isSittingOnShoulder = this.entity.setEntityOnShoulder(this.owner);  
  }
}
