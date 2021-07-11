public class Terminal extends Node {
    String value;

    Terminal(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return '"' + value + '"';
    }
}
