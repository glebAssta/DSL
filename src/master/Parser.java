package master;

import java.util.ArrayList;

public class Parser {
    ArrayList<Token> tokens;
    int pos=0;

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }
    public Token receive(String[] need){
        Token curToken;
        if (pos<tokens.size()) {
            curToken = tokens.get(pos);
            for (String tokenTypeName : need)
                if (tokenTypeName.equals(curToken.type.typeName)) {
                    pos++;
                    return curToken;
                }
        }
        return null;
    }
    public void need(String[] expected){
        Token token= receive(expected);
        if(token==null){
            throw new Error("\nНа позииции ("+pos+") ожидается "+expected[0]);
        }
    }
    public master.Node parseVarNum(){
        if (tokens.get(pos).type.typeName.equals("NUM")){
            pos++;
            return new master.NumberNode(tokens.get(pos-1));
        }
        if (tokens.get(pos).type.typeName.equals("VAR")){
            pos++;
            return new VarNode(tokens.get(pos-1));
        }

        throw new Error("\nОжидается переменная или число на позиции ("+pos+")\n");
    }
    public master.Node parsePar(){
        if (tokens.get(pos).type.typeName.equals("LPAR")){
            pos++;
            master.Node node = parseFormula();
            need(new String[]{"RPAR"});
            return node;
        }
        else
            return parseVarNum();
    }
    public master.Node parseMultDiv(){
        master.Node leftVal= parsePar();
        Token operator= receive(new String[]{"MULT","DIV"});
        // Парсер проходиться по "дереву"
        while (operator!=null){
            master.Node rightVal= parsePar();
            leftVal=new master.BinOpNode(operator,leftVal,rightVal);
            operator= receive(new String[]{"MULT","DIV"});
        }
        return leftVal;
    }
    public master.Node parseFormula(){
        master.Node leftVal= parseMultDiv();
        Token operator= receive(new String[]{"PLUS","MINUS"});
        while (operator!=null){
            master.Node rightVal= parseMultDiv();
            leftVal=new master.BinOpNode(operator,leftVal,rightVal);
            operator= receive(new String[]{"PLUS","MINUS"});
        }
        return leftVal;
    }
    public master.Node parseString(){
        if (tokens.get(pos).type.typeName.equals("VAR")) {
            master.Node varNode = parseVarNum();
            Token assign = receive(new String[]{"ASSIGN"});
            if (assign != null) {
                master.Node rightVal = parseFormula();
                return new master.BinOpNode(assign, varNode, rightVal);
            }
            throw new Error("\nПосле переменной ожидается = на позиции ("+pos+")\n");
        }
        else if (tokens.get(pos).type.typeName.equals("PRINT")){
            pos++;
            return new UnOpNode(tokens.get(pos-1), this.parseFormula());
        }
        else if(tokens.get(pos).type.typeName.equals("WHILE")){
            pos++;
            return  parseWhile();
        }
        else if(tokens.get(pos).type.typeName.equals("FOR"))
        {
            pos++;
            return parseFor();
        }
        throw new Error("\nОшибка на позиции ("+pos+"). Ожидалось действие или переменная");
    }
    public master.Node parseFor(){
        master.Node leftVal=parseFormula();
        Token operator=receive(new String[]{"LESS","MORE","EQUAL"});
        master.Node rightVal=parseFormula();

        need(new String[]{"END"});

        master.Node varNode = parseVarNum();
        Token assign = receive(new String[]{"ASSIGN"});
        master.Node rightActVal = parseFormula();
        master.BinOpNode action= new master.BinOpNode(assign, varNode, rightActVal);
        if (assign == null)
            throw new Error("\nПосле переменной ожидается = на позиции ("+pos+")\n");
        master.ForNode forNode= new master.ForNode(operator,leftVal,rightVal,action);
        need(new String[]{"LRectPar"});
        while(!tokens.get(pos).type.typeName.equals("RRectPAR")) {
            forNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("\nОшибка, ожидалось }");
        }
        pos++;
        return forNode;
    }
    public master.Node parseWhile(){
        master.Node leftVal=parseFormula();
        Token operator=receive(new String[]{"LESS","MORE","EQUAL"});
        master.Node rightVal=parseFormula();
        WhileNode whileNode=new WhileNode(operator,leftVal,rightVal);
        need(new String[]{"LRectPar"});
        while(!tokens.get(pos).type.typeName.equals("RRectPAR")) {
            whileNode.addOperations(getOperations());
            if (pos==tokens.size())
                throw new Error("\nОшибка, ожидалось }");
        }
        pos++;
        return whileNode;
    }
    public master.Node getOperations(){
        master.Node codeStringNode=parseString();
        need(new String[]{"END"});
        return codeStringNode;
    }
    public RootNode parseTokens(){
        RootNode root=new RootNode();
        while (pos<tokens.size()){
            master.Node codeStringNode= parseString();
            need(new String[]{"END"});
            root.addNode(codeStringNode);
        }
        return root;
    }
}