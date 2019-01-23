package org.rodens.lucene.nodes;

import java.util.List;

public class OrNode extends ConjunctiveNode {
    public OrNode(List<QueryNode> children) {
        super(children);
    }

    @Override
    public String toString() {
        return super.toString("OR");
    }
}
