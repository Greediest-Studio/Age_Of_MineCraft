package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMagmaCube extends EntitySlime {
  public EntityMagmaCube(World worldIn) {
    super(worldIn);
    this.field_70178_ae = true;
  }
  
  public String getDescName() {
    return "lava_slime";
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (func_70026_G()) {
      func_184185_a(SoundEvents.field_187658_bx, 1.0F, 1.0F);
      func_70097_a((new DamageSource("cooler")).func_76348_h().func_151518_m().func_76351_m(), 4.0F);
    } 
  }
  
  public int getNextLevelRequirement() {
    return 15 * getSlimeSize();
  }
  
  protected boolean isValidLightLevel() {
    return true;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return (getSlimeSize() >= 4) ? I18n.func_74838_a("entity.LavaSlimeHelpful.cmm4.name") : (isSmallSlime() ? I18n.func_74838_a("entity.LavaSlimeHelpful.cmm1.name") : I18n.func_74838_a("entity.LavaSlimeHelpful.cmm2.name"));
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return I18n.func_74838_a("entity." + s + ".name");
  }
  
  public boolean func_70058_J() {
    return (this.field_70170_p.func_72917_a(func_174813_aQ(), (Entity)this) && this.field_70170_p.func_184144_a((Entity)this, func_174813_aQ()).isEmpty() && !this.field_70170_p.func_72953_d(func_174813_aQ()));
  }
  
  public void setSlimeSize(int size) {
    super.setSlimeSize(size);
    func_110148_a(SharedMonsterAttributes.field_188791_g).func_111128_a((size * 2));
  }
  
  @SideOnly(Side.CLIENT)
  public int func_70070_b() {
    return 15728880;
  }
  
  public float func_70013_c() {
    return 1.0F;
  }
  
  protected EnumParticleTypes getParticleType() {
    return EnumParticleTypes.FLAME;
  }
  
  protected EntitySlime createInstance() {
    return new EntityMagmaCube(this.field_70170_p);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return isSmallSlime() ? LootTableList.field_186419_a : ELoot.ENTITIES_MAGMA_CUBE;
  }
  
  public boolean func_70027_ad() {
    return false;
  }
  
  protected int getJumpDelay() {
    return super.getJumpDelay() * 4;
  }
  
  protected void alterSquishAmount() {
    this.squishAmount *= 0.9F;
  }
  
  protected void func_70664_aZ() {
    func_184185_a(getJumpSound(), func_70599_aP(), ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.2F + 1.0F) / (EngenderConfig.mobs.useMobTalkerModels ? 1.35F : 1.1F));
    this.field_70181_x += (0.42F + getSlimeSize() * 0.1F);
    this.field_70160_al = true;
    ForgeHooks.onLivingJump((EntityLivingBase)this);
  }
  
  protected void func_180466_bG() {
    this.field_70181_x = (0.22F + getSlimeSize() * 0.05F);
    this.field_70160_al = true;
  }
  
  protected boolean canDamagePlayer() {
    return true;
  }
  
  protected int getAttackStrength() {
    return super.getAttackStrength() + 2;
  }
  
  protected SoundEvent getJumpSound() {
    return func_189101_db() ? SoundEvents.field_187894_fw : SoundEvents.field_187762_di;
  }
  
  protected boolean makesSoundOnLand() {
    return true;
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + ((getSlimeSize() > 6) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return func_189101_db() ? SoundEvents.field_187892_fv : SoundEvents.field_187760_dh;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + ((getSlimeSize() > 3) ? 0.15F : (0.6F - getSlimeSize() * 0.15F))); 
    return func_189101_db() ? SoundEvents.field_187890_fu : SoundEvents.field_187758_dg;
  }
  
  protected SoundEvent func_184709_cY() {
    return func_189101_db() ? SoundEvents.field_187894_fw : SoundEvents.field_187764_dj;
  }
  
  protected SoundEvent func_184710_cZ() {
    return func_189101_db() ? SoundEvents.field_187894_fw : SoundEvents.field_187762_di;
  }
}
