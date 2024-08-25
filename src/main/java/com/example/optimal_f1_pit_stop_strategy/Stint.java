package com.example.optimal_f1_pit_stop_strategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * A class that provides information about a stint performed by a specific driver within a
 * particular Grand Prix in the 2023 season.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Stint extends Race {
  // A collection of all the laps performed by driver_number in this specific stint
  @JsonIgnore // So that the Jackson deserialization process ignores an attempt at assigning a value
              // to this.laps
  private RedBlackTree<Lap> laps;
  // 0 = HARD, 1 = MEDIUM, 2 = SOFT
  private String compound;
  // the assigned number of the driver performing this stint
  private int driver_number;
  // number of the last lap completed within this stint
  private int lap_start;
  // number of the initial lap in this stint, starting at 1
  private int lap_end;
  // the sequential number of stint within this session, starting at 1
  private int stint_number;
  // the age of the tyres at the start of the stint, in laps completed
  private int tyre_age_at_start;

  public String getCompound() {
    return compound;
  }

  public void setCompund(String compound) {
    this.compound = compound;
  }

  public int getLap_start() {
    return lap_start;
  }

  public void setLap_start(int lap_start) {
    this.lap_start = lap_start;
  }

  public int getDriver_number() {
    return driver_number;
  }

  public void setDriver_number(int driver_number) {
    this.driver_number = driver_number;
  }

  public int getLap_end() {
    return lap_end;
  }

  public void setLap_end(int lap_end) {
    this.lap_end = lap_end;
  }

  public int getStint_number() {
    return stint_number;
  }

  public void setStint_number(int stint_number) {
    this.stint_number = stint_number;
  }

  public int getTyre_age_at_start() {
    return tyre_age_at_start;
  }

  public void setTyre_age_at_start(int tyre_age_at_start) {
    this.tyre_age_at_start = tyre_age_at_start;
  }

  public RedBlackTree<Lap> getLaps() {
    return laps;
  }

  public void setLaps(RedBlackTree<Lap> laps) {
    this.laps = laps;
  }
  
  // Just for testing
  @Override
  public String toString() {
    return this.getCompound() + " " + this.getDriver_number() + " " + this.getSession_key() + " " + this.getStint_number() + " " + this.getTyre_age_at_start();
  }

}
