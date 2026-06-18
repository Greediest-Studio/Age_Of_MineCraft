package net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.asorahphases;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import net.minecraft.AgeOfMinecraft.addons.abyssalcraft.entity.EntityDragonBoss;

public class PhaseListAsorah<T extends IPhaseAsorah> {
  private static PhaseListAsorah<?>[] phases = (PhaseListAsorah<?>[])new PhaseListAsorah[0];
  
  public static final PhaseListAsorah<PhaseCircle> HOLDING_PATTERN = create(PhaseCircle.class, "Circle");
  
  public static final PhaseListAsorah<PhaseFireballAndStrafe> STRAFE_PLAYER = create(PhaseFireballAndStrafe.class, "FireballAndStrafe");
  
  public static final PhaseListAsorah<PhaseApproachOwner> LANDING_APPROACH = create(PhaseApproachOwner.class, "ApproachOwner");
  
  public static final PhaseListAsorah<PhaseHoveringOverOwner> LANDING = create(PhaseHoveringOverOwner.class, "HoverOverOwner");
  
  public static final PhaseListAsorah<PhaseLightningAndStrafe> TAKEOFF = create(PhaseLightningAndStrafe.class, "LightningAndStrafe");
  
  public static final PhaseListAsorah<PhaseBreathing> SITTING_FLAMING = create(PhaseBreathing.class, "Breathe");
  
  public static final PhaseListAsorah<PhaseFaceNearestEnemy> SITTING_SCANNING = create(PhaseFaceNearestEnemy.class, "Search");
  
  public static final PhaseListAsorah<PhasePreBreathing> SITTING_ATTACKING = create(PhasePreBreathing.class, "Roar");
  
  public static final PhaseListAsorah<PhaseRamAttack> CHARGING_PLAYER = create(PhaseRamAttack.class, "MeleeAttack");
  
  public static final PhaseListAsorah<PhaseDeath> DYING = create(PhaseDeath.class, "Dying");
  
  public static final PhaseListAsorah<PhaseIdleHover> HOVER = create(PhaseIdleHover.class, "Hover");
  
  private final Class<? extends IPhaseAsorah> clazz;
  
  private final int id;
  
  private final String name;
  
  private PhaseListAsorah(int idIn, Class<? extends IPhaseAsorah> clazzIn, String nameIn) {
    this.id = idIn;
    this.clazz = clazzIn;
    this.name = nameIn;
  }
  
  public IPhaseAsorah createPhase(EntityDragonBoss dragon) {
    try {
      Constructor<? extends IPhaseAsorah> constructor = getConstructor();
      return constructor.newInstance(new Object[] { dragon });
    } catch (Exception exception) {
      throw new Error(exception);
    } 
  }
  
  protected Constructor<? extends IPhaseAsorah> getConstructor() throws NoSuchMethodException {
    return this.clazz.getConstructor(new Class[] { EntityDragonBoss.class });
  }
  
  public int getId() {
    return this.id;
  }
  
  public String toString() {
    return this.name + " (#" + this.id + ")";
  }
  
  public static PhaseListAsorah<?> getById(int p_188738_0_) {
    return (p_188738_0_ >= 0 && p_188738_0_ < phases.length) ? phases[p_188738_0_] : HOLDING_PATTERN;
  }
  
  public static int getTotalPhases() {
    return phases.length;
  }
  
  private static <T extends IPhaseAsorah> PhaseListAsorah<T> create(Class<T> phaseIn, String nameIn) {
    PhaseListAsorah<T> phaselist = new PhaseListAsorah<>(phases.length, phaseIn, nameIn);
    phases = (PhaseListAsorah<?>[])Arrays.<PhaseListAsorah>copyOf((PhaseListAsorah[])phases, phases.length + 1);
    phases[phaselist.getId()] = phaselist;
    return phaselist;
  }
}
