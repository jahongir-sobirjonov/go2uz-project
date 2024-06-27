package uniqueproject.uz.go2uzproject.forpayment.payme;


import jakarta.persistence.*;
import lombok.*;
import uniqueproject.uz.go2uzproject.entity.BaseEntity;
import uniqueproject.uz.go2uzproject.entity.Order;
import uniqueproject.uz.go2uzproject.entity.UserEntity;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    private Double amount;
    private String url;


}
