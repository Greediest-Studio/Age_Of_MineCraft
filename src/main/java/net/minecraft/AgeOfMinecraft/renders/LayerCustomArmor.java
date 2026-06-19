package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerCustomArmor extends LayerArmorBase<ModelBiped> {
  public LayerCustomArmor(RenderLivingBase<?> rendererIn) {
    super(rendererIn);
  }
  
  protected void initArmor() {
    RenderLayerCompat.setArmorLeggings(this, new ModelBiped(0.5F));
    RenderLayerCompat.setArmorBody(this, new ModelBiped(1.0F));
  }
  
  protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn) {
    setModelVisible(p_188359_1_);
    switch (slotIn) {
      case HEAD:
        p_188359_1_.bipedHead.showModel = true;
        p_188359_1_.bipedHeadwear.showModel = true;
        break;
      case CHEST:
        p_188359_1_.bipedBody.showModel = true;
        p_188359_1_.bipedRightArm.showModel = true;
        p_188359_1_.bipedLeftArm.showModel = true;
        break;
      case LEGS:
        p_188359_1_.bipedBody.showModel = true;
        p_188359_1_.bipedRightLeg.showModel = true;
        p_188359_1_.bipedLeftLeg.showModel = true;
        break;
      case FEET:
        p_188359_1_.bipedRightLeg.showModel = true;
        p_188359_1_.bipedLeftLeg.showModel = true;
        break;
    } 
  }
  
  protected void setModelVisible(ModelBiped model) {
    model.setVisible(false);
  }
  
  protected ModelBiped getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, ModelBiped model) {
    return ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
  }
}
