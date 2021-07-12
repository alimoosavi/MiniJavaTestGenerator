import java.io.IOException;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {


        Triple<LinkedList<NonTerminal>, LinkedList<Terminal>, LinkedList<Rule>> triple =
                GrammarParser.extractRules("/home/ali/MiniJavaCodeGenerator/src/BNFGrammar.txt");

        LinkedList<NonTerminal> nonTerminals = triple.getFirst();
        LinkedList<Terminal> terminals = triple.getSecond();
        LinkedList<Rule> rules = triple.getThird();

        NonTerminal startNonTerminal = nonTerminals.stream()
                .filter(nonTerminal -> nonTerminal.equals(new NonTerminal(GrammarParser.START_NAME)))
                .collect(Collectors.toList())
                .get(0);

        RuleGenerator ruleGenerator = new RuleGenerator(startNonTerminal, rules);
        ruleGenerator.generateTestCasesForCoverage();
    }
}
