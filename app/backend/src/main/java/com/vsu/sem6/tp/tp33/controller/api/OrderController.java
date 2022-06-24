package com.vsu.sem6.tp.tp33.controller.api;

import com.vsu.sem6.tp.tp33.controller.exception.ApiRequestException;
import com.vsu.sem6.tp.tp33.service.logic.OrderService;
import com.vsu.sem6.tp.tp33.service.model.OrderDto;
import com.vsu.sem6.tp.tp33.service.model.PageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
@RestController
@RequestMapping("/orders")
@CrossOrigin
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    PageDto<OrderDto> findAll(@RequestParam(defaultValue = "0", name = "page_number")
                                      Integer pageNumber,
                              @RequestParam(defaultValue = "10", name = "page_size")
                                      Integer pageSize,
                              @RequestParam(defaultValue = " ",name = "search") String search
    ) {
        return orderService.findAll(pageNumber, pageSize,search);
    }
    @GetMapping(value = "/{order_id}")
    OrderDto findById(@PathVariable(name = "order_id") String orderId) {
        try {
            UUID uuid = UUID.fromString(orderId);
            return orderService.findById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
    @GetMapping(value = "/get_user_order")
    List<OrderDto> findOrdersByToken() {
        try {
            return orderService.findOrdersByToken();
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    OrderDto create(@RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }


    @PutMapping
    OrderDto update(@RequestBody OrderDto orderDto) {
        return orderService.update(orderDto);
    }

    @DeleteMapping("/{id}")
    void delete(@PathVariable String id) {
        try {
            UUID uuid = UUID.fromString(id);
            orderService.deleteById(uuid);
        } catch (IllegalArgumentException e) {
            throw new ApiRequestException("Wrong id");
        }
    }
}
