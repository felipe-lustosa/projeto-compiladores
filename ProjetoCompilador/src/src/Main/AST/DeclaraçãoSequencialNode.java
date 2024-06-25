package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class DeclaraçãoSequencialNode extends DeclaraçãoNode {
	public DeclaraçãoNode D_1;
	public DeclaraçãoNode D_2;
	
	public void visit(Visitor v) {
		v.visitDeclaraçãoSequencial(this);
	}
	public DeclaraçãoSequencialNode() {
		this.D_1 = null;
		this.D_2 = null;
	}

}
