package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class RegExToken {
    static final int MAX_NUMBER_QUESTION_MARK = 1;
    static final int MAX_NUMBER_QUANTIFIER = 50;
    static final int MIN_OCCURRENCE = 1;

    private String token;
    private  Random random;
    private char quantifier;
    private int occurrences;
    private boolean isLiteral;
    private boolean haveQuantifier;

    public RegExToken() {
        isLiteral = false;
        random = new Random();
    }

    public void setLiteral( char literal) {
        this.token = Character.toString(literal);
        isLiteral = true;
    }

    public void setToken(String token) {
        this.token = token;
        if ( ! token.equals(".") && ! token.contains("[")) {
            this.isLiteral = true;
        }
    }

    public void setQuantifier(char quantifier) {
        this.quantifier = quantifier;
        this.haveQuantifier = true;
    }

    private int randomFromQuantifier() {
        if ( quantifier == '+' ) {
            occurrences = random.nextInt(MAX_NUMBER_QUANTIFIER) + 1;
        } else if ( quantifier == '?' ) {
            occurrences = random.nextInt(MAX_NUMBER_QUESTION_MARK + 1);
        } else if ( quantifier == '*' ) {
            occurrences = random.nextInt(MAX_NUMBER_QUANTIFIER);
        }

        return occurrences;
    }

    private String matchSet() {
        String matched = "";
        int tokenLength = this.token.length();
        String set = this.token.substring(1, tokenLength - 1);
        int setLength = set.length();
        for (int i = 0; i < occurrences; i++) {
            matched = matched + set.charAt(random.nextInt(setLength));
        }
        return matched;
    }

    private String matchDot() {
        String matched = "";
        for (int i = 0; i < occurrences; i++) {
            matched = matched + (char) random.nextInt(256);
        }
        return matched;
    }

    private String matchLiteral() {
        String matched = "";
        for (int i = 0; i < occurrences; i++) {
            matched = matched + this.token;
        }
        return matched;
    }

    public String make() {
        occurrences = haveQuantifier ? randomFromQuantifier() : MIN_OCCURRENCE;
        if (this.token.charAt(0) == '[' && !this.isLiteral) {
            return matchSet();
        } else if (this.token == "." && !this.isLiteral) {
            return matchDot();
        }
        return matchLiteral();

    }

}
