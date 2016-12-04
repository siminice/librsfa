package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rsfa.librsfa.util.Constants;

/**
 * Created by radu on 12/1/16.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Fixture {
  private int home = Constants.UNKNOWN;
  private int away = Constants.UNKNOWN;
  private int round = Constants.UNKNOWN;

  public Fixture(int h, int a) {
    this.home = h;
    this.away = a;
  }

  public static Fixture of(int h, int a) {
    return new Fixture (h, a);
  }

  public boolean has(int t) {
    return getHome() == t || getAway() == t;
  }
/*
  public static BiFunction<Fixture, Integer, Boolean> has = new BiFunction<Fixture, Integer, Boolean>() {
    public Boolean apply(Fixture f, Integer t) {
      return f.has(t);
    }
  };
*/
  public static final Fixture UNKNOWN = Fixture.of(Constants.UNKNOWN, Constants.UNKNOWN);
}
