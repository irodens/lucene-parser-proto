package org.rodens.lucene.nodes;

import java.util.Objects;

public abstract class PatternNode extends TerminalNode {
    protected final String pattern;
    protected final String unescapedPattern;

    PatternNode(String field, String pattern, String unescapedPattern) {
        super(field);
        this.pattern = pattern;
        this.unescapedPattern = unescapedPattern;
    }

    protected static String createUnescapedPattern(String pattern, String escapedChars) {
        if (pattern == null) {
            return null;
        }
        StringBuilder unescaped = new StringBuilder();
        int startIndex = 0;
        int endIndex;
        while (((endIndex = pattern.indexOf('\\', startIndex)) >= 0) && endIndex < (pattern.length() - 1)) {
            char escChar = pattern.charAt(endIndex+1);
            if (escapedChars.indexOf(escChar) > -1) {
                unescaped.append(pattern, startIndex, endIndex)
                        .append(pattern.charAt(endIndex + 1));
                startIndex = endIndex + 2;
            } else {
                endIndex += 2;
                unescaped.append(pattern, startIndex, endIndex);
                startIndex = endIndex;
            }
        }
        if (startIndex < pattern.length()) {
            unescaped.append(pattern, startIndex, pattern.length());
        }
        return unescaped.toString();
    }

    public String getPattern() {
        return pattern;
    }

    public String getUnescapedPattern() {
        return unescapedPattern;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PatternNode that = (PatternNode) o;
        return pattern.equals(that.pattern) &&
                unescapedPattern.equals(that.unescapedPattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), pattern, unescapedPattern);
    }
}
