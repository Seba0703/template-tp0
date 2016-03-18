package ar.fiuba.tdd.template.tp0;
import java.util.ArrayList;
import java.util.List;
public class RegExParser{

    List<RegExToken> tokens = new ArrayList<RegExToken>();

    public RegExParser(){}

    private boolean isQuantifier(char character) {
        return ( character == '?' || character == '*' || character == '+');
    }

    public List<RegExToken> parseString(String regEx) {
        for (int i = 0; i < regEx.length(); i++) {
            char character = regEx.charAt(i);
            RegExToken token = new RegExToken();
            if (character == '\\') {
                token.setLiteral(regEx.charAt(i+1));
                i++;
            } else if (character == '[') {
                int end = regEx.indexOf("]",i);
                int begin = i;
                token.setToken(regEx.substring(begin, end + 1));
                i = end;
            } else if (isQuantifier(character))
                tokens.get(tokens.size() - 1).setQuantifier(character);
            else
                token.setToken(Character.toString(character));
            if (!isQuantifier(character))
                tokens.add(token);
        }
        return tokens;
    }

}