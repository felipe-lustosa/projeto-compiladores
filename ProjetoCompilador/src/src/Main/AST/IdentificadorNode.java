package Main.AST;

import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Visitor;

public class IdentificadorNode extends NomeNode {
	public Token N;
	
//	public void visit(Visitor v) {
//		v.visitIdentificador(this);
//	}
	public IdentificadorNode() {

	}
}
