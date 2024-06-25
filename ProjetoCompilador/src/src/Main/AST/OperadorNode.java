package Main.AST;

import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Visitor;

public class OperadorNode extends Node {
	public Token O;
	
	public void visit(Visitor v) {
		v.visitOperador(this);
	}
	public OperadorNode(Token O) {
		this.O = O;
	}
}
