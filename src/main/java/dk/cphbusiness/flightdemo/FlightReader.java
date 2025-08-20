package dk.cphbusiness.flightdemo;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.cphbusiness.flightdemo.dtos.FlightDTO;
import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;
import dk.cphbusiness.utils.Utils;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * Purpose:
 *
 * @author: Thomas Hartmann
 */
public class FlightReader {

    private static List<FlightDTO> flightList;
    private static List<FlightInfoDTO> flightInfoList;
    private static FlightServices flightServices = new FlightServices();

    public static void main(String[] args) {
        try {
            flightList = getFlightsFromFile("flights.json");
            flightInfoList = getFlightInfoDetails(flightList).stream().distinct().toList();
        } catch (IOException e) {
            e.printStackTrace();
        }

//       showAllFlights();
//        exercise_one();
//        exercise_two();
//        exercise_three();
//        exercise_four();
//        exercise_five();
//        exercise_six();
        exercise_seven();
    }

    private static void showAllFlights() {
        System.out.printf("All flights. Count: %d%n", flightInfoList.size());
        flightInfoList.forEach(System.out::println);
    }


    private static void exercise_one() {
        System.out.println("Exercise 1 started");
        String airline = "Lufthansa";
        double average = flightServices.getAverageFligthTime(flightInfoList, airline);
        System.out.printf("Average flight time for %s: %.2f%n", airline, average);
        System.out.println("Nice formatted: " + Utils.convertDoubleInMinutesToString(average));
    }

    private static void exercise_two() {
        System.out.println("Exercise 2 started");
        String origin = "Fukuoka";
        String destination = "Haneda Airport";
        List<FlightInfoDTO> flights = flightServices.getFlightRouteConnections(origin, destination, flightInfoList);
        System.out.printf("Flights operating between %s and %s. Count: %d:%n", origin, destination, flights.size());
        flights.forEach(System.out::println);
    }

    private static void exercise_three() {
        System.out.println("Exercise 3 started");
        LocalTime cutoff = LocalTime.of(0, 15, 0);
        List<FlightInfoDTO> flights = flightServices.getFlightBefore(cutoff, flightInfoList);
        System.out.printf("Flights before %s. Count: %d%n", cutoff, flights.size());
        flights.forEach(System.out::println);

    }

    private static void exercise_four() {
        System.out.println("Exercise 4 started");
        Map<String, Double> averageDurations = flightServices.averageFlightTimePerAirline(flightInfoList);
        averageDurations.forEach((airline, avg) ->
                System.out.printf("%s: %.2f%n", airline, avg)
        );
    }

    private static void exercise_five() {
        System.out.println("Exercise 5 started");
        List<FlightInfoDTO> flights = flightServices.getFlightsSortedByArrival(flightInfoList);
        System.out.println("Flights sorted by arrival:");
        flights.forEach(System.out::println);
    }

    private static void exercise_six() {
        System.out.println("Exercise 6 started");
        Map<String, Double> totalDurations = flightServices.sumTotalFlightTimePerAirline(flightInfoList);
        totalDurations.forEach((airline, sum) ->
                System.out.printf("%s: %s hours %n", airline, Utils.convertDoubleInMinutesToString(sum))
        );
    }

    private static void exercise_seven() {
        System.out.println("Exercise 7 started");
        List<FlightInfoDTO> flights = flightServices.getFlightsSortedByArrival(flightInfoList);
        System.out.println("Flights sorted by duration ascending:");
        flights.forEach(System.out::println);
    }


    public static List<FlightDTO> getFlightsFromFile(String filename) throws IOException {

        ObjectMapper objectMapper = Utils.getObjectMapper();

        // Deserialize JSON from a file into FlightDTO[]
        FlightDTO[] flightsArray = objectMapper.readValue(Paths.get("flights.json").toFile(), FlightDTO[].class);

        // Convert to a list
        List<FlightDTO> flightsList = List.of(flightsArray);
        return flightsList;
    }

    public static List<FlightInfoDTO> getFlightInfoDetails(List<FlightDTO> flightList) {
        List<FlightInfoDTO> flightInfoList = flightList.stream()
           .map(flight -> {
                LocalDateTime departure = flight.getDeparture().getScheduled();
                LocalDateTime arrival = flight.getArrival().getScheduled();
                Duration duration = Duration.between(departure, arrival);
                FlightInfoDTO flightInfo =
                        FlightInfoDTO.builder()
                            .name(flight.getFlight().getNumber())
                            .iata(flight.getFlight().getIata())
                            .airline(flight.getAirline().getName())
                            .duration(duration)
                            .departure(departure)
                            .arrival(arrival)
                            .origin(flight.getDeparture().getAirport())
                            .destination(flight.getArrival().getAirport())
                            .build();

                return flightInfo;
            })
        .toList();
        return flightInfoList;
    }

}
