package org.rodens.lucene.parser;

import org.rodens.lucene.nodes.AndNode;
import org.rodens.lucene.nodes.OrNode;
import org.rodens.lucene.nodes.QueryNode;
import org.rodens.lucene.nodes.TerminalNode;

import java.util.Arrays;
import java.util.Stack;


@SuppressWarnings("unused")
class LuceneParserHelper implements  LuceneParserConstants {
    enum Operator {
        Sentinel(0, 0), Or(1, 2), And(2, 2), Not(3, 2), Parens(4, 0);
        final int priority;
        final int numArgs;

        Operator(int priority, int numArgs) {
            this.priority = priority;
            this.numArgs = numArgs;
        }
    }

    private Stack<Operator> operatorStack = new Stack<>();
    private Stack<QueryNode> operandStack = new Stack<>();
    private Operator currentOperator = Operator.Sentinel;

    private static final String LUCENE_ESCAPED_CHAR = "\\{}[](): \"/";
    private static final String WILDCARD_ESCAPED_CHAR = "*?\\";

    static String removeLuceneEscapes(String string) {
        return removeEscapes(string, LUCENE_ESCAPED_CHAR);
    }

    static String removeWildcardEscapes(String string) {
        return removeEscapes(string, WILDCARD_ESCAPED_CHAR);
    }

    static String removeEscapes(String string, String escapedChars) {
        StringBuilder unescaped = new StringBuilder();
        int iOrig;
        int iStart = 0;
        while ((iOrig = string.indexOf("\\", iStart)) >= 0) {
            if (iOrig == (string.length() - 1)) {
                unescaped.append(string, iStart, iOrig + 1);
            } else if (escapedChars.indexOf(string.charAt(iOrig + 1)) == -1) {
                unescaped.append(string, iStart, iOrig + 2);
            } else {
                if (iOrig > 0) {
                    unescaped.append(string, iStart, iOrig);
                }
                unescaped.append(string.charAt(iOrig + 1));
            }

            iStart = iOrig + 2;
        }
        if (iStart < string.length()) {
            unescaped.append(string, iStart, string.length());
        }
        return unescaped.toString();
    }


    private Operator convertOperator(Token t) {
        switch (t.kind) {
            case LPARENS:
                return Operator.Sentinel;
            case OR:
                return Operator.Or;
            case AND:
                return Operator.And;
            case NOT:
                return Operator.Not;
            case RPARENS:
                return Operator.Parens;
        }
        return null;
    }

    void pushOperand(TerminalNode node) {
        operandStack.push(node);
        if (currentOperator != null) {
            buildTree(currentOperator);
            currentOperator = null;
        }
    }

    void pushOperator(Token t) {
        currentOperator = convertOperator(t);
    }

    void buildTree(Operator op) {
        Operator topOp = op;
        while (!operatorStack.empty() && topOp.priority > operatorStack.peek().priority) {

            switch (topOp) {
                case Sentinel:
                    break;
                case And:
                    operandStack.push(new AndNode(Arrays.asList(operandStack.pop(), operandStack.pop())));
                    break;
                case Or:
                    operandStack.push(new OrNode(Arrays.asList(operandStack.pop(), operandStack.pop())));
                    break;

            }
            topOp = operatorStack.pop();
        }
        if (topOp != null) operatorStack.push(topOp);
    }

    QueryNode getCompletedTree() {
        buildTree(Operator.Parens);
        return operandStack.pop();
    }

    void initTree() {
        pushOperator(new Token(LPARENS));
    }
}
