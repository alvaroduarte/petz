package petz.com.br.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import petz.com.br.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
	
	//List<Cliente> findByNomeLike(String nome);
	
	//List<Cliente> findByNomeAndCpfLike(String nome, String cpf);
	
	Cliente findByCpf(String cpf);

}