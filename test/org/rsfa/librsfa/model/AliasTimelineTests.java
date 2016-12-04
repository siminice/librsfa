package org.rsfa.librsfa.model;

import org.junit.Test;
import org.junit.Assert;

/**
 * Created by radu on 11/30/16.
 */
public class AliasTimelineTests {
  private static final String testFullName = "Fullname";
  private static final String testFullNamePlus = "Fullname+";
  private static final String testNickName = "Nickname";

  @Test
  public void TestParse() {
    String a1 = "[1899-01-01:2016-06-03]=" + testFullName + "~" + testNickName;
    String a2 = "[2016-06-04:2017-12-31]=" + testFullNamePlus + "~" + testNickName;
    AliasTimeline atl = AliasTimeline.parse(a1 + "*" + a2);
    Assert.assertEquals(atl.getName(1990), testFullName);
    Assert.assertEquals(atl.getName(2017), testFullNamePlus);
  }
}
