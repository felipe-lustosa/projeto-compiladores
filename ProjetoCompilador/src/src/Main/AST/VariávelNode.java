package Main.AST;

import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Visitor;

public class VariávelNode extends FatorNode {
	public Token N;
	public SeletorNode S; // É uma lista de seletores
	public Node declaração;
	
	public void visit(Visitor v) {
		v.visitVariável(this);
	}
	public VariávelNode(Token N, SeletorNode S) {
		this.N = N;
		this.S = S;
	}
}
