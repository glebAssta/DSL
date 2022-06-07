package master;

//класс, в котором создается понятие токена и его нумерация.
public class Token {
    TokenType type;
    String value;
    int pos;
    public Token(TokenType type, String value, int pos) {
        this.type = type;
        this.value = value;
        this.pos = pos;}}