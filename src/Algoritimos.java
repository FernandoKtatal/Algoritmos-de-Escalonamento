import java.util.LinkedList;
import java.util.PriorityQueue;

public class Algoritimos {
	
	public static void FCFS(LinkedList<Job> filaprontos){

		int timeSys = 0; // Variável que armazena o tempo atual do sistema
		float tempoDeRetornoMedio = 0; 
		float tempoDeRespostaMedio = 0;
		float tempoDeEsperaMedio = 0;
		int length = filaprontos.size(); //Número de processos que serão escalonados
		Job tmp = new Job();

		//O escalonador vai executar enquanto existir processsos na fila de prontos.
		while(filaprontos.peek() != null){ 
			// peek serve parar retornar o primeiro elemento
			/*
			O sistema só pode escalonar processos que já se encontrem na fila de prontos, devido a isso é necessário 
			realizar um teste para ter certeza que o tempo do sistema, naquele instante, é maior ou igual a o tempo 
			de chegada do processo. Caso contrario precisamos atualizar o tempo do sistema para o tempo de chegada do
			processo.
			 */
			if(timeSys >= filaprontos.peek().getTempChegada()){
				tmp = filaprontos.poll(); //Simulação do momento em que o processo sai da fila e assume o processador

				//Calcula dos tempos de Resposta e Espera
				tempoDeRespostaMedio += timeSys - tmp.getTempChegada();
				tempoDeEsperaMedio += timeSys - tmp.getTempChegada();

				timeSys+= tmp.getDuracaoProc();//Atualização do tempo do sistema, momento em que o processo deixa o processador
				tempoDeRetornoMedio += timeSys - tmp.getTempChegada();//Calcula do tempo de retorno
			}
			else{
				/*
					Caso nenhum processo tenha chegado na fila para ser escalonado 
					essa instrução simula a passagem de tempo do sistema
				 */
				timeSys = filaprontos.peek().getTempChegada();
			}
		}

		System.out.printf("FCFS %.1f %.1f %.1f\n",(tempoDeRetornoMedio/length), (tempoDeRespostaMedio/length), (tempoDeEsperaMedio/length));
	}


	public static void SJF(LinkedList<Job> filaprontos){

		PriorityQueue<Job> filaauxSJF = new PriorityQueue<Job>(); //Fila de prioderidade auxiliar (poll e peek)
		int timeSys = 0; // Variável que armazena o tempo atual do sistema
		float tempoDeRetornoMedio = 0;
		float tempoDeRespostaMedio = 0;
		float tempoDeEsperaMedio = 0;
		int length = filaprontos.size(); //Número de processos que serão escalonados
		Job tmp = new Job();
		int i = 0;
		
		//Vai executar enquanto existir processos na fila de prontos ou na de prioridades
		while(filaprontos.peek() != null || filaauxSJF.peek() != null){
			/*
				Verifica se existem procesoss na fila de prontos e se o tempo do sistema sistema eh maior ou igual
				ao tempo de chegada desses processos, removemos estes processos da fila de prontos e 
				colocamos nade prioridades do SJF
			 */
			while(filaprontos.peek() != null && timeSys >= filaprontos.peek().getTempChegada()){
//				System.out.println("timesys = " + timeSys);
				filaauxSJF.add(filaprontos.poll());
			}
//			for(Job s : filaauxSJF) { 
//				System.out.println("ID = " + s.getPid()); 
//			}
			/*
				Teste que verifica se existe algum processo na fila do SJF
			 */
			if(filaauxSJF.peek() != null){
				tmp = filaauxSJF.poll(); //Simula a saida do processo na fila de prontos e entrando no processador

				tempoDeRespostaMedio += timeSys - tmp.getTempChegada();
				tempoDeEsperaMedio += timeSys - tmp.getTempChegada();
//				System.out.println("Rodar Processo [" + tmp.getPid() + "] de [" + timeSys + "]" + " até [" + (tmp.getDuracaoProc() + timeSys) + "]");
				timeSys += tmp.getDuracaoProc();	
				tempoDeRetornoMedio += timeSys - tmp.getTempChegada();			
			}else{
				/*
					Caso nenhum processo tenha chegado na fila do SJF para ser escalonado 
					essa instrução simula a passagem de tempo do sistema
				 */
				timeSys = filaprontos.peek().getTempChegada();
			}
		}

		System.out.printf("SJF %.1f %.1f %.1f\n",(tempoDeRetornoMedio/length), (tempoDeRespostaMedio/length), (tempoDeEsperaMedio/length));
	}

	public static void RR(LinkedList<Job> filaprontos){

		int timeSys = 0; // Variável que armazena o tempo atual do sistema
		float tempoDeRetornoMedio = 0;
		float tempoDeRespostaMedio = 0;
		float tempoDeEsperaMedio = 0;
		int length = filaprontos.size(); //Número de processos que serão escalonados

		LinkedList<Job> filaauxRR = new LinkedList<Job>();// Fila auxiliar 
		Job tmp = new Job();

		//Enquanto existir processos na fila de prontos ou na fila auxiliar o escalonador deve continuar a executar
		while (filaprontos.peek() != null || filaauxRR.peek() != null){
			/*
				Caso ainda existam procesoss na fila de prontos e o tempo do sistema sistema seja maior ou igual
				ao tempo de chegada desses processos, podemos retirar esses processos da fila de prontos e 
				inserir na fila de auxiliar do RR
			 */
			while(filaprontos.peek() != null && timeSys >= filaprontos.peek().getTempChegada())
				filaauxRR.add(filaprontos.poll());

			/*
				Teste que verifica se existe algum processo na fila do RR
			 */
			if(filaauxRR.peek() != null){

				tmp = filaauxRR.poll(); //Simula a saida do processo na fila de prontos e entrando no processador
				tempoDeEsperaMedio += timeSys - tmp.getTempEntrada();

				//Teste que verifica se é a primeira resposta do processo
				if(tmp.getEhPrimeiro()){
					tempoDeRespostaMedio += timeSys - tmp.getTempChegada();
					tmp.setEhPrimeiro(false); //deixa de ser a primeira vez
				}
				/*
				 	Teste que verifica se o tempo restante de processamento do processo é menor que 2
					para garantir que processo só executara o tempo necessário
				 */
				if(tmp.getDuracaoProc() < 2){
					timeSys += tmp.getDuracaoProc();
					tmp.setDuracaoProc(0);
				}else{
					timeSys += 2;				
					tmp.setDuracaoProc(tmp.getDuracaoProc() - 2);
				}

				/*
					Após a atualização do tempo do sistema devemos garantir que os processos que possuem 
					tempo de chegada maior ou igual ao tempo atual do sistema entrem na fila do RR antes de inserir 
					novamente o processo que está ocupando o processador.
				 */ 
				while(filaprontos.peek() != null && timeSys >= filaprontos.peek().getTempChegada())
					filaauxRR.add(filaprontos.poll());

				/*
					Teste que verifica se o processo terminou ou se deve ser inserido novamente na fila do RR
				 */
				if(tmp.getDuracaoProc() > 0){
					tmp.setTempEntrada(timeSys);
					filaauxRR.add(tmp);
				}else{
					/*
						Caso o processo não seja inserido novamente na fila, devemos considerar que ele já retornou
					 */
					tempoDeRetornoMedio += timeSys - tmp.getTempChegada();
				}
			}else
				/*
					Caso nenhum processo tenha chegado na fila do SJF para ser escalonado 
					essa instrução simula a passagem de tempo do sistema
				 */
				timeSys = filaprontos.peek().getTempChegada();
		}
		System.out.printf("RR %.1f %.1f %.1f\n",(tempoDeRetornoMedio / length), (tempoDeRespostaMedio / length), (tempoDeEsperaMedio / length));
	}
}