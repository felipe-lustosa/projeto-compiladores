package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class CorpoNode extends Node {
	public DeclaraçãoNode D;
	public ComandoNode CC;
	
	public void visit(Visitor v) {
		v.visitCorpo(this);
	}
	public CorpoNode(DeclaraçãoNode D, ComandoNode CC) {

		this.D = D;
		this.CC = CC;
	}
}
