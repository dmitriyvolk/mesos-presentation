package net.dmitriyvolk.demo.sqrt.server.db;

import net.dmitriyvolk.demo.sqrt.server.domain.Input;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.transaction.Transactional;

@RepositoryRestResource(path = "inputs", collectionResourceRel = "inputs")
@Transactional
public interface InputRepository extends CrudRepository<Input, Long> {

}
