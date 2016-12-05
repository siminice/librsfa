import org.rsfa.librsfa.model.*;
import org.rsfa.librsfa.util.Date650;
import org.rsfa.librsfa.util.Syntax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

import static org.rsfa.librsfa.util.Constants.UNKNOWN;

/**
 * Created by radu on 12/4/16.
 */
public class Main {

  private static String path = "";
  private static Fed f;
  private static League l;
  private static int currz;
  private static int currr;
  private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
  private static StatSorter sorter;
  private static LeaguePrinter printer;

  private static void init() {
    f = new Fed("");
    f.loadClubs(path + "teams.dat", Syntax.FIXED);
    f.loadChainedAliases(path + "alias.dat");
    l = new League(f);
    sorter = new StatSorter(l);
    printer = new LeaguePrinter(l);
  }

  private static void addResult() throws IOException{
    int home;
    int away;
    do {
      System.out.print("Home: ");
      String sHome = br.readLine();
      home = l.findMnem(sHome);
    } while (home == UNKNOWN);
    System.out.println(l.nameOf(home));
    do {
      System.out.print("Away: ");
      String sAway = br.readLine();
      away = l.findMnem(sAway);
    } while (away == UNKNOWN);
    System.out.println(l.nameOf(away));
    System.out.print("Score: ");
    String sScore = br.readLine();
    int score = Integer.parseInt(sScore);
    FixtureResult fr = FixtureResult.of(home, away, l.getMetadata().getYear(), 1000*currr+currz, score);
    l.getRes().add(fr);
    l.countResult(fr);
    sorter.sort();
    printer.setHighlight(Arrays.asList(home, away));
    printer.print();
  }

  public static void main(String[] args) {
    init();
    l.load(args[0]);
    l.getRes().getAll().forEach(fr -> l.countResult(fr));
    sorter.sort();
    System.out.println(l);

    int option = 0;
    do {
      System.out.println(String.format("[1] Add [2] Round (%d, %s) [3] Date++ [4] Date-- [0] Exit",
          currr, Date650.decode(currz)));
      try {
        String input = br.readLine();
        option = Integer.parseInt(input);
        switch(option) {
          case 1:
            addResult();
            break;
          case 2:
            System.out.print("Round: ");
            String newrs = br.readLine();
            int newr = Integer.parseInt(newrs);
            currr = newr/1000;
            currz = newr%1000;
            break;
          case 3:
            currz++;
            break;
          case 4:
            currz--;
            break;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } while (option!=0);
  }
}
