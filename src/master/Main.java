package master;
import java.io.FileReader;
import java.io.IOException;
public class Main {
    public static String checkFile(){
        String code="";
        try(FileReader reader = new FileReader("C:\\Users\\glebo\\Downloads\\Parser-Interpretator\\Parser-Interpretator\\src\\Code.txt"))
        {int c;
            while((c=reader.read())!=-1){
                code=code.concat(String.valueOf((char)c));	}}
        catch(IOException ex){
            System.out.println(ex.getMessage());}
        return code;}
    public static void main(String[] args) {
        String s=checkFile();
        System.out.println("\n\n Source code ");
        System.out.println(s);
        System.out.printf("\n" + "\n Lexer ");
        master.Lexer lexer=new master.Lexer(s);
        Parser parser=new Parser(lexer.analyze());
        System.out.println("\n");
    }}