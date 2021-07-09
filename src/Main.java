import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        NonTerminal A = new NonTerminal("A");
        NonTerminal B = new NonTerminal("B");
        NonTerminal C = new NonTerminal("C");

        Terminal a = new Terminal("a");
        Terminal b = new Terminal("b");
        Terminal c = new Terminal("c");

        Rule rule1 = new Rule(A, new LinkedList<>(Arrays.asList(a, B, b)));
        Rule rule2 = new Rule(B, new LinkedList<>(Collections.singletonList(C)));
        Rule rule3 = new Rule(B, new LinkedList<>(Arrays.asList(a,b,c)));
        Rule rule4 = new Rule(C, new LinkedList<>(Collections.singletonList(B)));

        LinkedList<Rule> rules = new LinkedList<>(Arrays.asList(rule1, rule2 , rule3, rule4));

        RuleGenerator ruleGenerator = new RuleGenerator(A, rules);
        ruleGenerator.generateTestCasesForCoverage();

    }
}
