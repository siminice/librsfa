package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by radu on 11/30/16.
 */
@AllArgsConstructor
public class HistoricAlias {

  private static final String INTERNAL_SEP = "=";

  @Getter private final Interval interval;
  @Getter private final Alias alias;

  public static HistoricAlias parse(final String s) {
    String parts[] = s.split(INTERNAL_SEP);
    return new HistoricAlias(Interval.parse(parts[0]), Alias.parse(parts[1]));
  }

  public static HistoricAlias chainedParse(final String s) {
    return new HistoricAlias(Interval.chainedParse(s.substring(0,4)), Alias.parse(s.substring(5,s.length())));
  }

  @Override
  public String toString() {
    return interval.toString() + INTERNAL_SEP + alias.toString();
  }
}
