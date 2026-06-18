package net.minecraft.AgeOfMinecraft.blocks;

import java.util.List;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntityRenderBeaconSPC extends TileEntitySpecialRenderer<TileEntityBeaconSPC> {
  public static final ResourceLocation TEXTURE_BEACON_BEAM = new ResourceLocation("textures/entity/beacon_beam.png");
  
  public void render(TileEntityBeaconSPC te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    renderBeacon(x, y, z, partialTicks, te.shouldBeamRender(), te.getBeamSegments(), te.func_145831_w().func_82737_E());
  }
  
  public void renderBeacon(double x, double y, double z, double partialTicks, double textureScale, List<TileEntityBeaconSPC.BeamSegment> beamSegments, double totalWorldTime) {
    GlStateManager.func_179092_a(516, 0.1F);
    func_147499_a(TEXTURE_BEACON_BEAM);
    if (textureScale > 0.0D) {
      GlStateManager.func_179106_n();
      int i = 0;
      for (int j = 0; j < beamSegments.size(); j++) {
        TileEntityBeaconSPC.BeamSegment tileentitybeacon$beamsegment = beamSegments.get(j);
        renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, i, tileentitybeacon$beamsegment.getHeight(), tileentitybeacon$beamsegment.getColors());
        i += tileentitybeacon$beamsegment.getHeight();
      } 
      GlStateManager.func_179127_m();
    } 
  }
  
  public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors) {
    renderBeamSegment(x, y, z, partialTicks, textureScale, totalWorldTime, yOffset, height, colors, 0.2D, 0.25D);
  }
  
  public static void renderBeamSegment(double x, double y, double z, double partialTicks, double textureScale, double totalWorldTime, int yOffset, int height, float[] colors, double beamRadius, double glowRadius) {
    int i = yOffset + height;
    GlStateManager.func_187421_b(3553, 10242, 10497);
    GlStateManager.func_187421_b(3553, 10243, 10497);
    GlStateManager.func_179140_f();
    GlStateManager.func_179129_p();
    GlStateManager.func_179084_k();
    GlStateManager.func_179132_a(true);
    GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    Tessellator tessellator = Tessellator.func_178181_a();
    BufferBuilder bufferbuilder = tessellator.func_178180_c();
    double d0 = totalWorldTime + partialTicks;
    double d1 = (height < 0) ? d0 : -d0;
    double d2 = MathHelper.func_181162_h(d1 * 0.2D - MathHelper.func_76128_c(d1 * 0.1D));
    float f = colors[0];
    float f1 = colors[1];
    float f2 = colors[2];
    double d3 = d0 * 0.025D * -1.5D;
    double d4 = 0.5D + Math.cos(d3 + 2.356194490192345D) * beamRadius;
    double d5 = 0.5D + Math.sin(d3 + 2.356194490192345D) * beamRadius;
    double d6 = 0.5D + Math.cos(d3 + 0.7853981633974483D) * beamRadius;
    double d7 = 0.5D + Math.sin(d3 + 0.7853981633974483D) * beamRadius;
    double d8 = 0.5D + Math.cos(d3 + 3.9269908169872414D) * beamRadius;
    double d9 = 0.5D + Math.sin(d3 + 3.9269908169872414D) * beamRadius;
    double d10 = 0.5D + Math.cos(d3 + 5.497787143782138D) * beamRadius;
    double d11 = 0.5D + Math.sin(d3 + 5.497787143782138D) * beamRadius;
    double d12 = 0.0D;
    double d13 = 1.0D;
    double d14 = -1.0D + d2;
    double d15 = height * textureScale * 0.5D / beamRadius + d14;
    bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
    bufferbuilder.func_181662_b(x + d4, y + i, z + d5).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d4, y + yOffset, z + d5).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d6, y + yOffset, z + d7).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d6, y + i, z + d7).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d10, y + i, z + d11).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d10, y + yOffset, z + d11).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d8, y + yOffset, z + d9).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d8, y + i, z + d9).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d6, y + i, z + d7).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d6, y + yOffset, z + d7).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d10, y + yOffset, z + d11).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d10, y + i, z + d11).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d8, y + i, z + d9).func_187315_a(1.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d8, y + yOffset, z + d9).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d4, y + yOffset, z + d5).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    bufferbuilder.func_181662_b(x + d4, y + i, z + d5).func_187315_a(0.0D, d15).func_181666_a(f, f1, f2, 1.0F).func_181675_d();
    tessellator.func_78381_a();
    GlStateManager.func_179147_l();
    GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
    GlStateManager.func_179132_a(false);
    d3 = 0.5D - glowRadius;
    d4 = 0.5D - glowRadius;
    d5 = 0.5D + glowRadius;
    d6 = 0.5D - glowRadius;
    d7 = 0.5D - glowRadius;
    d8 = 0.5D + glowRadius;
    d9 = 0.5D + glowRadius;
    d10 = 0.5D + glowRadius;
    d11 = 0.0D;
    d12 = 1.0D;
    d13 = -1.0D + d2;
    d14 = height * textureScale + d13;
    bufferbuilder.func_181668_a(7, DefaultVertexFormats.field_181709_i);
    bufferbuilder.func_181662_b(x + d3, y + i, z + d4).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d3, y + yOffset, z + d4).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d5, y + yOffset, z + d6).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d5, y + i, z + d6).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d9, y + i, z + d10).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d9, y + yOffset, z + d10).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d7, y + yOffset, z + d8).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d7, y + i, z + d8).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d5, y + i, z + d6).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d5, y + yOffset, z + d6).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d9, y + yOffset, z + d10).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d9, y + i, z + d10).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d7, y + i, z + d8).func_187315_a(1.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d7, y + yOffset, z + d8).func_187315_a(1.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d3, y + yOffset, z + d4).func_187315_a(0.0D, d13).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    bufferbuilder.func_181662_b(x + d3, y + i, z + d4).func_187315_a(0.0D, d14).func_181666_a(f, f1, f2, 0.125F).func_181675_d();
    tessellator.func_78381_a();
    GlStateManager.func_179145_e();
    GlStateManager.func_179098_w();
    GlStateManager.func_179132_a(true);
  }
  
  public boolean isGlobalRenderer(TileEntityBeaconSPC te) {
    return true;
  }
}
