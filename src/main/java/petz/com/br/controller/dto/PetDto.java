package petz.com.br.controller.dto;

import petz.com.br.domain.Pet;

public class PetDto {

	private Long id;
	private String nome;
	private String raca;
	
	public PetDto(Pet pet){
		this.id = pet.getId();
		this.nome = pet.getNome();
		this.raca = pet.getRaca();
	}
	
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getRaca() {
		return raca;
	}
}