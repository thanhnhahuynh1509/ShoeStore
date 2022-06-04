package com.dacs.adminmodules.service;

import com.dacs.adminmodules.repository.TrackRepository;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TrackService {

    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public Track save(Track track) {
        return trackRepository.save(track);
    }

    public List<Track> listByOrder(Order order) {
        return trackRepository.findAllByOrder(order);
    }

    public Track get(Integer id) {
        return trackRepository.findById(id).orElseGet(() -> null);
    }

    public void delete(Integer id) {
        trackRepository.deleteById(id);
    }
}
