package org.rodens.lucene.parser;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.StringReader;

import static org.rodens.lucene.parser.LuceneParserConstants.*;

class LuceneParserTokenManagerTest {

    @Test
    @Disabled
    void testTokenManager() {
        Token[] expectedTokens = new Token[]{
                new Token(FIELD, "field"),
                new Token(COLON),
                new Token(REGEX, ".*"),
                new Token(AND),
                new Token(FIELD, "IP"),
                new Token(COLON),
                new Token(FIELD, "abc")
        };
        String input = "field:/.*/ AND IP:abc";
        LuceneParserTokenManager tokenManager = new LuceneParserTokenManager(new SimpleCharStream(new StringReader(input)));
        Token t;
        int idx = 0;
        while ((t = tokenManager.getNextToken()) != null && t.kind != LuceneParserConstants.EOF) {
        }
    }

}

