package net.minecraft.AgeOfMinecraft.entity.tame;

public interface Flying {
  public static final double MAX_FLIGHT_TARGET_Y = 200.0D;
  
  public static double clampFlightY(double y) {
    return Math.min(y, MAX_FLIGHT_TARGET_Y);
  }
}
