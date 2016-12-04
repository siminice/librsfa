package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rsfa.librsfa.util.Constants;

/**
 * Created by radu on 12/1/16.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stat implements Comparable<Stat> {
  private int id = -1;
  private int win = 0;
  private int drw = 0;
  private int los = 0;
  private int gsc = 0;
  private int gre = 0;

  public void reset() {
    win = drw = los = gsc = gre = 0;
  }

  public int numPlayed() {
    return win + drw + los;
  }

  public int points(final int ppv) {
    return ppv * win + drw;
  }

  public int points() { return points(Constants.DEFAULT_PPV); }

  public double pct() {
    final int num = win + drw + los;
    if (num == 0) {
      return 0.0;
    }
    final double pts = 2.0 * win + 1.0 * drw;
    return 50.0 * pts / num;
  }

  public int gDiff() {
    return gsc - gre;
  }

  public void addResult(final int x, final int y) {
    if (x<0 || y<0) return;
    gsc += x;
    gre += y;
    if (x > y) {
      win++;
    } else if (x == y) {
      drw++;
    } else {
      los++;
    }
  }

  public void addResult(final int s) {
    if (s<0) return;
    addResult(s/100, s%100);
  }

  public void addResult(Result r) {
    addResult(r.getX(), r.getY());
  }

  public void addReverseResult(final int s) {
    if (s<0) return;
    addResult(s%100, s/100);
  }

  public void delResult(final int x, final int y) {
    gsc -= x;
    gre -= y;
    if (x > y) {
      win--;
    } else if (x == y) {
      drw--;
    } else {
      los--;
    }
  }

  public void add(Stat s) {
    this.win += s.win;
    this.drw += s.drw;
    this.los += s.los;
    this.gsc += s.gsc;
    this.gre += s.gre;
  }

  public void addReverse(Stat s) {
    this.win += s.los;
    this.drw += s.drw;
    this.los += s.win;
    this.gsc += s.gre;
    this.gre += s.gsc;
  }

  public void remove(Stat s) {
    this.win -= s.win;
    this.drw -= s.drw;
    this.los -= s.los;
    this.gsc -= s.gsc;
    this.gre -= s.gre;
  }

  @Override
  public String toString() {
    return toString(Constants.DEFAULT_PPV);
  }

  public String toString(int ppv) {
    StringBuilder sb = new StringBuilder();
    sb.append(String.format("%3d", numPlayed()))
        .append(String.format("%4d", win))
        .append(String.format("%3d", drw))
        .append(String.format("%3d", los))
        .append(String.format("%4d", gsc)).append("-").append(String.format("%3d", gre))
        .append(String.format("%4dp", points(ppv))).append("  ");
    return sb.toString();
  }

  public String serialize(int ppv) {
    return String.format("%3d %3d %3d %3d %3d %3d %3d %3d",
        id, numPlayed(), win, drw, los, gsc, gre, points(ppv));
  }

  @Override
  public int compareTo(Stat o) {
    if (this.win == o.win && this.drw == o.drw && this.los == o.drw
        && this.gsc == o.gsc && this.gre == o.gre) {
      return Integer.compare(this.id, o.id);
    }
    int p1 = this.points();
    int p2 = o.points();
    if (p1 < p2) return -1;
    if (p1 > p2) return 1;
    int d1 = this.gDiff();
    int d2 = o.gDiff();
    if (d1 < d2) return -1;
    if (d2 > d1) return 1;
    if (this.getGsc() < o.getGsc()) return -1;
    if (this.getGsc() > o.getGsc()) return 1;
    if (this.getWin() < o.getWin()) return -1;
    if (this.getWin() > o.getWin()) return 1;
    return Integer.compare(this.id, o.id);
  }
}
