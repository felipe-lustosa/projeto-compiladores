package Main.AST;

import Main.syntaxAnalysis.Visitor;

public abstract class Node {
	
	public void visit(Visitor v) {
		v.visitNode(this);
	}
}
