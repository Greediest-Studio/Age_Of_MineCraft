package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelWitherStorm extends ModelBase {
  public ModelRenderer CommandBlock;
  
  public ModelRenderer Cavity;
  
  public ModelRenderer Body1;
  
  public ModelRenderer Body2;
  
  public ModelRenderer Body3;
  
  public ModelRenderer Body4;
  
  public ModelRenderer Bottom1;
  
  public ModelRenderer Bottom2;
  
  public ModelRenderer Bottom3;
  
  public ModelRenderer Body5;
  
  public ModelRenderer Body6;
  
  public ModelRenderer Body7;
  
  public ModelRenderer BodyTop1;
  
  public ModelRenderer Body9;
  
  public ModelRenderer Body10;
  
  public ModelRenderer Body11;
  
  public ModelRenderer Body12;
  
  public ModelRenderer BodyTop7;
  
  public ModelRenderer BodyTop13;
  
  public ModelRenderer BodyLeft1;
  
  public ModelRenderer BodyLeft3;
  
  public ModelRenderer BodyLeft5;
  
  public ModelRenderer BodyRight1;
  
  public ModelRenderer BodyRight3;
  
  public ModelRenderer BodyRight5;
  
  public ModelRenderer BodyTop2;
  
  public ModelRenderer BodyTop3;
  
  public ModelRenderer BodyTop5;
  
  public ModelRenderer BodyTop4;
  
  public ModelRenderer BodyTop6;
  
  public ModelRenderer BodyTop8;
  
  public ModelRenderer BodyTop9;
  
  public ModelRenderer BodyTop11;
  
  public ModelRenderer BodyTop10;
  
  public ModelRenderer BodyTop12;
  
  public ModelRenderer BodyTop14;
  
  public ModelRenderer BodyTop15;
  
  public ModelRenderer BodyTop17;
  
  public ModelRenderer BodyTop16;
  
  public ModelRenderer BodyTop18;
  
  public ModelRenderer BodyLeft2;
  
  public ModelRenderer BodyLeft4;
  
  public ModelRenderer BodyLeft6;
  
  public ModelRenderer BodyRight2;
  
  public ModelRenderer BodyRight4;
  
  public ModelRenderer BodyRight6;
  
  public ModelWitherStorm() {
    this.field_78090_t = 512;
    this.field_78089_u = 512;
    this.BodyTop3 = new ModelRenderer(this, 0, 0);
    this.BodyTop3.func_78793_a(48.0F, -44.0F, 0.0F);
    this.BodyTop3.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop3, 0.0F, 0.0F, 0.5235988F);
    this.Body7 = new ModelRenderer(this, 0, 128);
    this.Body7.func_78793_a(-24.0F, 0.0F, 32.0F);
    this.Body7.func_78790_a(-32.0F, -100.0F, 0.0F, 64, 256, 64, 0.0F);
    this.BodyRight6 = new ModelRenderer(this, 0, 0);
    this.BodyRight6.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyRight6.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyTop17 = new ModelRenderer(this, 0, 0);
    this.BodyTop17.func_78793_a(-48.0F, -44.0F, 0.0F);
    this.BodyTop17.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop17, 0.0F, 0.0F, -0.5235988F);
    this.BodyTop18 = new ModelRenderer(this, 0, 0);
    this.BodyTop18.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop18.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyTop13 = new ModelRenderer(this, 0, 0);
    this.BodyTop13.func_78793_a(0.0F, -128.0F, -32.0F);
    this.BodyTop13.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop13, 0.6981317F, 0.0F, 0.0F);
    this.BodyLeft1 = new ModelRenderer(this, 0, 0);
    this.BodyLeft1.func_78793_a(100.0F, -128.0F, 0.0F);
    this.BodyLeft1.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyLeft1, 0.0F, 0.0F, 1.0471976F);
    this.BodyLeft4 = new ModelRenderer(this, 0, 0);
    this.BodyLeft4.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyLeft4.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.CommandBlock = new ModelRenderer(this, 0, 0);
    this.CommandBlock.func_78793_a(0.0F, 16.0F, 0.0F);
    this.CommandBlock.func_78790_a(-8.0F, -8.0F, -8.0F, 16, 16, 16, 0.0F);
    setRotateAngle(this.CommandBlock, 0.0F, 0.0F, 0.0017453292F);
    this.Body1 = new ModelRenderer(this, 0, 128);
    this.Body1.func_78793_a(-24.0F, 0.0F, 0.0F);
    this.Body1.func_78790_a(-64.0F, -100.0F, -32.0F, 64, 200, 64, 0.0F);
    this.Body6 = new ModelRenderer(this, 0, 128);
    this.Body6.func_78793_a(-16.0F, 0.0F, 8.0F);
    this.Body6.func_78790_a(16.0F, -100.0F, 16.0F, 64, 256, 64, 0.0F);
    this.Body4 = new ModelRenderer(this, 0, 128);
    this.Body4.func_78793_a(0.0F, 124.0F, 0.0F);
    this.Body4.func_78790_a(-32.0F, -100.0F, -32.0F, 64, 200, 64, 0.0F);
    this.BodyTop4 = new ModelRenderer(this, 0, 0);
    this.BodyTop4.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop4.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Body5 = new ModelRenderer(this, 256, 0);
    this.Body5.func_78793_a(-32.0F, 200.0F, -32.0F);
    this.Body5.func_78790_a(-24.0F, -100.0F, -24.0F, 48, 256, 48, 0.0F);
    this.BodyRight3 = new ModelRenderer(this, 0, 0);
    this.BodyRight3.func_78793_a(-196.0F, -164.0F, 0.0F);
    this.BodyRight3.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyRight3, 0.0F, 0.0F, -1.3089969F);
    this.BodyTop16 = new ModelRenderer(this, 0, 0);
    this.BodyTop16.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop16.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyRight4 = new ModelRenderer(this, 0, 0);
    this.BodyRight4.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyRight4.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Body9 = new ModelRenderer(this, 0, 128);
    this.Body9.func_78793_a(24.0F, 0.0F, -8.0F);
    this.Body9.func_78790_a(0.0F, -64.0F, 0.0F, 64, 256, 64, 0.0F);
    this.BodyTop10 = new ModelRenderer(this, 0, 0);
    this.BodyTop10.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop10.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Body10 = new ModelRenderer(this, 256, 0);
    this.Body10.func_78793_a(32.0F, 200.0F, 32.0F);
    this.Body10.func_78790_a(-24.0F, -100.0F, -24.0F, 48, 256, 48, 0.0F);
    this.BodyTop5 = new ModelRenderer(this, 0, 0);
    this.BodyTop5.func_78793_a(-48.0F, -44.0F, 0.0F);
    this.BodyTop5.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop5, 0.0F, 0.0F, -0.5235988F);
    this.BodyRight2 = new ModelRenderer(this, 0, 0);
    this.BodyRight2.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyRight2.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyTop1 = new ModelRenderer(this, 0, 0);
    this.BodyTop1.func_78793_a(0.0F, -84.0F, 0.0F);
    this.BodyTop1.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Cavity = new ModelRenderer(this, 0, 0);
    this.Cavity.func_78793_a(0.0F, -8.0F, 0.0F);
    this.Cavity.func_78790_a(-32.0F, -32.0F, -32.0F, 64, 64, 64, 0.0F);
    this.Body3 = new ModelRenderer(this, 0, 128);
    this.Body3.func_78793_a(0.0F, 0.0F, -32.0F);
    this.Body3.func_78790_a(-64.0F, -100.0F, -64.0F, 64, 240, 64, 0.0F);
    this.BodyTop8 = new ModelRenderer(this, 0, 0);
    this.BodyTop8.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop8.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyRight1 = new ModelRenderer(this, 0, 0);
    this.BodyRight1.func_78793_a(-100.0F, -128.0F, 0.0F);
    this.BodyRight1.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyRight1, 0.0F, 0.0F, -0.87266463F);
    this.BodyTop15 = new ModelRenderer(this, 0, 0);
    this.BodyTop15.func_78793_a(48.0F, -44.0F, 0.0F);
    this.BodyTop15.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop15, 0.0F, 0.0F, 0.5235988F);
    this.Body12 = new ModelRenderer(this, 0, 128);
    this.Body12.func_78793_a(48.0F, -24.0F, 0.0F);
    this.Body12.func_78790_a(0.0F, 0.0F, -64.0F, 64, 240, 64, 0.0F);
    this.BodyTop2 = new ModelRenderer(this, 0, 0);
    this.BodyTop2.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop2.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Bottom2 = new ModelRenderer(this, 256, 0);
    this.Bottom2.func_78793_a(0.0F, 200.0F, 0.0F);
    this.Bottom2.func_78790_a(-32.0F, 148.0F, -32.0F, 32, 128, 32, 0.0F);
    this.BodyTop12 = new ModelRenderer(this, 0, 0);
    this.BodyTop12.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop12.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyTop9 = new ModelRenderer(this, 0, 0);
    this.BodyTop9.func_78793_a(48.0F, -44.0F, 0.0F);
    this.BodyTop9.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop9, 0.0F, 0.0F, 0.5235988F);
    this.BodyTop11 = new ModelRenderer(this, 0, 0);
    this.BodyTop11.func_78793_a(-48.0F, -44.0F, 0.0F);
    this.BodyTop11.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop11, 0.0F, 0.0F, -0.5235988F);
    this.Body11 = new ModelRenderer(this, 0, 128);
    this.Body11.func_78793_a(48.0F, -24.0F, -32.0F);
    this.Body11.func_78790_a(-64.0F, 0.0F, -64.0F, 64, 240, 64, 0.0F);
    this.BodyLeft5 = new ModelRenderer(this, 0, 0);
    this.BodyLeft5.func_78793_a(196.0F, -164.0F, 0.0F);
    this.BodyLeft5.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyLeft5, 0.0F, 0.0F, 0.5235988F);
    this.BodyLeft6 = new ModelRenderer(this, 0, 0);
    this.BodyLeft6.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyLeft6.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Bottom1 = new ModelRenderer(this, 256, 0);
    this.Bottom1.func_78793_a(0.0F, 250.0F, 0.0F);
    this.Bottom1.func_78790_a(-24.0F, -100.0F, -24.0F, 48, 256, 48, 0.0F);
    this.BodyTop14 = new ModelRenderer(this, 0, 0);
    this.BodyTop14.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop14.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Body2 = new ModelRenderer(this, 0, 128);
    this.Body2.func_78793_a(-24.0F, 0.0F, 32.0F);
    this.Body2.func_78790_a(-32.0F, -100.0F, 0.0F, 64, 256, 64, 0.0F);
    this.BodyLeft3 = new ModelRenderer(this, 0, 0);
    this.BodyLeft3.func_78793_a(196.0F, -164.0F, 0.0F);
    this.BodyLeft3.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyLeft3, 0.0F, 0.0F, 1.3089969F);
    this.BodyTop6 = new ModelRenderer(this, 0, 0);
    this.BodyTop6.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyTop6.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.BodyLeft2 = new ModelRenderer(this, 0, 0);
    this.BodyLeft2.func_78793_a(0.0F, -112.0F, 0.0F);
    this.BodyLeft2.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    this.Bottom3 = new ModelRenderer(this, 256, 0);
    this.Bottom3.func_78793_a(0.0F, 200.0F, 0.0F);
    this.Bottom3.func_78790_a(0.0F, 96.0F, 0.0F, 32, 256, 32, 0.0F);
    this.BodyTop7 = new ModelRenderer(this, 0, 0);
    this.BodyTop7.func_78793_a(0.0F, -128.0F, 32.0F);
    this.BodyTop7.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyTop7, -0.6981317F, 0.0F, 0.0F);
    this.BodyRight5 = new ModelRenderer(this, 0, 0);
    this.BodyRight5.func_78793_a(-196.0F, -164.0F, 0.0F);
    this.BodyRight5.func_78790_a(-64.0F, -64.0F, -64.0F, 128, 128, 128, 0.0F);
    setRotateAngle(this.BodyRight5, 0.0F, 0.0F, -0.5235988F);
    this.BodyTop1.func_78792_a(this.BodyTop3);
    this.BodyRight5.func_78792_a(this.BodyRight6);
    this.BodyTop13.func_78792_a(this.BodyTop17);
    this.BodyTop17.func_78792_a(this.BodyTop18);
    this.BodyLeft3.func_78792_a(this.BodyLeft4);
    this.BodyTop3.func_78792_a(this.BodyTop4);
    this.BodyTop15.func_78792_a(this.BodyTop16);
    this.BodyRight3.func_78792_a(this.BodyRight4);
    this.BodyTop9.func_78792_a(this.BodyTop10);
    this.BodyTop1.func_78792_a(this.BodyTop5);
    this.BodyRight1.func_78792_a(this.BodyRight2);
    this.BodyTop7.func_78792_a(this.BodyTop8);
    this.BodyTop13.func_78792_a(this.BodyTop15);
    this.BodyTop1.func_78792_a(this.BodyTop2);
    this.BodyTop11.func_78792_a(this.BodyTop12);
    this.BodyTop7.func_78792_a(this.BodyTop9);
    this.BodyTop7.func_78792_a(this.BodyTop11);
    this.BodyLeft5.func_78792_a(this.BodyLeft6);
    this.BodyTop13.func_78792_a(this.BodyTop14);
    this.BodyTop5.func_78792_a(this.BodyTop6);
    this.BodyLeft1.func_78792_a(this.BodyLeft2);
  }
  
  public void func_78088_a(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
    this.Body7.func_78785_a(f5);
    this.BodyTop13.func_78785_a(f5);
    this.BodyLeft1.func_78785_a(f5);
    this.CommandBlock.func_78785_a(f5);
    this.Body1.func_78785_a(f5);
    this.Body6.func_78785_a(f5);
    this.Body4.func_78785_a(f5);
    this.Body5.func_78785_a(f5);
    this.BodyRight3.func_78785_a(f5);
    this.Body9.func_78785_a(f5);
    this.Body10.func_78785_a(f5);
    this.BodyTop1.func_78785_a(f5);
    this.Cavity.func_78785_a(f5);
    this.Body3.func_78785_a(f5);
    this.BodyRight1.func_78785_a(f5);
    this.Body12.func_78785_a(f5);
    this.Bottom2.func_78785_a(f5);
    this.Body11.func_78785_a(f5);
    this.BodyLeft5.func_78785_a(f5);
    this.Bottom1.func_78785_a(f5);
    this.Body2.func_78785_a(f5);
    this.BodyLeft3.func_78785_a(f5);
    this.Bottom3.func_78785_a(f5);
    this.BodyTop7.func_78785_a(f5);
    this.BodyRight5.func_78785_a(f5);
  }
  
  public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z) {
    modelRenderer.field_78795_f = x;
    modelRenderer.field_78796_g = y;
    modelRenderer.field_78808_h = z;
  }
}
