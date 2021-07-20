import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedList;

public class GrammarParser {

    public static String EPS_NAME = "eps";
    public static String START_NAME = "Goal";

    public static Triple<LinkedList<NonTerminal>, LinkedList<Terminal>, LinkedList<Rule>> extractRules(String address)
            throws IOException {
        Path filePath = Paths.get(address);
        LinkedList<Rule> rules = new LinkedList<>();
        LinkedList<Terminal> terminals = new LinkedList<>();
        LinkedList<NonTerminal> nonTerminals = new LinkedList<>();

        // exceptional eps rule
        NonTerminal epsNonTerminal = new NonTerminal(EPS_NAME);
        Rule epsRule = new Rule(epsNonTerminal, new LinkedList<>());

        nonTerminals.add(epsNonTerminal);
        rules.add(epsRule);

        Files.lines(filePath)
                .map(line -> line.split(" --> "))
                .forEach(splittedLine -> {
                    String leftSideValue = splittedLine[0];
                    NonTerminal leftSide;
                    if (nonTerminals.stream()
                            .anyMatch(nonTerminal -> nonTerminal.name == leftSideValue))
                        leftSide = (NonTerminal) nonTerminals.stream()
                                .filter(nonTerminal -> nonTerminal.name == leftSideValue)
                                .toArray()[0];

                    else {
                        NonTerminal nonTerminal = new NonTerminal(leftSideValue);
                        nonTerminals.add(nonTerminal);
                        leftSide = nonTerminal;
                    }


                    LinkedList<Node> rightSide = new LinkedList<>();
                    Arrays.stream(splittedLine[1].split(" ")).forEach(nodeValue -> {
                        // Node value indicates that it is terminal
                        if (nodeValue.charAt(0) == '"') {
                            String terminalValue = nodeValue.substring(1, nodeValue.length() - 1);
                            if (terminals.stream()
                                    .anyMatch(terminal -> terminal.value == terminalValue))
                                rightSide.add(
                                        (Node) terminals.stream()
                                                .filter(terminal -> terminal.value == terminalValue)
                                                .toArray()[0]
                                );
                            else {
                                Terminal terminal = new Terminal(terminalValue);
                                terminals.add(terminal);
                                rightSide.add(terminal);
                            }
                        }
                        // Node value indicates that it is non-terminal
                        else {
                            if (nonTerminals.stream()
                                    .anyMatch(nonTerminal -> nonTerminal.name == nodeValue))
                                rightSide.add(
                                        (Node) nonTerminals.stream()
                                                .filter(nonTerminal -> nonTerminal.name == nodeValue)
                                                .toArray()[0]
                                );
                            else {
                                NonTerminal nonTerminal = new NonTerminal(nodeValue);
                                nonTerminals.add(nonTerminal);
                                rightSide.add(nonTerminal);
                            }
                        }
                    });
                    rules.add(new Rule(leftSide, rightSide));
                });


        return new Triple(nonTerminals, terminals, rules);
    }
}
