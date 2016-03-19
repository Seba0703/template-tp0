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

    private boolean isReservedCharacter(String set, int pos) {
        CharSequence character = set.subSequence(pos, pos + 1);
        String reservedCharacter = ".[]*+?";
        return reservedCharacter.contains(character);
    }

    private void ifEmptySetException(String set) throws RegExFormatException {
        if (set.length() <= 2) {
            throw new RegExFormatException();
        }
    }

    private String verifySetFormat(String set) throws RegExFormatException {
        ifEmptySetException(set);       //i.e []
        //only search between square braces
        for (int i = 1; i < set.length() - 1; i++ ) {
            if (set.charAt(i) == '\\') {
                StringBuilder setCopy = new StringBuilder(set);
                setCopy.deleteCharAt(i);
                set = setCopy.toString();
            } else if ( isReservedCharacter(set, i)) {
                throw new RegExFormatException();
            }
        }
        return set;
    }

    private boolean stillEscaped(String regEx, int first) {
        return first != -1 && regEx.charAt(first - 1) == '\\' && first < regEx.length();
    }

    private int indexOfFirstSquareBracketWithoutEscape(String regEx, int begin) throws RegExFormatException {
        int first = regEx.indexOf("]",begin);
        while (stillEscaped(regEx, first)) {
            begin = first + 1;
            first = regEx.indexOf("]", begin);
        }
        if (first < 0) {
            throw new RegExFormatException();
        }
        return first;
    }

    private int defineSet(RegExToken token, String regEx, int begin) throws RegExFormatException {
        int end = indexOfFirstSquareBracketWithoutEscape(regEx, begin);
        String set = regEx.substring(begin, end + 1);
        set = verifySetFormat(set);
        token.setToken(set);
        return end;
    }

    private void addTokenIfHaveNoQuantifier(RegExToken token, char character) {
        if (!isQuantifier(character)) {
            tokens.add(token);
        }
    }

    private RegExToken lastToken() throws RegExFormatException {
        if (tokens.size() > 0) {
            return tokens.get(tokens.size() - 1);
        } else {
            throw new RegExFormatException();
        }
    }

    public List<RegExToken> parseString(String regEx) throws RegExFormatException {
        for (int i = 0; i < regEx.length(); i++) {
            char character = regEx.charAt(i);
            RegExToken token = new RegExToken(maxLength);
            if (character == '\\') {         // literals tokens
                token.setLiteral(regEx.charAt(i + 1));
                i++;
            } else if (character == '[') {   //  set tokens
                i = defineSet(token, regEx, i);
            } else if (isQuantifier(character)) {
                if (!lastToken().haveQuantifier()) {
                    lastToken().setQuantifier(character);   // modify last token to add quantifier, no add to lis
                } else {
                    throw new RegExFormatException();
                }
            } else {
                token.setToken(Character.toString(character));  //dots
            }
            addTokenIfHaveNoQuantifier(token,character);
        }
        return tokens;
    }
}