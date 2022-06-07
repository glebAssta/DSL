package master;
import java.util.ArrayList;

//класс, в котором создается корневая нода и уже ниже при вывозе функции, создаются дополнительные.
public class RootNode extends master.Node {
    ArrayList<master.Node> codeStr=new ArrayList<>();
    public void addNode(master.Node node){
        codeStr.add(node);}}