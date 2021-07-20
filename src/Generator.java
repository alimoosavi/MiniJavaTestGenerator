import java.util.*;
import java.util.stream.Collectors;

public class Generator {
    private NonTerminal start;
    private HashMap<Rule, Boolean> rules;

    Generator(NonTerminal start, LinkedList<Rule> ruleLinkedList) {
        this.start = start;
        this.rules = new HashMap<>();

        for (Rule rule : ruleLinkedList) {
            this.rules.put(rule, false);
        }
    }

    void generateTestCasesForCoverage() {
        int i = 0;
        while (rules.values().stream().anyMatch(checked -> !checked) && i < 20) {
            System.out.println(String.format("creating test case %x th", i));
            i++;
            printNodes(generateTestCase());
            System.out.println("*********************************************");
        }
    }

    ArrayList<Node> generateTestCase() {
        ArrayList<Node> str = new ArrayList<>();
        str.add(this.start);

        while (str.stream().anyMatch(node -> node instanceof NonTerminal)) {
//            printNodes(str);

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
            boolean hasChecked = entry.getValue();

            if (rule.leftSide.equals(nonTerminal) && !hasChecked) {
                entry.setValue(true);
                return rule;
            }
        }

        List<Rule> rules = this.rules.keySet()
                .stream()
                .filter(rule -> rule.leftSide.equals(nonTerminal))
                .sorted(Comparator.comparingInt(rule -> (int) rule.rightSide.stream()
                        .filter(node -> node instanceof NonTerminal)
                        .count()
                ))
                .collect(Collectors.toList());

        Random rand = new Random();
        int index = rand.nextInt(rules.size());
        return rules.get(index);

    }

    public void printNodes(ArrayList<Node> nodes) {
        for (Node node : nodes) {
            System.out.print(" " + node);
        }
        System.out.println(" ");
    }
}
