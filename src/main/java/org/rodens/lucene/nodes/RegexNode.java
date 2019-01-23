package org.rodens.lucene.nodes;

public final class RegexNode extends PatternNode {

    public RegexNode(String field, String pattern) {
        super(field, pattern, createUnescapedPattern(pattern, "[]().*?+{}"));
    }

    @Override
    public String toString() {
        return getField() + " LIKE_REG \"" + getPattern() + "\"";
    }

}
