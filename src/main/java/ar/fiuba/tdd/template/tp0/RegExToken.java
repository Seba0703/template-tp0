package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class RegExToken {
    static final int MAX_NUMBER_QUESTION_MARK = 1;
    static final int MIN_OCCURRENCE = 1;

    private String token;
    private Random randomSet;
    private RandomInRanges randomDot;
    private Random randomQuantifiers;
    private char quantifier;
    private int maxLength;
    private int occurrences;
    private boolean isLiteral;
    private boolean haveQuantifier;

    public RegExToken(int maxLength) {
        this.maxLength = maxLength;
        isLiteral = false;
        randomSet = new Random();
        randomDot = new RandomInRanges();
        randomQuantifiers = new Random();
    }

    public boolean haveQuantifier() {
        return haveQuantifier;
    }

    public void setLiteral( char literal) {
        this.token = Character.toString(literal);
        isLiteral = true;
    }

    private boolean isLiteral() {
        return (!token.equals(".") && !token.contains("[") );
    }

    public void setToken(String token) throws RegExFormatException {
        this.token = token;
        if ( isLiteral() ) {
            if ( ! token.contains("]")) {
                this.isLiteral = true;
            } else {
                throw new RegExFormatException();
            }
        }
    }

    public void setQuantifier(char quantifier) {
        this.quantifier = quantifier;
        this.haveQuantifier = true;
    }

    private int randomFromQuantifier() {
        if ( quantifier == '+' ) {
            occurrences = randomQuantifiers.nextInt(maxLength) + 1;
        } else if ( quantifier == '?' ) {
            occurrences = randomQuantifiers.nextInt(MAX_NUMBER_QUESTION_MARK + 1);
        } else if ( quantifier == '*' ) {
            occurrences = randomQuantifiers.nextInt(maxLength + 1);
        }
        return occurrences;
    }

    private String matchSet() {
        StringBuilder matched = new StringBuilder();
        int tokenLength = this.token.length();
        String set = this.token.substring(1, tokenLength - 1);
        int setLength = set.length();
        for (int i = 0; i < occurrences; i++) {
            matched.append(set.charAt(randomSet.nextInt(setLength)));
        }
        return matched.toString();
    }

    private String matchDot() {
        StringBuilder matched = new StringBuilder();
        for (int i = 0; i < occurrences; i++) {
            int randNum = randomDot.getRandom();
            matched.append((char) randNum);
        }
        return matched.toString();
    }

    private String matchLiteral() {
        StringBuilder matched = new StringBuilder();
        for (int i = 0; i < occurrences; i++) {
            matched.append(this.token);
        }
        return matched.toString();
    }

    public String make() {
        occurrences = haveQuantifier ? randomFromQuantifier() : MIN_OCCURRENCE;
        if (this.token.charAt(0) == '[' && !this.isLiteral) {
            return matchSet();
        } else if (this.token.equals(".") && !this.isLiteral) {
            return matchDot();
        }
        return matchLiteral();
    }
}
