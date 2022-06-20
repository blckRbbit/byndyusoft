package support;

public class Lexeme {

    private final LexemeType type;
    private final String value;

    public Lexeme(LexemeType type, String value) {
        this.type = type;
        this.value = value;
    }

    public Lexeme(LexemeType type, Character value) {
        this.type = type;
        this.value = value.toString();
    }

    public LexemeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("Lexeme:\n{\n\ttype: %s,\n\tvalue: %s", type, value);
    }
}
