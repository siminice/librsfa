package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.rsfa.librsfa.util.Syntax;

import static org.rsfa.librsfa.util.Syntax.FIXED;

/**
 * Created by radu on 11/30/16.
 */
@AllArgsConstructor
public class Club {
  @Getter
  private final String name;
  @Getter
  private final String mnem;
  @Getter @Setter
  private AliasTimeline alias;

  public static Club parse(String s, Syntax style) {
    if (style.equals(FIXED)) {
      return new Club(s.substring(15), s.substring(0, 15).trim(), null);
    } else {
      final String[] tkc = s.split(",");
      return new Club(tkc[1], tkc[0].trim(), null);
    }

  }
}
