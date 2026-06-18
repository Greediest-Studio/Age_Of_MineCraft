package net.minecraft.AgeOfMinecraft.entity.tame.ai;

import net.minecraft.AgeOfMinecraft.entity.tame.tier2.EntityVillager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAITradePlayer extends EntityAIBase {
  private EntityVillager villager;
  
  public EntityAITradePlayer(EntityVillager p_i1658_1_) {
    this.villager = p_i1658_1_;
    func_75248_a(5);
  }
  
  public boolean func_75250_a() {
    if (!this.villager.func_70089_S())
      return false; 
    if (this.villager.func_70090_H())
      return false; 
    if (!this.villager.field_70122_E)
      return false; 
    if (this.villager.field_70133_I)
      return false; 
    EntityPlayer entityplayer = this.villager.func_70931_l_();
    return (entityplayer == null) ? false : ((this.villager.func_70068_e((Entity)entityplayer) > 16.0D) ? false : (entityplayer.field_71070_bA instanceof net.minecraft.inventory.Container));
  }
  
  public void func_75249_e() {
    this.villager.func_70661_as().func_75499_g();
  }
  
  public void func_75251_c() {
    this.villager.func_70932_a_((EntityPlayer)null);
  }
}
