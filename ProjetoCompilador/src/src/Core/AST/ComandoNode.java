package Core.AST;

import Core.syntaxAnalysis.Visitor;

public abstract class ComandoNode extends Node {
//	public ComandoNode próximoC;
	
	public void visit(Visitor v) {
		v.visitComando(this);
	}
	public ComandoNode() {
		// TODO Auto-generated constructor stub
//		this.próximoC = null;
	}
//	public ComandoNode(ComandoNode próximoC) {
//		// TODO Auto-generated constructor stub
//		this.próximoC = próximoC;
//	}
}
