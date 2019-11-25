package parser;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ParserBraceTest {

    @Test
    public void whenFirstInput() {
        String forTest = "{}}{}}";
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("{}{}", "{{}}"));
        testParser = new JungsTableParserBrace();
        result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("{}{}", "{{}}"));
    }

    @Test
    public void whenSecondInput() {
        String forTest = "{}x}x}";
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("{}xx", "{x}x", "{xx}"));
        testParser = new JungsTableParserBrace();
        result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("{}xx", "{x}x", "{xx}"));
    }

    @Test
    public void whenThirdInput() {
        String forTest = "{";
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder(""));
        testParser = new JungsTableParserBrace();
        result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder(""));
    }

    @Test
    public void whenFourthInput() {
        String forTest = "xfr}rfx";
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("xfrrfx"));
        testParser = new JungsTableParserBrace();
        result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("xfrrfx"));
    }

    @Test
    public void whenFifthInput() {
        String forTest = "{}{}}{}}}";
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("{}{}{}", "{{}{}}", "{{}}{}", "{}{{}}", "{{{}}}"));
        testParser = new JungsTableParserBrace();
        result = testParser.validate(forTest);
        assertThat(result, Matchers.containsInAnyOrder("{}{}{}", "{{}{}}", "{{}}{}", "{}{{}}", "{{{}}}"));
    }

}