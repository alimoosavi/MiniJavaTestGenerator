import java.util.*;
import java.util.stream.Collectors;

public class RuleGenerator {
    private NonTerminal start;
    private HashMap<Rule, Boolean> rules;

    RuleGenerator(NonTerminal start, LinkedList<Rule> ruleLinkedList) {
        this.start = start;
        this.rules = new HashMap<>();

        for (Rule rule : ruleLinkedList) {
            this.rules.put(rule, false);
        }
    }

    void generateTestCasesForCoverage() {
        int i = 0;
        while (rules.values().stream().anyMatch(checked -> !checked)) {
            System.out.println(String.format("creating test case %x th", i));
            printNodes(generateTestCase());
            System.out.println("*********************************************");
        }
    }

    ArrayList<Node> generateTestCase() {
        ArrayList<Node> str = new ArrayList<>();
        str.add(this.start);

        while (str.stream().anyMatch(node -> node instanceof NonTerminal)) {
            printNodes(str);

            Optional<Node> nonTerminal = str.stream().filter(node -> node instanceof NonTerminal).findFirst();
            Rule rule = this.findRuleByNonTerminal((NonTerminal) nonTerminal.get());
            str = replaceNonTerminalByRule(str, (NonTerminal) nonTerminal.get(), rule);
        }

        return str;
    }

    public ArrayList<Node> replaceNonTerminalByRule(ArrayList<Node> str, NonTerminal nonTerminal, Rule rule) {
        ArrayList<Node> newStr = new ArrayList<>();

        for (Node node : str) {
            if (node.equals(nonTerminal)) {
                newStr.addAll(rule.rightSide);
            } else {
                newStr.add(node);
            }
        }
        return newStr;
    }

    Rule findRuleByNonTerminal(NonTerminal nonTerminal) {
        for (Map.Entry<Rule, Boolean> entry : this.rules.entrySet()) {
            Rule rule = entry.getKey();
            Boolean hasChecked = entry.getValue();

            if (rule.leftSide.equals(nonTerminal) && !hasChecked) {
                entry.setValue(true);
                return rule;
            }
        }

        Set<Rule> ruleSet = this.rules.keySet()
                .stream()
                .filter(rule -> rule.leftSide.equals(nonTerminal))
                .collect(Collectors.toSet());

        return (Rule) ruleSet.toArray()[0];
    }

    public void printNodes(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            System.out.print(node);
        }
        System.out.println(" ");
    }
}
