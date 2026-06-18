package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.MobEffects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityPrisonSlime extends EntitySlime {
  public EntityPrisonSlime(World worldIn) {
    super(worldIn);
    this.field_70178_ae = true;
  }
  
  public String getDescName() {
    return "prison_slime";
  }
  
  public void setSlimeSize(int size) {
    super.setSlimeSize(size);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a((size * 1));
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
  }
  
  protected EntitySlime createInstance() {
    return new EntityPrisonSlime(this.field_70170_p);
  }
  
  protected void func_180466_bG() {
    this.field_70181_x = (0.22F + getSlimeSize() * 0.05F);
    this.field_70160_al = true;
  }
  
  protected int getAttackStrength() {
    return super.getAttackStrength() + 1;
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return isSmallSlime() ? ELoot.ENTITIES_PRISON_SLIME : LootTableList.field_186419_a;
  }
  
  public boolean func_70652_k(Entity p_70652_1_) {
    if (super.func_70652_k(p_70652_1_)) {
      p_70652_1_.field_70159_w = 0.0D;
      p_70652_1_.field_70181_x = 0.0D;
      p_70652_1_.field_70179_y = 0.0D;
      if (p_70652_1_ instanceof EntityLivingBase) {
        inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_76421_d, 5, 1);
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.NORMAL || this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
          inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_76421_d, 5, 0); 
        if (this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD)
          inflictCustomStatusEffect(this.field_70170_p.func_175659_aa(), (EntityLivingBase)p_70652_1_, MobEffects.field_76421_d, 5, 0); 
        if (p_70652_1_ instanceof EntityLiving && ((EntityLiving)p_70652_1_).func_70638_az() != null && this.field_70170_p.func_175659_aa() == EnumDifficulty.HARD && this.field_70146_Z.nextInt(3) == 0)
          ((EntityLiving)p_70652_1_).func_70624_b(null); 
      } 
      return true;
    } 
    return false;
  }
}
