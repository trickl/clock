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
import org.junit.Before;

public class RelativeClockTest {
    
    private final MutableClock referenceClock =
        MutableClock.at(Instant.MIN);
    
    private final Instant referenceClockStart = LocalDateTime.of(
            LocalDate.of(2018, Month.JANUARY, 1),
            LocalTime.NOON
        ).toInstant(ZoneOffset.UTC);
    
    @Before 
    public void init() {
      referenceClock.setTime(referenceClockStart);
    }

    @Test
    public void testStart() {
        RelativeClock relativeClock = RelativeClock.standard()
            .withReferenceClock(referenceClock)
            .isRunning(false);
        
        referenceClock.advance(Duration.ofMinutes(7));        
        relativeClock.start();
        referenceClock.advance(Duration.ofMinutes(3));
        
        Instant expectedTime = referenceClockStart.plus(Duration.ofMinutes(3));
        
        assertEquals(expectedTime, relativeClock.instant());
    }

    @Test
    public void testStop() {
        RelativeClock relativeClock = RelativeClock.standard()
            .withReferenceClock(referenceClock);
        
        referenceClock.advance(Duration.ofMinutes(7));
        relativeClock.stop();
        referenceClock.advance(Duration.ofMinutes(3));
        
        Instant expectedTime = referenceClockStart.plus(Duration.ofMinutes(7));
        
        assertEquals(expectedTime, relativeClock.instant());
    }

    @Test
    public void testSetSpeed() {
        RelativeClock relativeClock = RelativeClock.standard()
            .withReferenceClock(referenceClock);
        
        relativeClock.setSpeed(3);
        referenceClock.advance(Duration.ofMinutes(3));
        relativeClock.setSpeed(5);
        referenceClock.advance(Duration.ofMinutes(5));
        relativeClock.setSpeed(7);
        referenceClock.advance(Duration.ofMinutes(7));
        
        Instant expectedTime = referenceClockStart.plus(Duration.ofMinutes(83));
        
        assertEquals(expectedTime, relativeClock.instant());
    }

    @Test
    public void testInstant() {
        RelativeClock relativeClock = RelativeClock.standard()
            .withReferenceClock(referenceClock);
                
        Instant expectedTime = referenceClockStart;
        
        assertEquals(expectedTime, relativeClock.instant());
    }

    @Test
    public void testWithZone() {
        RelativeClock relativeClock = RelativeClock.standard()
            .withReferenceClock(referenceClock)
            .withZone(ZoneId.of("GMT+2"));
        
        assertEquals(ZoneId.of("GMT+2"), relativeClock.getZone());
                
        Instant expectedTime = referenceClockStart;
        
        assertEquals(expectedTime, relativeClock.instant());
    }

    @Test
    public void testWithTime() {
        Instant startTime = LocalDateTime.of(
            LocalDate.of(2019, Month.FEBRUARY, 2),
            LocalTime.NOON).toInstant(ZoneOffset.UTC);
        
        RelativeClock relativeClock = RelativeClock.standard()
            .withReferenceClock(referenceClock)            
            .withTime(startTime);
                
        Instant expectedTime = startTime;
        
        assertEquals(expectedTime, relativeClock.instant());
    }

    @Test
    public void testWithAllowFuture() {
        Instant startTime = LocalDateTime.of(
            LocalDate.of(2017, Month.DECEMBER, 30),
            LocalTime.NOON).toInstant(ZoneOffset.UTC);
        
        RelativeClock relativeClock = RelativeClock.standard()
            .withAllowFuture(false)
            .withReferenceClock(referenceClock)            
            .withTime(startTime);
        
        relativeClock.setSpeed(10);
        referenceClock.advance(Duration.ofMinutes(5));
        
        Instant expectedTime = startTime.plus(Duration.ofMinutes(50));
        assertEquals(expectedTime, relativeClock.instant());
                
        referenceClock.advance(Duration.ofHours(6));
        
        // Won't move beyond reference clock
        expectedTime = referenceClock.instant();
        assertEquals(expectedTime, relativeClock.instant());
    }    
}
