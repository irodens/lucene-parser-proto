package org.rodens.lucene.nodes;

import java.util.Objects;

import static com.google.common.base.Preconditions.checkNotNull;


public final class RangeNode extends TerminalNode {
    private final String lowerBound;
    private final boolean lowerBoundInclusive;
    private final String upperBound;
    private final boolean upperBoundInclusive;

    public RangeNode(String field, String lowerBound, boolean lowerBoundInclusive, String upperBound, boolean upperBoundInclusive) {
        super(field);
        this.lowerBound = checkNotNull(lowerBound);
        this.lowerBoundInclusive = lowerBoundInclusive;
        this.upperBound = checkNotNull(upperBound);
        this.upperBoundInclusive = upperBoundInclusive;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RangeNode rangeNode = (RangeNode) o;
        return lowerBoundInclusive == rangeNode.lowerBoundInclusive &&
                upperBoundInclusive == rangeNode.upperBoundInclusive &&
                lowerBound.equals(rangeNode.lowerBound) &&
                upperBound.equals(rangeNode.upperBound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), lowerBound, lowerBoundInclusive, upperBound, upperBoundInclusive);
    }

    @Override
    public String toString() {
        return getField() + ":"
                + (lowerBoundInclusive ? "(" : "[")
                + "\"" + lowerBound + "\""
                + " TO "
                + "\"" + upperBound + "\""
                + (upperBoundInclusive ? ")" : "]");

    }
}
