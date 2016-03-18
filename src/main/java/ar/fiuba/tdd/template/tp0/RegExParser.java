package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;

public class RegExParser {
    int maxLength;
    List<RegExToken> tokens = new ArrayList<RegExToken>();

    public RegExParser(int maxLength) {
        this.maxLength = maxLength;
    }

    private boolean isQuantifier(char character) {
        return ( character == '?' || character == '*' || character == '+');
    }

    private int defineSet(RegExToken token, String set, int begin) {
        int end = set.indexOf("]",begin);
        token.setToken(set.substring(begin, end + 1));
        return end;
    }

    private void addTokenIfHaveNoQuantifier(RegExToken token, char character) {
        if (!isQuantifier(character)) {
            tokens.add(token);
        }
    }

    public List<RegExToken> parseString(String regEx) {
        for (int i = 0; i < regEx.length(); i++) {
            char character = regEx.charAt(i);
            RegExToken token = new RegExToken(maxLength);
            if (character == '\\') {
                token.setLiteral(regEx.charAt(i + 1));
                i++;
            } else if (character == '[') {
                i = defineSet(token, regEx, i);
            } else if (isQuantifier(character)) {
                tokens.get(tokens.size() - 1).setQuantifier(character);
            } else {
                token.setToken(Character.toString(character));
            }
            addTokenIfHaveNoQuantifier(token,character);
        }
        return tokens;
    }

}