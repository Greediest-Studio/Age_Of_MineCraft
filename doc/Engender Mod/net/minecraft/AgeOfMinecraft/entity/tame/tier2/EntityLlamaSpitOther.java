package net.minecraft.AgeOfMinecraft.entity.tame.tier2;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import java.util.List;
import java.util.UUID;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityLlamaSpit;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLlamaSpitOther extends EntityLlamaSpit {
  public EntityLlama field_190539_a;
  
  private NBTTagCompound ownerNbt;
  
  public EntityLlamaSpitOther(World worldIn) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
  }
  
  public EntityLlamaSpitOther(World worldIn, EntityLlama p_i47273_2_) {
    super(worldIn);
    this.field_190539_a = p_i47273_2_;
    func_70107_b(p_i47273_2_.field_70165_t - (p_i47273_2_.field_70130_N + 1.0F) * 0.5D * MathHelper.func_76126_a(p_i47273_2_.field_70761_aq * 0.017453292F), p_i47273_2_.field_70163_u + p_i47273_2_.func_70047_e(), p_i47273_2_.field_70161_v + (p_i47273_2_.field_70130_N + 1.0F) * 0.5D * MathHelper.func_76134_b(p_i47273_2_.field_70761_aq * 0.017453292F));
    func_70105_a(0.25F, 0.25F);
  }
  
  @SideOnly(Side.CLIENT)
  public EntityLlamaSpitOther(World worldIn, double x, double y, double z, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
    super(worldIn);
    func_70105_a(0.25F, 0.25F);
    func_70107_b(x, y, z);
    for (int i = 0; i < 20; i++) {
      double d0 = 0.4D + 0.1D * i;
      worldIn.func_175688_a(EnumParticleTypes.SPIT, x, y, z, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0, new int[0]);
    } 
    this.field_70159_w = p_i47274_8_;
    this.field_70181_x = p_i47274_10_;
    this.field_70179_y = p_i47274_12_;
  }
  
  public void func_190536_a(RayTraceResult movingObject) {
    if (!this.field_70170_p.field_72995_K && movingObject.field_72308_g != null)
      if (movingObject.field_72308_g != this.field_190539_a && movingObject.field_72308_g instanceof EntityLivingBase && (!this.field_190539_a.func_184191_r(movingObject.field_72308_g) || (movingObject.field_72308_g instanceof net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase && this.field_190539_a.func_184191_r(movingObject.field_72308_g) && this.field_190539_a.getFakeHealth() > 0.0F)) && movingObject.field_72308_g != null && this.field_190539_a != null && movingObject.field_72308_g != this.field_190539_a) {
        this.field_190539_a.inflictEngenderMobDamage((EntityLivingBase)movingObject.field_72308_g, " was spat on by ", DamageSource.func_76356_a((Entity)this.field_190539_a, (Entity)this), 1.0F);
        if (((EntityLivingBase)movingObject.field_72308_g).func_184222_aU())
          ((EntityLivingBase)movingObject.field_72308_g).func_70653_a((Entity)this, 0.75F, MathHelper.func_76126_a(this.field_190539_a.field_70759_as * 0.017453292F), -MathHelper.func_76134_b(this.field_190539_a.field_70759_as * 0.017453292F)); 
        if (!this.field_70170_p.field_72995_K)
          func_70106_y(); 
      }  
    if (!this.field_70170_p.field_72995_K && movingObject.field_72308_g == null)
      func_70106_y(); 
  }
  
  protected void func_70088_a() {}
  
  public void func_70071_h_() {
    super.func_70071_h_();
    func_70105_a(0.25F, 0.25F);
    List<EntityLivingBase> list = this.field_70170_p.func_175647_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(4.0D), Predicates.and(new Predicate[] { EntitySelectors.field_180132_d }));
    if (!list.isEmpty())
      for (EntityLivingBase entity1 : list) {
        if (this.field_190539_a != null && entity1 instanceof net.minecraft.entity.IEntityMultiPart && entity1 != null && entity1.func_70089_S() && !this.field_190539_a.func_184191_r((Entity)entity1))
          func_190536_a(new RayTraceResult((Entity)entity1)); 
      }  
  }
  
  protected void func_70037_a(NBTTagCompound compound) {
    if (compound.func_150297_b("Owner", 10))
      this.ownerNbt = compound.func_74775_l("Owner"); 
    func_70105_a(0.25F, 0.25F);
  }
  
  protected void func_70014_b(NBTTagCompound compound) {
    if (this.field_190539_a != null) {
      NBTTagCompound nbttagcompound = new NBTTagCompound();
      UUID uuid = this.field_190539_a.func_110124_au();
      nbttagcompound.func_186854_a("OwnerUUID", uuid);
      compound.func_74782_a("Owner", (NBTBase)nbttagcompound);
    } 
    func_70105_a(0.25F, 0.25F);
  }
  
  private void restoreOwnerFromSave() {
    if (this.ownerNbt != null && this.ownerNbt.func_186855_b("OwnerUUID")) {
      UUID uuid = this.ownerNbt.func_186857_a("OwnerUUID");
      for (EntityLlama entityllama : this.field_70170_p.func_72872_a(EntityLlama.class, func_174813_aQ().func_186662_g(15.0D))) {
        if (entityllama.func_110124_au().equals(uuid)) {
          this.field_190539_a = entityllama;
          break;
        } 
      } 
    } 
    this.ownerNbt = null;
  }
}
