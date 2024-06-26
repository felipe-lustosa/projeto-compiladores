package Main.AST;

import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Visitor;

public class LiteralNode extends FatorNode {
	public Token L; // pode ser bool-lit, int-lit, float-lit
	
	public void visit(Visitor v) {
		v.visitLiteral(this);
	}
	public LiteralNode(Token L) {
		this.L = L;
	}
}
