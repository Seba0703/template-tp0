package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(20);
        List<String> results = null;
        try {
            results = generator.generate(regEx, numberOfResults);
        } catch (RegExFormatException e) {
            return false;
        }
        // force matching the beginning and the end of the strings
        Pattern pattern = Pattern.compile("^" + regEx + "$");
        return results
                .stream()
                .reduce(true,
                    (acc, item) -> {
                        Matcher matcher = pattern.matcher(item);
                        return acc && matcher.find();
                    },
                    (item1, item2) -> item1 && item2);
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
    }
    @Test
    public void testOneLetter() {
        assertTrue(validate("a", 1));
    }

    @Test
    public void testNegativeOnlyOneQuantifier() {
        assertFalse(validate("?", 1));
    }
    @Test
    public void testDoubleQuantifier() {
        assertFalse(validate(".??", 1));
    }
    @Test
    public void testDoubleQuantifier2() {
        assertFalse(validate(".**", 1));
    }
    @Test
    public void testDoubleQuantifier3() {
        assertFalse(validate(".++", 1));
    }

    @Test
    public void testMultipleCharacters() {
        assertTrue(validate("...", 1));
    }

    @Test
    public void testLiteral() {
        assertTrue(validate("\\@", 1));
    }

    @Test
    public void testLiteralDotCharacter() {
        assertTrue(validate("\\@..", 1));
    }

    @Test
    public void testZeroOrOneCharacter() {
        assertTrue(validate("\\@.h?", 1));
    }

    @Test
    public void testCharacterSet() {
        assertTrue(validate("[abc]", 1));
    }

    @Test
    public void testCharacterSetWithQuantifiers() {
        assertTrue(validate("[abc]+", 1));
    }

    @Test
    public void testLongRegEx() {
        assertTrue(validate("t*.*,..*.*.*..+.+.+\\+.*", 1));
    }
    @Test
    public void testEscapedCharacterAndQuantifier() {
        assertTrue(validate("\\+?", 1));
    }

    @Test
    public void testEscapedCharacterAndQuantifier2() {
        assertTrue(validate("\\+*", 1));
    }

    @Test
    public void testEscapedCharacterAndQuantifier3() {
        assertTrue(validate("\\++", 1));
    }

}
