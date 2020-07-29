package petz.com.br.controller.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;

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
	
	public static List<PetDto> converter(List<Pet> pets){
		return pets.stream().map(PetDto::new).collect(toList());
	}
}