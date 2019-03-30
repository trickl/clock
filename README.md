# Trickl Clock
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.trickl/clock/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.trickl/clock)
[![build_status](https://travis-ci.com/trickl/clock.svg?branch=master)](https://travis-ci.com/trickl/clock)
[![Maintainability](https://api.codeclimate.com/v1/badges/6039371d2409365c76dc/maintainability)](https://codeclimate.com/github/trickl/clock/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/6039371d2409365c76dc/test_coverage)](https://codeclimate.com/github/trickl/clock/test_coverage)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A set of [java.time.Clock](https://docs.oracle.com/javase/8/docs/api/java/time/Clock.html) implementations with more useful functionality.

* MutableClock - A version of FixedClock with a mutable time.
* RelativeClock - An adjustable clock that time keeps using a relative clock, but can be stopped, started and run at variable speeds.

Installation
============

To install from Maven Central:

```xml
<dependency>
  <groupId>com.github.trickl</groupId>
  <artifactId>clock</artifactId>
  <version>0.1.1</version>
</dependency>
```

## Usage

See the Junit tests for usage

### Building

To download the library into a folder called "clock" run

```
git clone https://github.com/trickl/clock.git
```

To build the library run

```
mvn clean build
```

## Examples

### Mutable Clock
```
        MutableClock mutableClock = MutableClock.at(clockStart);
        Duration adjustment = Duration.ofMinutes(5);
        mutableClock.advance(adjustment);
        assertEquals(clockStart.plus(adjustment), mutableClock.instant());
```
### Relative Clock
```
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
```
