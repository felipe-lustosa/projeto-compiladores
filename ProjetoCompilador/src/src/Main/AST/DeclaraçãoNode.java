package Main.AST;

import Main.syntaxAnalysis.Visitor;

public abstract class DeclaraçãoNode extends Node {
	public DeclaraçãoNode próximaD;
	
	public void visit(Visitor v) {
		v.visitDeclaração(this);
	}
	public DeclaraçãoNode() {
		this.próximaD = null;
	}
	
	public DeclaraçãoNode(DeclaraçãoNode próximaD) {
		this.próximaD = próximaD;
	}

}
