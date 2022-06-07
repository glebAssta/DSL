package master;

//класс вывода значения и оператора ноды.
public class UnOpNode extends master.Node {
    master.Token operator;
    master.Node value;
    public UnOpNode(master.Token operator, master.Node value) {
        this.operator = operator;
        this.value = value;}}