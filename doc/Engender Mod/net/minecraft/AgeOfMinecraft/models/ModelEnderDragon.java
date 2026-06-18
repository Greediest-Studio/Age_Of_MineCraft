package net.minecraft.AgeOfMinecraft.models;

import net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelEnderDragon extends ModelBase {
  public ModelRenderer head;
  
  private ModelRenderer spine;
  
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
  
  public ModelEnderDragon(float p_i46360_1_) {
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
    float f = -16.0F;
    this.head = new ModelRenderer(this, "head");
    this.head.func_78786_a("upperlip", -6.0F, -1.0F, -8.0F + f, 12, 5, 16);
    this.head.func_78786_a("upperhead", -8.0F, -8.0F, 6.0F + f, 16, 16, 16);
    this.head.field_78809_i = true;
    this.head.func_78786_a("scale", -5.0F, -12.0F, 12.0F + f, 2, 4, 6);
    this.head.func_78786_a("nostril", -5.0F, -3.0F, -6.0F + f, 2, 2, 4);
    this.head.field_78809_i = false;
    this.head.func_78786_a("scale", 3.0F, -12.0F, 12.0F + f, 2, 4, 6);
    this.head.func_78786_a("nostril", 3.0F, -3.0F, -6.0F + f, 2, 2, 4);
    this.jaw = new ModelRenderer(this, "jaw");
    this.jaw.func_78793_a(0.0F, 4.0F, 8.0F + f);
    this.jaw.func_78786_a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
    this.head.func_78792_a(this.jaw);
    this.spine = new ModelRenderer(this, "neck");
    this.spine.func_78786_a("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
    this.spine.func_78786_a("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
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
  
  public void func_78086_a(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
    this.partialTicks = partialTickTime;
  }
  
  public void func_78088_a(Entity entityIn, float p_78088_2_, float limbSwing, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    GlStateManager.func_179094_E();
    EntityEnderDragon entitydragon = (EntityEnderDragon)entityIn;
    float Pitch = headPitch / 57.295776F;
    float f = entitydragon.prevAnimTime + (entitydragon.animTime - entitydragon.prevAnimTime) * this.partialTicks;
    this.jaw.field_78795_f = (float)(Math.sin((f * 6.2831855F)) + 1.0D) * 0.2F;
    if (entitydragon.getJukeboxToDanceTo() != null) {
      this.jaw.field_78795_f = 0.5F;
      headPitch = 15.0F;
    } 
    float f1 = (float)(Math.sin((f * 6.2831855F - 1.0F)) + 1.0D);
    f1 = (f1 * f1 + f1 * 2.0F) * 0.05F;
    GlStateManager.func_179109_b(0.0F, f1 - 2.0F, -3.0F);
    GlStateManager.func_179114_b(f1 * 2.0F, 1.0F, 0.0F, 0.0F);
    float f2 = -30.0F;
    float f4 = 0.0F;
    float f5 = 1.5F;
    double[] adouble = entitydragon.getMovementOffsets(6, this.partialTicks);
    float f6 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] - entitydragon.getMovementOffsets(10, this.partialTicks)[0]);
    float f7 = updateRotations(entitydragon.getMovementOffsets(5, this.partialTicks)[0] + (f6 / 2.0F));
    f2 += 2.0F;
    float f8 = f * 6.2831855F;
    f2 = 20.0F;
    float f3 = -12.0F;
    for (int i = 0; i < 5; i++) {
      Pitch = -headPitch * -0.0021816615F * (2 + i);
      double[] adouble1 = entitydragon.getMovementOffsets(5 - i, this.partialTicks);
      float f9 = (float)Math.cos((i * 0.45F + f8)) * 0.15F;
      this.spine.field_78796_g = updateRotations(adouble1[0] - adouble[0]) * 0.017453292F * f5;
      this.spine.field_78795_f = f9 + entitydragon.getHeadPartYOffset(i, adouble, adouble1) * 0.017453292F * f5 * 5.0F + Pitch;
      this.spine.field_78808_h = -updateRotations(adouble1[0] - f7) * 0.017453292F * f5;
      this.spine.field_78797_d = f2;
      this.spine.field_78798_e = f3;
      this.spine.field_78800_c = f4 + Pitch;
      f2 = (float)(f2 + Math.sin(this.spine.field_78795_f) * 10.0D);
      f3 = (float)(f3 - Math.cos(this.spine.field_78796_g) * Math.cos(this.spine.field_78795_f) * 10.0D);
      f4 = (float)(f4 - Math.sin(this.spine.field_78796_g) * Math.cos(this.spine.field_78795_f) * 10.0D);
      this.spine.func_78785_a(scale);
    } 
    Pitch = -headPitch * -0.017453292F;
    this.head.field_78797_d = f2;
    this.head.field_78798_e = f3;
    this.head.field_78800_c = f4 + Pitch;
    double[] adouble2 = entitydragon.getMovementOffsets(0, this.partialTicks);
    this.head.field_78796_g = updateRotations(adouble2[0] - adouble[0]) * 0.017453292F;
    this.head.field_78795_f = updateRotations(entitydragon.getHeadPartYOffset(6, adouble, adouble2)) * 0.017453292F * f5 * 5.0F + Pitch;
    this.head.field_78808_h = -updateRotations(adouble2[0] - f7) * 0.017453292F;
    this.head.func_78785_a(scale);
    if (entitydragon.getJukeboxToDanceTo() != null) {
      GlStateManager.func_179114_b(MathHelper.func_76134_b(f * 3.1415927F) * 30.0F, 0.0F, 0.0F, 1.0F);
      this.head.field_78808_h -= MathHelper.func_76134_b(f * 3.1415927F);
    } 
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 1.0F, 0.0F);
    GlStateManager.func_179114_b(-f6 * f5, 0.0F, 0.0F, 1.0F);
    GlStateManager.func_179109_b(0.0F, -1.0F, 0.0F);
    this.body.field_78808_h = 0.0F;
    this.body.func_78785_a(scale);
    for (int j = 0; j < 2; j++) {
      GlStateManager.func_179089_o();
      float f11 = f * 6.2831855F;
      this.wing.field_78795_f = (entitydragon.func_70093_af() ? 0.5F : 0.125F) - (float)Math.cos(f11) * (entitydragon.func_70093_af() ? 0.1F : ((entitydragon.getJukeboxToDanceTo() != null) ? 0.75F : 0.25F));
      this.wing.field_78796_g = 0.25F;
      this.wing.field_78808_h = (float)(Math.sin(f11) + 0.125D) * (entitydragon.func_70093_af() ? 0.3F : 0.8F);
      this.wingTip.field_78808_h = -((float)(Math.sin((f11 + 2.0F)) + 0.5D)) * (entitydragon.func_70093_af() ? 0.5F : ((entitydragon.getJukeboxToDanceTo() != null) ? 1.5F : 0.75F));
      this.rearLeg.field_78795_f = (entitydragon.func_70093_af() ? 0.5F : 1.0F) + f1 * 0.1F;
      this.rearLegTip.field_78795_f = (entitydragon.func_70093_af() ? 1.5F : 0.5F) + f1 * 0.1F;
      this.rearFoot.field_78795_f = 0.75F + f1 * 0.1F;
      this.rearLeg.field_78808_h = (entitydragon.getJukeboxToDanceTo() != null) ? ((j == 1) ? ((float)Math.cos((f * 3.1415927F + 1.0F)) * 0.75F) : -((float)Math.cos((f * 3.1415927F + 1.0F)) * 0.75F)) : 0.0F;
      this.rearLegTip.field_78808_h = (entitydragon.getJukeboxToDanceTo() != null) ? ((j == 1) ? ((float)Math.cos((f * 3.1415927F + 2.0F)) * 0.5F) : -((float)Math.cos((f * 3.1415927F + 2.0F)) * 0.5F)) : 0.0F;
      this.rearFoot.field_78808_h = (entitydragon.getJukeboxToDanceTo() != null) ? ((j == 1) ? ((float)Math.cos((f * 3.1415927F + 3.0F)) * 0.5F) : -((float)Math.cos((f * 3.1415927F + 3.0F)) * 0.5F)) : 0.0F;
      this.frontLeg.field_78808_h = (entitydragon.getJukeboxToDanceTo() != null) ? (1.0F - (float)Math.cos(f11)) : 0.0F;
      this.frontLeg.field_78795_f = (entitydragon.func_70093_af() ? -1.3F : 1.3F) + f1 * 0.1F - ((entitydragon.getJukeboxToDanceTo() != null) ? 2.0F : 0.0F);
      this.frontLegTip.field_78808_h = (entitydragon.getJukeboxToDanceTo() != null) ? -0.5F : 0.0F;
      this.frontLegTip.field_78795_f = (entitydragon.func_70093_af() ? 2.5F : -0.5F) - f1 * 0.1F;
      this.frontFoot.field_78795_f = 0.75F + f1 * 0.1F + ((entitydragon.getJukeboxToDanceTo() != null) ? 0.5F : 0.0F);
      this.wing.func_78785_a(scale);
      this.frontLeg.func_78785_a(scale);
      this.rearLeg.func_78785_a(scale);
      GlStateManager.func_179152_a(-1.0F, 1.0F, 1.0F);
      if (j == 0)
        GlStateManager.func_187407_a(GlStateManager.CullFace.FRONT); 
    } 
    GlStateManager.func_179121_F();
    GlStateManager.func_187407_a(GlStateManager.CullFace.BACK);
    GlStateManager.func_179129_p();
    float f10 = -((float)Math.sin((f * 6.2831855F))) * 0.0F;
    f8 = f * 6.2831855F;
    f2 = 10.0F;
    f3 = 60.0F;
    f4 = 0.0F;
    adouble = entitydragon.getMovementOffsets(11, this.partialTicks);
    for (int k = 0; k < 12; k++) {
      adouble2 = entitydragon.getMovementOffsets(12 + k, this.partialTicks);
      f10 = (float)(f10 + Math.sin((k * 0.45F + f8)) * 0.05000000074505806D);
      this.spine.field_78796_g = (updateRotations(adouble2[0] - adouble[0]) * f5 + 180.0F) * 0.017453292F + ((entitydragon.getJukeboxToDanceTo() != null) ? ((float)Math.cos((f * 6.2831855F - k * 0.25F)) * 0.75F) : 0.0F);
      this.spine.field_78795_f = f10 + (float)(adouble2[1] - adouble[1]) * 0.017453292F * f5 * 5.0F;
      this.spine.field_78808_h = updateRotations(adouble2[0] - f7) * 0.017453292F * f5;
      this.spine.field_78797_d = f2;
      this.spine.field_78798_e = f3;
      this.spine.field_78800_c = f4;
      f2 = (float)(f2 + Math.sin(this.spine.field_78795_f) * 10.0D);
      f3 = (float)(f3 - Math.cos(this.spine.field_78796_g) * Math.cos(this.spine.field_78795_f) * 10.0D);
      f4 = (float)(f4 - Math.sin(this.spine.field_78796_g) * Math.cos(this.spine.field_78795_f) * 10.0D);
      this.spine.func_78785_a(scale);
    } 
    GlStateManager.func_179121_F();
  }
  
  private float updateRotations(double p_78214_1_) {
    while (p_78214_1_ >= 180.0D)
      p_78214_1_ -= 360.0D; 
    while (p_78214_1_ < -180.0D)
      p_78214_1_ += 360.0D; 
    return (float)p_78214_1_;
  }
}
