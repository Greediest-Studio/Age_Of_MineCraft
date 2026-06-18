package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.model;

import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonMinion;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelDragonMinion extends ModelBase {
  private ModelRenderer head;
  
  private ModelRenderer neck;
  
  private ModelRenderer jaw;
  
  private ModelRenderer body;
  
  private ModelRenderer rearLeg;
  
  private ModelRenderer frontLeg;
  
  private ModelRenderer rearLegTip;
  
  private ModelRenderer frontLegTip;
  
  private ModelRenderer rearFoot;
  
  private ModelRenderer frontFoot;
  
  private ModelRenderer wing;
  
  private ModelRenderer wingTip;
  
  private float partialTicks;
  
  public ModelDragonMinion(float par1) {
    this.field_78090_t = 256;
    this.field_78089_u = 256;
    func_78085_a("body.body", 0, 0);
    func_78085_a("wing.skin", -56, 88);
    func_78085_a("wingtip.skin", -56, 144);
    func_78085_a("rearleg.main", 0, 0);
    func_78085_a("rearfoot.main", 112, 0);
    func_78085_a("rearlegtip.main", 196, 0);
    func_78085_a("head.upperhead", 112, 30);
    func_78085_a("wing.bone", 112, 88);
    func_78085_a("head.upperlip", 176, 44);
    func_78085_a("jaw.jaw", 176, 65);
    func_78085_a("frontleg.main", 112, 104);
    func_78085_a("wingtip.bone", 112, 136);
    func_78085_a("frontfoot.main", 144, 104);
    func_78085_a("neck.box", 192, 104);
    func_78085_a("frontlegtip.main", 226, 138);
    func_78085_a("body.scale", 220, 53);
    func_78085_a("head.scale", 0, 0);
    func_78085_a("neck.scale", 48, 0);
    func_78085_a("head.nostril", 112, 0);
    float f1 = -16.0F;
    this.head = new ModelRenderer(this, "head");
    this.head.func_78786_a("upperlip", -6.0F, -1.0F, -8.0F + f1, 12, 5, 16);
    this.head.func_78786_a("upperhead", -8.0F, -8.0F, 6.0F + f1, 16, 16, 16);
    this.head.field_78809_i = true;
    this.head.func_78786_a("scale", -5.0F, -12.0F, 12.0F + f1, 2, 4, 6);
    this.head.func_78786_a("nostril", -5.0F, -3.0F, -6.0F + f1, 2, 2, 4);
    this.head.field_78809_i = false;
    this.head.func_78786_a("scale", 3.0F, -12.0F, 12.0F + f1, 2, 4, 6);
    this.head.func_78786_a("nostril", 3.0F, -3.0F, -6.0F + f1, 2, 2, 4);
    this.jaw = new ModelRenderer(this, "jaw");
    this.jaw.func_78793_a(0.0F, 4.0F, 8.0F + f1);
    this.jaw.func_78786_a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
    this.head.func_78792_a(this.jaw);
    this.neck = new ModelRenderer(this, "neck");
    this.neck.func_78786_a("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
    this.neck.func_78786_a("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
    this.body = new ModelRenderer(this, "body");
    this.body.func_78793_a(0.0F, 4.0F, 8.0F);
    this.body.func_78786_a("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
    this.body.func_78786_a("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
    this.body.func_78786_a("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
    this.body.func_78786_a("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
    this.wing = new ModelRenderer(this, "wing");
    this.wing.func_78793_a(-12.0F, 5.0F, 2.0F);
    this.wing.func_78786_a("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
    this.wing.func_78786_a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
    this.wingTip = new ModelRenderer(this, "wingtip");
    this.wingTip.func_78793_a(-56.0F, 0.0F, 0.0F);
    this.wingTip.func_78786_a("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
    this.wingTip.func_78786_a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
    this.wing.func_78792_a(this.wingTip);
    this.frontLeg = new ModelRenderer(this, "frontleg");
    this.frontLeg.func_78793_a(-12.0F, 20.0F, 2.0F);
    this.frontLeg.func_78786_a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
    this.frontLegTip = new ModelRenderer(this, "frontlegtip");
    this.frontLegTip.func_78793_a(0.0F, 20.0F, -1.0F);
    this.frontLegTip.func_78786_a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
    this.frontLeg.func_78792_a(this.frontLegTip);
    this.frontFoot = new ModelRenderer(this, "frontfoot");
    this.frontFoot.func_78793_a(0.0F, 23.0F, 0.0F);
    this.frontFoot.func_78786_a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
    this.frontLegTip.func_78792_a(this.frontFoot);
    this.rearLeg = new ModelRenderer(this, "rearleg");
    this.rearLeg.func_78793_a(-16.0F, 16.0F, 42.0F);
    this.rearLeg.func_78786_a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
    this.rearLegTip = new ModelRenderer(this, "rearlegtip");
    this.rearLegTip.func_78793_a(0.0F, 32.0F, -4.0F);
    this.rearLegTip.func_78786_a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
    this.rearLeg.func_78792_a(this.rearLegTip);
    this.rearFoot = new ModelRenderer(this, "rearfoot");
    this.rearFoot.func_78793_a(0.0F, 31.0F, 4.0F);
    this.rearFoot.func_78786_a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
    this.rearLegTip.func_78792_a(this.rearFoot);
  }
  
  public void func_78086_a(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4) {
    this.partialTicks = par4;
  }
  
  public void func_78088_a(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
    GlStateManager.func_179094_E();
    EntityDragonMinion entitydragon = (EntityDragonMinion)par1Entity;
    float f6 = entitydragon.prevAnimTime + (entitydragon.animTime - entitydragon.prevAnimTime) * this.partialTicks;
    this.jaw.field_78795_f = (float)(Math.sin((f6 * 3.1415927F * 2.0F)) + 1.0D) * 0.2F;
    float f7 = (float)(Math.sin((f6 * 3.1415927F * 2.0F - 1.0F)) + 1.0D);
    f7 = (f7 * f7 * 1.0F + f7 * 2.0F) * 0.05F;
    GlStateManager.func_179109_b(0.0F, f7 - 2.0F, -3.0F);
    GlStateManager.func_179114_b(f7 * 2.0F, 1.0F, 0.0F, 0.0F);
    float f8 = -30.0F;
    float f9 = 0.0F;
    float f10 = 1.5F;
    double[] adouble = entitydragon.getMovementOffsets(6, this.partialTicks);
    float f11 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] - entitydragon.getMovementOffsets(10, this.partialTicks)[0]);
    float f12 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] + (f11 / 2.0F));
    f8 += 2.0F;
    float f13 = f6 * 3.1415927F * 2.0F;
    f8 = 20.0F;
    float f14 = -12.0F;
    for (int i = 0; i < 5; i++) {
      double[] adouble1 = entitydragon.getMovementOffsets(5 - i, this.partialTicks);
      float f15 = (float)Math.cos((i * 0.45F + f13)) * 0.15F;
      this.neck.field_78796_g = updateRotations(adouble1[0] - adouble[0]) * 3.1415927F / 180.0F * f10;
      this.neck.field_78795_f = f15 + (float)(adouble1[1] - adouble[1]) * 3.1415927F / 180.0F * f10 * 5.0F;
      this.neck.field_78808_h = -updateRotations(adouble1[0] - f12) * 3.1415927F / 180.0F * f10;
      this.neck.field_78797_d = f8;
      this.neck.field_78798_e = f14;
      this.neck.field_78800_c = f9;
      f8 = (float)(f8 + Math.sin(this.neck.field_78795_f) * 10.0D);
      f14 = (float)(f14 - Math.cos(this.neck.field_78796_g) * Math.cos(this.neck.field_78795_f) * 10.0D);
      f9 = (float)(f9 - Math.sin(this.neck.field_78796_g) * Math.cos(this.neck.field_78795_f) * 10.0D);
      this.neck.func_78785_a(par7);
    } 
    this.head.field_78797_d = f8;
    this.head.field_78798_e = f14;
    this.head.field_78800_c = f9;
    double[] adouble2 = entitydragon.getMovementOffsets(0, this.partialTicks);
    this.head.field_78796_g = updateRotations(adouble2[0] - adouble[0]) * 3.1415927F / 180.0F * 1.0F;
    this.head.field_78808_h = -updateRotations(adouble2[0] - f12) * 3.1415927F / 180.0F * 1.0F;
    this.head.func_78785_a(par7);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(-f11 * f10 * 1.0F, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F);
    this.body.field_78808_h = 0.0F;
    this.body.func_78785_a(par7);
    for (int j = 0; j < 2; j++) {
      GlStateManager.func_179089_o();
      float f15 = f6 * 3.1415927F * 2.0F;
      this.wing.field_78795_f = 0.125F - (float)Math.cos(f15) * 0.2F;
      this.wing.field_78796_g = 0.25F;
      this.wing.field_78808_h = (float)(Math.sin(f15) + 0.125D) * 0.8F;
      this.wingTip.field_78808_h = -((float)(Math.sin((f15 + 2.0F)) + 0.5D)) * 0.75F;
      this.rearLeg.field_78795_f = 1.0F + f7 * 0.1F;
      this.rearLegTip.field_78795_f = 0.5F + f7 * 0.1F;
      this.rearFoot.field_78795_f = 0.75F + f7 * 0.1F;
      this.frontLeg.field_78795_f = 1.3F + f7 * 0.1F;
      this.frontLegTip.field_78795_f = -0.5F - f7 * 0.1F;
      this.frontFoot.field_78795_f = 0.75F + f7 * 0.1F;
      this.wing.func_78785_a(par7);
      this.frontLeg.func_78785_a(par7);
      this.rearLeg.func_78785_a(par7);
      GlStateManager.func_179152_a(-1.0F, 1.0F, 1.0F);
      if (j == 0)
        GlStateManager.func_187407_a(GlStateManager.CullFace.FRONT); 
    } 
    GlStateManager.func_179121_F();
    GlStateManager.func_187407_a(GlStateManager.CullFace.BACK);
    GlStateManager.func_179129_p();
    float f16 = -((float)Math.sin((f6 * 3.1415927F * 2.0F))) * 0.0F;
    f13 = f6 * 3.1415927F * 2.0F;
    f8 = 10.0F;
    f14 = 60.0F;
    f9 = 0.0F;
    adouble = entitydragon.getMovementOffsets(11, this.partialTicks);
    for (int k = 0; k < 12; k++) {
      adouble2 = entitydragon.getMovementOffsets(12 + k, this.partialTicks);
      f16 = (float)(f16 + Math.sin((k * 0.45F + f13)) * 0.05000000074505806D);
      this.neck.field_78796_g = (updateRotations(adouble2[0] - adouble[0]) * f10 + 180.0F) * 3.1415927F / 180.0F;
      this.neck.field_78795_f = f16 + (float)(adouble2[1] - adouble[1]) * 3.1415927F / 180.0F * f10 * 5.0F;
      this.neck.field_78808_h = updateRotations(adouble2[0] - f12) * 3.1415927F / 180.0F * f10;
      this.neck.field_78797_d = f8;
      this.neck.field_78798_e = f14;
      this.neck.field_78800_c = f9;
      f8 = (float)(f8 + Math.sin(this.neck.field_78795_f) * 10.0D);
      f14 = (float)(f14 - Math.cos(this.neck.field_78796_g) * Math.cos(this.neck.field_78795_f) * 10.0D);
      f9 = (float)(f9 - Math.sin(this.neck.field_78796_g) * Math.cos(this.neck.field_78795_f) * 10.0D);
      this.neck.func_78785_a(par7);
    } 
    GlStateManager.func_179121_F();
  }
  
  private float updateRotations(double par1) {
    while (par1 >= 180.0D)
      par1 -= 360.0D; 
    while (par1 < -180.0D)
      par1 += 360.0D; 
    return (float)par1;
  }
}
