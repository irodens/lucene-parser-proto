package org.rodens.lucene.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

class LuceneParserHelperTest {

    @Test
    void removeLuceneEscapes() {
    }

    @ParameterizedTest
    @CsvSource({
            "'wildcard','wildcard'",
            "'wild*ca?d','wild*ca?d'",
            "'wild\\*card','wild*card'",
            "'\\*wildcard','*wildcard'",
            "'wil\\dcard', 'wil\\dcard'",
            "'wild\\\\*card','wild\\*card'",
            "'wild?ca\\?\\','wild?ca?\\'"
    })
    void removeWildcardEscapes(String input, String expectedValue) {
        assertThat(LuceneParserHelper.removeWildcardEscapes(input), equalTo(expectedValue));
    }
}