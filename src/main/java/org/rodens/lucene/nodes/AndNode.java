package org.rodens.lucene.nodes;

import java.util.List;

public final class AndNode extends ConjunctiveNode {
    public AndNode(List<QueryNode> children) {
        super(children);
    }

    @Override
    public String toString() {
        return toString("AND");
    }
}
