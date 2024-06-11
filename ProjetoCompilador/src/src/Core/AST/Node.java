package Core.AST;

import Core.syntaxAnalysis.Visitor;

public abstract class Node {
	
	public void visit(Visitor v) {
		v.visitNode(this);
	}
}
