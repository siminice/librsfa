package org.rsfa.librsfa.model;

import lombok.Data;

/**
 * Created by radu on 11/30/16.
 */
@Data
public class LeagueFormat {
  private int size;
  private int ppv = 2;
  private int[] promo;
  private int[] releg;
  private int groups = 1;
  private int tbr;
  private int rounds = 1;
  private boolean winter = false;

  @Override
  public String toString() {
    return String.format("%d %d %d %d %d %d %d",
        size, ppv, tbr, promo[0], promo[1], releg[0], releg[1]);
  }
}
