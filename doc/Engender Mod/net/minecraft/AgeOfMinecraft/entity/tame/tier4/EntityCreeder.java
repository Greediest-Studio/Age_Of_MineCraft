package net.minecraft.AgeOfMinecraft.entity.tame.tier4;

import com.google.common.base.Predicate;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.EngenderConfig;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.AgeOfMinecraft.entity.tame.EnumTier;
import net.minecraft.AgeOfMinecraft.entity.tame.Light;
import net.minecraft.AgeOfMinecraft.entity.tame.ai.EntityAIAvoidEntitySPC;
import net.minecraft.AgeOfMinecraft.entity.tame.tier1.EntityOcelot;
import net.minecraft.AgeOfMinecraft.entity.tame.tier3.EntitySpider;
import net.minecraft.AgeOfMinecraft.registry.ELoot;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAreaEffectCloud;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.boss.EntityWither;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityCreeder extends EntitySpider implements Light {
  private static final DataParameter<Integer> STATE = EntityDataManager.func_187226_a(EntityCreeder.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> POWERED = EntityDataManager.func_187226_a(EntityCreeder.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Boolean> IGNITED = EntityDataManager.func_187226_a(EntityCreeder.class, DataSerializers.field_187198_h);
  
  private int lastActiveTime;
  
  private int timeSinceIgnited;
  
  private int fuseTime = 30;
  
  private int explosionRadius = 5;
  
  public EntityCreeder(World worldIn) {
    super(worldIn);
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)this, EntityOcelot.class, new Predicate<EntityOcelot>() {
            public boolean apply(EntityOcelot p_apply_1_) {
              return (p_apply_1_ != null && p_apply_1_.func_70089_S() && EntityCreeder.this.getIntelligence() < 10.0F);
            }
          },  6.0F, 1.0D, 1.2D));
    this.field_70714_bg.func_75776_a(0, (EntityAIBase)new EntityAIAvoidEntitySPC((EntityCreature)this, EntityOcelot.class, new Predicate<EntityOcelot>() {
            public boolean apply(EntityOcelot p_apply_1_) {
              return (p_apply_1_ != null && p_apply_1_.func_70089_S() && EntityCreeder.this.getIntelligence() < 10.0F);
            }
          },  6.0F, 1.0D, 1.2D));
    func_70105_a(0.75F, 1.75F);
    this.field_70728_aV = 10;
  }
  
  public int getNextLevelRequirement() {
    return 200;
  }
  
  public EntityTameBase spawnBaby(EntityTameBase par1idleTimeable) {
    return (EntityTameBase)new EntityCreeder(this.field_70170_p);
  }
  
  protected void func_110147_ax() {
    super.func_110147_ax();
    func_110148_a(SharedMonsterAttributes.field_111263_d).func_111128_a(0.33D);
    func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(4.0D);
    func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(20.0D);
  }
  
  public String getDescName() {
    return "creeder";
  }
  
  public EnumTier getTier() {
    return EnumTier.TIER4;
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
    String s = EntityList.func_75621_b((Entity)this);
    if (s == null)
      s = "generic"; 
    return getPowered() ? I18n.func_74838_a("entity.ChargedCreederHelpful.name") : I18n.func_74838_a("entity." + s + ".name");
  }
  
  public int func_82143_as() {
    return (func_70638_az() == null) ? 3 : (3 + (int)(func_110143_aJ() - 1.0F));
  }
  
  public boolean takesFallDamage() {
    return false;
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
    return SoundEvents.field_187570_aq;
  }
  
  protected SoundEvent func_184615_bR() {
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
      }  
  }
  
  public void inflictEngenderMobDamage(EntityLivingBase entity, String killmessage, DamageSource attacktype, float damage) {
    if (this.field_70170_p.func_82736_K().func_82766_b("doMobLoot"))
      if (!entity.func_70089_S() && getPowered() && (this.field_70146_Z.nextInt(20) == 0 || isHero())) {
        if (entity instanceof net.minecraft.entity.monster.EntitySkeleton)
          entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 0), 0.0F); 
        if (entity instanceof net.minecraft.entity.monster.EntityWitherSkeleton)
          entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 1), 0.0F); 
        if (entity instanceof net.minecraft.entity.monster.EntityZombie && !(entity instanceof net.minecraft.entity.monster.EntityPigZombie))
          entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 2), 0.0F); 
        if (entity instanceof net.minecraft.entity.monster.EntityCreeper)
          entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 4), 0.0F); 
        if (entity instanceof net.minecraft.entity.boss.EntityDragon)
          entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 5), 0.0F); 
      }  
    if (getPowered()) {
      this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
      entity.func_70077_a(new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
    } 
    super.inflictEngenderMobDamage(entity, killmessage, attacktype, damage);
  }
  
  public boolean func_70652_k(Entity entity) {
    if (super.func_70652_k(entity)) {
      if (this.field_70170_p.func_82736_K().func_82766_b("doMobLoot"))
        if (!entity.func_70089_S() && getPowered() && (this.field_70146_Z.nextInt(20) == 0 || isHero())) {
          if (entity instanceof net.minecraft.entity.monster.EntitySkeleton)
            entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 0), 0.0F); 
          if (entity instanceof net.minecraft.entity.monster.EntityWitherSkeleton)
            entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 1), 0.0F); 
          if (entity instanceof net.minecraft.entity.monster.EntityZombie && !(entity instanceof net.minecraft.entity.monster.EntityPigZombie))
            entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 2), 0.0F); 
          if (entity instanceof net.minecraft.entity.monster.EntityCreeper)
            entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 4), 0.0F); 
          if (entity instanceof net.minecraft.entity.boss.EntityDragon)
            entity.func_70099_a(new ItemStack(Items.field_151144_bL, 1, 5), 0.0F); 
        }  
      if (getPowered()) {
        this.field_70170_p.func_72942_c((Entity)new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
        entity.func_70077_a(new EntityLightningBolt(this.field_70170_p, entity.field_70165_t - 0.5D, entity.field_70163_u + entity.field_70131_O, entity.field_70161_v - 0.5D, true));
      } 
      return true;
    } 
    return false;
  }
  
  public float func_180428_a(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
    float f = super.func_180428_a(explosionIn, worldIn, pos, blockStateIn) * 0.5F;
    Block block = blockStateIn.func_177230_c();
    if (EntityWither.func_181033_a(block))
      f = Math.min(0.8F, f); 
    return f;
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
    return ELoot.ENTITIES_CREEDER;
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
      if (func_70638_az() != null && getSpecialAttackTimer() <= 0 && isHero()) {
        createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, 20.0F * f, flag);
        this.field_70180_af.func_187227_b(IGNITED, Boolean.valueOf(false));
        setCreeperState(-1);
        setSpecialAttackTimer(800);
        this.timeSinceIgnited = 0;
        this.field_70181_x = 0.0D;
      } else {
        this.timeSinceIgnited = 0;
        this.field_70180_af.func_187227_b(IGNITED, Boolean.valueOf(false));
        setCreeperState(-1);
        createEngenderModExplosionFireless((Entity)this, this.field_70165_t, this.field_70163_u + 1.0D, this.field_70161_v, this.explosionRadius * f, flag);
      } 
      if (func_110143_aJ() <= func_110138_aP() / 2.0F || isWild()) {
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
      } else {
        this.timeSinceIgnited = 0;
        this.field_70180_af.func_187227_b(IGNITED, Boolean.valueOf(false));
      } 
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
  
  public class EntityAICreeperSwell extends EntityAIBase {
    EntityCreeder swellingCreeper;
    
    EntityLivingBase creeperAttackTarget;
    
    public EntityAICreeperSwell(EntityCreeder p_i1655_1_) {
      this.swellingCreeper = p_i1655_1_;
      func_75248_a(1);
    }
    
    public boolean func_75250_a() {
      EntityLivingBase entitylivingbase = this.swellingCreeper.func_70638_az();
      return ((this.swellingCreeper.getCreeperState() > 0 || (entitylivingbase != null && this.swellingCreeper.func_70068_e((Entity)entitylivingbase) < 9.0D)) && this.swellingCreeper.func_110143_aJ() <= this.swellingCreeper.func_110138_aP() / 2.0F);
    }
    
    public void func_75249_e() {
      this.swellingCreeper.func_70661_as().func_75499_g();
      this.creeperAttackTarget = this.swellingCreeper.func_70638_az();
    }
    
    public void func_75251_c() {
      this.creeperAttackTarget = null;
    }
    
    public void func_75246_d() {
      if (this.creeperAttackTarget == null) {
        this.swellingCreeper.setCreeperState(-1);
      } else if (this.swellingCreeper.func_70068_e((Entity)this.creeperAttackTarget) > 49.0D) {
        this.swellingCreeper.setCreeperState(-1);
      } else if (!this.swellingCreeper.func_70635_at().func_75522_a((Entity)this.creeperAttackTarget)) {
        this.swellingCreeper.setCreeperState(-1);
      } else {
        this.swellingCreeper.setCreeperState(1);
      } 
    }
  }
}
