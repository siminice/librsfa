package org.rsfa.librsfa.model;

import lombok.Getter;
import org.rsfa.librsfa.util.Syntax;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.rsfa.librsfa.util.Constants.UNKNOWN;

/**
 * Created by radu on 11/30/16.
 */
public class Fed {
  private @Getter int size;
  private @Getter final String ctty;
  private Club[] club;

  public Fed(final String ct) {
    ctty = ct;
    size = 0;
  }

  public void loadClubs(final String ifile, final Syntax style) {
    try {
      final FileInputStream fstream = new FileInputStream(ifile);
      final InputStreamReader dis = new InputStreamReader(fstream, "ISO-8859-2");
      final BufferedReader br = new BufferedReader(dis);
      String s = br.readLine();
      final String[] tkh = s.split(" ");
      size = Integer.parseInt(tkh[0]);
      club = new Club[size];
      for (int i = 0; i < size; ++i) {
        s = br.readLine();
        club[i] = Club.parse(s, style);
      }
      br.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void loadAliases(final String afile) {
    try {
      final FileInputStream fstream = new FileInputStream(afile);
      final InputStreamReader dis = new InputStreamReader(fstream, "ISO-8859-2");
      final BufferedReader br = new BufferedReader(dis);
      for (int i = 0; i < size; i++) {
        String line = br.readLine();
        if (line != null) {
          club[i].setAlias(AliasTimeline.parse(line));
        }
      }
      br.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public void loadChainedAliases(final String afile) {
    try {
      final FileInputStream fstream = new FileInputStream(afile);
      final InputStreamReader dis = new InputStreamReader(fstream, "ISO-8859-2");
      final BufferedReader br = new BufferedReader(dis);

      for (int i = 0; i < size; i++) {
        String line = br.readLine();
        if (line != null) {
          club[i].setAlias(AliasTimeline.chainedParse(line));
        }
      }
      br.close();
    } catch (final Exception e) {
      e.printStackTrace();
    }
  }

  public Collection<AliasTimeline> getAliases() {
    return Stream.of(club)
        .map(c -> c.getAlias())
        .collect(Collectors.toList());
  }

  public Club getClub(int i) {
    return (i>=0 && i<size)? club[i] : null;
  }

  public String nameOf(final int i, final int y) {
    if (i < 0 || i >= size) {
      return null;
    }
    return club[i].getAlias().getName(y);
  }

  public String nickOf(final int i, final int y) {
    if (i < 0 || i >= size) {
      return null;
    }
    return club[i].getAlias().getNick(y);
  }

  public String getMnem(final int i) {
    if (i < 0 || i >= size) {
      return null;
    }
    return club[i].getMnem();
  }

  public int findMnem(final String s) {
    // start with capital letter;
    final String us = s.substring(0, 1).toUpperCase() + s.substring(1);
    final String ts = us.replaceAll("_", " ");

    // exact match
    for (int i = 0; i < size; ++i) {
      if (ts.equals(club[i].getMnem())) {
        return i;
      }
    }

    // Substring
    for (int i = 0; i < size; ++i) {
      if (club[i].getMnem().contains(ts)) {
        return i;
      }
    }

    // try uppercase all
    final String cs = ts.toUpperCase();
    for (int i = 0; i < size; ++i) {
      if (club[i].getMnem().contains(cs)) {
        return i;
      }
    }

    return UNKNOWN;
  }

}
