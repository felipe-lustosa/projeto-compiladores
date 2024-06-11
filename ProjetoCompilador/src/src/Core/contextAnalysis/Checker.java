package Core.contextAnalysis;
import Core.syntaxAnalysis.Visitor;
import Core.AST.SeletorNode;
import Core.AST.TipoAgregadoNode;
import Core.AST.ComandoCondicionalNode;
import Core.AST.SequênciaFatoresNode;
import Core.AST.FatorNode;
import Core.AST.ComandoNode;
import Core.AST.OperadorNode;
import Core.AST.VariávelNode;
import Core.AST.ExpressãoNode;
import Core.AST.ListaDeIdsNode;
import Core.AST.TermoNode;
import Core.AST.ComandoAtribuiçãoNode;
import Core.AST.CorpoNode;
import Core.AST.ListaDeComandosNode;
import Core.AST.ProgramaNode;
import Core.AST.Node;
import Core.AST.ExpressãoSimplesNode;
import Core.AST.TipoNode;
import Core.AST.ComandoIterativoNode;
import Core.AST.TipoSimplesNode;
import Core.AST.SequênciaTermosNode;
import Core.AST.DeclaraçãoSequencialNode;
import Core.AST.DeclaraçãoDeVariávelNode;
import Core.AST.DeclaraçãoNode;
import Core.AST.ComandoCompostoNode;
import Core.AST.LiteralNode;
import Core.lexicalAnalysis.Token;

public class Checker implements Visitor {

	private TabelaDeIdentificação idTable = new TabelaDeIdentificação();
	
	public String indent() {
		String retorno = new String("\t");
		return retorno;
	}
	public void cabeçalhoErro() {
		System.out.println(indent() + "!ERRO - ANALISE DE CONTEXTO");
		System.out.print(indent() +   "  └ ");
	}
	public void cabeçalhoErro(int linha, int posição) {
		System.out.println(indent() + "!ERRO - ANALISE DE CONTEXTO");
		if (posição == -1)
			System.out.println(indent() + "  * Linha: " + linha);
		else 
			System.out.println(indent() + "  * Linha: " + linha + ", Posição: " + posição);
		System.out.print(indent() +   "  └ ");
	}
	public Checker() {
		this.idTable = new TabelaDeIdentificação();
	}
	
	
	public void check (ProgramaNode P) {
		System.out.println ("---> Iniciando identificacao de nomes");
		P.visit(this);
	}
	
	@Override
	public void visitComandoAtribuição(ComandoAtribuiçãoNode CA) {
		
		if (CA.V != null) CA.V.visit(this); // Isto cobre a regra E1.
		
		if (CA.E != null) CA.E.visit(this);
		boolean erro = false;
		switch (CA.V.tipo) {
			case TabelaDeIdentificação.BOOLEAN:
				if (CA.E.tipo != TabelaDeIdentificação.BOOLEAN)
					erro = true;
				break;
			case TabelaDeIdentificação.INTEGER:
				if (CA.E.tipo != TabelaDeIdentificação.INTEGER)
					erro = true;
				break;
			case TabelaDeIdentificação.REAL:
				erro = true;
				break;
			default:
				break;
		}
		if (erro) {
			cabeçalhoErro(CA.V.N.getLine(), CA.V.N.getColumn());
			System.out.println("[T1] Atribuição com tipos incompativeis." 
				+ " A variável " + CA.V.N.getSpelling() + " possui tipo " + CA.V.tipo + " [" + Token.spellings[CA.V.tipo] + "]" 
				+ " enquanto a expressão possui tipo " + CA.E.tipo + " [" + Token.spellings[CA.E.tipo] + "].");
		}
	}

	@Override
	public void visitComandoComposto(ComandoCompostoNode CC) {
		if (CC.LC != null) CC.LC.visit(this);
	}

	@Override
	public void visitComandoCondicional(ComandoCondicionalNode CC) {
		
		if (CC.E != null) CC.E.visit(this);
		if (CC.E.tipo != TabelaDeIdentificação.BOOLEAN) {
			cabeçalhoErro();
			System.out.println("[T2] Tipos incompativeis no comando condicional." 
					+ " A expressão possui tipo " + CC.E.tipo + " [" + Token.spellings[CC.E.tipo] + "]" 
					+ " enquanto era esperado o tipo " + TabelaDeIdentificação.BOOLEAN + " [" + Token.spellings[TabelaDeIdentificação.BOOLEAN] + "].");
		}
		if (CC.C1 != null) CC.C1.visit(this);
		if (CC.C2 != null) CC.C2.visit(this);
	}

	@Override
	public void visitComandoIterativo(ComandoIterativoNode CC) {
		// TODO Auto-generated method stub
		// REGRA T5. O tipo da expressão avaliada deve ser um valor lógico (booleano).
		if (CC.E != null) CC.E.visit(this);
		if (CC.E.tipo != TabelaDeIdentificação.BOOLEAN) {
//			cabeçalhoErro(, (int) -1);
			cabeçalhoErro();
			System.out.println("[T5] Tipos incompativeis no comando iterativo." 
					+ " A expressão possui tipo " + CC.E.tipo + " [" + Token.spellings[CC.E.tipo] + "]" 
					+ " enquanto era esperado o tipo " + TabelaDeIdentificação.BOOLEAN + " [" + Token.spellings[TabelaDeIdentificação.BOOLEAN] + "].");
		}
		if (CC.C != null) CC.C.visit(this);
	}

	@Override
	public void visitComando(ComandoNode C) {
		// TODO Auto-generated method stub
		if (C != null) C.visit(this);
	}

	@Override
	public void visitCorpo(CorpoNode C) {
		// TODO Auto-generated method stub
		if (C.D != null) C.D.visit(this);

		if (C.CC != null) C.CC.visit(this);
	}

	@Override
	public void visitDeclaraçãoDeVariável(DeclaraçãoDeVariávelNode D) {
		// TODO Auto-generated method stub
		// REGRA E2. Os identificadores presentes na lista de ids não podem ter sido declarados
		// anteriormente no mesmo bloco da declaração atual
		ListaDeIdsNode temp = D.LI;
		Node atributo = D;
		while (temp != null) {
			this.idTable.enter(temp.I, atributo); // Se já tiverem sido declarados, informará erro.
			temp = temp.próximaLI;
		}
		if (D.próximaD != null) {
			D.próximaD.visit(this);
		}
		D.T.visit(this);
	}

	@Override
	public void visitDeclaração(DeclaraçãoNode D) {
		// TODO Auto-generated method stub
		if (D != null) D.visit(this);
	}

	@Override
	public void visitDeclaraçãoSequencial(DeclaraçãoSequencialNode D) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitExpressão(ExpressãoNode E) {
		// TODO Auto-generated method stub
		// REGRA T3. Os tipos de ambas expressões simples avaliadas devem ser iguais
		if (E.E1 != null) E.E1.visit(this);
//		if (E.O != null) E.O.visit(this);
//		if (E.E2 != null) E.E2.visit(this);
		switch(E.E1.tipo) {
			case Token.INTLITERAL:
				E.tipo = Token.INTEGER;
				break;
			case Token.FLOATLITERAL:
				E.tipo = Token.REAL;
				break;
			case Token.FALSE: case Token.TRUE:
				E.tipo = Token.BOOLEAN;
				break;
			default:
				E.tipo = E.E1.tipo; // Caso padrão tem o mesmo tipo da Expressão 1
				break;
		}
		
		if (E.O != null && E.E2 != null) {
			E.E2.visit(this);
			if (E.E1.tipo != E.E2.tipo) {
				cabeçalhoErro(E.O.O.getLine(), E.O.O.getColumn());
				System.out.println("[T3] Operandos"
						+ " possuem tipos incompativeis para o operador relacional " + E.O.O.getSpelling() + "." 
						+ " O primeiro operando possui tipo " + E.E1.tipo + " [" + Token.spellings[E.E1.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + E.E2.tipo + " [" + Token.spellings[E.E2.tipo] + "].");
			}
			switch (E.O.O.getType()) {
				case Token.OPLOWERTHN: 
				case Token.OPGREATTHN: 
				case Token.OPLOWOREQ: 
				case Token.OPGREOREQ: 
				case Token.OPEQUAL: 
				case Token.OPDIFF:
					E.tipo = TabelaDeIdentificação.BOOLEAN;
				default:
			}
		}
	}

	@Override
	public void visitExpressãoSimples(ExpressãoSimplesNode ES) {
		// TODO Auto-generated method stub
		// REGRA T4. O tipo da expressão simples avaliada deve ser igual ao tipo do termo avaliado.
		if (ES.T != null) {
			ES.T.visit(this);
			switch(ES.T.tipo) {
				case Token.INTLITERAL:
					ES.tipo = Token.INTEGER;
					break;
				case Token.FLOATLITERAL:
					ES.tipo = Token.REAL;
					break;
				case Token.FALSE: case Token.TRUE:
					ES.tipo = Token.BOOLEAN;
					break;
				default:
					ES.tipo = ES.T.tipo; // Caso padrão tem o mesmo tipo do primeiro Termo
					break;
			}
		}
		
		if (ES.ST != null) {
			ES.ST.visit(this);
			if (ES.T.tipo != ES.ST.tipo) {	// REGRA T4
				cabeçalhoErro(ES.ST.O.O.getLine(), ES.ST.O.O.getColumn() );
				System.out.println("[T4] Operandos"
						+ " possuem tipos incompativeis para o operador " + ES.ST.O.O.getSpelling() + "." 
						+ " O primeiro operando possui tipo " + ES.T.tipo + " [" + Token.spellings[ES.T.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + ES.ST.tipo + " [" + Token.spellings[ES.ST.tipo] + "].");
			}
			switch (ES.ST.O.O.getType()) {
				case Token.OPSUM: 
				case Token.OPSUB:
					switch(ES.T.tipo) {
						case Token.INTLITERAL:
							ES.tipo = Token.INTEGER;
							break;
						case Token.FLOATLITERAL:
							ES.tipo = Token.REAL;
							break;
						case Token.FALSE: case Token.TRUE:
							ES.tipo = Token.BOOLEAN;
							break;
						default:
							ES.tipo = ES.T.tipo;
							break;
					}					
					break;
				case Token.OR:
					ES.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitFator(FatorNode F) {
		if (F != null) F.visit(this);
	}

	@Override
	public void visitListaDeComandos(ListaDeComandosNode LC) {
		
		LC.C.visit(this);
		
		if (LC.próximaLC != null) {
			LC.próximaLC.visit(this);
		}
	}

	@Override
	public void visitListaDeIds(ListaDeIdsNode LI) {

			if (LI.I != null) {

			}
			
			if (LI.próximaLI != null) {
				LI.próximaLI.visit(this);
			}
	}

	@Override
	public void visitLiteral(LiteralNode L) {
		
		switch(L.L.getType()) {
			case Token.INTLITERAL:
				L.tipo = Token.INTEGER;
				break;
			case Token.FLOATLITERAL:
				L.tipo = Token.REAL;
				break;
			case Token.FALSE: case Token.TRUE:
				L.tipo = Token.BOOLEAN;
				break;
			default:
				L.tipo = L.L.getType(); // Caso padrão O tipo do literal é o tipo do token
				break;
		}
	}

	@Override
	public void visitNode(Node N) {

	}

	@Override
	public void visitOperador(OperadorNode O) {
		if (O.O != null) {
		}
	}

	@Override
	public void visitPrograma(ProgramaNode P) {
		if (P != null) {
			Node atributo = P;
			this.idTable.enter(P.N, atributo); // Se já tiverem sido declarados, informará erro.

			if (P.C != null) P.C.visit(this);
		}
	}

	@Override
	public void visitSeletor(SeletorNode S) {
		if (S.E != null) S.E.visit(this);
		if (S.E.tipo != TabelaDeIdentificação.INTEGER) {
			cabeçalhoErro();
			System.out.println("[T6] Tipos incompativeis do indice do seletor." 
					+ " A expressão possui tipo " + S.E.tipo + " [" + Token.spellings[S.E.tipo] + "]" 
					+ " enquanto era esperado o tipo " + TabelaDeIdentificação.INTEGER + " [" + Token.spellings[TabelaDeIdentificação.INTEGER] + "].");
		}
		if (S.próximoS != null) {
			S.próximoS.visit(this);
		}
	}

	@Override
	public void visitSequênciaFatores(SequênciaFatoresNode SF) {
		if (SF.O != null) SF.O.visit(this);
		if (SF.F != null) {
			SF.F.visit(this);
			switch(SF.F.tipo) {
				case Token.INTLITERAL:
					SF.tipo = Token.INTEGER;
					break;
				case Token.FLOATLITERAL:
					SF.tipo = Token.REAL;
					break;
				case Token.FALSE: case Token.TRUE:
					SF.tipo = Token.BOOLEAN;
					break;
				default:
					SF.tipo = SF.F.tipo; // Caso padrão
					break;
			}
		}
		if (SF.próximaS != null) {
			SF.próximaS.visit(this);
			if (SF.F.tipo != SF.próximaS.tipo) {	// REGRA T7
				cabeçalhoErro(SF.próximaS.O.O.getLine(), SF.próximaS.O.O.getColumn());
				System.out.println("[T7] Operandos"
						+ " possuem tipos incompativeis para o operador " + SF.próximaS.O.O.getSpelling() + "." 
						+ " O primeiro operando possui tipo " + SF.F.tipo + " [" + Token.spellings[SF.F.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + SF.próximaS.tipo + " [" + Token.spellings[SF.próximaS.tipo] + "].");
			}
			switch (SF.próximaS.O.O.getType()) {
				case Token.OPSUM: 
				case Token.OPSUB:
					switch(SF.F.tipo) {
						case Token.INTLITERAL:
							SF.tipo = Token.INTEGER;
							break;
						case Token.FLOATLITERAL:
							SF.tipo = Token.REAL;
							break;
						case Token.FALSE: case Token.TRUE:
							SF.tipo = Token.BOOLEAN;
							break;
						default:
							SF.tipo = SF.F.tipo;
							break;
					}
					break;
				case Token.OR:
					SF.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitSequênciaTermos(SequênciaTermosNode ST) {
		if (ST.O != null) ST.O.visit(this);
		if (ST.T != null) {
			ST.T.visit(this);
			switch(ST.T.tipo) {
				case Token.INTLITERAL:
					ST.tipo = Token.INTEGER;
					break;
				case Token.FLOATLITERAL:
					ST.tipo = Token.REAL;
					break;
				case Token.FALSE: case Token.TRUE:
					ST.tipo = Token.BOOLEAN;
					break;
				default:
					ST.tipo = ST.T.tipo; // Caso padrão
					break;
			}
		}		
		if (ST.próximaS != null) {
			ST.próximaS.visit(this);
			if (ST.T.tipo != ST.próximaS.tipo) {	// REGRA T4
				cabeçalhoErro(ST.próximaS.O.O.getLine(),ST.próximaS.O.O.getColumn());
				System.out.println("[T4] Operandos"
						+ " possuem tipos incompativeis para o operador " + ST.próximaS.O.O.getSpelling() + "." 
						+ " O primeiro operando possui tipo " + ST.T.tipo + " [" + Token.spellings[ST.T.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + ST.próximaS.tipo + " [" + Token.spellings[ST.próximaS.tipo] + "].");
			}
			switch (ST.próximaS.O.O.getType()) {
				case Token.OPSUM: 
				case Token.OPSUB:
					switch(ST.T.tipo) {
						case Token.INTLITERAL:
							ST.tipo = Token.INTEGER;
							break;
						case Token.FLOATLITERAL:
							ST.tipo = Token.REAL;
							break;
						case Token.FALSE: case Token.TRUE:
							ST.tipo = Token.BOOLEAN;
							break;
						default:
							ST.tipo = ST.T.tipo;
							break;
					}
					break;
				case Token.OR:
					ST.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitTermo(TermoNode T) {
		
		if (T.F != null) {
			T.F.visit(this);
			switch(T.F.tipo) {
				case Token.INTLITERAL:
					T.tipo = Token.INTEGER;
					break;
				case Token.FLOATLITERAL:
					T.tipo = Token.REAL;
					break;
				case Token.FALSE: case Token.TRUE:
					T.tipo = Token.BOOLEAN;
					break;
				default:
					T.tipo = T.F.tipo; // Caso padrão
					break;
			}
		}
		
		if (T.SF != null) {
			T.SF.visit(this);
			if (T.F.tipo != T.SF.tipo) {	// REGRA T7
				cabeçalhoErro(T.SF.O.O.getLine(), T.SF.O.O.getColumn());
				System.out.println("[T7] Operandos"
						+ " possuem tipos incompativeis para o operador " + T.SF.O.O.getSpelling() + "." 
						+ " O primeiro operando possui tipo " + T.F.tipo + " [" + Token.spellings[T.F.tipo] + "]" 
						+ " enquanto o segundo operando possui tipo " + T.SF.tipo + " [" + Token.spellings[T.SF.tipo] + "].");
			}
			switch (T.SF.O.O.getType()) {
				case Token.OPMULT: 
				case Token.OPDIV:
					switch(T.F.tipo) {
						case Token.INTLITERAL:
							T.tipo = Token.INTEGER;
							break;
						case Token.FLOATLITERAL:
							T.tipo = Token.REAL;
							break;
						case Token.FALSE: case Token.TRUE:
							T.tipo = Token.BOOLEAN;
							break;
						default:
							T.tipo = T.F.tipo;
							break;
					}
					break;
				case Token.AND:
					T.tipo = TabelaDeIdentificação.BOOLEAN;
					break;
				default:
			}
		}
	}

	@Override
	public void visitTipoAgregado(TipoAgregadoNode TA) {
		
		if (TA.T != null) TA.T.visit(this);
		if (TA.INDEX_1 != null) TA.INDEX_1.visit(this);		
		if (TA.INDEX_2  != null) TA.INDEX_2.visit(this);
		if (Integer.parseInt(TA.INDEX_1.getSpelling()) > Integer.parseInt(TA.INDEX_2.getSpelling())) {
			cabeçalhoErro();
			System.out.println("[T8] Indices inválidos na declaracao de tipo agregado.");
		}
	}

	@Override
	public void visitTipo(TipoNode T) {
		if (T != null) T.visit(this);
	}

	@Override
	public void visitTipoSimples(TipoSimplesNode TS) {
		if (TS.N != null) TS.N.visit(this);
	}
	
	@Override
	public void visitToken(Token T) {
		if (T != null) {
			
		}
	}

	@Override
	public void visitVariável(VariávelNode V) {
		
		V.declaração = idTable.retrieve(V.N);	// Responsável pelas regras E1 e E3.
		if (V.declaração instanceof DeclaraçãoDeVariávelNode) {
			TipoNode ti = ((DeclaraçãoDeVariávelNode) V.declaração).T;

			if (ti instanceof TipoAgregadoNode) {
				TipoNode temp = ((TipoAgregadoNode) ti).T;
				while (temp instanceof TipoAgregadoNode) { // Vamos chegar no tipo primitivo do array
					temp = ((TipoAgregadoNode) temp).T;
				}
				V.tipo = ((TipoSimplesNode) temp).N.getType();
				// Se for um tipo agregado e não houverem seletores eu não sei o que deve acontecer
				if (V.S == null)  { // REGRA T9
					
		
				}
				else {
					V.S.visit(this);
				}
			}
			else if (ti instanceof TipoSimplesNode) {
				V.tipo = ((TipoSimplesNode) ti).N.getType(); 
				// Se for um tipo simples e houver um seletor, então deve retornar erro.
				if (V.S != null) { // REGRA T9
					cabeçalhoErro(V.N.getLine(), V.N.getColumn());
					System.out.println("[T9] O tipo da variável " + V.N.getSpelling()
					+ " deve ser um array.");
//					+ " O primeiro operando possui tipo " + ST.T.tipo + " [" + Token.spellings[ST.T.tipo] + "]" 
//					+ " enquanto o segundo operando possui tipo " + ST.próximaS.tipo + " [" + Token.spellings[ST.próximaS.tipo] + "].");
				}
			} 

		}
		if (V.S != null) V.S.visit(this);
	}
	
	public TabelaDeIdentificação getTabelaDeIdentificação() {
		return this.idTable;
	}
}
