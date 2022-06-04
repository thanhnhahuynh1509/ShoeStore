package com.dacs.adminmodules.repository;

import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends CrudRepository<Track, Integer> {

    @Query("FROM Track t WHERE t.order = ?1")
    List<Track> findAllByOrder(Order order);

}
