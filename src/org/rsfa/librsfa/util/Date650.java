package org.rsfa.librsfa.util;

import java.time.LocalDate;

/**
 * Created by radu on 11/30/16.
 */
public class Date650 {

  public final static int MAXDAY = 631;
  public final static int MUXER = 1000;
  public final static int DPM = 50;
  public final static String UNKNOWN = "?";
  private final static String[] month = { "???", "Jan", "Feb", "Mar", "Apr",
      "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };

  public Date650() {
  };

  public static int getRound(int rd) {
    return rd / MUXER;
  }

  public static int getDate(int rd) {
    return rd % MUXER;
  }

  public static String printRoundDate(int rd) {
    int r = getRound(rd);
    int z = getDate(rd);
    return "R" + String.format("%-2d", r) + ", " + toString(z);
  }

  public static String toString(int d) {
    if (d < 0 || d > MAXDAY) {
      return UNKNOWN;
    }
    int mo = d / DPM;
    int da = d % DPM;
    return String.format("%2d", da) + " " + month[mo];
  }

  public static LocalDate of(int y, int d) {
    return LocalDate.of(y, d/50, d%50);
  }

  public static int encode(LocalDate z) {
    return 50*z.getMonthValue() + z.getDayOfMonth();
  }

  public static String decode(int z) {
    return String.format("%s %d", month[z/50], z%50);
  }

}
