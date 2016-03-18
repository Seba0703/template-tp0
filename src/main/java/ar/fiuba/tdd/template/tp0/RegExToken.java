package ar.fiuba.tdd.template.tp0;

import java.util.Random;

public class RegExToken {
    static final int MAX_NUMBER_QUESTION_MARK = 1;
    static final int MIN_OCCURRENCE = 1;

    private String token;
    private  Random random;
    private char quantifier;
    private int maxLength;
    private int occurrences;
    private boolean isLiteral;
    private boolean haveQuantifier;

    public RegExToken(int maxLength) {
        this.maxLength = maxLength;
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
            occurrences = random.nextInt(maxLength) + 1;
        } else if ( quantifier == '?' ) {
            occurrences = random.nextInt(MAX_NUMBER_QUESTION_MARK + 1);
        } else if ( quantifier == '*' ) {
            occurrences = random.nextInt(maxLength);
        }

        return occurrences;
    }

    private String matchSet() {
        StringBuilder matched = new StringBuilder();
        int tokenLength = this.token.length();
        String set = this.token.substring(1, tokenLength - 1);
        int setLength = set.length();
        for (int i = 0; i < occurrences; i++) {
            matched.append(set.charAt(random.nextInt(setLength)));
        }
        return matched.toString();
    }

    private String matchDot() {
        StringBuilder matched = new StringBuilder();
        for (int i = 0; i < occurrences; i++) {
            matched.append((char) random.nextInt(256));
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
