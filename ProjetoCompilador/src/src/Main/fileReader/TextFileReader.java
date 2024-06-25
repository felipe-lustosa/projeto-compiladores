package Main.fileReader;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;


public class TextFileReader {
	BufferedReader text;
	public static final String UTF8_BOM = "\uFEFF";
	public static final char UTF8_SPACE = '\u0020';
	
	public void cabecalhoErro() {
		System.out.println("!ERRO - LEITURA DE ARQUIVO");
//		System.out.println("-> Linha: " + currentToken.getLine() + ", Posicao: " + currentToken.getColumn());
		
	}
	public String indent() {
		String retorno = new String("\t");
		return retorno;
	}
	public void interromperLeitura() {
		System.out.println(indent() + "A LEITURA DO ARQUIVO FOI INTERROMPIDA DEVIDO A ERROS OCORRIDOS");
		System.exit(0);
	}
	public TextFileReader(String path) {
		try  {
			//text = new BufferedReader(new FileReader(path));	
			text = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
		}
		catch(Exception error) {
			cabecalhoErro();
			System.out.println("-> Nao foi possivel abrir o arquivo para leitura.");
			interromperLeitura();
		}
		
		try {
			text.mark(1000000);//marca a posicao inicial da stream
		}
		catch(Exception E) {
			cabecalhoErro();
			System.out.println("-> Nao foi possivel configurar a marcacao da posicao incial do arquivo.");
			interromperLeitura();
		}
		
		try {	
				
			String current = Character.toString((char) text.read());
			if (current.hashCode() != UTF8_BOM.hashCode())
				text.reset();
			else 
				text.mark(10000000);
		}
		catch(Exception E) {
			cabecalhoErro();
			System.out.println("-> Nao foi possivel configurar a marcacao da posicao incial do arquivo.");
			interromperLeitura();
		}
		
	}
	public char getNextChar() { 
		char current = (char) -1;
		try {
			current = (char) text.read();
			text.mark(10000000);
		}
		catch (Exception E)	{
			cabecalhoErro();
			System.out.println("-> Nao foi possivel ler um dos caracteres do arquivo.");
			interromperLeitura();
		}
		return current;
	}
	public char lookahead()	{
		char current = (char) -1;
		try	{
			current = (char) text.read();
			text.reset(); 
		}
		catch (Exception E) {
			cabecalhoErro();
			System.out.println("-> Nao foi possivel ler um dos caracteres do arquivo.");
			interromperLeitura();
		}
		return current;
	}
	
	public String toString() {
		String str = new String();
		String aux = new String();
		do {
			try {
				aux = text.readLine();
			}
			catch(Exception E) { 
				cabecalhoErro();
				System.out.println("-> Nao foi possivel ler uma das linhas do arquivo.");
				interromperLeitura();
			}
			if (aux != null)
				str = str+aux+"\n";
		} while(aux != null);
		
		reset();
		return str;
	}
	
	public void reset() {
		try {
			text.reset();
		}
		catch(Exception E) {
			cabecalhoErro();
			System.out.println("-> Nao foi possivel resetar o posicao do buffer do arquivo.");
			interromperLeitura();
		}
	}
	
	public void close() {
		try {
			text.close();
		}
		catch(Exception E) {
			cabecalhoErro();
			System.out.println("-> Nao foi possivel fechar o arquivo.");
			interromperLeitura();
		}
	}
}
