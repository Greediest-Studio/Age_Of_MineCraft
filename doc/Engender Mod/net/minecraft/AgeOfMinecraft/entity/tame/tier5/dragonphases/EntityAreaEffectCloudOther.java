package net.minecraft.AgeOfMinecraft.entity.tame.tier5.dragonphases;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.block.material.EnumPushReaction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class EntityAreaEffectCloudOther extends Entity {
  private static final DataParameter<Float> RADIUS = EntityDataManager.func_187226_a(EntityAreaEffectCloudOther.class, DataSerializers.field_187193_c);
  
  private static final DataParameter<Integer> COLOR = EntityDataManager.func_187226_a(EntityAreaEffectCloudOther.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.func_187226_a(EntityAreaEffectCloudOther.class, DataSerializers.field_187198_h);
  
  private static final DataParameter<Integer> PARTICLE = EntityDataManager.func_187226_a(EntityAreaEffectCloudOther.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> PARTICLE_PARAM_1 = EntityDataManager.func_187226_a(EntityAreaEffectCloudOther.class, DataSerializers.field_187192_b);
  
  private static final DataParameter<Integer> PARTICLE_PARAM_2 = EntityDataManager.func_187226_a(EntityAreaEffectCloudOther.class, DataSerializers.field_187192_b);
  
  private PotionType potion;
  
  private final List<PotionEffect> effects;
  
  private final Map<Entity, Integer> reapplicationDelayMap;
  
  private int duration;
  
  private int waitTime;
  
  private int reapplicationDelay;
  
  private boolean colorSet;
  
  private int durationOnUse;
  
  private float radiusOnUse;
  
  private float radiusPerTick;
  
  private EntityTameBase owner;
  
  private UUID ownerUniqueId;
  
  public EntityAreaEffectCloudOther(World worldIn) {
    super(worldIn);
    this.potion = PotionTypes.field_185229_a;
    this.effects = Lists.newArrayList();
    this.reapplicationDelayMap = Maps.newHashMap();
    this.duration = 600;
    this.waitTime = 0;
    this.reapplicationDelay = 5;
    this.field_70145_X = true;
    this.field_70178_ae = true;
    setRadius(3.0F);
  }
  
  public EntityAreaEffectCloudOther(World worldIn, double x, double y, double z) {
    this(worldIn);
    func_70107_b(x, y, z);
  }
  
  protected void func_70088_a() {
    func_184212_Q().func_187214_a(COLOR, Integer.valueOf(0));
    func_184212_Q().func_187214_a(RADIUS, Float.valueOf(0.5F));
    func_184212_Q().func_187214_a(IGNORE_RADIUS, Boolean.valueOf(false));
    func_184212_Q().func_187214_a(PARTICLE, Integer.valueOf(EnumParticleTypes.SPELL_MOB.func_179348_c()));
    func_184212_Q().func_187214_a(PARTICLE_PARAM_1, Integer.valueOf(0));
    func_184212_Q().func_187214_a(PARTICLE_PARAM_2, Integer.valueOf(0));
  }
  
  public void setRadius(float radiusIn) {
    double d0 = this.field_70165_t;
    double d1 = this.field_70163_u;
    double d2 = this.field_70161_v;
    func_70105_a(radiusIn * 2.0F, 0.5F);
    func_70107_b(d0, d1, d2);
    if (!this.field_70170_p.field_72995_K)
      func_184212_Q().func_187227_b(RADIUS, Float.valueOf(radiusIn)); 
  }
  
  public float getRadius() {
    return ((Float)func_184212_Q().func_187225_a(RADIUS)).floatValue();
  }
  
  public void setPotion(PotionType potionIn) {
    this.potion = potionIn;
    if (!this.colorSet)
      updateFixedColor(); 
  }
  
  private void updateFixedColor() {
    if (this.potion == PotionTypes.field_185229_a && this.effects.isEmpty()) {
      func_184212_Q().func_187227_b(COLOR, Integer.valueOf(0));
    } else {
      func_184212_Q().func_187227_b(COLOR, Integer.valueOf(PotionUtils.func_185181_a(PotionUtils.func_185186_a(this.potion, this.effects))));
    } 
  }
  
  public void addEffect(PotionEffect effect) {
    this.effects.add(effect);
    if (!this.colorSet)
      updateFixedColor(); 
  }
  
  public int getColor() {
    return ((Integer)func_184212_Q().func_187225_a(COLOR)).intValue();
  }
  
  public void setColor(int colorIn) {
    this.colorSet = true;
    func_184212_Q().func_187227_b(COLOR, Integer.valueOf(colorIn));
  }
  
  public EnumParticleTypes getParticle() {
    return EnumParticleTypes.func_179342_a(((Integer)func_184212_Q().func_187225_a(PARTICLE)).intValue());
  }
  
  public void setParticle(EnumParticleTypes particleIn) {
    func_184212_Q().func_187227_b(PARTICLE, Integer.valueOf(particleIn.func_179348_c()));
  }
  
  public int getParticleParam1() {
    return ((Integer)func_184212_Q().func_187225_a(PARTICLE_PARAM_1)).intValue();
  }
  
  public void setParticleParam1(int particleParam) {
    func_184212_Q().func_187227_b(PARTICLE_PARAM_1, Integer.valueOf(particleParam));
  }
  
  public int getParticleParam2() {
    return ((Integer)func_184212_Q().func_187225_a(PARTICLE_PARAM_2)).intValue();
  }
  
  public void setParticleParam2(int particleParam) {
    func_184212_Q().func_187227_b(PARTICLE_PARAM_2, Integer.valueOf(particleParam));
  }
  
  protected void setIgnoreRadius(boolean ignoreRadius) {
    func_184212_Q().func_187227_b(IGNORE_RADIUS, Boolean.valueOf(ignoreRadius));
  }
  
  public boolean shouldIgnoreRadius() {
    return ((Boolean)func_184212_Q().func_187225_a(IGNORE_RADIUS)).booleanValue();
  }
  
  public int getDuration() {
    return this.duration;
  }
  
  public void setDuration(int durationIn) {
    this.duration = durationIn;
  }
  
  public void func_70071_h_() {
    super.func_70071_h_();
    if (!this.field_70170_p.field_72995_K && !this.field_70170_p.func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u - 0.1D, this.field_70161_v)).func_177230_c().func_149662_c(this.field_70170_p.func_180495_p(new BlockPos(this.field_70165_t, this.field_70163_u - 0.1D, this.field_70161_v))))
      this.field_70163_u -= 0.2D; 
    if (getRadius() <= 0.0F)
      func_70106_y(); 
    boolean flag = shouldIgnoreRadius();
    float f = getRadius();
    if (this.field_70170_p.field_72995_K) {
      EnumParticleTypes enumparticletypes = getParticle();
      int[] aint = new int[enumparticletypes.func_179345_d()];
      if (aint.length > 0)
        aint[0] = getParticleParam1(); 
      if (aint.length > 1)
        aint[1] = getParticleParam2(); 
      if (flag) {
        if (this.field_70146_Z.nextBoolean())
          for (int i = 0; i < 2; i++) {
            float f1 = this.field_70146_Z.nextFloat() * 6.2831855F;
            float f2 = MathHelper.func_76129_c(this.field_70146_Z.nextFloat()) * 0.2F;
            float f3 = MathHelper.func_76134_b(f1) * f2;
            float f4 = MathHelper.func_76126_a(f1) * f2;
            if (enumparticletypes == EnumParticleTypes.SPELL_MOB) {
              int j = this.field_70146_Z.nextBoolean() ? 16777215 : getColor();
              int k = j >> 16 & 0xFF;
              int l = j >> 8 & 0xFF;
              int i1 = j & 0xFF;
              this.field_70170_p.func_190523_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), this.field_70165_t + f3, this.field_70163_u, this.field_70161_v + f4, (k / 255.0F), (l / 255.0F), (i1 / 255.0F), new int[0]);
            } else {
              this.field_70170_p.func_190523_a(enumparticletypes.func_179348_c(), this.field_70165_t + f3, this.field_70163_u, this.field_70161_v + f4, 0.0D, 0.0D, 0.0D, aint);
            } 
          }  
      } else {
        float f5 = 3.1415927F * f * f;
        for (int k1 = 0; k1 < f5; k1++) {
          float f6 = this.field_70146_Z.nextFloat() * 6.2831855F;
          float f7 = MathHelper.func_76129_c(this.field_70146_Z.nextFloat()) * f;
          float f8 = MathHelper.func_76134_b(f6) * f7;
          float f9 = MathHelper.func_76126_a(f6) * f7;
          if (enumparticletypes == EnumParticleTypes.SPELL_MOB) {
            int l1 = getColor();
            int i2 = l1 >> 16 & 0xFF;
            int j2 = l1 >> 8 & 0xFF;
            int j1 = l1 & 0xFF;
            this.field_70170_p.func_190523_a(EnumParticleTypes.SPELL_MOB.func_179348_c(), this.field_70165_t + f8, this.field_70163_u, this.field_70161_v + f9, (i2 / 255.0F), (j2 / 255.0F), (j1 / 255.0F), new int[0]);
          } else {
            this.field_70170_p.func_190523_a(enumparticletypes.func_179348_c(), this.field_70165_t + f8, this.field_70163_u, this.field_70161_v + f9, (0.5D - this.field_70146_Z.nextDouble()) * 0.15D, 0.009999999776482582D, (0.5D - this.field_70146_Z.nextDouble()) * 0.15D, aint);
          } 
        } 
      } 
    } else {
      if (this.field_70173_aa >= this.waitTime + this.duration) {
        func_70106_y();
        return;
      } 
      boolean flag1 = (this.field_70173_aa < this.waitTime);
      if (flag != flag1)
        setIgnoreRadius(flag1); 
      if (flag1)
        return; 
      if (this.radiusPerTick != 0.0F) {
        f += this.radiusPerTick;
        if (f < 0.5F) {
          func_70106_y();
          return;
        } 
        setRadius(f);
      } 
      if (this.field_70173_aa % 20 == 0) {
        Iterator<Map.Entry<Entity, Integer>> iterator = this.reapplicationDelayMap.entrySet().iterator();
        while (iterator.hasNext()) {
          Map.Entry<Entity, Integer> entry = iterator.next();
          if (this.field_70173_aa >= ((Integer)entry.getValue()).intValue())
            iterator.remove(); 
        } 
        List<PotionEffect> potions = Lists.newArrayList();
        for (PotionEffect potioneffect1 : this.potion.func_185170_a())
          potions.add(new PotionEffect(potioneffect1.func_188419_a(), potioneffect1.func_76459_b() / 4, potioneffect1.func_76458_c(), potioneffect1.func_82720_e(), potioneffect1.func_188418_e())); 
        potions.addAll(this.effects);
        if (potions.isEmpty()) {
          this.reapplicationDelayMap.clear();
        } else {
          List<EntityLivingBase> list = this.field_70170_p.func_72872_a(EntityLivingBase.class, func_174813_aQ().func_186662_g(1.0D));
          if (!list.isEmpty() && getOwner() != null)
            for (EntityLivingBase entitylivingbase : list) {
              if (entitylivingbase.func_184222_aU() && !this.reapplicationDelayMap.containsKey(entitylivingbase) && entitylivingbase.func_184603_cC()) {
                double d0 = entitylivingbase.field_70165_t - this.field_70165_t;
                double d1 = entitylivingbase.field_70161_v - this.field_70161_v;
                double d2 = d0 * d0 + d1 * d1;
                if (d2 <= (f * f)) {
                  this.reapplicationDelayMap.put(entitylivingbase, Integer.valueOf(this.field_70173_aa + 5));
                  if (getOwner().func_184191_r((Entity)entitylivingbase)) {
                    for (PotionEffect potioneffect : potions) {
                      if (!potioneffect.func_188419_a().func_76398_f()) {
                        if (potioneffect.func_188419_a().func_76403_b()) {
                          potioneffect.func_188419_a().func_180793_a(this, (Entity)getOwner(), entitylivingbase, potioneffect.func_76458_c(), 0.5D);
                          continue;
                        } 
                        entitylivingbase.func_70690_d(new PotionEffect(potioneffect));
                      } 
                    } 
                  } else {
                    for (PotionEffect potioneffect : potions) {
                      if (potioneffect.func_188419_a().func_76403_b()) {
                        getOwner().inflictEngenderMobDamage(entitylivingbase, " was killed by magic created by ", (new EntityDamageSource("indirectMagic", (Entity)getOwner())).func_82726_p().func_76348_h().func_151518_m(), 8.0F);
                        continue;
                      } 
                      if (potioneffect.func_188419_a().func_76398_f() && entitylivingbase.func_70687_e(potioneffect)) {
                        entitylivingbase.func_70690_d(new PotionEffect(potioneffect));
                        continue;
                      } 
                      getOwner().inflictEngenderMobDamage(entitylivingbase, " was killed by magic created by ", (new EntityDamageSource("indirectMagic", (Entity)getOwner())).func_82726_p().func_76348_h().func_151518_m(), 8.0F);
                    } 
                  } 
                  if (this.radiusOnUse != 0.0F) {
                    f += this.radiusOnUse;
                    if (f < 0.5F) {
                      func_70106_y();
                      return;
                    } 
                    setRadius(f);
                  } 
                  if (this.durationOnUse != 0) {
                    this.duration += this.durationOnUse;
                    if (this.duration <= 0) {
                      func_70106_y();
                      return;
                    } 
                  } 
                } 
              } 
            }  
        } 
      } 
    } 
  }
  
  public void setRadiusOnUse(float radiusOnUseIn) {
    this.radiusOnUse = radiusOnUseIn;
  }
  
  public void setRadiusPerTick(float radiusPerTickIn) {
    this.radiusPerTick = radiusPerTickIn;
  }
  
  public void setWaitTime(int waitTimeIn) {
    this.waitTime = waitTimeIn;
  }
  
  public void setOwner(@Nullable EntityTameBase ownerIn) {
    this.owner = ownerIn;
    this.ownerUniqueId = (ownerIn == null) ? null : ownerIn.func_110124_au();
  }
  
  @Nullable
  public EntityTameBase getOwner() {
    if (this.owner == null && this.ownerUniqueId != null && this.field_70170_p instanceof WorldServer) {
      Entity entity = ((WorldServer)this.field_70170_p).func_175733_a(this.ownerUniqueId);
      if (entity instanceof EntityTameBase)
        this.owner = (EntityTameBase)entity; 
    } 
    return this.owner;
  }
  
  public boolean func_184230_a(EntityPlayer player, EnumHand hand) {
    ItemStack stack = player.func_184586_b(hand);
    if (!this.field_70170_p.field_72995_K && !stack.func_190926_b() && stack.func_77973_b() == Items.field_151069_bo) {
      setRadius(getRadius() - 0.5F);
      this.field_70170_p.func_184148_a((EntityPlayer)null, player.field_70165_t, player.field_70163_u, player.field_70161_v, SoundEvents.field_187618_I, SoundCategory.NEUTRAL, 1.0F, 1.0F);
      turnBottleIntoItem(stack, player, new ItemStack(Items.field_185157_bK));
      return true;
    } 
    return super.func_184230_a(player, hand);
  }
  
  protected ItemStack turnBottleIntoItem(ItemStack p_185061_1_, EntityPlayer player, ItemStack stack) {
    p_185061_1_.func_190918_g(1);
    player.func_71029_a(StatList.func_188057_b(stack.func_77973_b()));
    if (p_185061_1_.func_190926_b())
      return stack; 
    if (!player.field_71071_by.func_70441_a(stack))
      player.func_71019_a(stack, false); 
    return p_185061_1_;
  }
  
  protected void func_70037_a(NBTTagCompound compound) {
    this.field_70173_aa = compound.func_74762_e("Age");
    this.duration = compound.func_74762_e("Duration");
    this.waitTime = compound.func_74762_e("WaitTime");
    this.reapplicationDelay = compound.func_74762_e("ReapplicationDelay");
    this.durationOnUse = compound.func_74762_e("DurationOnUse");
    this.radiusOnUse = compound.func_74760_g("RadiusOnUse");
    this.radiusPerTick = compound.func_74760_g("RadiusPerTick");
    setRadius(compound.func_74760_g("Radius"));
    this.ownerUniqueId = compound.func_186857_a("OwnerUUID");
    if (compound.func_150297_b("Particle", 8)) {
      EnumParticleTypes enumparticletypes = EnumParticleTypes.func_186831_a(compound.func_74779_i("Particle"));
      if (enumparticletypes != null) {
        setParticle(enumparticletypes);
        setParticleParam1(compound.func_74762_e("ParticleParam1"));
        setParticleParam2(compound.func_74762_e("ParticleParam2"));
      } 
    } 
    if (compound.func_150297_b("Color", 99))
      setColor(compound.func_74762_e("Color")); 
    if (compound.func_150297_b("Potion", 8))
      setPotion(PotionUtils.func_185187_c(compound)); 
    if (compound.func_150297_b("Effects", 9)) {
      NBTTagList nbttaglist = compound.func_150295_c("Effects", 10);
      this.effects.clear();
      for (int i = 0; i < nbttaglist.func_74745_c(); i++) {
        PotionEffect potioneffect = PotionEffect.func_82722_b(nbttaglist.func_150305_b(i));
        if (potioneffect != null)
          addEffect(potioneffect); 
      } 
    } 
  }
  
  protected void func_70014_b(NBTTagCompound compound) {
    compound.func_74768_a("Age", this.field_70173_aa);
    compound.func_74768_a("Duration", this.duration);
    compound.func_74768_a("WaitTime", this.waitTime);
    compound.func_74768_a("ReapplicationDelay", this.reapplicationDelay);
    compound.func_74768_a("DurationOnUse", this.durationOnUse);
    compound.func_74776_a("RadiusOnUse", this.radiusOnUse);
    compound.func_74776_a("RadiusPerTick", this.radiusPerTick);
    compound.func_74776_a("Radius", getRadius());
    compound.func_74778_a("Particle", getParticle().func_179346_b());
    compound.func_74768_a("ParticleParam1", getParticleParam1());
    compound.func_74768_a("ParticleParam2", getParticleParam2());
    if (this.ownerUniqueId != null)
      compound.func_186854_a("OwnerUUID", this.ownerUniqueId); 
    if (this.colorSet)
      compound.func_74768_a("Color", getColor()); 
    if (this.potion != PotionTypes.field_185229_a && this.potion != null)
      compound.func_74778_a("Potion", ((ResourceLocation)PotionType.field_185176_a.func_177774_c(this.potion)).toString()); 
    if (!this.effects.isEmpty()) {
      NBTTagList nbttaglist = new NBTTagList();
      for (PotionEffect potioneffect : this.effects)
        nbttaglist.func_74742_a((NBTBase)potioneffect.func_82719_a(new NBTTagCompound())); 
      compound.func_74782_a("Effects", (NBTBase)nbttaglist);
    } 
  }
  
  public void func_184206_a(DataParameter<?> key) {
    if (RADIUS.equals(key))
      setRadius(getRadius()); 
    super.func_184206_a(key);
  }
  
  public EnumPushReaction func_184192_z() {
    return EnumPushReaction.IGNORE;
  }
  
  public boolean func_70067_L() {
    return true;
  }
}
