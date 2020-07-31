package petz.com.br.controller.request.converter;


import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import petz.com.br.controller.CrudClienteController;
import petz.com.br.controller.request.ClienteRequest;
import petz.com.br.domain.Cliente;
import petz.com.br.domain.Pet;
import petz.com.br.repository.ClienteRepository;

@Configuration
public class ClienteRequestConverterCliente implements Converter<ClienteRequest, Cliente> {
	
	private Logger logger = LogManager.getLogger(CrudClienteController.class);
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Cliente convert(ClienteRequest source) {
			
		logger.info("convert {}", source);
		
		if(ofNullable(source.getId()).isEmpty()) {
			
			var cliente = new Cliente(source.getNome(), source.getCpf(), source.getCelular());
			
			cliente.setPets(source.getPets().stream().map(p -> new Pet(p.getNome(), p.getRaca(), cliente)).collect(Collectors.toList()));
			
			return cliente;
			
		} else {
			
			logger.debug("Buscando o cliente id {} na base de dados", source.getId());
			
			final Optional<Cliente> clienteOptional = clienteRepository.findById(source.getId());
			
			if(clienteOptional.isPresent()) {
				
				final var cliente = clienteOptional.get();
				
				logger.debug("Convertendo o cliente {}", cliente);
				
				cliente.setNome(source.getNome());
				cliente.setCelular(source.getCelular());
				cliente.setCpf(source.getCpf());
				
		        final var pets = source.getPets().stream().map(p -> new Pet(p.getId(), p.getNome(), p.getRaca(), cliente)).collect(Collectors.toList());
		         
		        cliente.getPets().clear();
		        cliente.getPets().addAll(pets);
		      			
				logger.debug("{} covertido com sucesso!", cliente);
				
				return cliente;
				
			}
			
			logger.debug("Cliente id {} não encontrado na base de dados", source.getId());
			
			return null;
			
		}
	}
}