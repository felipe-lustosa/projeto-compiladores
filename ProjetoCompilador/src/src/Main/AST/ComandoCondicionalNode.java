package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class ComandoCondicionalNode extends ComandoNode {
	public ExpressãoNode E;
	public ComandoNode C1, C2;
	
	public void visit(Visitor v) {
		v.visitComandoCondicional(this);
	}
	public ComandoCondicionalNode(ExpressãoNode E, ComandoNode C1, ComandoNode C2) {
		this.E = E;
		this.C1 = C1;
		this.C2 = C2;
	}
}
