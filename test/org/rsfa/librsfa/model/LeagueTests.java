package org.rsfa.librsfa.model;

import org.junit.Before;
import org.junit.Test;
import org.rsfa.librsfa.util.Syntax;

/**
 * Created by radu on 12/1/16.
 */
public class LeagueTests {
  private String ctty = "rom";
  private String ssn = "a.2017";

  private Fed f;
  private League l;

  @Before
  public void Init() {
    f = new Fed(ctty);
    f.loadClubs("resources/"+ctty+"/teams.dat", Syntax.FIXED);
    f.loadChainedAliases("resources/"+ctty+"/alias.dat");
    l = new League(f);
  }

  public void transformChainedAliases() {
    f.getAliases().forEach(a -> System.out.println(a));
  }

  @Test
  public void testLoad() {
    l.load("resources/"+ctty+"/"+ssn);

    StatSorter sorter = new StatSorter(l);
    sorter.sort();

    System.out.println(l);

    System.out.println("Missing rounds for " + l.nameOf(2) + ": " + l.getRes().availableRounds(2));
    System.out.println(l.getRes().roundsFreqByTeam(2));
    System.out.println(l.toString(l.getRes().round(1)));

  }

  @Test
  public void testSerialize() {
    l.load("resources/"+ctty+"/"+ssn);
    System.out.println(l.serialize());
  }
}
