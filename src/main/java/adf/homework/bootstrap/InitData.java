package adf.homework.bootstrap;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import adf.homework.domain.Customer;
import adf.homework.domain.DeliveryType;
import adf.homework.domain.Order;
import adf.homework.domain.PaymentType;
import adf.homework.domain.Product;
import adf.homework.domain.User;
import adf.homework.repository.CustomerRepository;
import adf.homework.repository.OrderRepository;
import adf.homework.repository.ProductRepository;
import adf.homework.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class InitData implements CommandLineRunner {

    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final Random random = new Random();

    @Override public void run(String... args) throws Exception {
        initCustomers();
        initProducts();
        initOrders();
        initUsers();
    }

    private void initCustomers() {
        try {
            List<Customer> customers = objectMapper.readValue(new URL("classpath:data/customers.json"),
                new TypeReference<List<Customer>>() {});
            customerRepository.saveAll(customers);
        } catch (IOException e) {
            log.warn("Exception initializing customers", e);
        }
    }

    private void initOrders() {
        List<Customer> customers = customerRepository.findAll();
        List<Product> products = productRepository.findAll();
        List<Order> orders = new ArrayList<>(50);

        DeliveryType[] deliveryTypes = DeliveryType.values();
        PaymentType[] paymentTypes = PaymentType.values();

        for (int i = 0; i < 50; i++) {
            int customer = random.nextInt(customers.size());
            int product = random.nextInt(products.size());
            int amount = random.nextInt(10) + 1;
            int deliveryType = random.nextInt(deliveryTypes.length);
            int paymentType = random.nextInt(paymentTypes.length);

            Order order = new Order();
            order.setCustomer(customers.get(customer));
            order.setProduct(products.get(product));
            order.setUnitPrice(order.getProduct().getUnitPrice());
            order.setAmount(amount);
            order.setDeliveryType(deliveryTypes[deliveryType]);
            order.setPaymentType(paymentTypes[paymentType]);

            orders.add(order);
        }

        orderRepository.saveAll(orders);
    }

    private void initProducts() {
        try {
            List<Product> products = objectMapper.readValue(new URL("classpath:data/products.json"),
                new TypeReference<List<Product>>() {});
            productRepository.saveAll(products);
        } catch (IOException e) {
            log.warn("Exception initializing products", e);
        }
    }

    private void initUsers() {
        try {
            List<User> users = objectMapper.readValue(new URL("classpath:data/users.json"),
                new TypeReference<List<User>>() {});
            users.forEach(u -> u.setPassword(passwordEncoder.encode(u.getPassword())));
            userRepository.saveAll(users);
        } catch (IOException e) {
            log.warn("Exception initializing users", e);
        }
    }

}
