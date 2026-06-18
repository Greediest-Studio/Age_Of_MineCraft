package net.minecraft.AgeOfMinecraft.entity.tame.tier3;

import com.google.common.base.Predicate;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.addons.mutantbeasts.entity.EntityMutantCreeper;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAICreeperSwell;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFollowLeader;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIFriendlyAttackMelee;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.AgeOfMinecraft.registry.ESound;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCreeper extends EntityTameBase implements Light {
  private static final DataParameter<Integer> STATE = EntityDataManager.func_187226_a(EntityCreeper.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> POWERED = EntityDataManager.func_187226_a(EntityCreeper.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> IGNITED = EntityDataManager.func_187226_a(EntityCreeper.class, DataSerializers.field_187198_h);
  
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  private int fuseTime = 30;
  
  private int explosionRadius = 3;
  
  public EntityCreeper(World worldIn) {
    super(worldIn);
    this.isOffensive = true;
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)this, EntityOcelot.class, new Predicate<EntityOcelot>() {
            public boolean apply(EntityOcelot p_apply_1_) {
              return (p_apply_1_ != null && p_apply_1_.func_70089_S() && EntityCreeper.this.getIntelligence() < 10.0F);
            }
          },  6.0F, 1.0D, 1.2D));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)this, EntityOcelot.class, new Predicate<EntityOcelot>() {
            public boolean apply(EntityOcelot p_apply_1_) {
              return (p_apply_1_ != null && p_apply_1_.func_70089_S() && EntityCreeper.this.getIntelligence() < 10.0F);
            }
          },  6.0F, 1.0D, 1.2D));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIOpenDoor((EntityLiving)this, true));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAICreeperSwell(this));
    this.field_70714_bg.func_75776_a(2, (EntityAIBase)new EntityAIFollowLeader(this, 1.0D, 32.0F, 6.0F));
    this.field_70714_bg.func_75776_a(3, (EntityAIBase)new EntityAIFriendlyAttackMelee(this, 1.2D, true));
    this.field_70714_bg.func_75776_a(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 0.8D, 80));
    this.field_70714_bg.func_75776_a(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
    if (EngenderConfig.mobs.useMobTalkerModels) {
      func_70105_a(0.45F, 1.75F);
    } else {
      func_70105_a(0.5F, 1.625F);
    } 
    this.field_70728_aV = 5;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return new EntityCreeper(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.25D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(2.0D);
  }
  
  public String getDescName() {
    return getPowered() ? "charged_creeper" : "creeper";
  }
  
  public int getNextLevelRequirement() {
    return 25;
  }
  
  protected float func_70647_i() {
    return EngenderConfig.mobs.useMobTalkerModels ? (super.func_70647_i() + (getPowered() ? 0.1F : 0.2F)) : super.func_70647_i();
  }
  
  public float func_70047_e() {
    return EngenderConfig.mobs.useMobTalkerModels ? (getPowered() ? (this.field_70131_O * 0.94F) : (this.field_70131_O * 0.83F)) : super.func_70047_e();
  }
  
  public int timesToConvert() {
    return 13;
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER3;
  }
  
  public void createChild() {
    super.createChild();
    if (!this.field_70170_p.field_72995_K)
      for (int i = 0; i < 1 + this.field_70146_Z.nextInt(2); i++) {
        EntityCreeper baby = new EntityCreeper(this.field_70170_p);
        baby.func_82149_j((Entity)this);
        baby.func_180482_a(this.field_70170_p.func_175649_E(func_180425_c()), null);
        baby.setGrowingAge(-36000);
        baby.setChild(true);
        baby.setIsAntiMob(isAntiMob());
        baby.setIsHero(isHero());
        baby.setOwnerId(func_184753_b());
        if (getPowered())
          this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, baby.field_70165_t - 0.5D, baby.field_70163_u + 1.0D, baby.field_70161_v - 0.5D, false)); 
        if (isMarried())
          for (int e = 0; e < 10 + this.field_70146_Z.nextInt(10); e++)
            baby.levelUp();  
        this.field_70170_p.func_72838_d((Entity)baby);
      }  
  }
  
  public EntityTameBase getMutant() {
    if (Loader.isModLoaded("mutantbeasts"))
      return (EntityTameBase)new EntityMutantCreeper(this.field_70170_p); 
    return null;
  }
  
  public float getBonusVSLight() {
    return 1.1F;
  }
  
  public float getBonusVSArmored() {
    return 1.5F;
  }
  
  public float getBonusVSMassive() {
    return 0.75F;
  }
  
  public String func_70005_c_() {
    if (func_145818_k_())
      return func_95999_t(); 
    if (EngenderConfig.mobs.useMobTalkerModels) {
      String str = EntityList.func_75621_b((Entity)this);
      if (str == null)
        str = "generic"; 
      return getPowered() ? I18n.func_74838_a("entity.ChargedCreeperHelpful.cmm.name") : I18n.func_74838_a("entity." + str + ".cmm.name");
    } 
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return getPowered() ? I18n.func_74838_a("entity.ChargedCreeperHelpful.name") : I18n.func_74838_a("entity." + s + ".name");
  }
  
  public int func_82143_as() {
    return (func_70638_az() == null) ? 3 : (3 + (int)(func_110143_aJ() - 1.0F));
  }
  
  public void func_180430_e(float distance, float damageMultiplier) {
    super.func_180430_e(distance, damageMultiplier);
    this.timeSinceIgnited = (int)(this.timeSinceIgnited + distance * 1.5F);
    if (this.timeSinceIgnited > this.fuseTime - 5)
      this.timeSinceIgnited = this.fuseTime - 5; 
  }
  
  protected void func_70088_a() {
    super.func_70088_a();
    this.field_70180_af.func_187214_a(STATE, Integer.valueOf(-1));
    this.field_70180_af.func_187214_a(POWERED, Boolean.valueOf(false));
    this.field_70180_af.func_187214_a(IGNITED, Boolean.valueOf(false));
  }
  
  public void func_70014_b(NBTTagCompound tagCompound) {
    super.func_70014_b(tagCompound);
    if (((Boolean)this.field_70180_af.func_187225_a(POWERED)).booleanValue())
      tagCompound.func_74757_a("powered", true); 
    tagCompound.func_74777_a("Fuse", (short)this.fuseTime);
    tagCompound.func_74774_a("ExplosionRadius", (byte)this.explosionRadius);
    tagCompound.func_74757_a("ignited", hasIgnited());
  }
  
  public void func_70037_a(NBTTagCompound tagCompund) {
    super.func_70037_a(tagCompund);
    this.field_70180_af.func_187227_b(POWERED, Boolean.valueOf(tagCompund.func_74767_n("powered")));
    if (tagCompund.func_150297_b("Fuse", 99))
      this.fuseTime = tagCompund.func_74765_d("Fuse"); 
    if (tagCompund.func_150297_b("ExplosionRadius", 99))
      this.explosionRadius = tagCompund.func_74771_c("ExplosionRadius"); 
    if (tagCompund.func_74767_n("ignited"))
      ignite(); 
  }
  
  public void func_70071_h_() {
    ItemStack charge = getPowered() ? new ItemStack(Items.field_151156_bN) : ItemStack.field_190927_a;
    charge.func_151001_c("Is Charged");
    this.basicInventory.func_70299_a(7, charge);
    if (func_70089_S()) {
      this.lastActiveTime = this.timeSinceIgnited;
      if (hasIgnited())
        setCreeperState(1); 
      int i = getCreeperState();
      if (i > 0 && this.timeSinceIgnited == 0)
        func_184185_a(SoundEvents.field_187572_ar, 1.0F, 0.5F); 
      this.timeSinceIgnited += i;
      if (this.timeSinceIgnited < 0)
        this.timeSinceIgnited = 0; 
      if (this.timeSinceIgnited >= this.fuseTime) {
        this.timeSinceIgnited = this.fuseTime;
        explode();
      } 
    } 
    if (this.field_70725_aQ == 1)
      if (EngenderConfig.mobs.useMobTalkerModels && getPowered()) {
        func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i() + 0.2F);
        func_184185_a(func_184615_bR(), func_70599_aP(), func_70647_i() - 0.2F);
      }  
    super.func_70071_h_();
  }
  
  protected SoundEvent func_184601_bQ(DamageSource source) {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlHurt, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187570_aq;
  }
  
  protected SoundEvent func_184615_bR() {
    if (EngenderConfig.mobs.useMobTalkerModels)
      func_184185_a(ESound.girlDeath, func_70599_aP(), func_70647_i() + 0.1F); 
    return SoundEvents.field_187568_ap;
  }
  
  public void func_70645_a(DamageSource cause) {
    super.func_70645_a(cause);
    if (this.field_70170_p.func_82736_K().func_82766_b("doMobLoot"))
      if (cause.func_76346_g() instanceof net.minecraft.entity.monster.AbstractSkeleton) {
        int i = Item.func_150891_b(Items.field_151096_cd);
        int j = Item.func_150891_b(Items.field_151084_co);
        int k = i + this.field_70146_Z.nextInt(j - i + 1);
        func_145779_a(Item.func_150899_d(k), 1);
      } else if (cause.func_76346_g() instanceof EntityCreeper && cause.func_76346_g() != this && ((EntityCreeper)cause.func_76346_g()).getPowered()) {
        func_70099_a(new ItemStack(Items.field_151144_bL, 1, 4), 0.0F);
      }  
  }
  
  public float func_180428_a(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    return blockStateIn.func_177230_c().getExplosionResistance(worldIn, pos, (Entity)this, explosionIn) * 0.5F;
  }
  
  public void inflictEngenderMobDamage(EntityLivingBase entity, String killmessage, DamageSource attacktype, float damage) {
    if (getPowered()) {
      this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
      entity.func_70077_a(new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
    } 
    super.inflictEngenderMobDamage(entity, killmessage, attacktype, damage);
  }
  
  public boolean func_70652_k(Entity entity) {
    if (super.func_70652_k(entity)) {
      if (getPowered()) {
        this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
        entity.func_70077_a(new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
      } 
      return true;
    } 
    return false;
  }
  
  public boolean getPowered() {
    return ((Boolean)this.field_70180_af.func_187225_a(POWERED)).booleanValue();
  }
  
  public void setPowered(boolean powered) {
    if (powered)
      func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(7.0D); 
    this.field_70180_af.func_187227_b(POWERED, Boolean.valueOf(powered));
  }
  
  @SideOnly(Side.CLIENT)
  public float getCreeperFlashIntensity(float p_70831_1_) {
    return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
  }
  
  @Nullable
  protected ResourceLocation func_184647_J() {
    return ELoot.ENTITIES_CREEPER;
  }
  
  public int getCreeperState() {
    return ((Integer)this.field_70180_af.func_187225_a(STATE)).intValue();
  }
  
  public void setCreeperState(int state) {
    this.field_70180_af.func_187227_b(STATE, Integer.valueOf(state));
  }
  
  public void func_70077_a(EntityLightningBolt lightningBolt) {
    setPowered(true);
  }
  
  public boolean interact(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    ItemStack heldItem = new ItemStack(stack.func_77973_b());
    if (!stack.func_190926_b() && stack.func_77973_b() == Items.field_151033_d) {
      this.field_70170_p.func_184148_a(player, this.field_70165_t, this.field_70163_u, this.field_70161_v, SoundEvents.field_187649_bu, func_184176_by(), 1.0F, this.field_70146_Z.nextFloat() * 0.4F + 0.8F);
      player.func_184609_a(hand);
      if (!this.field_70170_p.field_72995_K) {
        if (hasOwner(player)) {
          if (player.func_70093_af())
            ignite(); 
          if (!getPowered()) {
            if (this.field_70170_p.func_175710_j(func_180425_c())) {
              this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, this.field_70165_t - 0.5D, this.field_70163_u + 1.625D, this.field_70161_v - 0.5D, false));
            } else {
              func_70656_aK();
            } 
          } else {
            func_70691_i(1.0F);
          } 
        } else {
          ignite();
        } 
        stack.func_77972_a(1, (EntityLivingBase)player);
      } 
      return true;
    } 
    return false;
  }
  
  public void performSpecialAttack() {
    ignite();
    func_184185_a(SoundEvents.field_187941_ho, 10.0F, 1.0F);
  }
  
  private void explode() {
    if (!this.field_70170_p.field_72995_K) {
      boolean flag = EngenderConfig.mobs.grief;
      float f = getPowered() ? 2.0F : 1.0F;
      if (getSpecialAttackTimer() <= 0 && isHero()) {
        createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 20.0F * f, flag);
        setSpecialAttackTimer(800);
        this.field_70181_x = 0.0D;
      } else if (func_110143_aJ() > func_110138_aP() * getIntelligence() * 0.01F || isWild()) {
        createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, this.explosionRadius * f, flag);
        func_70106_y();
        Collection<PotionEffect> collection = func_70651_bq();
        if (!collection.isEmpty()) {
          EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.field_70170_p, this.field_70165_t, this.field_70163_u, this.field_70161_v);
          entityareaeffectcloud.func_184483_a(2.5F);
          entityareaeffectcloud.func_184495_b(-0.5F);
          entityareaeffectcloud.func_184485_d(10);
          entityareaeffectcloud.func_184486_b(entityareaeffectcloud.func_184489_o() / 2);
          entityareaeffectcloud.func_184487_c(-entityareaeffectcloud.func_184490_j() / entityareaeffectcloud.func_184489_o());
          for (PotionEffect potioneffect : collection)
            entityareaeffectcloud.func_184496_a(new PotionEffect(potioneffect)); 
          this.field_70170_p.func_72838_d((Entity)entityareaeffectcloud);
        } 
        if (!this.field_70170_p.field_72995_K && EngenderConfig.general.useMessage && !isWild() && getOwner() instanceof net.minecraft.entity.player.EntityPlayerMP)
          getOwner().func_145747_a((ITextComponent)new TextComponentTranslation("death.attack.self_destruct", new Object[] { func_145748_c_() })); 
      } 
      setCreeperState(-1);
      this.timeSinceIgnited = 0;
      this.field_70180_af.func_187227_b(IGNITED, Boolean.valueOf(false));
    } 
  }
  
  public void func_70636_d() {
    super.func_70636_d();
    if (getPowered())
      func_70066_B(); 
    if (func_70638_az() != null && !hasIgnited() && func_70068_e((Entity)func_70638_az()) < 128.0D && getSpecialAttackTimer() <= 0 && isHero())
      performSpecialAttack(); 
  }
  
  public boolean hasIgnited() {
    return ((Boolean)this.field_70180_af.func_187225_a(IGNITED)).booleanValue();
  }
  
  public void ignite() {
    this.field_70180_af.func_187227_b(IGNITED, Boolean.valueOf(true));
  }
}
