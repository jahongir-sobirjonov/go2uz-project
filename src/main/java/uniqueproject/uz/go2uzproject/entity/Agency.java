package uniqueproject.uz.go2uzproject.entity;
import jakarta.persistence.*;
import lombok.*;
import uniqueproject.uz.go2uzproject.entity.enums.ServiceType;
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

   @ElementCollection(targetClass = ServiceType.class, fetch = FetchType.EAGER)
   @JoinTable(name = "agency_service_types",
           joinColumns = @JoinColumn(name = "agency_id"))
   @Enumerated(EnumType.STRING)
    private List<ServiceType> serviceTypes;

    private Double rating;

    @OneToMany(mappedBy = "agency", fetch = FetchType.LAZY)
    private List<Notification> notifications;
}

