package com.trickl.time;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZoneOffset;
import org.junit.Test;
import static org.junit.Assert.*;

public class MutableClockTest {
    
    private final Instant clockStart = LocalDateTime.of(
            LocalDate.of(2018, Month.JANUARY, 1),
            LocalTime.NOON
        ).toInstant(ZoneOffset.UTC);
    
    @Test
    public void testAdvance() {
        MutableClock mutableClock = MutableClock.at(clockStart);
        Duration adjustment = Duration.ofMinutes(5);
        mutableClock.advance(adjustment);
        assertEquals(clockStart.plus(adjustment), mutableClock.instant());
    }

    @Test
    public void testWithZone() {
        Clock mutableClock = MutableClock.at(clockStart)
            .withZone(ZoneId.of("GMT+2"));
        
        assertEquals(ZoneId.of("GMT+2"), mutableClock.getZone());
    }

    @Test
    public void testInstant() {
        MutableClock mutableClock = MutableClock.at(clockStart);
        assertEquals(clockStart, mutableClock.instant());
    }

    @Test
    public void testSetTime() {
        MutableClock mutableClock = MutableClock.at(clockStart);
        Instant adjustedTime = clockStart.plus(Duration.ofMinutes(5));
        mutableClock.setTime(adjustedTime);
        assertEquals(adjustedTime, mutableClock.instant());
    }
}
