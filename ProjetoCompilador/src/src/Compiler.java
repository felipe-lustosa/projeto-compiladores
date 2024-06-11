
import Core.AST.ProgramaNode;
import Core.fileReader.TextFileReader;
import Core.lexicalAnalysis.Scanner;
import Core.lexicalAnalysis.Token;
//import frontEnd.lexicalAnalysis.Token;
import Core.syntaxAnalysis.Parser;
import Core.syntaxAnalysis.Printer;
import Core.contextAnalysis.Checker;

public class Compiler {
	private String filePath; 		//	Caminho completo do arquivo
	private TextFileReader reader;          //	Leitura do arquivo com código fonte
	private Scanner scanner;		//	-l Análise léxica
	private Parser parser;			//	-s Análise sintática
	private Printer printer;		// 	-a Impressão AST
	private Checker checker;		// 	-c Análise de contexto
//	private Coder coder;			// 	-g Geração de código
	private ProgramaNode P;
	
	public Compiler() {
		
	}
	public void setFilePath(String path) {
		filePath = new String(path);
	}
	
	public void resetReader() {
		String text = new String();
		reader = new TextFileReader(filePath);
		text = reader.toString();
		if (text.isEmpty()) {
			System.out.println("O arquivo lido estava vazio.");
		}
	}
	
	public void analiseLexica() {
		System.out.println("Caminho do arquivo-fonte: " + filePath);
		resetReader(); // Reinicia a leitura do arquivo
		System.out.println ("> Análise Léxica - START");
		scanner = new Scanner(reader);
		Token temp = scanner.scan();
		while (Token.EOF != temp.getType())
			temp = scanner.scan();
		reader.reset();
		scanner.toString();
		System.out.println ("< Análise Léxica - END");
		System.out.println ("");
	}
	
	public void analiseSintatica() {
		analiseLexica();
		resetReader();
		System.out.println ("> Análise Sintática - START");
		reader.reset();
		parser = new Parser(reader);
		P = parser.parse();
		System.out.println ("< Análise Sintática - END");
		System.out.println ("");
	}
	
	public void impressaoAST() {
		analiseSintatica();
		System.out.println ("> Impressão da Ast - START");
		printer = new Printer();
		printer.print(P);
		System.out.println ("< Impressão da Ast - END");
		System.out.println ("");
	}
	public void tabelaIdenfificacao() {
		System.out.println ("> Tabela de Identificação - START");
		System.out.println(checker.getTabelaDeIdentificação().toString());
		System.out.println ("< Tabela de Identificação - END");
	}
	public void analiseContexto() {
		impressaoAST();
		System.out.println ("> Análise de Contexto - START");
		checker = new Checker();
		checker.check(P);
		System.out.println ("< Análise de Contexto - END");
//		tabelaIdenfificacao();
		System.out.println ("");
	}
	
	public void geracaoCodigo() {
		analiseContexto();
		System.out.println ("> GERAÇÃO DE CÓDIGO - START");
//		coder = new Coder();
//		coder.code(P);
		System.out.println ("< GERAÇÃO DE CÓDIGO - END");
		System.out.println ("");
	}
}