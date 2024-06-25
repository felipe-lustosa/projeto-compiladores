package Main.contextAnalysis;
import Main.AST.Node;
import Main.lexicalAnalysis.Token;

public class TableLine {
	public int level;
	public Token identifier; // Alternamos para Token para dar mais informações na geração de erros.
	public boolean used; // se o identificador declarado foi utilizado ou não
	public Node attribute;
	
	public TableLine() {
	}
	
	public String toString() {
		String resultado = new String(Integer.valueOf(level).toString() + "\t " + 
							identifier.getSpelling() + "\t\t " + 
							used +  "\t\t " + attribute);
		return resultado;
	}
}
