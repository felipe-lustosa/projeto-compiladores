package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class TermoNode extends Node {
	public FatorNode F;
	public SequênciaFatoresNode SF;
	public int tipo;
	
	public void visit(Visitor v) {
		v.visitTermo(this);
	}
	public TermoNode(FatorNode F, SequênciaFatoresNode SF) {
		this.F = F;
		this.SF = SF;
	}
}
