package net.minecraft.AgeOfMinecraft.entity.tame;

import com.shinoow.abyssalcraft.lib.util.SpecialTextUtil;
import net.minecraft.AgeOfMinecraft.registry.EItem;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class EntityItemCarrier extends EntityItem {
  public EntityItemCarrier(World worldIn) {
    super(worldIn);
  }
  
  public void func_70106_y() {
    func_184185_a(SoundEvents.field_187635_cQ, 0.8F, 0.8F + this.field_70170_p.field_73012_v.nextFloat() * 0.4F);
    ItemStack stack = func_92059_d();
    if (!stack.func_77942_o())
      stack.func_77982_d(new NBTTagCompound()); 
    if (stack.func_77973_b() == EItem.carrier)
      if (stack.func_77978_p().func_74764_b("Entity")) {
        Entity entity = EntityList.func_75615_a(stack.func_77978_p().func_74775_l("Entity"), this.field_70170_p);
        if (entity instanceof EntityTameBase) {
          EntityTameBase entityliving = (EntityTameBase)entity;
          if (!this.field_70170_p.field_72995_K && entityliving instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityJzahar)
            SpecialTextUtil.JzaharGroup(this.field_70170_p, new String[] { I18n.func_74838_a("message.jzahar.scold") }); 
          entityliving.func_189511_e(stack.func_77978_p().func_74775_l("Entity"));
          entityliving.func_70642_aH();
          if (!entityliving.isWild() && (entityliving instanceof net.minecraft.AgeOfMinecraft.entity.tame.tier5.EntityEnderDragon || entityliving instanceof net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss)) {
            entityliving.func_70012_b((entityliving.getOwner()).field_70165_t, (entityliving.getOwner()).field_70163_u + 4.0D, (entityliving.getOwner()).field_70161_v, entityliving.field_70177_z, entityliving.field_70125_A);
          } else {
            entityliving.func_70012_b(this.field_70165_t, this.field_70163_u, this.field_70161_v, entityliving.field_70177_z, entityliving.field_70125_A);
          } 
          entity.func_82149_j((Entity)this);
          entity.field_70170_p.func_72960_a(entity, (byte)20);
          entity.func_184185_a(SoundEvents.field_187539_bB, 1.0F, 2.0F);
          entity.func_184185_a(SoundEvents.field_187561_bM, 1.0F, 2.0F);
          entity.func_184185_a(ESound.createMob, 1.0F, 1.0F);
          entity.field_70170_p.func_72960_a(entity, (byte)35);
          stack.func_77978_p().func_82580_o("Entity");
          stack.func_77978_p().func_82580_o("EntityName");
          if (!this.field_70170_p.field_72995_K)
            this.field_70170_p.func_72838_d(entity); 
        } 
      }  
    super.func_70106_y();
  }
}
