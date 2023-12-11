package com.RubenJimenez.TFG.repo;

import com.RubenJimenez.TFG.models.Product;
import com.RubenJimenez.TFG.models.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends ElasticsearchRepository<User,Integer> {

    Optional<User> findByName(String name);

}
