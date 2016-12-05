package org.rsfa.librsfa.model;

import org.junit.Before;
import org.junit.Test;
import org.rsfa.librsfa.util.Syntax;

import java.util.Collection;

/**
 * Created by radu on 12/1/16.
 */
public class LeagueTests {
  private String ctty = "rom";
  private String ssn = "a.2017";
  private String cPath = "resources/"+ctty;
  private String sFile = cPath+"/"+ssn;

  private Fed f;
  private League l;

  @Before
  public void Init() {
    f = new Fed(ctty);
    f.loadClubs(cPath+"/teams.dat", Syntax.FIXED);
    f.loadChainedAliases(cPath+"/alias.dat");
    l = new League(f);
  }

  public void transformChainedAliases() {
    f.getAliases().forEach(a -> System.out.println(a));
  }

  @Test
  public void testLoad() {
    l.load(sFile);
    StatSorter sorter = new StatSorter(l);
    System.out.println(l);
    int firstRound = l.getRes().firstRound().orElse(1);
    int lastRound = l.getRes().lastRound().orElse(1);
    for (int r=firstRound; r<=lastRound; r++) {
      System.out.println("Round " + r);
      Collection<FixtureResult> rres = l.getRes().round(r);
      System.out.println(l.toString(rres));
      rres.forEach(fr -> l.countResult(fr));
      sorter.sort();
      System.out.println(l);
    }
  }

  @Test
  public void testSerialize() {
    l.load(sFile);
    System.out.println(l.serialize());
  }

  @Test
  public void testSave() {
    l.load(sFile);
    l.save();
  }
}
