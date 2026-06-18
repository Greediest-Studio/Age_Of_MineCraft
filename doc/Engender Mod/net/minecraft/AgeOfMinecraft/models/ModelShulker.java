package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityShulker;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelShulker extends ModelBase {
  public final ModelRenderer base;
  
  public final ModelRenderer lid;
  
  public ModelRenderer head;
  
  public ModelShulker() {
    this.field_78089_u = 64;
    this.field_78090_t = 64;
    this.lid = new ModelRenderer(this);
    this.base = new ModelRenderer(this);
    this.head = new ModelRenderer(this);
    this.lid.func_78784_a(0, 0).func_78789_a(-8.0F, -16.0F, -8.0F, 16, 12, 16);
    this.lid.func_78793_a(0.0F, 24.0F, 0.0F);
    this.base.func_78784_a(0, 28).func_78789_a(-8.0F, -8.0F, -8.0F, 16, 8, 16);
    this.base.func_78793_a(0.0F, 24.0F, 0.0F);
    this.head.func_78784_a(0, 52).func_78789_a(-3.0F, -6.0F, -3.0F, 6, 6, 6);
    this.head.func_78793_a(0.0F, 6.0F, 0.0F);
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    EntityShulker entityshulker = (EntityShulker)entityIn;
    float f = ageInTicks - entityshulker.field_70173_aa;
    float f1 = (0.5F + entityshulker.getClientPeekAmount(f)) * 3.1415927F;
    float f2 = -1.0F + MathHelper.func_76126_a(f1);
    float f3 = 0.0F;
    if (f1 > 3.1415927F)
      f3 = MathHelper.func_76126_a(ageInTicks * 0.1F) * 0.7F; 
    float f4 = MathHelper.func_76126_a(ageInTicks * 0.125F) * 0.7F;
    this.head.func_78793_a(0.0F, 18.0F + f4, 0.0F);
    this.lid.func_78793_a(0.0F, 16.0F + MathHelper.func_76126_a(f1) * 8.0F + f3, 0.0F);
    if (entityshulker.getJukeboxToDanceTo() != null) {
      this.lid.func_78793_a(0.0F, 10.0F + MathHelper.func_76126_a(f1) * 8.0F + f3 + MathHelper.func_76134_b(ageInTicks * 0.5F) * 4.0F, 0.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 2.0F);
      this.head.func_78793_a(0.0F, 18.0F + f4 + MathHelper.func_76134_b(ageInTicks * 0.5F) * 1.0F, 0.0F + MathHelper.func_76134_b(ageInTicks * 0.25F) * 1.0F);
    } 
    if (!entityshulker.getCurrentBook().func_190926_b())
      this.head.func_78793_a(0.0F, 16.0F + f4, 0.0F); 
    if (entityshulker.getClientPeekAmount(f) > 0.3F) {
      this.lid.field_78796_g = f2 * f2 * f2 * f2 * 3.1415927F * 0.125F;
    } else {
      this.lid.field_78796_g = 0.0F;
    } 
    this.head.field_78795_f = headPitch * 0.017453292F;
    this.head.field_78796_g = netHeadYaw * 0.017453292F;
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    this.base.func_78785_a(scale);
    this.lid.func_78785_a(scale);
  }
}
