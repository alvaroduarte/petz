package petz.com.br.controller.dto;

import static java.util.stream.Collectors.toList;

import java.util.List;

import petz.com.br.domain.Cliente;

public class ClienteDto {
	
	private Long id;
	private String nome;
	private String cpf;
	private String celular;
    private List<PetDto> pets;
    
    public ClienteDto(Cliente cliente) {
    	this.id = cliente.getId();
    	this.nome = cliente.getNome();
    	this.cpf = cliente.getCpf();
    	this.celular = cliente.getCelular();
    	//this.pets = cliente.getPets();
    	this.pets = cliente.getPets().stream().map(PetDto::new).collect(toList());
    	
    }
    
	public Long getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public String getCelular() {
		return celular;
	}
	
	public List<PetDto> getPets() {
		return pets;
	}
	
	public static List<ClienteDto> converter(List<Cliente> clientes){
		return clientes.stream().map(ClienteDto::new).collect(toList());
	}
}