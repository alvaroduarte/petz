package petz.com.br.controller.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;

import petz.com.br.domain.Cliente;

public class ClienteDto {
	
	private Long id;
	private String nome;
	private Long cpf;
	private Long celular;
    private List<PetDto> pets;
    
    public ClienteDto(Cliente cliente) {
    	this.id = cliente.getId();
    	this.nome = cliente.getNome();
    	this.cpf = cliente.getCpf();
    	this.celular = cliente.getCelular();
    	this.pets = cliente.getPets().stream().map(PetDto::new).collect(toList());
    }
    
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public Long getCpf() {
		return cpf;
	}
	
	public Long getCelular() {
		return celular;
	}
	
	public List<PetDto> getPets() {
		return pets;
	}
}