package Core.contextAnalysis;
import Core.lexicalAnalysis.Token;
import Core.AST.Node;

public class TableLine {
	public int level;
	public Token identifier; // Alternamos para Token para dar mais informações na geração de erros.
	public boolean used; // se o identificador declarado foi utilizado ou não
	public Node attribute;
	
	public TableLine() {
	}
	
	public String toString() {
		String resultado = new String((new Integer(level)).toString() + "\t " + 
							identifier.getSpelling() + "\t\t " + 
							used +  "\t\t " + attribute);
		return resultado;
	}
}
