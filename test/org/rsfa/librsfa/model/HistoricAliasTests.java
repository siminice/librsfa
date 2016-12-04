package org.rsfa.librsfa.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by radu on 11/30/16.
 */
public class HistoricAliasTests {
  private static final String testFullName = "Fullname";
  private static final String testNickName = "Nickname";

  @Test
  public void TestParse() {
    HistoricAlias ha = HistoricAlias.parse("[1899-01-01:2016-06-03]="
        + testFullName + "~" + testNickName);
    Interval i = ha.getInterval();
    Alias a = ha.getAlias();
    Assert.assertTrue(a.getFullName().equals(testFullName));
    Assert.assertTrue(a.getNickName().equals(testNickName));
    Assert.assertEquals(i.getStart().getYear(), 1899);
    Assert.assertEquals(i.getEnd().getYear(), 2016);
  }
}
