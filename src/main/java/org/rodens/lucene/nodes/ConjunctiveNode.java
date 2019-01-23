package org.rodens.lucene.nodes;

import java.util.List;

public abstract class ConjunctiveNode extends QueryNode {

    protected ConjunctiveNode(List<QueryNode> children) {
        super(children);
    }

}
