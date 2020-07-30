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
import petz.com.br.controller.request.ClienteRequest;
import petz.com.br.service.ClienteService;

@RestController
@RequestMapping("cliente")
public class CrudClienteController {

	private Logger logger = LogManager.getLogger(CrudClienteController.class);

	@Autowired
	private ClienteService clienteService;


	@GetMapping
	public ResponseEntity<List<ClienteDto>> buscar(String cpf) {	

		logger.info("Buscar");

		if(ofNullable(cpf).isEmpty()) {

			logger.info("Buscando todos os clientes");
			
			var clientes = clienteService.buscarTodos();
			
			if(clientes.isEmpty()) {
				
				return new ResponseEntity<> (HttpStatus.NOT_FOUND);
			
			}

			return new ResponseEntity<>(ClienteDto.converter(clientes), HttpStatus.OK);

		} else {

			logger.info("Buscando pelo cpf {}", cpf);
			
			var cliente = clienteService.buscarPorCpf(Long.valueOf(cpf));

			if(ofNullable(cliente).isEmpty()) {
				
				return new ResponseEntity<> (HttpStatus.NOT_FOUND);
			
			}
			
			return new ResponseEntity<>(ClienteDto.converter(Arrays.asList( cliente ))  , HttpStatus.OK);
		
		}

	}

	@GetMapping("/{id}")
	public ResponseEntity <ClienteDto> buscarPorId(@PathVariable Long id) {	

		logger.info("buscarPorId{}", id);
		
		var cliente = clienteService.buscarPorId(id);
		
		if(ofNullable(cliente).isPresent()) {
			
			return new ResponseEntity<>( new ClienteDto( cliente ) , HttpStatus.OK);
			
		}

		return new ResponseEntity<> (HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<ClienteDto> salvar(@RequestBody @Valid ClienteRequest clienteRequest) {

		logger.info("salvar {}", clienteRequest);

		var cliente = clienteService.salvar(clienteRequest.converter());

		return new ResponseEntity<>(new ClienteDto(cliente), HttpStatus.CREATED);

	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteDto> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequest clienteRequest) {	

		logger.info("atualizar id {}, {}", id, clienteRequest);

		var cliente = clienteService.atualizar(id, clienteRequest);

		if(ofNullable(cliente).isPresent()) {

			return new ResponseEntity<>( new ClienteDto( cliente ) , HttpStatus.OK);

		}

		return new ResponseEntity<> (HttpStatus.NOT_FOUND);

	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ClienteDto> remover(@PathVariable Long id) {		

		logger.info("remover id {}", id);

		if(clienteService.remover(id)) {
			return new ResponseEntity<>(HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}