public class NonTerminal extends Node {
    String name;

    NonTerminal(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof NonTerminal) && (this.name.equals(((NonTerminal) object).name));
    }
}
