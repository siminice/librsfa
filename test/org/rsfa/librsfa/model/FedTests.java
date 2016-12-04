package org.rsfa.librsfa.model;

import org.junit.Assert;
import org.junit.Test;
import org.rsfa.librsfa.util.Syntax;

/**
 * Created by radu on 11/30/16.
 */
public class FedTests {

  private static final int testId = 0;
  private static final String testNickname0 = "Newcastle";
  private static final String testNickname1 = "Lincoln";

  @Test
  public void TestLoadFixed() {
    Fed fed = new Fed("ctty");
    fed.loadClubs("resources/gib/teams.dat", Syntax.FIXED);
    fed.loadAliases("resources/gib/alias.dat");
    Assert.assertTrue(fed.nickOf(testId, 2005).equals(testNickname0));
    Assert.assertTrue(fed.nickOf(testId, 2015).equals(testNickname1));
  }

  @Test
  public void TestLoadVariable() {
    Fed fed = new Fed("ctty");
    fed.loadClubs("resources/gib/webteams.dat", Syntax.VARIABLE);
    fed.loadAliases("resources/gib/alias.dat");
    Assert.assertTrue(fed.nickOf(testId, 2005).equals(testNickname0));
    Assert.assertTrue(fed.nickOf(testId, 2015).equals(testNickname1));
  }
}
