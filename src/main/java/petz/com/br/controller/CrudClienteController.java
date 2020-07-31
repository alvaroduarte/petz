package petz.com.br.controller;

import static java.util.Optional.ofNullable;

import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import petz.com.br.controller.dto.ClienteDto;
import petz.com.br.controller.dto.converter.ClienteConverterClienteDto;
import petz.com.br.controller.dto.converter.ClientesConverterClientesDto;
import petz.com.br.controller.request.ClienteRequest;
import petz.com.br.controller.request.converter.ClienteRequestConverterCliente;
import petz.com.br.service.ClienteService;

@RestController
@RequestMapping("cliente")
public class CrudClienteController {

	private Logger logger = LogManager.getLogger(CrudClienteController.class);

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private ClientesConverterClientesDto clientesConverterClientesDto; 
	
	@Autowired
	private ClienteRequestConverterCliente clienteRequestConverterCliente;
	
	@Autowired
	private ClienteConverterClienteDto clienteConverterClienteDto;

	@GetMapping
	public ResponseEntity<List<ClienteDto>> buscar(String cpf) {	

		logger.info("Buscar");

		if(ofNullable(cpf).isEmpty()) {

			logger.debug("Buscando todos os clientes");
			
			final var clientes = clienteService.buscarTodos();
			
			if(clientes.isEmpty()) {
				
				logger.debug("Não existe cliente cadastrado na base de dados");
				
				return new ResponseEntity<> (HttpStatus.NOT_FOUND);
			
			}

			return new ResponseEntity<>(clientesConverterClientesDto.convert( clientes ), HttpStatus.OK);

		} else {

			logger.info("Buscando pelo cpf {}", cpf);
			
			final var cliente = clienteService.buscarPorCpf(Long.valueOf(cpf));

			if(ofNullable(cliente).isEmpty()) {
				
				logger.debug("Não existe cliente cadastrado na base de dados com o cpf {}", cpf);
				
				return new ResponseEntity<> (HttpStatus.NOT_FOUND);
			
			}
			
			return new ResponseEntity<>(clientesConverterClientesDto.convert( Arrays.asList( cliente ) ), HttpStatus.OK);
		
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity <ClienteDto> buscarPorId(@PathVariable Long id) {	

		logger.info("buscarPorId {}", id);
		
		final var cliente = clienteService.buscarPorId(id);
		
		if(ofNullable(cliente).isPresent()) {
			
			logger.debug("{} id {} econtrado com sucesso", cliente, id);
			
			return new ResponseEntity<>( clienteConverterClienteDto.convert( cliente ) , HttpStatus.OK);
			
		}
		
		logger.debug("Cliente id {} não econtrado na base de dados", id);

		return new ResponseEntity<> (HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<ClienteDto> salvar(@RequestBody @Valid ClienteRequest clienteRequest) {

		logger.info("salvar {}", clienteRequest);

		var cliente = clienteService.salvar(clienteRequestConverterCliente.convert(clienteRequest));
		
		logger.debug("{} salvo com sucesso!", cliente);
		
		return new ResponseEntity<>(clienteConverterClienteDto.convert( cliente ), HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequest clienteRequest) {	

		logger.info("atualizar id {}, {}", id, clienteRequest);
		
		clienteRequest.setId(id);
		
		var cliente = clienteRequestConverterCliente.convert(clienteRequest);
		
		if(ofNullable(cliente).isPresent()){
			
			clienteService.salvar(cliente);
			
			logger.debug("{} atualizado com sucesso!", cliente);

			return new ResponseEntity<>( clienteConverterClienteDto.convert( cliente ) , HttpStatus.OK);
			
		}
	
		logger.debug("Cliente id {} não encontrado na base de dados!", id);
		
		return new ResponseEntity<> (HttpStatus.NOT_FOUND);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ClienteDto> remover(@PathVariable Long id) {		

		logger.info("remover id {}", id);

		if(clienteService.remover(id)) {
			
			logger.info("Cliente id {} removido com sucesso!", id);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
		}
		
		logger.debug("Cliente id {} não econtrado na base de dados!", id);

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}