package org.rsfa.librsfa.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by radu on 12/1/16.
 */
public class Results {
  @Setter @Getter private int n;
  @Setter @Getter private int m;
  @Setter private Collection<FixtureResult> res;

  Results(int n, int m) {
    this.n = n;
    this.m = m;
    res = new ArrayList<FixtureResult>();
  }

  public List<FixtureResult> vs(int i, int j) {
    return res.stream().filter(r ->
        r.getFixture().getHome() == i && r.getFixture().getAway() == j)
        .collect(Collectors.toList());
  }

  public FixtureResult getResult(int i, int j, int k) {
    List<FixtureResult> resij = vs(i, j);
    if (resij.isEmpty() || k<0 || k>=resij.size()) return new FixtureResult();
    return resij.get(k);
  }

  public void add(FixtureResult r) {
    res.add(r);
  }

  public Stat vsStat(int i, int j) {
    Stat s = new Stat();
    vs(i, j).forEach(r -> s.addResult(r.getResult()));
    return s;
  }

  public Collection<FixtureResult> round(int r) {
    return res.stream()
        .filter(x -> x.getFixture().getRound() == r)
        .sorted(FixtureResult.byDate)
        .collect(Collectors.toList());
  }

  public Collection<FixtureResult> date(LocalDate z) {
    return res.stream()
      .filter(x -> x.getResult().getZ().isEqual(z))
        .collect(Collectors.toList());
  }

  /*
  public Map<Integer, Collection<FixtureResult>> byRound(int r) {
    return res.stream()
        .collect(Collectors.groupingBy(x -> x.getFixture().getRound(),
            Collectors.mapping(Function.identity(), Collectors.toSet()));
  }
*/

  public Collection<FixtureResult> filterByTeam(int t) {
    return res.stream()
        .filter(r -> r.getFixture().has(t))
        .collect(Collectors.toList());
  }

  public String serialize(int i, int k) {
    StringBuilder sb = new StringBuilder();
    for (int j=0; j<n; j++) {
      sb.append(getResult(i, j, k).serialize()+" ");
    }
    return sb.toString();
  }

  public String serialize() {
    StringBuilder sb = new StringBuilder();
    for (int k=0; k<m; k++) {
      for (int i=0; i<n; i++) {
        sb.append(serialize(i, k)+"\n");
      }
    }
    return sb.toString();
  }

  public Map<Integer, Long> roundsFreq() {
    return res.stream()
        .map(r -> r.getFixture().getRound())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  public Map<Integer, Long> roundsFreqByTeam(int t) {
    return res.stream()
        .filter(r -> r.getFixture().has(t))
        .map(r -> r.getFixture().getRound())
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
  }

  public Set<Integer> availableRounds(int t) {
    Set<Integer> allRounds = res.stream()
        .map(r -> r.getFixture().getRound())
        .filter(r -> r>0)
        .collect(Collectors.toSet());
    allRounds.removeAll(roundsFreqByTeam(t).keySet());
    return allRounds;
  }

  public Set<Integer> availableRounds(int h, int a) {
    Set<Integer> avh = availableRounds(h);
    Set<Integer> ava = availableRounds(a);
    avh.retainAll(ava);
    return avh;
  }

  public OptionalInt firstRound() {
    return res.stream()
        .mapToInt(r -> r.getFixture().getRound())
        .filter(r -> r > 0)
        .min();
  }

  public OptionalInt lastRound() {
    return res.stream()
        .mapToInt(r -> r.getFixture().getRound())
        .max();
  }

}
