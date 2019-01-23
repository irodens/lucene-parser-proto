package org.rodens.lucene.parser;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.rodens.lucene.nodes.QueryNode;

import java.io.StringReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class LuceneParserTest {

    @ParameterizedTest
    @MethodSource("data")
    void testLuceneParser(String input, String expectedOutput, int numNodesExpected) throws ParseException {
        LuceneParser parser = new LuceneParser(new StringReader(input));
        QueryNode root = parser.parse();
        assertThat(root.toString(), is(equalTo(expectedOutput)));
        assertThat("Contains wrong number of nodes in output", getNumberOfNodes(root), is(equalTo(numNodesExpected)));

    }

    private int getNumberOfNodes(QueryNode root) {
        Deque<QueryNode> nodeStack = new ArrayDeque<>(root.getChildren());
        int numNodes = 1;
        while (!nodeStack.isEmpty()) {
            numNodes++;
            QueryNode topNode = nodeStack.pop();
            nodeStack.addAll(topNode.getChildren());
        }
        return numNodes;
    }

    static Stream<Arguments> data() {
        return Stream.<Arguments>builder()
                .add(Arguments.of("field:123value", "field = \"123value\"", 1))
                .add(Arguments.of("field:*wild*", "field LIKE_WILD \"*wild*\"", 1))
                .add(Arguments.of("field:wil?d", "field LIKE_WILD \"wil?d\"", 1))
                .add(Arguments.of("field:wild\\?animals\\*", "field = \"wild?animals*\"", 1))
                .add(Arguments.of("string:whatever and value:10.9.12.6", "(string = \"whatever\" AND value = \"10.9.12.6\")", 3))
                .add(Arguments.of("string:whatever and value:10\\:9\\:12\\:6", "(string = \"whatever\" AND value = \"10:9:12:6\")", 3))
                .add(Arguments.of("(field1:/regex[a-b]+x/ and field2:value24@) or field3:xyz*", "((field1 LIKE_REG \"regex[a-b]+x\" AND field2 = \"value24@\") OR field3 LIKE_WILD \"xyz*\")",5))
                .add(Arguments.of("field:\"12345* junk\"", "field = \"12345* junk\"", 1))
                .build();
    }

}
