package dk.cphbusiness.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dk.cphbusiness.flightdemo.FlightReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Properties;

/**
 * Purpose: Utility class to read properties from a file
 * Author: Thomas Hartmann
 */
public class Utils {

    public static String convertDoubleInMinutesToString(double timeInMinutes) {
        long minutes = (long) timeInMinutes;
        long seconds = Math.round((timeInMinutes - minutes) * 60);

        LocalTime time = LocalTime.MIDNIGHT
                .plusMinutes(minutes)
                .plusSeconds(seconds);

       return time.toString();
    }

    public String getPropertyValue(String key) throws IOException {
        Properties props = new Properties();
        props.load(FlightReader.class.getClassLoader().getResourceAsStream("config.properties"));
        return props.getProperty(key);
    }
    public static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper(); // Create a new ObjectMapper instance.

        // Ignore unknown properties during deserialization.
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Add support for Java 8 Date and Time API (e.g., LocalDate, LocalDateTime).
        objectMapper.registerModule(new JavaTimeModule());

        // Return the fully-configured ObjectMapper instance.
        return objectMapper;
    }

}
