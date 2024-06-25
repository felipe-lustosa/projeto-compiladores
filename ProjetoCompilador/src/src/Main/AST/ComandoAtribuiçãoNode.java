
package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class ComandoAtribuiçãoNode extends ComandoNode {
	public VariávelNode V;
	public ExpressãoNode E;	
	public void visit(Visitor v) {
		v.visitComandoAtribuição(this);
	}
	public ComandoAtribuiçãoNode(VariávelNode V, ExpressãoNode E) {
		this.V = V;
		this.E = E;
	}
}
