package Main.AST;

import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Visitor;



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
