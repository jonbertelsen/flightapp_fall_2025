package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FlightServices {

    public double getAverageFligthTime(List<FlightInfoDTO> flights, String airline) {
        double average = flights.stream()
                .filter(f -> f.getAirline() != null)
                .filter(f -> f.getAirline().equals(airline))
                .mapToLong(f -> f.getDuration().toMinutes())
                .average()
                .orElse(0.0);
        return average;
    }

    public List<FlightInfoDTO> getFlightRouteConnections(String origin, String destination, List<FlightInfoDTO> flightInfoList) {
        List<FlightInfoDTO> flightInfoDTOs = flightInfoList.stream()
                .filter(f -> f.getOrigin() != null && f.getDestination() != null)
                .filter(f -> f.getOrigin().equals(origin) && f.getDestination().equals(destination))
                .toList();
        return flightInfoDTOs;
    }

    public List<FlightInfoDTO> getFlightBefore(LocalTime cutoff, List<FlightInfoDTO> flightInfoList) {
        List<FlightInfoDTO> flightInfoDTOs = flightInfoList.stream()
                .filter(f -> f.getDeparture() != null)
                .filter(f -> f.getDeparture().toLocalTime().isBefore(cutoff))
                .toList();
        return flightInfoDTOs;
    }

    public Map<String, Double> averageFlightTimePerAirline(List<FlightInfoDTO> flightInfoList) {
        Map<String, Double> flightMap = flightInfoList.stream()
                .filter(f -> f.getAirline() != null && f.getDuration() != null)
                .collect(Collectors.groupingBy(f -> f.getAirline(), Collectors.averagingDouble(f -> f.getDuration().toMinutes())));
        return flightMap;
    }

    public List<FlightInfoDTO> getFlightsSortedByArrival(List<FlightInfoDTO> flightInfoList) {
        List<FlightInfoDTO> flights =  flightInfoList.stream()
                .filter(f -> f.getArrival() != null)
                .sorted(Comparator.comparing(FlightInfoDTO::getArrival))
                .collect(Collectors.toList());
        return flights;
    }

    public Map<String, Double> sumTotalFlightTimePerAirline(List<FlightInfoDTO> flightInfoList) {
        Map<String, Double> flightMap = flightInfoList.stream()
                .filter(f -> f.getAirline() != null && f.getDuration() != null)
                .collect(Collectors.groupingBy(f -> f.getAirline(), Collectors.summingDouble(f -> f.getDuration().toMinutes())));
        return flightMap;
    }

    public List<FlightInfoDTO> getFlightsSortedByDuration(List<FlightInfoDTO> flightInfoList) {
        List<FlightInfoDTO> flights =  flightInfoList.stream()
                .filter(f -> f.getDuration() != null)
                .sorted(Comparator.comparing(f -> f.getDuration().toMinutes()))
                .collect(Collectors.toList());
        return flights;
    }




}
