package com.trickl.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
final class MutableClock extends Clock {
  @Setter private Instant time;
  @Getter private final ZoneId zone;

  /**
   * Create a mutable clock.
   *
   * @return A mutable clock
   */
  public static MutableClock at(Instant time) {
    return new MutableClock(time, ZoneId.of("Z"));
  }

  public void advance(Duration duration) {
    time = time.plus(duration);
  }

  @Override
  public Clock withZone(ZoneId zone) {
    if (zone.equals(this.zone)) {
      return this;
    }
    return new MutableClock(time, zone);
  }

  @Override
  public Instant instant() {
    return time;
  }
}
