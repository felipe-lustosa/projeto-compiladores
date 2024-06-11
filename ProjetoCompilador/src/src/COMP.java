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
Compiler compiler = new Compiler();
String path = args.length > 1 ? args[1] : "";

if (args.length <= 1 || args[0].equals("--help") || args[0].equals("-h")) {
    intrucoesIniciais();
} else {
    compiler.setFilePath(path);
    switch (args[0]) {
        case "-l":
            compiler.analiseLexica();
            break;
        case "-s":
            compiler.analiseSintatica();
            break;
        case "-a":
            compiler.impressaoAST();
            break;
        case "-c":
            compiler.analiseContexto();
            break;
        case "-g":
            compiler.geracaoCodigo();
            break;
        default:
            System.out.println("A opcao especificada " + args[0] + " é invalida.");
            System.out.println("Siga as instrucoes do comando de ajuda abaixo para compilar.");
            break;
    }
}

	}
}
