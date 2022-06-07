package master;

//добавление в ноду значения переменной.
public class VarNode extends master.Node {
    master.Token var;
    public VarNode(master.Token var) {
        super();
        this.var = var;}}