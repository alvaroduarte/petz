package petz.com.br.controller.request;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import petz.com.br.domain.Cliente;
import petz.com.br.domain.Pet;

public class ClienteRequest {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	private Long cpf;
	
	private Long celular;
    
	@NotNull
	@NotEmpty
	private List<PetRequest> pets;
    
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
	public Long getCpf() {
		return cpf;
	}
	public void setCpf(Long cpf) {
		this.cpf = cpf;
	}
	public Long getCelular() {
		return celular;
	}
	public void setCelular(Long celular) {
		this.celular = celular;
	}
	public List<PetRequest> getPets() {
		return pets;
	}
	public void setPets(List<PetRequest> pets) {
		this.pets = pets;
	}
	public Cliente converter() {
		
		var cliente = new Cliente(nome, cpf, celular);
		
		cliente.setPets(pets.stream().map(p -> new Pet(p.getNome(), p.getRaca(), cliente)).collect(Collectors.toList()));
		
		return cliente;
	}
	@Override
	public String toString() {
		return "ClienteRequest [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", celular=" + celular + ", pets="
				+ pets + "]";
	}	
}