package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class ComandoCompostoNode extends ComandoNode {
	public ListaDeComandosNode LC; 
	
	public void visit(Visitor v) {
		v.visitComandoComposto(this);
	}
	public ComandoCompostoNode(ListaDeComandosNode LC) {
		this.LC = LC;
	}
}
