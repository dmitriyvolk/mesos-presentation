package net.dmitriyvolk.demo.sqrt.server.db;


import net.dmitriyvolk.demo.sqrt.server.domain.SqrtResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "results", collectionResourceRel = "results")
public interface ResultRepository extends CrudRepository<SqrtResult, Long> {
}
