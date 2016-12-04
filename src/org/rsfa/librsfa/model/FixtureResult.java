package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Created by radu on 12/1/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixtureResult {
  private Fixture fixture = Fixture.UNKNOWN;
  private TimedResult result = TimedResult.UNKNOWN;

  public FixtureResult(Fixture f) {
    this.fixture = f;
  }

  public FixtureResult(int h, int a) {
    this(new Fixture(h, a));
  }

  public static FixtureResult of(int h, int a, int y, int rz, int s) {
    int r = rz/1000;
    int z = rz%1000;
    Fixture f = new Fixture(h, a, r);
    if (s>=0) {
      if (rz > 0) {
        TimedResult tr = TimedResult.of(s, z > 50 ? LocalDate.of(y, z / 50, z % 50) : LocalDate.MIN);
        return new FixtureResult(f, tr);
      }
      else return new FixtureResult(f, TimedResult.of(s, LocalDate.MIN));
    }
    return new FixtureResult(f, TimedResult.UNKNOWN);
  }

  public String serialize() {
    if (fixture.getRound() > 0) {
      return String.format(" %2d", fixture.getRound()) + result.serialize();
    } else {
      return "   " + result.serialize();
    }
  }
}
