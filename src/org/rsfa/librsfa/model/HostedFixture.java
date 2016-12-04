package org.rsfa.librsfa.model;

import lombok.Data;

/**
 * Created by radu on 12/1/16.
 */
@Data
public class HostedFixture extends Fixture {
  private int host;

  public HostedFixture(int h, int a, int w) {
    super(h, a);
    this.host = w;
  }

  public static HostedFixture ofRegular(int h, int a) {
    return new HostedFixture(h, a, h);
  }
}
