package com.dacs.adminmodules.controller;

import com.dacs.adminmodules.service.OrderService;
import com.dacs.adminmodules.service.TrackService;
import com.dacs.entitymodules.Order;
import com.dacs.entitymodules.Track;
import com.dacs.entitymodules.enumType.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final TrackService trackService;

    public OrderController(OrderService orderService, TrackService trackService) {
        this.orderService = orderService;
        this.trackService = trackService;
    }

    @GetMapping("")
    public String index(Model model,
                        @RequestParam(name = "ordinal", required = false) Integer ordinal) {
        if(ordinal == null)
            ordinal = 0;
        if(ordinal >= OrderStatus.values().length)
            ordinal = OrderStatus.values().length - 1;
        OrderStatus status = OrderStatus.values()[ordinal];
        List<Order> orders = orderService.list(status);
        model.addAttribute("orders", orders);
        model.addAttribute("ordinal", ordinal);
        return "/order/index";
    }

    @GetMapping("/details")
    public String details(Integer id, Model model) {
        Order order = orderService.get(id);
        List<Track> tracks = trackService.listByOrder(order);
        model.addAttribute("order", order);
        model.addAttribute("tracks", tracks);
        return "/order/details";
    }
}
