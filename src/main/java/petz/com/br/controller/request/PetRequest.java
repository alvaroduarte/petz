package petz.com.br.controller.request;

public class PetRequest {
	
	private Long id;
	
	private String nome;
	private String raca;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getRaca() {
		return raca;
	}
	public void setRaca(String raca) {
		this.raca = raca;
	}
	
	@Override
	public String toString() {
		return "PetRequest [id=" + id + ", nome=" + nome + ", raca=" + raca + "]";
	}
}