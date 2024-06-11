public class COMP {
	private static void intrucoesIniciais() {
		System.out.println("\n=== BEM-VINDO AO COMPILADOR ===\n");

		System.out.println("Escolha uma das opçoes de compilaçao:\n");
		System.out.println("\t-l\tAnálise léxica");
		System.out.println("\t-s\tAnálise sintática");
		System.out.println("\t-a\tImpressão da AST");
		System.out.println("\t-c\tAnálise de contexto\n");

		System.out.println("Para compilar, use o comando:");
		System.out.println("\tjava COMP <opçao> <caminho_do_arquivo>\n");

		System.out.println("Exemplo de Análise Léxica:");
		System.out.println("\tjava COMP -l C:\\Users\\User\\Documents\\codigo-fonte.txt\n");
	}
	
	public static void main(String[] args) {
		String path = new String();
		Compiler compiler = new Compiler();
		if (args.length <= 1 || args[0].equals("--help") || args[0].equals("-h")) {
			intrucoesIniciais();
		}
		else {
			path = args[1];
			if (args[0].equals("-l")){
				compiler.setFilePath(path);
				compiler.analiseLexica();
			}
			else if (args[0].equals("-s")){
				compiler.setFilePath(path);
				compiler.analiseSintatica();
	
			}
			else if (args[0].equals("-a")){
				compiler.setFilePath(path);
				compiler.impressaoAST();
			}
			else if (args[0].equals("-c")){
				compiler.setFilePath(path);
				compiler.analiseContexto();
			}
			else if (args[0].equals("-g")){
				compiler.setFilePath(path);
				compiler.geracaoCodigo();
			}
			else {
				System.out.println("A opcao especificada " + args[0] + " é invalida.");
				System.out.println("Siga as instrucoes do comando de ajuda abaixo para compilar.");
			}
		}

	}
}
