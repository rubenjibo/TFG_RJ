package com.RubenJimenez.TFG.repo;

import com.RubenJimenez.TFG.models.Prioritat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioritatRepo extends ElasticsearchRepository<Prioritat,Integer> {

    boolean existsByPosition(int position);
}
