package Core.AST;

import Core.lexicalAnalysis.Token;
import Core.syntaxAnalysis.Visitor;

public class TipoSimplesNode extends TipoNode {
	public Token N;
	
	public void visit(Visitor v) {
		v.visitTipoSimples(this);
	}
	public TipoSimplesNode(Token N) {
		this.N = N;
	}
}
