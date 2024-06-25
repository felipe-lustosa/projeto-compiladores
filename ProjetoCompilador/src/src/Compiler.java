
import Main.AST.ProgramaNode;
import Main.contextAnalysis.Checker;
import Main.fileReader.TextFileReader;
import Main.lexicalAnalysis.Scanner;
import Main.lexicalAnalysis.Token;
import Main.syntaxAnalysis.Parser;
import Main.syntaxAnalysis.Printer;
// import Main.codeGeneration.Coder;

public class Compiler {
	private String filePath; 	
	private TextFileReader reader;       
	private Scanner scanner;		
	private Parser parser;	
	private Printer printer;		
	private Checker checker;		
	// private Coder coder;		
	private ProgramaNode P;
	
	public void lexicalAnalysis() {
		System.out.println("Caminho do arquivo-fonte: " + filePath);
		resetReader(); // Reinicia a leitura do arquivo
		System.out.println ("Analise Léxica - Inicio");
		scanner = new Scanner(reader);
		Token temp = scanner.scan();
		while (Token.EOF != temp.getType())
			temp = scanner.scan();
		reader.reset();
		scanner.toString();
		System.out.println ("Analise Léxica - Fim");
		System.out.println ("");
	}
	
	public void syntaxAnalysis() {
		lexicalAnalysis();
		resetReader();
		System.out.println ("Analise Sintatica - Inicio");
		reader.reset();
		parser = new Parser(reader);
		P = parser.parse();
		System.out.println ("Analise Sintatica - Fim");
		System.out.println ("");
	}
	
	public void AST() {
		syntaxAnalysis();
		System.out.println ("Impressao da Ast - Inicio");
		printer = new Printer();
		printer.print(P);
		System.out.println ("Impressao da Ast - Fim");
		System.out.println ("");
	}
	public void tabelaIdenfificacao() {
		System.out.println ("Tabela de Identificacao - Inicio");
		System.out.println(checker.getTabelaDeIdentificação().toString());
		System.out.println ("Tabela de Identificacao - Fim");
	}
	public void contextAnalysis() {
		AST();
		System.out.println ("Analise de Contexto - Inicio");
		checker = new Checker();
		checker.check(P);
		System.out.println ("Analise de Contexto - Fim");
		System.out.println ("");
	}
	
	public void geracaoCodigo() {
		contextAnalysis();
		System.out.println ("GERAÇÃO DE CÓDIGO - Inicio");
		// coder = new Coder();
		// coder.code(P);
		System.out.println ("GERAÇÃO DE CÓDIGO - Fim");
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