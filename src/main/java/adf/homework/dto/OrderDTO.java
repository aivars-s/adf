package adf.homework.dto;

import java.math.BigDecimal;
import java.time.Instant;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import adf.homework.domain.DeliveryType;
import adf.homework.domain.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {

    private Long id;
    private Instant createdAt;

    @NotNull(message = "Customer must be selected")
    private Long customerId;

    @NotNull(message = "Product must be selected")
    private Long productId;

    @NotNull(message = "Amount must not be empty")
    @Positive(message = "Amount must be a positive integer")
    private Integer amount;

    @NotNull(message = "Delivery type must not be null")
    private DeliveryType deliveryType;

    @NotNull(message = "Payment type must not be null")
    private PaymentType paymentType;

    private BigDecimal unitPrice;
    private CustomerDTO customer;
    private ProductDTO product;

}
