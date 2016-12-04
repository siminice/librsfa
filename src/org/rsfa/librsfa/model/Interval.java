package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by radu on 11/30/16.
 */
@AllArgsConstructor
@NoArgsConstructor
public class Interval {
  public static final String INTERVAL_START = "[";
  public static final String INTERVAL_SEP = ":";
  public static final String INTERVAL_END = "]";
  public static final String INTERVAL_FMT = "YYYY-MM-DD";

  @Getter @Setter private LocalDate start = LocalDate.of(1800,1,1);
  @Getter @Setter private LocalDate end = LocalDate.of(9999,12,31);

  public static Interval parse(String s) {
    LocalDate startDate;
    LocalDate endDate;
    try {
      String t = s
          .replaceAll("\\[", "")
          .replaceAll("\\]", "");
      String parts[] = t.split(INTERVAL_SEP);
      if (parts.length != 2) {
        return new Interval();
      }
      startDate = LocalDate.parse(parts[0], DateTimeFormatter.ISO_LOCAL_DATE);
      endDate = LocalDate.parse(parts[1], DateTimeFormatter.ISO_LOCAL_DATE);
    } catch (Exception e) {
      return new Interval();
    }
    return new Interval(startDate, endDate);
  }

  public static Interval chainedParse(String s) {
    LocalDate startDate;
    try {
      String parts[] = s.split(INTERVAL_SEP);
      if (parts.length < 1) {
        return new Interval();
      }
      startDate = LocalDate.of(Integer.parseInt(parts[0]), 1, 1);
    } catch (Exception e) {
      return new Interval();
    }
    return new Interval(startDate, LocalDate.of(9999,12,31));
  }

  public Duration getDuration() {
    return Duration.between(start, end);
  }

  @Override
  public String toString() {
    return INTERVAL_START
        + DateTimeFormatter.ISO_LOCAL_DATE.format(start)
        + INTERVAL_SEP
        + DateTimeFormatter.ISO_LOCAL_DATE.format(end)
        + INTERVAL_END;
  }
}
