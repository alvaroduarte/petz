package petz.com.br.controller.dto.converter;


import static java.util.stream.Collectors.toList;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import petz.com.br.controller.dto.ClienteDto;
import petz.com.br.domain.Cliente;

@Configuration
public class ClientesConverterClientesDto implements Converter<List<Cliente>, List<ClienteDto>> {

	@Override
	public List<ClienteDto> convert(List<Cliente> source) {
		
		return source.stream().map(ClienteDto::new).collect(toList());
		
	}
	
}