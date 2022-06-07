package master;
import java.util.ArrayList;

// создает новый arraylist и создает возможность двигаться по списку
public class ForNode extends Node{
    Token operator;
    Node leftVal;
    Node rightVal;
    Node action;
    public ArrayList<Node> operations = new ArrayList<>();
    public ForNode(Token operator, Node leftVal, Node rightVal,Node action) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;
        this.action=action;}
    public void addOperations(Node op){
        operations.add(op);}}