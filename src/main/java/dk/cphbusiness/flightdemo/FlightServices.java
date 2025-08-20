package dk.cphbusiness.flightdemo;

import dk.cphbusiness.flightdemo.dtos.FlightInfoDTO;

import java.util.List;

public class FlightServices {

    public double getAverageFligthTime(List<FlightInfoDTO> flights, String airline) {
        double average = flights.stream()
                .filter(f -> f.getAirline() != null)
                .filter(f -> f.getAirline()
                        .equals(airline))
                .mapToLong(f -> f.getDuration()
                        .toMinutes())
                .average()
                .orElse(0.0);
        return average;
    }

}
