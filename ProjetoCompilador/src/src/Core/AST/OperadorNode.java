package Core.AST;

import Core.lexicalAnalysis.Token;
import Core.syntaxAnalysis.Visitor;

public class OperadorNode extends Node {
	public Token O;
	
	public void visit(Visitor v) {
		v.visitOperador(this);
	}
	public OperadorNode(Token O) {
		this.O = O;
	}
}
