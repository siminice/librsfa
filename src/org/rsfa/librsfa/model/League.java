package org.rsfa.librsfa.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rsfa.librsfa.util.Constants;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.StringTokenizer;

/**
 * Created by radu on 11/30/16.
 */
@NoArgsConstructor
public class League {
  @Setter private Fed fed;
  @Setter private String filename;
  @Getter @Setter private LeagueFormat format;
  @Getter @Setter private LeagueMetadata metadata;
  @Getter @Setter private int size = 0;
  @Getter private int[] id;
  @Getter private int[] rank;
  @Getter private Stat[] stat;
  @Getter @Setter private String[] deco;
  @Getter @Setter private int[] pen;
  @Getter @Setter private int[] pdt;
  @Getter private Results res;
  @Getter private int numr;

  public League(Fed f) {
    fed = f;
  }

  public void load(String ifile) {
    size = 0;
    filename = ifile;
    try {
      String tkf[] = ifile.split("\\.");
      setMetadata(new LeagueMetadata(
        Integer.parseInt(tkf[1]),
        tkf[0].getBytes()[0] - 96, 0));

      final FileInputStream fstream = new FileInputStream(ifile);
      final DataInputStream dis = new DataInputStream(fstream);
      final BufferedReader br = new BufferedReader(new InputStreamReader(dis));
      String line = br.readLine().trim();
      final String[] tkh = line.split(" ");

      size = Integer.parseInt(tkh[0]);
      format = new LeagueFormat();
      id = new int[size];
      rank = new int[size];
      stat = new Stat[size];
      pen = new int[size];
      pdt = new int[size];
      deco = new String[size];

      format.setPpv(Integer.parseInt(tkh[1]));
      int tb = Integer.parseInt(tkh[2]);
      numr = 1 + tb/Constants.NUMORD;
      format.setSize(size);
      format.setRounds(numr);
      format.setTbr(tb);
      int[] promo = new int[2];
      int[] releg = new int[2];
      promo[0] = Integer.parseInt(tkh[3]);
      promo[1] = Integer.parseInt(tkh[4]);
      releg[0] = Integer.parseInt(tkh[5]);
      releg[1] = Integer.parseInt(tkh[6]);
      format.setPromo(promo);
      format.setReleg(releg);

      for (int i = 0; i < size; i++) {
        line = br.readLine().trim();
        final StringTokenizer tkt = new StringTokenizer(line);
        final int ntk = tkt.countTokens();
        if (ntk >= 8) {
          final String sid = tkt.nextToken();
          final int x = Integer.parseInt(sid);
          tkt.nextToken(); // dummy
          int w = Integer.parseInt(tkt.nextToken());
          int d = Integer.parseInt(tkt.nextToken());
          int l = Integer.parseInt(tkt.nextToken());
          int s = Integer.parseInt(tkt.nextToken());
          int r = Integer.parseInt(tkt.nextToken());
          tkt.nextToken(); // pts
          id[i] = x;

          if (ntk >= 10) {
            pen[i] = Integer.parseInt(tkt.nextToken());
            pdt[i] = Integer.parseInt(tkt.nextToken());
          } else {
            pen[i] = 0; pdt[i] = 0;
          }
          if (ntk >= 11) deco[i] = tkt.nextToken(); else deco[i] = "";
        }
        stat[i] = new Stat();
        stat[i].setId(id[i]);
        rank[i] = i;
      }

      res = new Results(size, numr);

      for (int k = 0; k < numr; k++) {
        for (int i = 0; i < size; i++) {
          line = br.readLine().trim();
          final String[] tkr = line.split(" +");
          if (tkr.length < 2 * size) {
            System.out.println("Input results error on line " + i
                + ", " + tkr.length + " tokens instead of "
                + (2 * size));
          }
          for (int j = 0; j < size; j++) {
            if (2 * j + 1 < tkr.length) {
              int rz = Integer.parseInt(tkr[2 * j]);
              int s = Integer.parseInt(tkr[2 * j + 1]);
              stat[i].addResult(s);
              stat[j].addReverseResult(s);
              FixtureResult fr = FixtureResult.of(i, j, metadata.getYear(), rz, s);
              if (s>=0) { res.add(fr); }
            }
          }
        }
      }
      dis.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void save() {
  }

  public String nameOf(int i) {
    return fed.nameOf(id[i], metadata.getYear());
  }

  public String nickOf(int i) {
    return fed.nickOf(id[i], metadata.getYear());
  }

  public int rankOf(int t) {
    for (int i=0; i<size; i++) {
      if (rank[i]==t) return i;
    }
    return Constants.UNKNOWN;
  }

  public String toString(FixtureResult fr) {
    Fixture f = fr.getFixture();
    TimedResult r = (TimedResult) fr.getResult();
    return String.format("[R%2d] %s: %-15s %d-%d %-15s",
        f.getRound(), r.getZ(),
        nickOf(f.getHome()),
        r.getX(),
        r.getY(),
        nickOf(f.getAway())
        );
  }

  public String toString(Collection<FixtureResult> fr) {
    StringBuilder sb = new StringBuilder();
    fr.forEach(r -> sb.append(toString(r)+"\n"));
    return sb.toString();
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int p1 = format.getPromo()[0];
    int p2 = format.getPromo()[1];
    int r1 = format.getReleg()[0];
    int r2 = format.getReleg()[1];
    for (int i=0; i<size; i++) {
      int x = getRank()[i];
      sb.append((i+1) + ".");
      sb.append(String.format("%-30s", fed.nameOf(id[x], metadata.getYear())));
      sb.append("\t");
      sb.append(stat[x].toString(format.getPpv()));
      sb.append(deco[x]);
      sb.append("\n");
      if (i==p1-1) sb.append(Constants.SINGLE_LINE_CONT);
      if (i==p1+p2-1 && p2>0) sb.append(Constants.SINGLE_LINE_DASH);
      if (i==size-(r1+r2)-1 && r2>0) sb.append(Constants.SINGLE_LINE_DASH);
      if (i==size-r1-1 && r1>0) sb.append(Constants.SINGLE_LINE_CONT);
    }
    return sb.toString();
  }

  public String statLine(int i) {
    StringBuilder sb = new StringBuilder();
    sb.append(stat[i].serialize(format.getPpv()));
    if (pen[i]!=0 || deco[i].length()>0) sb.append(String.format(" %3d %3d", pen[i], pdt[i]));
    if (deco[i].length()>0) {
      sb.append(" " + deco[i]);
    }
    return sb.toString();
  }

  public String results(int i, int k) {
    return res.serialize(i, k);
  }

  public String serialize() {
    StringBuilder sb = new StringBuilder();
    sb.append(getFormat().toString()+"\n");
    for (int i=0; i<size; i++) {
      sb.append(statLine(i)+"\n");
    }
    sb.append(res.serialize());
    return sb.toString();
  }
}
