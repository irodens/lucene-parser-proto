package org.rodens.lucene.nodes;

import java.util.Collections;

public abstract class TerminalNode extends QueryNode {
    private final String field;

    public String getField() {
        return field;
    }

    public TerminalNode(String field) {
        super(Collections.emptyList());
        this.field = field;
    }


}
