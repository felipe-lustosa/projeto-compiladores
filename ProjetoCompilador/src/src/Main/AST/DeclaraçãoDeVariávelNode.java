package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class DeclaraçãoDeVariávelNode extends DeclaraçãoNode {
	public ListaDeIdsNode LI;
	public TipoNode T;
	
	public void visit(Visitor v) {
		v.visitDeclaraçãoDeVariável(this);
	}
	public DeclaraçãoDeVariávelNode(ListaDeIdsNode LI, TipoNode T, DeclaraçãoNode próximaD) {
		super(próximaD);
		this.LI = LI;
		this.T = T;
	}
	public DeclaraçãoDeVariávelNode(ListaDeIdsNode LI, TipoNode T) {
		super(null);
		this.LI = LI;
		this.T = T;
	}

}
