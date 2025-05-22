package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private final Map<String, String> codeToName;
    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */

    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {
        codeToName = new HashMap<>();

        try {
            var resource = getClass().getClassLoader().getResource(filename);
            if (resource == null) {
                throw new RuntimeException("Resource file not found: " + filename);
            }
            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()));
            for (String line : lines) {
                // Skip header line
                if (line.startsWith("Country") || line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split("\t"); // tab-separated
                if (parts.length >= 3) {
                    String countryName = parts[0].trim();
                    String alpha3Code = parts[2].trim().toUpperCase();
                    codeToName.put(alpha3Code, countryName);
                }
            }
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        return codeToName.getOrDefault(code.toUpperCase().trim(), "unknown");
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        for (Map.Entry<String, String> entry : codeToName.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(country.trim())) {
                return entry.getKey();
            }
        }
        return "unknown";
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return codeToName.size();
    }
}
