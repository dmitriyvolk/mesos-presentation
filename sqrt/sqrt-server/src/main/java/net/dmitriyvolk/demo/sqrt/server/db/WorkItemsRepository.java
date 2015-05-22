package net.dmitriyvolk.demo.sqrt.server.db;


import net.dmitriyvolk.demo.sqrt.server.domain.WorkItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "workitems", collectionResourceRel = "workitems")
public interface WorkItemsRepository extends CrudRepository<WorkItem, Long> {

    List<WorkItem> findFirstByOrderByIterationNumberDesc();
}

