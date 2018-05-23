
public class Job implements Comparable<Job>{
	private int tempChegada;
	private int duracaoProc;
	private int tempEntrada;
	private int pid;
	private boolean ehPrimeiro;
	
	public Job() {
		this.tempChegada = 0; // tempo de chegada
		this.duracaoProc = 0; // pico de CPU
		this.tempEntrada = 0; // tempo de entrada
		this.pid = 0; //numero do processo, pra debugar
		this.ehPrimeiro = true; //booleano se eh a primeira vez 
	}

	public int getTempChegada() {
		return tempChegada;
	}

	public void setTempChegada(int tempChegada) {
		this.tempChegada = tempChegada;
	}

	public int getDuracaoProc() {
		return duracaoProc;
	}

	public void setDuracaoProc(int duracaoProc) {
		this.duracaoProc = duracaoProc;
	}

	public int getTempEntrada() {
		return tempEntrada;
	}

	public void setTempEntrada(int tempEntrada) {
		this.tempEntrada = tempEntrada;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public boolean getEhPrimeiro() {
		return ehPrimeiro;
	}

	public void setEhPrimeiro(boolean ehPrimeiro) {
		this.ehPrimeiro = ehPrimeiro;
	}

//	Pra debugar
//	@Override
//	public String toString() {
//		return "Job [tempChegada=" + tempChegada + ", duracaoProc=" + duracaoProc + ", tempEntrada=" + tempEntrada
//				+ ", pid=" + pid + ", ehPrimeiro=" + ehPrimeiro + "]";
//	}

	public boolean equals(Job lvalue){
		return this.getDuracaoProc() == lvalue.getDuracaoProc();
	}
	
	public int compareTo(Job lvalue){
		if(equals(lvalue))
			return 0;
		if(this.duracaoProc > lvalue.getDuracaoProc())
			return 1;
		else
			return -1;
	}
}