package Core.AST;

import Core.syntaxAnalysis.Visitor;
import Core.lexicalAnalysis.Token;



public class ProgramaNode extends Node {
	public Token N;
	public CorpoNode C;
	
	public void visit(Visitor v) {
		v.visitPrograma(this);
	}
	public ProgramaNode(Token N, CorpoNode C) {
		this.N = N;
		this.C = C;
	}
}
