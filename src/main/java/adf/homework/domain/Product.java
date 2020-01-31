package adf.homework.domain;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "products")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@ToString(exclude = "orders")
@NoArgsConstructor
public class Product extends BaseEntity {

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "remaining_amount", nullable = false)
    private Integer remainingAmount;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "discontinued", nullable = false)
    private Boolean discontinued;

    @OneToMany(mappedBy = "product")
    private Set<Order> orders = new HashSet<>();

    @Builder
    public Product(Long id, String category, String manufacturer, String name, Integer remainingAmount,
        BigDecimal unitPrice,
        Boolean discontinued, Set<Order> orders) {
        super(id);
        this.category = category;
        this.manufacturer = manufacturer;
        this.name = name;
        this.remainingAmount = remainingAmount;
        this.unitPrice = unitPrice;
        this.discontinued = discontinued;
        this.orders = orders;
    }

}
