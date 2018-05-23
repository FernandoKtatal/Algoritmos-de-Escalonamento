import java.util.LinkedList;
import java.util.Scanner;

public class main {

	//	public static String Arq = "test.txt"; // Caminho do arquivo, alterar depois para relativo antesd e enviar.
	public static void main(String args[]){
		
		//Usuario passa o arquivo pelo pipeline
		Scanner entrada  = new Scanner(System.in);
		
		//Cria a lista de trabalhos do processador
		LinkedList<Job> filaprontos = new LinkedList<Job>();
		int count = 0;
		Job tmp1 = new Job();
		
		while(entrada.hasNextInt()) {
			Job tmp = new Job();
			int aux;
			aux = entrada.nextInt();
			tmp.setTempChegada(aux);
			tmp.setTempEntrada(aux);
			aux = entrada.nextInt();
			tmp.setDuracaoProc(aux);
			filaprontos.add(tmp);
		}
		entrada.close();

		
		// Ordena os processos na ordem de chegada usando o Insertion Sort
		for (int i = 0; i < filaprontos.size(); i++) {
			tmp1 = filaprontos.get(i);
			for (int j = i-1; j >= 0 && filaprontos.get(j).getTempChegada() > tmp1.getTempChegada(); j--) {
				filaprontos.set(j+1, filaprontos.get(j));
				filaprontos.set(j, tmp1);
			}
		}
		//Set os ID do Processo correto (depois que o arquivo eh ordenado corretamente pelo tempo de chegada)
		for (int auxp = 0; auxp < filaprontos.size(); auxp++) {
			filaprontos.get(auxp).setPid(count++);	
		}
		
		Algoritimos.FCFS(new LinkedList<>(filaprontos));
		Algoritimos.SJF(new LinkedList<>(filaprontos));
		Algoritimos.RR(new LinkedList<>(filaprontos));

	}
}