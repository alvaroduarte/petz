package petz.com.br.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import petz.com.br.domain.Pet;

public interface PetRepository extends CrudRepository<Pet, Long> {
	
	List<Pet> findByNome(String nome);

}