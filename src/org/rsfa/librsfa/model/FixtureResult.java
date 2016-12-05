package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Comparator;

/**
 * Created by radu on 12/1/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FixtureResult implements Comparable<FixtureResult> {
  private Fixture fixture = Fixture.UNKNOWN;
  private TimedResult result = TimedResult.UNKNOWN;

  public int getPacked() {
    return result.pack();
  }

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

  @Override
  public int compareTo(FixtureResult o) {
    if (this.getResult().getZ() == null) {
      return o.getResult().getZ() == null? 0 : -1;
    }
    if (o.getResult().getZ() == null) return 1;
    return this.getResult().getZ().compareTo(o.getResult().getZ());
  }

  public static Comparator<FixtureResult> byDate = new Comparator<FixtureResult>() {
    public int compare(FixtureResult x, FixtureResult y) {
      return x.compareTo(y);
    }
  };
}
