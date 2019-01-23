package org.rodens.lucene.nodes;

import java.util.Objects;

public final class EqualsNode extends TerminalNode {
    private final String value;

    public EqualsNode(String field, String value) {
        super(field);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EqualsNode that = (EqualsNode) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), value);
    }

    @Override
    public String toString() {
        return getField() + " = \"" + value.replace("\"", "\\\"") + "\"";
    }
}
