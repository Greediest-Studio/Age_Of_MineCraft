package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIDoorInteract;
import net.minecraft.util.EnumHand;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.event.ForgeEventFactory;

public class EntityAIVindicatorBreakDoor extends EntityAIDoorInteract {
  private int breakingTime;
  
  private int previousBreakProgress = -1;
  
  public EntityAIVindicatorBreakDoor(EntityLiving entityIn) {
    super(entityIn);
  }
  
  public boolean func_75250_a() {
    if (!super.func_75250_a())
      return false; 
    if (!this.field_75356_a.field_70170_p.func_180495_p(this.field_179507_b).func_177230_c().canEntityDestroy(this.field_75356_a.field_70170_p.func_180495_p(this.field_179507_b), (IBlockAccess)this.field_75356_a.field_70170_p, this.field_179507_b, (Entity)this.field_75356_a) || !ForgeEventFactory.onEntityDestroyBlock((EntityLivingBase)this.field_75356_a, this.field_179507_b, this.field_75356_a.field_70170_p.func_180495_p(this.field_179507_b)))
      return false; 
    BlockDoor blockdoor = this.field_151504_e;
    return !BlockDoor.func_176514_f((IBlockAccess)this.field_75356_a.field_70170_p, this.field_179507_b);
  }
  
  public void func_75249_e() {
    super.func_75249_e();
    this.breakingTime = 0;
  }
  
  public boolean func_75253_b() {
    double d0 = this.field_75356_a.func_174818_b(this.field_179507_b);
    if (this.breakingTime <= 8) {
      BlockDoor blockdoor = this.field_151504_e;
      if (!BlockDoor.func_176514_f((IBlockAccess)this.field_75356_a.field_70170_p, this.field_179507_b) && d0 < 4.0D) {
        boolean bool = true;
        return bool;
      } 
    } 
    boolean flag = false;
    return flag;
  }
  
  public void func_75251_c() {
    super.func_75251_c();
    this.field_75356_a.field_70170_p.func_175715_c(this.field_75356_a.func_145782_y(), this.field_179507_b, -1);
  }
  
  public void func_75246_d() {
    super.func_75246_d();
    if (this.field_75356_a.field_70173_aa % 10 == 0) {
      this.breakingTime++;
      this.field_75356_a.func_184609_a(EnumHand.MAIN_HAND);
      this.field_75356_a.field_70170_p.func_175718_b(1019, this.field_179507_b, 0);
    } 
    int i = (int)(this.breakingTime / 8.0F * 10.0F);
    if (i != this.previousBreakProgress) {
      this.field_75356_a.field_70170_p.func_175715_c(this.field_75356_a.func_145782_y(), this.field_179507_b, i);
      this.previousBreakProgress = i;
    } 
    if (this.breakingTime == 8) {
      if (this.breakingTime > 8)
        this.breakingTime = 8; 
      this.field_75356_a.field_70170_p.func_175698_g(this.field_179507_b);
      this.field_75356_a.field_70170_p.func_175698_g(this.field_179507_b.func_177977_b());
      this.field_75356_a.func_184185_a(ESound.heresJohnny, 2.0F, 1.0F);
      this.field_75356_a.field_70170_p.func_175718_b(1021, this.field_179507_b, 0);
      this.field_75356_a.field_70170_p.func_175718_b(2001, this.field_179507_b, Block.func_149682_b((Block)this.field_151504_e));
    } 
  }
}
