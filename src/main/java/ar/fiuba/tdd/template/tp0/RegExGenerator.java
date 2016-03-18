package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegExGenerator {
    // TODO: Uncomment this field
    private int maxLength;
    private RegExParser parser;
    private List<RegExToken> tokens;
    private ArrayList<String> matchedStrings;

    public RegExGenerator(int maxLength) {
        this.maxLength = maxLength;
        this.parser = new RegExParser();
    }

    private String matchString(){
        String matchedString = "";
        for (RegExToken token:tokens) {
            matchedString = matchedString + token.make();
        }
        return matchedString;
    }

    // TODO: Uncomment parameters
    public List<String> generate(String regEx, int numberOfResults) {
        tokens = parser.parseString(regEx);
        matchedStrings = new ArrayList<String>();
        for (int i = 0; i < numberOfResults; i++) {
            matchedStrings.add(matchString());
        }
        return matchedStrings;

    }
}
