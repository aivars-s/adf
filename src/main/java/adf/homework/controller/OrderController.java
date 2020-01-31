package adf.homework.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import adf.homework.dto.OrderDTO;
import adf.homework.dto.ProductDTO;
import adf.homework.service.CustomerService;
import adf.homework.service.OrderService;
import adf.homework.service.ProductService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final String ORDER = "order";

    private final CustomerService customerService;
    private final OrderService orderService;
    private final ProductService productService;

    @GetMapping
    public String getOrders(Model model) {
        model.addAttribute("orders", orderService.getOrders());
        return "order/index";
    }

    @GetMapping("/new")
    public String getOrderCreateForm(Model model) {
        model.addAttribute("customers", customerService.getCustomers());
        model.addAttribute("products",
            productService.getProducts().stream().filter(p -> !p.getDiscontinued()).collect(Collectors.toList()));
        model.addAttribute(ORDER, new OrderDTO());
        return "order/form";
    }

    @PostMapping("/new")
    public String postOrderCreateForm(@Valid @ModelAttribute("order") OrderDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("customers", customerService.getCustomers());
            model.addAttribute("products",
                productService.getProducts().stream().filter(p -> !p.getDiscontinued()).collect(Collectors.toList()));
            model.addAttribute(ORDER, dto);
            return "order/form";
        }

        OrderDTO saved = orderService.createOrder(dto);

        return "redirect:/orders/" + saved.getId();
    }

    @GetMapping("/{id}")
    public String getOrder(@PathVariable long id, Model model) {
        model.addAttribute(ORDER, orderService.getOrder(id));
        return "order/details";
    }

}
