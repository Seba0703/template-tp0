package ar.fiuba.tdd.template.tp0;

import org.junit.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RegExGeneratorTest {

    private boolean validate(String regEx, int numberOfResults) {
        RegExGenerator generator = new RegExGenerator(20);
        List<String> results;
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
    public void testEmptyRegEx() {
        assertTrue(validate("", 1));
    }

    @Test
    public void testAnyCharacter() {
        assertTrue(validate(".", 1));
        assertTrue(validate(".*", 10));
    }

    @Test
    public void testOneLetter() {
        assertTrue(validate("a", 1));
    }

    @Test
    public void testNegativeOnlyOneQuantifier() {
        assertFalse(validate("?", 1));
        assertFalse(validate("*", 1));
        assertFalse(validate("+", 1));
    }

    @Test
    public void testDoubleQuantifier() {
        assertFalse(validate(".??", 1));
        assertFalse(validate(".**", 1));
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
        assertTrue(validate("t*.*,..*.*.*..+.+.+\\+.*", 100));
    }

    @Test
    public void testEscapedCharacterAndQuantifier() {
        assertTrue(validate("\\+?", 1));
        assertTrue(validate("\\+*", 1));
        assertTrue(validate("\\++", 1));
        assertTrue(validate("\\*+", 1));
        assertTrue(validate("\\*?", 1));
        assertTrue(validate("\\**", 1));
        assertTrue(validate("\\?*", 1));
        assertTrue(validate("\\??", 1));
        assertTrue(validate("\\?+", 1));
    }

    @Test
    public void testSetWithEscapes() {
        assertTrue(validate("[\\&a\\]\\?\\d\\]\\*2dbsd\\[hh\\.\\+kk]2*", 90));
        assertTrue(validate("[\\]]", 1));
        assertTrue(validate("[\\[]", 1));
    }

    @Test
    public void testNegativeBadSetFormat() {
        assertFalse(validate("d[abcd]]s", 1));
        assertFalse(validate("d[[abcd]s", 1));
        assertFalse(validate("d[ab[cd]s", 1));
        assertFalse(validate("d[ab]cd]s", 1));
        assertFalse(validate("abcd]", 1));
        assertFalse(validate("s[abcd", 1));
    }

    @Test
    public void testNegativeBadSetFormat2() {
        assertFalse(validate("[?]", 1));
        assertFalse(validate("[.]", 1));
        assertFalse(validate("[*]", 1));
        assertFalse(validate("[+]", 1));
        assertFalse(validate("[]", 1));
        assertFalse(validate("[", 1));
        assertFalse(validate("]", 1));
    }

    @Test
    public void testManySets() {
        assertTrue(validate("[as12\\]]?[as\\s]?as[5423]*.*[a\\?][asd]+",1));
    }
}
