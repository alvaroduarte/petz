package petz.com.br.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petz.com.br.controller.CrudClienteController;
import petz.com.br.controller.request.ClienteRequest;
import petz.com.br.domain.Cliente;
import petz.com.br.domain.Pet;
import petz.com.br.repository.ClienteRepository;

@Service
public class ClienteService {
	
	private Logger logger = LogManager.getLogger(CrudClienteController.class);
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public List<Cliente> buscarTodos() {
		
		logger.info("buscarTodos");
		
		return clienteRepository.findAll();
		
	}
	
	public Cliente buscarPorCpf(Long cpf) {
		
		logger.info("buscarPorCpf {}", cpf);
		
		return clienteRepository.findByCpf(cpf);
		
	}
	
	public Cliente buscarPorId(Long id) {
		
		logger.info("buscarPorId {}", id);
			
		final Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		
		if(clienteOptional.isPresent()) {
		
			return clienteOptional.get();
			
		}

		return null;
		
	}
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		
		logger.info("salvar {}", cliente);
		
		var retorno = clienteRepository.save(cliente);
		
		return retorno;
		
	}
	
	@Transactional
	public Cliente atualizar(Long id, ClienteRequest clienteRequest) {
		
		logger.info("atualizar id {}, {}", id, clienteRequest);
		
		final Optional<Cliente> clienteOptional = clienteRepository.findById(id);
		
		if(clienteOptional.isPresent()) {
			
			logger.info("atualizando o cliente id {}", id);
			
			final var cliente = clienteOptional.get();
			
			cliente.setNome(clienteRequest.getNome());
			cliente.setCelular(clienteRequest.getCelular());
			cliente.setCpf(clienteRequest.getCpf());
			
	        final var pets = clienteRequest.getPets().stream().map(p -> new Pet(p.getId(), p.getNome(), p.getRaca(), cliente)).collect(Collectors.toList());
	         
	        cliente.getPets().clear();
	        cliente.getPets().addAll(pets);
	      
			final var retorno = clienteRepository.save(cliente);
			
			logger.info("{} atualizado com sucesso!", retorno);
			
			return retorno;
			
		}
		
		logger.info("Cliente id {} não encontrado na base de dados", id);
		
		return null;
	}
	
	@Transactional
	public Boolean remover(Long id){
		
		logger.info("remover id {}", id);
		
		final var clienteOptional = clienteRepository.findById(id);
		
		if(clienteOptional.isPresent()) {
			
			logger.info("removendo o cliente id {}", id);
			
			clienteRepository.deleteById(id);
			
			logger.info("cliente id {} removido com sucesso", id);
			
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
}