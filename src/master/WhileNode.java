package master;
import java.util.ArrayList;

//реализация цикла While
public class WhileNode extends master.Node {
    master.Token operator;
    master.Node leftVal;
    master.Node rightVal;
    public ArrayList<master.Node> operations = new ArrayList<>();
    public WhileNode(master.Token operator, master.Node leftVal, master.Node rightVal) {
        this.operator = operator;
        this.leftVal = leftVal;
        this.rightVal = rightVal;}
    public void addOperations(master.Node op){
        operations.add(op);}}
