package adf.homework.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import adf.homework.domain.Order;
import adf.homework.dto.OrderDTO;
import adf.homework.exception.ResourceNotFoundException;
import adf.homework.mapper.OrderMapper;
import adf.homework.repository.OrderRepository;
import adf.homework.service.CustomerService;
import adf.homework.service.OrderService;
import adf.homework.service.ProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final CustomerService customerService;
    private final OrderMapper orderMapper;
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO dto) {
        Order order = orderMapper.toOrder(dto);
        order.setCustomer(customerService.getCustomerDomainObject(dto.getCustomerId()));
        order.setProduct(productService.getProductDomainObject(dto.getProductId()));
        order.setUnitPrice(order.getProduct().getUnitPrice());
        return orderMapper.toOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO getOrder(long id) {
        return orderMapper.toOrderDTOFull(orderRepository.findById(id).orElseThrow(ResourceNotFoundException::new));
    }

    @Override
    public List<OrderDTO> getOrders() {
        return orderMapper.toOrderDTO(orderRepository.findAll());
    }

}
