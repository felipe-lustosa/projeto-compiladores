
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
	private Scanner scanner;		//	-l Analise léxica
	private Parser parser;			//	-s Analise sintatica
	private Printer printer;		// 	-a Impressao AST
	private Checker checker;		// 	-c Analise de contexto
//	private Coder coder;			// 	-g Geração de código
	private ProgramaNode P;
	
	public void lexicalAnalysis() {
		System.out.println("Caminho do arquivo-fonte: " + filePath);
		resetReader(); // Reinicia a leitura do arquivo
		System.out.println ("> Analise Léxica - START");
		scanner = new Scanner(reader);
		Token temp = scanner.scan();
		while (Token.EOF != temp.getType())
			temp = scanner.scan();
		reader.reset();
		scanner.toString();
		System.out.println ("< Analise Léxica - END");
		System.out.println ("");
	}
	
	public void syntaxAnalysis() {
		lexicalAnalysis();
		resetReader();
		System.out.println ("> Analise Sintatica - START");
		reader.reset();
		parser = new Parser(reader);
		P = parser.parse();
		System.out.println ("< Analise Sintatica - END");
		System.out.println ("");
	}
	
	public void AST() {
		syntaxAnalysis();
		System.out.println ("> Impressao da Ast - START");
		printer = new Printer();
		printer.print(P);
		System.out.println ("< Impressao da Ast - END");
		System.out.println ("");
	}
	public void tabelaIdenfificacao() {
		System.out.println ("> Tabela de Identificacao - START");
		System.out.println(checker.getTabelaDeIdentificação().toString());
		System.out.println ("< Tabela de Identificacao - END");
	}
	public void contextAnalysis() {
		AST();
		System.out.println ("> Analise de Contexto - START");
		checker = new Checker();
		checker.check(P);
		System.out.println ("< Analise de Contexto - END");
//		tabelaIdenfificacao();
		System.out.println ("");
	}
	
	public void geracaoCodigo() {
		contextAnalysis();
		System.out.println ("> GERAÇÃO DE CÓDIGO - START");
//		coder = new Coder();
//		coder.code(P);
		System.out.println ("< GERAÇÃO DE CÓDIGO - END");
		System.out.println ("");
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
}