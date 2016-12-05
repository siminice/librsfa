package org.rsfa.librsfa.model;

import lombok.Setter;
import org.rsfa.librsfa.util.Constants;
import org.rsfa.librsfa.util.TermColors;

import java.util.Collection;

/**
 * Created by radu on 12/4/16.
 */
public class LeaguePrinter {
  private @Setter League l;
  private @Setter Collection<Integer> highlight;

  public LeaguePrinter(League l) {
    this.l = l;
  }

  public boolean isHighlighted(int x) {
    return highlight.contains(x);
  }

  public void print() {
    StringBuilder sb = new StringBuilder();
    int p1 = l.getFormat().getPromo()[0];
    int p2 = l.getFormat().getPromo()[1];
    int r1 = l.getFormat().getReleg()[0];
    int r2 = l.getFormat().getReleg()[1];
    for (int i=0; i<l.getSize(); i++) {
      int x = l.getRank()[i];
      if (isHighlighted(x)) sb.append(TermColors.yellow);
      sb.append((i+1) + ".");
      sb.append(String.format("%-30s", l.nameOf(x)));
      sb.append("\t");
      sb.append(l.getStat()[x].toString(l.getFormat().getPpv()));
      sb.append(l.getDeco()[x]);
      if (isHighlighted(x)) sb.append(TermColors.normal);
      sb.append("\n");
      if (i==p1-1) sb.append(Constants.SINGLE_LINE_CONT);
      if (i==p1+p2-1 && p2>0) sb.append(Constants.SINGLE_LINE_DASH);
      if (i==l.getSize()-(r1+r2)-1 && r2>0) sb.append(Constants.SINGLE_LINE_DASH);
      if (i==l.getSize()-r1-1 && r1>0) sb.append(Constants.SINGLE_LINE_CONT);
    }
    System.out.println(sb.toString());
  }
}
