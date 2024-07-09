package uniqueproject.uz.go2uzproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.*;
import uniqueproject.uz.go2uzproject.entity.enums.OrderStatus;
import uniqueproject.uz.go2uzproject.forpayment.payme.Transaction;

import java.time.LocalDate;

@Entity(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Order extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tour_id", nullable = false)
    private Tour tour;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private String phoneNumber;

    private Integer numberOfSeats;

    private LocalDate orderDate;

    private Double totalCost;

    private String url;
}

