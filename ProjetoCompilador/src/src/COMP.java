public class COMP {
	private static void help() {
		System.out.println("\n\n----- BEM VINDO AO COMPILADOR -----\n\n");
		System.out.println("Opcoes de compilacao disponiveis:\n");
		System.out.println("\t -l \t Analise lexica.");
		System.out.println("\t -s \t Analise sintatica.");
		System.out.println("\t -a \t Impressao da AST.");
		System.out.println("\t -c \t Analise de contexto.");
		System.out.println("");
		System.out.println("Para executar a compilacao e necessario digitar o comando \"java COMP\", \n"
                        + "seguido da opcao de compilacao e o caminho completo \npara o arquivo de "
                        + "codigo-fonte desejado.");
		System.out.println("\nO caminho para o arquivo deve ser escrito sem as aspas duplas.\n"
                        + "Exemplo da Analise lexica abaixo:\n");
		System.out.println("\t java COMP -l \"C:\\Users\\User\\Documents\\codigo-fonte.txt\"\n");
	}
	
	public static void main(String[] args) {
		String path = new String();
		Compiler compiler = new Compiler();
		if (args.length <= 1 || args[0].equals("--help") || args[0].equals("-h")) {
			help();
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
