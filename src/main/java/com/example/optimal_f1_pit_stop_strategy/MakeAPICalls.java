package com.example.optimal_f1_pit_stop_strategy;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.File;

public class MakeAPICalls {

  private ArrayList<Race> racesSince23;

  public static void main(String[] args) {
    // TODO delete?
    MakeAPICalls a = new MakeAPICalls();
    a.loadAPIData("Suzuka");
  }

  /**
   * Calls methods from the OpenF1 API to load the race data, stint data, and lap data for this
   * circuit in the exact order mentioned. It uses Jackson to parse the JSON data and loads them
   * into their respective classes and data structures.
   * 
   * @param circuit_short_name the circuit name for which to load the data from the OpenF1 API
   */
  public void loadAPIData(String circuit_short_name) {
    OkHttpClient okhttpclient = new OkHttpClient();

    /**
     * For a specific race in the 2023 season, 1. Get all of the stints for a specific driver Then
     * for each the three tyre compounds used i) get all the different StintObjects performed by
     * different drivers ii) for each of those stints perform an analysis to find a metric on
     * average tyre degradation for that race track in the 2023 season
     */

    // Make API calls to OpenF1 and store data in data structures
    // Request through the API, get and store all the Race objects (for each possible year) in an

    String url1 =
        "https://api.openf1.org/v1/sessions?session_type=Race&session_name=Race&circuit_short_name="
            + circuit_short_name;

    Request request1 = new Request.Builder().url(url1).build();
    String stringResponse1;

    // Initialize the ArrayList containing all the possible Race objects since 2023
    racesSince23 = new ArrayList<Race>();

    try (Response response1 = okhttpclient.newCall(request1).execute()) {
      if (response1.isSuccessful()) {
        // Store the contents of the race in a string object
        ObjectMapper objectMapper = new ObjectMapper();
        stringResponse1 = response1.body().string();
        racesSince23 = objectMapper.readValue(stringResponse1,
            objectMapper.getTypeFactory().constructCollectionType(List.class, Race.class));
        // racesSince23 = objectMapper.readValue(json1,
        // objectMapper.getTypeFactory().constructCollectionType(List.class, Race.class));
        for (Race race1 : racesSince23) {
          System.out.println(race1.getSession_key()); // TODO remove
        }
      } else {
        System.out.println("Request failed: " + response1.message());
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    // For each of the Race sessions, store the three different types of stints performed in
    // different variables: stintsOnSoft, stintsOnMedium, stintsOnHard
    for (Race race : racesSince23) {
      // Now, create a request to the OpenF1 api, requesting all the stints peformed on specific
      // tyre compounds within that race session, and save them in a collection of Stint objects
      // (eg. stintsOnSoft collection of Stint objects in a Race object)
      // A string array which stores all the different tyre compounds
      RedBlackTree<Stint> rbtOfStints = new RedBlackTree<Stint>();
      ArrayList<Stint> listOfStints = new ArrayList<Stint>(); // TODO delete the use of an ArrayList
                                                              // later
      String[] compounds = {"SOFT", "MEDIUM", "HARD"};
      for (int i = 0; i < 3; i++) {
        String requestURLForStints = "https://api.openf1.org/v1/stints?session_key="
            + race.getSession_key() + "&compound=" + compounds[i];
        Request request2 = new Request.Builder().url(requestURLForStints).build();
        // Now, process the response
        try (Response response2 = okhttpclient.newCall(request2).execute()) {
          if (response2.isSuccessful()) {
            String stringResponse2 = response2.body().string();

            // Use Jackson ObjectMapper to first write the values into a json file
            ObjectMapper objectMapper = new ObjectMapper();
            listOfStints = objectMapper.readValue(stringResponse2,
                objectMapper.getTypeFactory().constructCollectionType(List.class, Stint.class));
            // TODO delete the use of an ArrayList later
            System.out.println(race.getSession_key() + " " + listOfStints.size()); // TODO remove
            for (Stint stint : listOfStints) {
              System.out.println(stint + " " + "Lap Details: "); // TODO remove
              System.out.println();
              addLapsForStint(stint);
              rbtOfStints.insert(stint);
            }
            if (compounds[i].equals("SOFT"))
              race.setStintsOnSoft(rbtOfStints);
            else if (compounds[i].equals("MEDIUM"))
              race.setStintsOnMedium(rbtOfStints);
            else
              race.setStintsOnHard(rbtOfStints);

          } else {
            System.out.println("Request failed: " + response2.message());
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

    }

  }

  /**
   * This method queries the OpenF1 API, and find and stores the Lap objects associated with the
   * specific stint performed by a driver_number in a session_key
   * 
   * @param stint the Stint object for which to query
   */
  private void addLapsForStint(Stint stint) {
    OkHttpClient okhttpclient = new OkHttpClient();
    // Stores the lap numbers performed by driver_number within this stint
    ArrayList<Integer> lapNumbers = new ArrayList<Integer>();
    // TODO look at specific calculatins for lap_start and lap_end
    for (int i = stint.getLap_start(); i <= stint.getLap_end(); i++) {
      lapNumbers.add(i);
    }

    RedBlackTree<Lap> rbtOfLaps = new RedBlackTree<Lap>();
    ArrayList<Lap> listOfLaps = new ArrayList<Lap>(); // TODO delete the use of an ArrayList
                                                      // later

    for (Integer lap_number : lapNumbers) {
      String requestURLForLaps =
          "https://api.openf1.org/v1/laps?session_key=" + stint.getSession_key() + "&driver_number="
              + stint.getDriver_number() + "&lap_number=" + lap_number;
      Request request = new Request.Builder().url(requestURLForLaps).build();
      // Now, process the response
      try (Response response = okhttpclient.newCall(request).execute()) {
        if (response.isSuccessful()) {
          String stringResponse = response.body().string();

          // Use Jackson ObjectMapper to first write the values into a json file
          ObjectMapper objectMapper = new ObjectMapper();
          listOfLaps = objectMapper.readValue(stringResponse,
              objectMapper.getTypeFactory().constructCollectionType(List.class, Lap.class));
          // TODO delete the use of an ArrayList later
          for (Lap lap : listOfLaps) {
            rbtOfLaps.insert(lap);
            System.out.print(lap.getLap_number() + ": " + lap.getLap_duration() + ", "); // TODO
                                                                                          // remove
          }

          stint.setLaps(rbtOfLaps);

        } else {
          System.out.println("Request failed: " + response.message());
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }



}
