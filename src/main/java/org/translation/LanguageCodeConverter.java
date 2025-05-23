package org.translation;

import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides the service of converting language codes to their names.
 */
public class LanguageCodeConverter {
    private final List<String[]> codeMap = new ArrayList<>();

    // TO-DO Task: pick appropriate instance variables to store the data necessary for this class
    /**
     * Default constructor which will load the language codes from "language-codes.txt"
     * in the resources folder.
     */
    public LanguageCodeConverter() {
        this("language-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the language code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public LanguageCodeConverter(String filename) {

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));
            boolean first = true;
            for (String line: lines) {
                if (first) {
                    first = false;
                    continue;
                }
                char[] chars = line.toCharArray();
                StringBuilder st = new StringBuilder();
                for (int i = 0; i < chars.length - 2; i++) {
                    st.append(chars[i]);
                }
                String[] arr = new String[2];
                String s = st.toString().stripTrailing();
                arr[1] = s;
                String f = String.valueOf(chars[chars.length - 2]) + chars[chars.length - 1];
                arr[0] = f;
                codeMap.add(arr);
            }
            // TO-DO Task: use lines to populate the instance variable
            //           tip: you might find it convenient to create an iterator using lines.iterator()
            // TO-DO Checkstyle: '}' on next line should be alone on a line.
        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the language for the given language code.
     * @param code the language code
     * @return the name of the language corresponding to the code
     */
    public String fromLanguageCode(String code) {
        // TO-DO Task: update this code to use your instance variable to return the correct value
        for (String[] v: codeMap) {
            if (v[0].equals(code)) {
                return v[1];
            }
        }
        return code;
    }

    /**
     * Returns the code of the language for the given language name.
     * @param language the name of the language
     * @return the 2-letter code of the language
     */
    public String fromLanguage(String language) {
        // TO-DO Task: update this code to use your instance variable to return the correct value
        for (String[] v: codeMap) {
            if (v[1].equals(language)) {
                return v[0];
            }
        }
        return language;
    }

    /**
     * Returns how many languages are included in this code converter.
     * @return how many languages are included in this code converter.
     */
    public int getNumLanguages() {
        // TO-DO Task: update this code to use your instance variable to return the correct value
        return codeMap.size();
    }
}
