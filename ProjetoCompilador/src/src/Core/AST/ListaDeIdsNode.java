package Core.AST;

import Core.lexicalAnalysis.Token;
import Core.syntaxAnalysis.Visitor;

public class ListaDeIdsNode extends Node {
	public Token I;
	public ListaDeIdsNode próximaLI;
	
	public void visit(Visitor v) {
		v.visitListaDeIds(this);
	}
	public ListaDeIdsNode(Token I) {
		this.I = I;
		this.próximaLI = null;
	}
	public ListaDeIdsNode(Token I, ListaDeIdsNode próximaLI) {
		this.I = I;
		this.próximaLI = próximaLI;
	}
}
