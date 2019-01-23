package org.rodens.lucene.nodes;

import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

public abstract class QueryNode {
    private final List<QueryNode> children;

    protected QueryNode(List<QueryNode> children) {
        this.children = children;
    }

    public List<QueryNode> getChildren() {
        return children;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueryNode queryNode = (QueryNode) o;
        return Objects.equals(children, queryNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children);
    }

    String toString(String conjunction) {
        if (children.isEmpty()) {
            return "";
        } else if (children.size() == 1) {
            return children.get(0).toString();
        } else {
            StringBuilder bldr = new StringBuilder("(");
            String delim = "";
            for (int i = children.size() - 1; i >= 0; i--) {
                bldr.append(delim).append(children.get(i));
                delim = " " + conjunction + " ";
            }
            return bldr.append(")").toString();

        }
    }

}
