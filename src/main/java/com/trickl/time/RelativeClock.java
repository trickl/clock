package com.trickl.time;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class RelativeClock extends Clock {
  @Getter private final Clock referenceClock;

  @Getter private final ZoneId zone;

  @Getter private final boolean allowFuture;

  @Getter private boolean running;

  @Getter private double speed;

  private Instant lastTime;

  private Instant lastReferenceTime;

  /**
   * Create a clock matching system UTC.
   * @return A relative clock
   */
  public static RelativeClock standard() {
    Clock referenceClock = Clock.systemUTC();
    Instant time = referenceClock.instant();
    return new RelativeClock(
        referenceClock, ZoneId.of("Z"), true, true, 1.0d, time, time);
  }

  /**
   * Start the clock.
   *
   * @return The time on the reference clock
   */
  public Instant start() {
    lastTime = instant();
    lastReferenceTime = referenceClock.instant();
    running = true;
    return lastReferenceTime;
  }

  /**
   * Stop the clock.
   *
   * @return The time on the reference clock
   */
  public Instant stop() {
    lastTime = instant();
    lastReferenceTime = referenceClock.instant();
    running = false;
    return lastReferenceTime;
  }

  /**
   * Adjust the clock speed.
   *
   * @param speed The new speed
   * @return The time on the reference clock
   */
  public Instant setSpeed(double speed) {
    lastTime = instant();
    lastReferenceTime = referenceClock.instant();
    this.speed = speed;
    return lastReferenceTime;
  }

  /**
   * Get the clock time.
   *
   * @return The current clock time
   */
  @Override
  public Instant instant() {
    if (!running) {
      return lastTime;
    }

    Instant referenceTime = referenceClock.instant();

    // Get elapsed time since the checkpoint
    long referenceElapsedMs = lastReferenceTime.until(referenceTime, ChronoUnit.MILLIS);
    long relativeElapsedMs = (long) (referenceElapsedMs * speed);

    Instant relativeTime = lastTime.plus(relativeElapsedMs, ChronoUnit.MILLIS);
    return !allowFuture && relativeTime.isAfter(referenceTime) ? referenceTime : relativeTime;
  }

  /**
   * Create an identical clock with a different time zone.
   *
   * @param zone The new time zone
   * @return A new clock
   */
  @Override
  public RelativeClock withZone(ZoneId zone) {
    return new RelativeClock(
        referenceClock, zone, allowFuture, running, speed, lastTime, lastReferenceTime);
  }

  /**
   * Create an identical clock with a different time.
   *
   * @param time The new time
   * @return A new clock
   */
  public RelativeClock withTime(Instant time) {
    return new RelativeClock(
        referenceClock, zone, allowFuture, running, speed, time, lastReferenceTime);
  }

  /**
   * Create an identical clock with a different speed.
   *
   * @param speed The new speed
   * @return A new clock
   */
  public RelativeClock withSpeed(double speed) {
    return new RelativeClock(
        referenceClock, zone, allowFuture, running, speed, lastTime, lastReferenceTime);
  }

  /**
   * Create an identical clock with a different reference clock.
   *
   * @param referenceClock The new reference clock
   * @return A new clock
   */
  public RelativeClock withReferenceClock(Clock referenceClock) {    
    Instant referenceTime = referenceClock.instant();
    return new RelativeClock(
        referenceClock, zone, allowFuture, running, speed, referenceTime, referenceTime);
  }

  /**
   * Create an identical clock with a different allow future setting.
   *
   * @param allowFuture IF the clock can run ahead of the reference clock
   * @return A new clock
   */
  public RelativeClock withAllowFuture(boolean allowFuture) {
    return new RelativeClock(
        referenceClock, zone, allowFuture, running, speed, lastTime, lastReferenceTime);
  }

  /**
   * Create an identical clock with a different run state.
   *
   * @param running IF the clock is running
   * @return A new clock
   */
  public RelativeClock isRunning(boolean running) {
    return new RelativeClock(
        referenceClock, zone, allowFuture, running, speed, lastTime, lastReferenceTime);
  }
}
