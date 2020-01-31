package adf.homework.service;

import java.util.List;

import adf.homework.dto.OrderDTO;

public interface OrderService {

    OrderDTO createOrder(OrderDTO dto);
    OrderDTO getOrder(long id);
    List<OrderDTO> getOrders();

}
