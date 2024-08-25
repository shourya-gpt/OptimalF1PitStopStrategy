package com.example.optimal_f1_pit_stop_strategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * A class that provides information about a sepcific lap performed by a driver within a
 * particular Grand Prix in the 2023 season.
 */
public class Lap extends Stint {
  // the assigned number of the driver performing this lap
  private int driver_number;
  // the total time taken, in seconds, to complete the entire lap
  private double lap_duration;
  // the unique identifier for the meeting
  private int lap_number;
  public int getLap_number() {
    return lap_number;
  }
  public void setLap_number(int lap_number) {
    this.lap_number = lap_number;
  }
  public double getLap_duration() {
    return lap_duration;
  }
  public void setLap_duration(double lap_duration) {
    this.lap_duration = lap_duration;
  }

}