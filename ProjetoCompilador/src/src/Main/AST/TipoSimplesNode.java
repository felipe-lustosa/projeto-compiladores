package Main.AST;

import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Visitor;

public class TipoSimplesNode extends TipoNode {
	public Token N;
	
	public void visit(Visitor v) {
		v.visitTipoSimples(this);
	}
	public TipoSimplesNode(Token N) {
		this.N = N;
	}
}
