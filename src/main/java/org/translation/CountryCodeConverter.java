package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
// TO-DO CheckStyle: Wrong lexicographical order for 'java.util.HashMap' import (remove this comment once resolved)

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {
    private final List<String> data;
    private final int deltaCode = 4;
    private final int lenCode = 3;

    // TO-DO Task: pick appropriate instance variable(s) to store the data necessary for this class
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

        try {
            this.data = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            // TO-DO Task: use lines to populate the instance variable(s)
            this.data.remove(0);
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
        // TO-DO Task: update this code to use an instance variable to return the correct value
        String ode = code.toUpperCase();
        for (String line: this.data) {
            if (line.contains(ode)) {
                int i = line.lastIndexOf(ode);
                char[] c = line.toCharArray();
                StringBuilder s = new StringBuilder();
                for (int j = 0; j < i - deltaCode; j++) {
                    s.append(c[j]);
                }
                return s.toString();
            }
        }
        return code;
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        // TO-DO Task: update this code to use an instance variable to return the correct value
        for (String line: this.data) {
            if (line.startsWith(country)) {
                char[] c = line.toCharArray();
                StringBuilder s = new StringBuilder();
                for (int j = 0; j < lenCode; j++) {
                    s.append(c[country.length() + deltaCode + j]);
                }
                return s.toString().toLowerCase();
            }
        }
        return country;
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        // TO-DO Task: update this code to use an instance variable to return the correct value
        return this.data.size();
    }
}
