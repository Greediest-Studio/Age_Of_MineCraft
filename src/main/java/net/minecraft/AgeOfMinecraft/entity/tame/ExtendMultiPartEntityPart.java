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
  
  public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
    return ((Entity)this.parent).processInitialInteract(player, hand);
  }
  
  public boolean attackEntityFrom(DamageSource source, float amount) {
    return isEntityInvulnerable(source) ? false : this.parent.attackEntityFromPart(this, source, amount);
  }
}
