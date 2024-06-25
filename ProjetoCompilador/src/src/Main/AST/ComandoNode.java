package Main.AST;

import Main.syntaxAnalysis.Visitor;

public abstract class ComandoNode extends Node {
	
	public void visit(Visitor v) {
		v.visitComando(this);
	}
	public ComandoNode() {
	}
}
