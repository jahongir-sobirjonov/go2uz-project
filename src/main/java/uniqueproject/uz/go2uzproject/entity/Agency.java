package uniqueproject.uz.go2uzproject.entity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Entity(name = "agencies")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Agency extends BaseEntity {
    @ManyToOne
    private UserEntity owner;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "agency", fetch = FetchType.LAZY) //
    private List<Tour> tours;
   // @ManyToMany(fetch = FetchType.EAGER)

    private Double rating;

    @OneToMany(mappedBy = "agency", fetch = FetchType.LAZY)
    private List<Notification> notifications;
}

