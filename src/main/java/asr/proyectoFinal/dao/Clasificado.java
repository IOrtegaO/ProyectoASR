package asr.proyectoFinal.dao;

public class Clasificado {
	
	private String clase;
	private String score;
	
	public Clasificado(String clase, String score) {
		super();
		this.clase = clase;
		this.score = score;
	}
	
	

	public Clasificado() {
		super();
	}



	public String getClase() {
		return clase;
	}

	public void setClase(String clase) {
		this.clase = clase;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}
	
	

}
