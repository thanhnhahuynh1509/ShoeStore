package com.dacs.adminmodules.api;

import com.dacs.adminmodules.service.OrderService;
import com.dacs.adminmodules.service.TrackService;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import com.dacs.entitymodules.enumType.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tracks")
public class TrackRestController {

    private final OrderService orderService;
    private final TrackService trackService;

    public TrackRestController(TrackService trackService, OrderService orderService) {
        this.trackService = trackService;
        this.orderService = orderService;
    }

    @PostMapping("")
    public Track save(Integer orderId, String content, Integer ordinal) {
        Order order = new Order();
        order.setId(orderId);

        orderService.updateOrderStatus(orderId, OrderStatus.values()[ordinal]);

        Track track = new Track();
        track.setContent(content);
        track.setOrder(order);

        return trackService.save(track);
    }

    @GetMapping("/delete")
    public void delete(Integer orderId) {
        trackService.delete(orderId);
    }
}
