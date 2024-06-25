package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class ComandoIterativoNode extends ComandoNode {
	public ExpressãoNode E;
	public ComandoNode C; 
	
	public void visit(Visitor v){
		v.visitComandoIterativo(this);
	}
	public ComandoIterativoNode(ExpressãoNode E, ComandoNode C) {
		this.E = E;
		this.C = C;
	}
}
