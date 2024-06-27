package uniqueproject.uz.go2uzproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uniqueproject.uz.go2uzproject.dto.response.OrderResponse;
import uniqueproject.uz.go2uzproject.entity.Order;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findByTour_Agency_Id(UUID agencyId);

    @Query("SELECT o FROM orders o WHERE o.orderDate BETWEEN :startDate AND :endDate " +
            "AND (:travelTypes IS NULL OR o.tour.category IN :travelTypes) " +
            "AND (:bookingStatuses IS NULL OR o.status IN :bookingStatuses)")
    List<Order> findOrdersBetweenDates(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("travelTypes") List<String> travelTypes,
            @Param("bookingStatuses") List<String> bookingStatuses);

    List<Order> findByUserId(UUID userId);
    List<Order> findByTourId(UUID tourId);
}
