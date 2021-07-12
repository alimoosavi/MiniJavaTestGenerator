import java.io.IOException;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) throws IOException {


        Triple<LinkedList<NonTerminal>, LinkedList<Terminal>, LinkedList<Rule>> triple =
                GrammarParser.extractRules("/home/ali/MiniJavaCodeGenerator/src/BNFGrammar.txt");

        LinkedList<NonTerminal> nonTerminals = triple.getFirst();
        LinkedList<Terminal> terminals = triple.getSecond();
        LinkedList<Rule> rules = triple.getThird();

        NonTerminal startNonTerminal = nonTerminals.get(1);
        RuleGenerator ruleGenerator = new RuleGenerator(startNonTerminal, rules);
        ruleGenerator.generateTestCasesForCoverage();
    }
}
