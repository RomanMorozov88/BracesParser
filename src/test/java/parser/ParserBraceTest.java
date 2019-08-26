package parser;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.*;

public class ParserBraceTest {

    @Test
    public void whenFirstInput() {
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate("{}}{}}");
        assertThat(result, Matchers.containsInAnyOrder("{}{}", "{{}}"));
    }

    @Test
    public void whenSecondInput() {
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate("{}x}x}");
        assertThat(result, Matchers.containsInAnyOrder("{}xx", "{x}x", "{xx}"));
    }

    @Test
    public void whenThirdInput() {
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate("{");
        assertThat(result, Matchers.containsInAnyOrder(""));
    }

    @Test
    public void whenFourthInput() {
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate("xfr}rfx");
        assertThat(result, Matchers.containsInAnyOrder("xfrrfx"));
    }

    @Test
    public void whenFifthInput() {
        ParserBrace testParser = new ParserBrace();
        Set<String> result = testParser.validate("{}{}}{}}}");
        assertThat(result, Matchers.containsInAnyOrder("{}{}{}", "{{}{}}", "{{}}{}", "{}{{}}", "{{{}}}"));
    }

}