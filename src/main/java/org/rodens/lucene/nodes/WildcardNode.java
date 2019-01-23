package org.rodens.lucene.nodes;

public final class WildcardNode extends PatternNode {
    public WildcardNode(String field, String pattern) {
        super(field, pattern, createUnescapedPattern(pattern, "*?"));
    }

    @Override
    public String toString() {
        return getField() + " LIKE_WILD \"" + pattern + "\"";
    }
}
