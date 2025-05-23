package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    // TO-DO Task: pick appropriate instance variables for this class
    private final JSONArray jsonArray;
    private final CountryCodeConverter converter = new CountryCodeConverter();

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        // read the file to get the data to populate things...
        try {

            String jsonString = Files.readString(Paths.get(getClass().getClassLoader().getResource(filename).toURI()));

            this.jsonArray = new JSONArray(jsonString);

            // TO-DO Task: use the data in the jsonArray to populate your instance variables
            //            Note: this will likely be one of the most substantial pieces of code you write in this lab.

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        // TO-DO Task: return an appropriate list of language codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> list = new ArrayList<>();
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject obj = this.jsonArray.getJSONObject(i);
            Iterator<String> it = obj.keys();
            it.next();
            it.next();
            it.next();
            if (obj.getString("alpha3").equals(country)) {
                while (it.hasNext()) {
                    list.add(it.next());
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getCountries() {
        // TO-DO Task: return an appropriate list of country codes,
        //            but make sure there is no aliasing to a mutable object
        List<String> list = new ArrayList<>();
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject obj = this.jsonArray.getJSONObject(i);
            list.add(this.converter.fromCountryCode(obj.getString("alpha2")));
        }
        return list;
    }

    @Override
    public String translate(String country, String language) {
        // TO-DO Task: complete this method using your instance variables as needed
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject obj = this.jsonArray.getJSONObject(i);
            if (obj.getString("alpha3").equals(country)) {
                return obj.getString(language);
            }
        }
        return null;
    }

    /**
     * Gets the 2 length code from the 3 length country code.
     * @param country the 3 letter country code
     * @return the 2 letter country code
     */
    public final String getAlpha(String country) {
        // TO-DO Task: complete this method using your instance variables as needed
        for (int i = 0; i < this.jsonArray.length(); i++) {
            JSONObject obj = this.jsonArray.getJSONObject(i);
            if (obj.getString("alpha3").equals(country)) {
                return obj.getString(obj.getString("alpha2"));
            }
        }
        return null;
    }
}
