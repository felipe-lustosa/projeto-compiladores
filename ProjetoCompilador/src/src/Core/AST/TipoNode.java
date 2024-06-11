package Core.AST;

import Core.syntaxAnalysis.Visitor;

public abstract class TipoNode extends Node {
	
	public void visit(Visitor v) {
		v.visitTipo(this);
	}
	public TipoNode() {
		
	}
}
