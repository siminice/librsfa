package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.rsfa.librsfa.util.Algorithm;

/**
 * Created by radu on 12/3/16.
 */
@AllArgsConstructor
public class StatSorter {
  @Setter private League l;
  @Setter private Algorithm alg = Algorithm.DEFAULT;

  public StatSorter(League l) {
    this.l = l;
  }
  
  private int compare(Stat x, Stat y) {
    if (x.getWin() == y.getWin() && x.getDrw() == y.getDrw() && x.getLos() == y.getDrw()
        && x.getGsc() == y.getGsc() && x.getGre() == y.getGre()) {
      return Integer.compare(x.getId(), y.getId());
    }
    int ppv = l.getFormat().getPpv();
    int p1 = x.points(ppv);
    int p2 = y.points(ppv);
    if (p1 < p2) return -1;
    if (p1 > p2) return 1;
    int d1 = x.gDiff();
    int d2 = y.gDiff();
    if (d1 < d2) return -1;
    if (d2 > d1) return 1;
    if (x.getGsc() < y.getGsc()) return -1;
    if (x.getGsc() > y.getGsc()) return 1;
    if (x.getWin() < y.getWin()) return -1;
    if (x.getWin() > y.getWin()) return 1;
    return Integer.compare(x.getId(), y.getId());
  }

  /**
   * Surprise, surprise!
   * Bubble sort is the <B>best</B>sorter for small ranking movements.
   */
  public void sort() {
    int n = l.getSize();
    int last = n-1;
    int prev = 0;
    boolean sorted = false;
    while (!sorted) {
      sorted = true;
      prev = 0;
      for (int i=0; i<last; i++) {
        int x = l.getRank()[i];
        int y = l.getRank()[i+1];
        if (compare(l.getStat()[x], l.getStat()[y]) < 0) {
          int aux = x;
          l.getRank()[i] = y;
          l.getRank()[i+1] = aux;
          sorted = false;
          prev = i;
        }
      }
      last = prev;
    }
  }
}
