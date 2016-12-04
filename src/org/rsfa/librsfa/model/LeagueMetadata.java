package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by radu on 12/1/16.
 */
@Data
@AllArgsConstructor
public class LeagueMetadata {
  private int year;
  private int tier;
  private int pool;

}
