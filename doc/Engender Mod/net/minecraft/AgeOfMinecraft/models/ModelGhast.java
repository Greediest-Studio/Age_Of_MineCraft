package net.minecraft.AgeOfMinecraft.models;

import java.util.Random;
import net.minecraft.AgeOfMinecraft.entity.tame.tier4.EntityGhast;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelGhast extends ModelBase {
  public ModelRenderer body;
  
  public ModelRenderer[] tentacles = new ModelRenderer[9];
  
  public ModelGhast() {
    this.body = new ModelRenderer(this, 0, 0);
    this.body.func_78789_a(-8.0F, -8.0F, -8.0F, 16, 16, 16);
    this.body.field_78797_d += 8.0F;
    Random random = new Random(1660L);
    for (int j = 0; j < this.tentacles.length; j++) {
      this.tentacles[j] = new ModelRenderer(this, 0, 0);
      float f = (((j % 3) - (j / 3 % 2) * 0.5F + 0.25F) / 2.0F * 2.0F - 1.0F) * 5.0F;
      float f1 = ((j / 3) / 2.0F * 2.0F - 1.0F) * 5.0F;
      int k = random.nextInt(7) + 8;
      this.tentacles[j].func_78789_a(-1.0F, 0.0F, -1.0F, 2, k, 2);
      (this.tentacles[j]).field_78800_c = f;
      (this.tentacles[j]).field_78798_e = f1;
      (this.tentacles[j]).field_78797_d = 15.0F;
      this.body.func_78792_a(this.tentacles[j]);
    } 
  }
  
  public void func_78087_a(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
    for (int i = 0; i < this.tentacles.length; i++) {
      (this.tentacles[i]).field_78808_h = 0.0F;
      (this.tentacles[i]).field_78797_d = 7.0F;
      (this.tentacles[i]).field_78795_f = 0.2F * MathHelper.func_76126_a((((EntityGhast)entityIn).func_175446_cd() ? 1.0F : ageInTicks) * 0.3F + i) + 0.4F;
    } 
    EntityGhast entity = (EntityGhast)entityIn;
    if (entity.getCurrentBook().func_190926_b())
      this.body.field_78796_g = netHeadYaw / 57.295776F; 
    this.body.field_78795_f = !entity.func_70089_S() ? 0.0F : (headPitch / 57.295776F);
    if (entity.getJukeboxToDanceTo() != null) {
      this.body.field_78795_f += MathHelper.func_76126_a(ageInTicks * 0.5F) * 0.25F;
      this.body.field_78796_g += MathHelper.func_76126_a(ageInTicks * 0.25F) * 0.5F;
      for (int k = 0; k < this.tentacles.length; k++)
        (this.tentacles[k]).field_78808_h += MathHelper.func_76126_a(k * 2.0F + (entity.func_175446_cd() ? 1.0F : ageInTicks) * 0.25F) * 0.5F; 
    } 
    if (!entity.getCurrentBook().func_190926_b()) {
      (this.tentacles[0]).field_78796_g = entity.bookSpread - 1.0F;
      (this.tentacles[2]).field_78796_g = -entity.bookSpread + 1.0F;
      (this.tentacles[0]).field_78808_h = 0.0F;
      (this.tentacles[2]).field_78808_h = 0.0F;
      (this.tentacles[0]).field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
      (this.tentacles[2]).field_78795_f = -1.5F + 0.1F + MathHelper.func_76126_a(entity.field_70173_aa * 0.1F) * 0.01F;
    } 
  }
  
  public void func_78088_a(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
    func_78087_a(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
    GlStateManager.func_179094_E();
    GlStateManager.func_179109_b(0.0F, 0.55F, 0.0F);
    this.body.func_78785_a(scale);
    GlStateManager.func_179121_F();
  }
}
