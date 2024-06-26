package Main.AST;

import Main.syntaxAnalysis.Visitor;

public class SequênciaTermosNode extends Node {
	public OperadorNode O; 
	public TermoNode T;
	public SequênciaTermosNode próximaS; // próxima opcional
	public int tipo;
	
	public void visit(Visitor v) {
		v.visitSequênciaTermos(this);
	}
	public SequênciaTermosNode(OperadorNode O, TermoNode T, SequênciaTermosNode próximaS) {

		this.O = O;
		this.T = T;
		this.próximaS = próximaS;
	}

}
