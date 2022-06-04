package com.dacs.usermodules.repository;

import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends CrudRepository<Track, Integer> {

    List<Track> findAllByOrderOrderByCreatedDateDesc(Order order);

}
