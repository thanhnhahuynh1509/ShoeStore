package com.dacs.usermodules.service;

import com.dacs.entitymodules.Customer;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import com.dacs.usermodules.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {

    private final TrackRepository trackRepository;

    public TrackService(TrackRepository trackRepository) {
        this.trackRepository = trackRepository;
    }

    public List<Track> listByOrder(Order order) {
        return trackRepository.findAllByOrderOrderByCreatedDateDesc(order);
    }

    public Track getLastTrack(Order order) {
        List<Track> tracks = listByOrder(order);
        return tracks != null && tracks.size() > 0 ? tracks.get(0) : null;
    }

}
