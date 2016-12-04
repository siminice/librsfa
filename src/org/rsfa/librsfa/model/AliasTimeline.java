package org.rsfa.librsfa.model;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by radu on 11/30/16.
 */

public class AliasTimeline extends TreeMap<Interval, Alias> {

  private static final String ALIAS_SEP = "*";
  private static final int END_TIME_YEAR = 9999;
  private static final LocalDate END_TIME = LocalDate.of(END_TIME_YEAR, 1, 1);
  private static final Comparator<Interval> compStartDate = new Comparator<Interval>() {
    @Override
    public int compare(Interval i1, Interval i2) {
      if (i1.getStart().isBefore(i2.getStart()))
        return -1;
      else if (i1.getStart().isAfter(i2.getStart()))
        return 1;
      else
        return 0;
    }
  };

  private AliasTimeline() {
    super(compStartDate);
  }

  public static AliasTimeline parse(String line) {
    AliasTimeline atl = new AliasTimeline();
    if (line != null) {
        String parts[] = line.split("\\*");
        for (int i = 0; i < parts.length; ++i) {
          HistoricAlias ha = HistoricAlias.parse(parts[i]);
          atl.put(ha.getInterval(), ha.getAlias());
        }
    }
    return atl;
  }

  public static AliasTimeline chainedParse(String line) {
    AliasTimeline atl = new AliasTimeline();
    HistoricAlias prev = new HistoricAlias(new Interval(), new Alias());
    LocalDate last = LocalDate.of(9999,12,31);
    if (line != null) {
      String parts[] = line.split("\\*");
      for (int i = 0; i < parts.length; ++i) {
        HistoricAlias ha = HistoricAlias.chainedParse(parts[i]);
        Interval iv = ha.getInterval();
        prev.getInterval().setEnd(iv.getStart());
        atl.put(iv, ha.getAlias());
        prev = ha;
      }
    }
    return atl;
  }

  public Alias getAlias(final LocalDate t) {
    for (Map.Entry<Interval, Alias> e : this.entrySet()) {
      Interval interval = e.getKey();
      if (!interval.getStart().isAfter(t) && interval.getEnd().isAfter(t)) {
        return e.getValue();
      }
    }
    return null;
  }

  public Alias getAlias(final int year) {
    return getAlias(LocalDate.of(year, 1, 1));
  }

  public String getName(final LocalDate d) {
    Alias alias = getAlias(d);
    if (alias != null)
      return alias.getFullName();
    else
      return null;
  }

  public String getName(final int year) {
    return getName(LocalDate.of(year, 1, 1));
  }

  public String getNick(final LocalDate t) {
    final Alias alias = getAlias(t);
    if (alias != null)
      return alias.getNickName();
    return null;
  }

  public String getNick(final int year) {
    return getNick(LocalDate.of(year, 1, 1));
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (Map.Entry<Interval, Alias> e : this.entrySet()) {
      HistoricAlias ha = new HistoricAlias(e.getKey(), e.getValue());
      sb.append(ALIAS_SEP).append(ha.toString());
    }
    return sb.toString();
  }
}