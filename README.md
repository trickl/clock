# Trickl Clock

[![build_status](https://travis-ci.com/trickl/clock.svg?branch=master)](https://travis-ci.com/trickl/clock)
[![Maintainability](https://api.codeclimate.com/v1/badges/be4af1f4cc620e465849/maintainability)](https://codeclimate.com/github/trickl/clock/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/be4af1f4cc620e465849/test_coverage)](https://codeclimate.com/github/trickl/clock/test_coverage)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

A set of java.time.Clock implementations with more useful functionality.

* MutableClock - A version of FixedClock with a mutable time.
* RelativeClock - An adjustable clock that time keeps using a relative clock, but can be stopped, started and run at variable speeds.

### Prerequisites

Requires Maven and a Java 8 compiler installed on your system.

## Usage

See the Junit tests for usage

### Installing

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

### Relative Clock
