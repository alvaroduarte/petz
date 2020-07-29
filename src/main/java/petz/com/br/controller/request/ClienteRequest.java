package petz.com.br.controller.request;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import petz.com.br.domain.Cliente;
import petz.com.br.domain.Pet;

public class ClienteRequest {
	
	private Long id;
	
	@NotNull
	@NotEmpty
	private String nome;
	
	@NotNull
	@NotEmpty
	@Length(min = 11)
	@Length(max = 11)
	private String cpf;
	
	private String celular;
    
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCelular() {
		return celular;
	}
	public void setCelular(String celular) {
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
		var petsConverter = new ArrayList<Pet>();
		pets.forEach(p -> {
			petsConverter.add(new Pet(p.getNome(), p.getRaca(), cliente));
		});
		cliente.setPets(petsConverter);
		return cliente;
	}
	@Override
	public String toString() {
		return "ClienteRequest [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", celular=" + celular + ", pets="
				+ pets + "]";
	}	
}