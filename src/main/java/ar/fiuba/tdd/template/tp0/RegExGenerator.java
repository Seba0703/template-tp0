package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegExGenerator {
    private RegExParser parser;
    private List<RegExToken> tokens;
    private ArrayList<String> matchedStrings;

    public RegExGenerator(int maxLength) {
        this.parser = new RegExParser(maxLength);
    }

    private String matchString() {
        StringBuilder matchedString = new StringBuilder();
        for (RegExToken token:tokens) {
            matchedString.append(token.make());
        }
        return matchedString.toString();
    }

    public List<String> generate(String regEx, int numberOfResults) {
        tokens = parser.parseString(regEx);
        matchedStrings = new ArrayList<String>();
        for (int i = 0; i < numberOfResults; i++)  {
            matchedStrings.add(matchString());
        }
        return matchedStrings;

    }
}
