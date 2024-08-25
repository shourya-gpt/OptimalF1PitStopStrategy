package com.example.optimal_f1_pit_stop_strategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * This class models a Race object and provides important information for identificaiton and its
 * characteristics.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Race {

  // A red black tree of Stint objects storing a collection of the stints performed by any drivers
  // during this race session with the given tyre compund
  @JsonIgnore // these fields are not written through deserialization by Jackson
  private RedBlackTree<Stint> stintsOnSoft;
  @JsonIgnore // these fields are not written through deserialization by Jackson
  private RedBlackTree<Stint> stintsOnMedium;
  @JsonIgnore // these fields are not written through deserialization by Jackson
  private RedBlackTree<Stint> stintsOnHard;
  // the unique identifier for the race session correspoding to this race
  private int session_key;
  // the year the event takes place
  private int year;
  // a short name for the circut where this race session takes place
  private String circuit_short_name;
  // the full name for the country where this event takes place
  private String country_name;
  // the city of geographical location where this event takes place
  private String location;

  public int getSession_key() {
    return session_key;
  }

  public void setSession_key(int session_key) {
    this.session_key = session_key;
  }

  public int getYear() {
    return year;
  }

  public void setYear(int year) {
    this.year = year;
  }

  public String getCircuit_short_name() {
    return circuit_short_name;
  }

  public void setCircuit_short_name(String circuit_short_name) {
    this.circuit_short_name = circuit_short_name;
  }

  public String getCountry_name() {
    return country_name;
  }

  public void setCountry_name(String country_name) {
    this.country_name = country_name;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return this.getCountry_name() + " " + this.getLocation() + " " + this.getCircuit_short_name()
        + " " + this.getSession_key() + " " + this.getYear();
  }

  public RedBlackTree<Stint> getStintsOnSoft() {
    return stintsOnSoft;
  }

  public void setStintsOnSoft(RedBlackTree<Stint> stintsOnSoft) {
    this.stintsOnSoft = stintsOnSoft;
  }

  public RedBlackTree<Stint> getStintsOnHard() {
    return stintsOnHard;
  }

  public void setStintsOnHard(RedBlackTree<Stint> stintsOnHard) {
    this.stintsOnHard = stintsOnHard;
  }

  public RedBlackTree<Stint> getStintsOnMedium() {
    return stintsOnMedium;
  }

  public void setStintsOnMedium(RedBlackTree<Stint> stintsOnMedium) {
    this.stintsOnMedium = stintsOnMedium;
  }
}
