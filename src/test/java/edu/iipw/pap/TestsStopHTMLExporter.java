package edu.iipw.pap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TestsStopHTMLExporter {
    static Stream<Arguments> argsForEscape() {
        return Stream.of(
            Arguments.of("foo", "foo"),
            Arguments.of("foo & bar", "foo &amp; bar"),
            Arguments.of("foo <-> bar", "foo &lt;-&gt; bar"),
            Arguments.of("Armii \"Poznań\"", "Armii &quot;Poznań&quot;"),
            Arguments.of("Don't do this", "Don&#x27;t do this")
        );
    }

    @ParameterizedTest
    @MethodSource("argsForEscape")
    void checkEscape(String src, String expected) {
        assertEquals(expected, StopHTMLExporter.escape(src));
    }
}
