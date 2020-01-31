package adf.homework.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

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
public class ProductDTO {

    private Long id;
    @NotBlank private String category;

    @NotBlank private String manufacturer;

    @NotBlank private String name;

    @NotNull(message = "Remaining amount must not be empty")
    @PositiveOrZero(message = "Remaining amount must be a non-negative integer")
    private Integer remainingAmount;

    @NotNull(message = "Unit price must not be empty")
    @Positive(message = "Unit price must be a positive decimal")
    private BigDecimal unitPrice;

    @NotNull(message = "Discontinuation status must be specified")
    private Boolean discontinued;

    private List<OrderDTO> orders;

}
