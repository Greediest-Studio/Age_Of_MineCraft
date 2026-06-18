package net.minecraft.AgeOfMinecraft.triggers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.AgeOfMinecraft.entity.tame.EntityTameBase;
import net.minecraft.advancements.ICriterionInstance;
import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class MarryMobTrigger implements ICriterionTrigger<MarryMobTrigger.Instance> {
  private static final ResourceLocation ID = new ResourceLocation("marry_mob");
  
  private final Map<PlayerAdvancements, Listeners> listeners = Maps.newHashMap();
  
  public ResourceLocation getId() {
    return ID;
  }
  
  public void addListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<Instance> listener) {
    Listeners tameanimaltrigger$listeners = this.listeners.get(playerAdvancementsIn);
    if (tameanimaltrigger$listeners == null) {
      tameanimaltrigger$listeners = new Listeners(playerAdvancementsIn);
      this.listeners.put(playerAdvancementsIn, tameanimaltrigger$listeners);
    } 
    tameanimaltrigger$listeners.add(listener);
  }
  
  public void removeListener(PlayerAdvancements playerAdvancementsIn, ICriterionTrigger.Listener<Instance> listener) {
    Listeners tameanimaltrigger$listeners = this.listeners.get(playerAdvancementsIn);
    if (tameanimaltrigger$listeners != null) {
      tameanimaltrigger$listeners.remove(listener);
      if (tameanimaltrigger$listeners.isEmpty())
        this.listeners.remove(playerAdvancementsIn); 
    } 
  }
  
  public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {
    this.listeners.remove(playerAdvancementsIn);
  }
  
  public Instance deserializeInstance(JsonObject json, JsonDeserializationContext context) {
    EntityPredicate entitypredicate = EntityPredicate.deserialize(json.get("entity"));
    return new Instance(entitypredicate);
  }
  
  public void trigger(EntityPlayerMP player, EntityTameBase entity) {
    Listeners tameanimaltrigger$listeners = this.listeners.get(player.getAdvancements());
    if (tameanimaltrigger$listeners != null)
      tameanimaltrigger$listeners.trigger(player, entity); 
  }
  
  public static class Instance extends AbstractCriterionInstance {
    private final EntityPredicate entity;
    
    public Instance(EntityPredicate entity) {
      super(MarryMobTrigger.ID);
      this.entity = entity;
    }
    
    public boolean test(EntityPlayerMP player, EntityTameBase entity) {
      return this.entity.test(player, (Entity)entity);
    }
  }
  
  static class Listeners {
    private final PlayerAdvancements playerAdvancements;
    
    private final Set<ICriterionTrigger.Listener<MarryMobTrigger.Instance>> listeners = Sets.newHashSet();
    
    public Listeners(PlayerAdvancements playerAdvancementsIn) {
      this.playerAdvancements = playerAdvancementsIn;
    }
    
    public boolean isEmpty() {
      return this.listeners.isEmpty();
    }
    
    public void add(ICriterionTrigger.Listener<MarryMobTrigger.Instance> listener) {
      this.listeners.add(listener);
    }
    
    public void remove(ICriterionTrigger.Listener<MarryMobTrigger.Instance> listener) {
      this.listeners.remove(listener);
    }
    
    public void trigger(EntityPlayerMP player, EntityTameBase entity) {
      List<ICriterionTrigger.Listener<MarryMobTrigger.Instance>> list = null;
      for (ICriterionTrigger.Listener<MarryMobTrigger.Instance> listener : this.listeners) {
        if (((MarryMobTrigger.Instance)listener.getCriterionInstance()).test(player, entity)) {
          if (list == null)
            list = Lists.newArrayList(); 
          list.add(listener);
        } 
      } 
      if (list != null)
        for (ICriterionTrigger.Listener<MarryMobTrigger.Instance> listener1 : list)
          listener1.grantCriterion(this.playerAdvancements);  
    }
  }
}
