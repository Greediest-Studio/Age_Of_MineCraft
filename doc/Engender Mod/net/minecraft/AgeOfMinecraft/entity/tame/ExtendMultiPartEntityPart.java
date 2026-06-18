package net.minecraft.AgeOfMinecraft.entity.tame;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;

public class ExtendMultiPartEntityPart extends MultiPartEntityPart {
  public ExtendMultiPartEntityPart(IEntityMultiPart parent, String partName, float width, float height) {
    super(parent, partName, width, height);
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    return ((Entity)this.field_70259_a).func_184230_a(player, hand);
  }
  
  public boolean func_70097_a(DamageSource source, float amount) {
    return func_180431_b(source) ? false : this.field_70259_a.func_70965_a(this, source, amount);
  }
}
