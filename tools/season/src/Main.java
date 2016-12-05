import org.rsfa.librsfa.model.Fed;
import org.rsfa.librsfa.model.FixtureResult;
import org.rsfa.librsfa.model.League;
import org.rsfa.librsfa.model.StatSorter;
import org.rsfa.librsfa.util.Syntax;

import java.util.Collection;

/**
 * Created by radu on 12/4/16.
 */
public class Main {

  private static Fed f;
  private static League l;

  private static void init() {
    f = new Fed("");
    f.loadClubs("./teams.dat", Syntax.FIXED);
    f.loadChainedAliases("./alias.dat");
    l = new League(f);
  }

  public static void main(String[] args) {
    init();
    l.load(args[0]);
    StatSorter sorter = new StatSorter(l);
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
}
