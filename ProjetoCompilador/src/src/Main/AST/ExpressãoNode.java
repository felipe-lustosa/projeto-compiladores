package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class ExpressãoNode extends FatorNode {
	public ExpressãoSimplesNode E1;
	public OperadorNode O;
	public ExpressãoSimplesNode E2; 
	
	public void visit(Visitor v) {
		v.visitExpressão(this);
	}
	public ExpressãoNode(ExpressãoSimplesNode E1, OperadorNode O, ExpressãoSimplesNode E2) {
		this.E1 = E1;
		this.O = O;
		this.E2 = E2;
	}

}
