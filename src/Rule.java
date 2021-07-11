import java.util.LinkedList;

public class Rule {
    NonTerminal leftSide;
    LinkedList<Node> rightSide;

    Rule(NonTerminal leftSide, LinkedList<Node> rightSide) {
        this.leftSide = leftSide;
        this.rightSide = rightSide;
    }

    @Override
    public String toString() {
        String rulePresentation = leftSide.name + " -> ";
        for (Node node : rightSide) {
            rulePresentation += " " + node.toString();
        }
        return rulePresentation;
    }
}
