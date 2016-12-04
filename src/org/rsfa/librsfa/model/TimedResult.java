package org.rsfa.librsfa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.rsfa.librsfa.util.Constants;

import java.time.LocalDate;

/**
 * Created by radu on 12/1/16.
 */
@Data
@AllArgsConstructor
public class TimedResult extends Result {

  private LocalDate z = LocalDate.MIN;

  public TimedResult(int x, int y, LocalDate z) {
    super(x, y);
    this.z = z;
  }

  public TimedResult(int x, int y) {
    super(x, y);
  }

  public TimedResult(int s) {
    this(s/100, s%100);
  }

  public TimedResult(Result r) {
    setX(r.getX());
    setY(r.getY());
    if (r instanceof TimedResult) {
      setZ(((TimedResult) r).getZ());
    }
  }

  public Result toResult() {
    return new  Result(getX(), getY());
  }

  public static TimedResult of(int s, LocalDate z) {
    return new TimedResult(s/100, s%100, z);
  }

  public static final TimedResult UNKNOWN = TimedResult.of(Constants.UNKNOWN, LocalDate.MIN);

  public String serialize() {
    if (z.getYear() < 0) {
      return String.format("%3d %4s", Constants.UNKNOWN, super.serialize());
    } else {
      return String.format("%03d %4s", 50 * z.getMonthValue() + z.getDayOfMonth(), super.serialize());
    }
  }
}
