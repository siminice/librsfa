package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Created by radu on 11/30/16.
 */
@NoArgsConstructor
@AllArgsConstructor
public class Alias {

  private static final String NICK_MARKER = "~";
  private static final String NICK_START_SEP = "(";
  private static final String NICK_END_SEP = ")";

  @Getter
  private String fullName = "";
  @Getter
  private String nickName = "";

  public static Alias parse(final String s) {
    String parts[] = s.split(NICK_MARKER);
    if (parts.length > 1) {
      return new Alias(parts[0], parts[1]);
    } else {
      return new Alias(parts[0], parts[0]);
    }
  }

  @Override
  public String toString() {
    return fullName + NICK_MARKER + NICK_START_SEP + nickName + NICK_END_SEP;
  }
}


