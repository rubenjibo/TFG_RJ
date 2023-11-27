package com.RubenJimenez.TFG.repo;

import com.RubenJimenez.TFG.models.Event;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepo extends ElasticsearchRepository<Event,Integer> {
}
