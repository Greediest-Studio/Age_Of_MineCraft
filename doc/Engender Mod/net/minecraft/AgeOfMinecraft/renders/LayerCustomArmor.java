package net.minecraft.AgeOfMinecraft.renders;

import net.minecraft.client.model.ModelBase;
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
  
  protected void func_177177_a() {
    this.field_177189_c = (ModelBase)new ModelBiped(0.5F);
    this.field_177186_d = (ModelBase)new ModelBiped(1.0F);
  }
  
  protected void setModelSlotVisible(ModelBiped p_188359_1_, EntityEquipmentSlot slotIn) {
    setModelVisible(p_188359_1_);
    switch (slotIn) {
      case HEAD:
        p_188359_1_.field_78116_c.field_78806_j = true;
        p_188359_1_.field_178720_f.field_78806_j = true;
        break;
      case CHEST:
        p_188359_1_.field_78115_e.field_78806_j = true;
        p_188359_1_.field_178723_h.field_78806_j = true;
        p_188359_1_.field_178724_i.field_78806_j = true;
        break;
      case LEGS:
        p_188359_1_.field_78115_e.field_78806_j = true;
        p_188359_1_.field_178721_j.field_78806_j = true;
        p_188359_1_.field_178722_k.field_78806_j = true;
        break;
      case FEET:
        p_188359_1_.field_178721_j.field_78806_j = true;
        p_188359_1_.field_178722_k.field_78806_j = true;
        break;
    } 
  }
  
  protected void setModelVisible(ModelBiped model) {
    model.func_178719_a(false);
  }
  
  protected ModelBiped getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot, ModelBiped model) {
    return ForgeHooksClient.getArmorModel(entity, itemStack, slot, model);
  }
}
