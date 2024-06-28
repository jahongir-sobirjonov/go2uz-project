package uniqueproject.uz.go2uzproject.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import uniqueproject.uz.go2uzproject.dto.request.OrderRequest;
import uniqueproject.uz.go2uzproject.dto.response.OrderResponse;
import uniqueproject.uz.go2uzproject.entity.Order;
import uniqueproject.uz.go2uzproject.entity.Tour;
import uniqueproject.uz.go2uzproject.entity.UserEntity;
import uniqueproject.uz.go2uzproject.entity.enums.OrderStatus;
import uniqueproject.uz.go2uzproject.entity.enums.TourStatus;
import uniqueproject.uz.go2uzproject.exception.DataNotFoundException;
import uniqueproject.uz.go2uzproject.forpayment.payme.Transaction;
import uniqueproject.uz.go2uzproject.forpayment.payme.TransactionRepository;
import uniqueproject.uz.go2uzproject.repository.OrderRepository;
import uniqueproject.uz.go2uzproject.repository.TourRepository;
import uniqueproject.uz.go2uzproject.repository.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final NotificationService notificationService;
    private final TransactionRepository transactionRepository;


    public String orderTour(OrderRequest orderRequest) {
        // Validate tour
        Tour tour = tourRepository.findById(orderRequest.getTourId())
                .orElseThrow(() -> new DataNotFoundException("Tour not found with id: " + orderRequest.getTourId()));

        if (tour.getStatus() == TourStatus.CANCELLED) {
            return "Trip is cancelled";
        }

        if (tour.getAvailableSeats() < orderRequest.getNumberOfSeats()) {
            return "Not enough available seats";
        }

        // Validate user
        UserEntity user = userRepository.findById(orderRequest.getUserId())
                .orElseThrow(() -> new DataNotFoundException("User not found with id: " + orderRequest.getUserId()));

        // Create order
        Order order = Order.builder()
                .tour(tour)
                .status(OrderStatus.PENDING)
                .orderDate(LocalDate.now())
                .numberOfSeats(orderRequest.getNumberOfSeats())
                .phoneNumber(orderRequest.getPhoneNumber())
                .user(user)
                .totalCost(tour.getCost() * orderRequest.getNumberOfSeats())
                .url(orderRequest.getUrl())
                .build();
        orderRepository.save(order);

        // Notify agency and user
        notificationService.notifyAgency(order);
        notificationService.notifyUser(order);

        // Update available seats for the tour
        tour.setAvailableSeats(tour.getAvailableSeats() - orderRequest.getNumberOfSeats());
        tour.setCountOfOrders(orderRequest.getNumberOfSeats());
        if (tour.getAvailableSeats() == 0) {
            tour.setStatus(TourStatus.FULL);
        }
        tourRepository.save(tour);

        Transaction transaction = Transaction.builder()
                .order(order)
                .amount(order.getTotalCost())
                .url(order.getUrl())
                .user(order.getUser())
                .build();
        transactionRepository.save(transaction);

        // Return payment URL to frontend
        return transaction.getUrl();
    }

    public OrderResponse updateOrderStatus(UUID orderId, OrderStatus status) {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            order.setStatus(status);
            order.setUpdatedDate(LocalDateTime.now());
            Order updatedOrder = orderRepository.save(order);

            // Notify user
            notificationService.notifyUser(order);

            OrderResponse orderResponse = modelMapper.map(updatedOrder, OrderResponse.class);
            orderResponse.setUserId(order.getUser().getId());
            orderResponse.setTourId(order.getTour().getId());
            orderResponse.setStatus(status);
            orderResponse.setPhoneNumber(order.getPhoneNumber());
            return orderResponse;
        } else {
            throw new EntityNotFoundException("Order not found");
        }
    }



    public List<OrderResponse> getOrdersByUserId(UUID userId) {
        List<Order> myOrders =  orderRepository.findByUserId(userId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order myOrder : myOrders) {
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderDate(myOrder.getOrderDate())
                    .id(myOrder.getId())
                    .userId(myOrder.getUser().getId())
                    .numberOfSeats(myOrder.getNumberOfSeats())
                    .phoneNumber(myOrder.getPhoneNumber())
                    .totalCost(myOrder.getTotalCost())
                    .status(myOrder.getStatus())
                    .tourId(myOrder.getTour().getId()).build();
            orderResponses.add(orderResponse);

        }
        return orderResponses;
    }

    public List<OrderResponse> getOrdersByTourId(UUID tourId) {
        List<Order> myOrders =  orderRepository.findByTourId(tourId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (Order myOrder : myOrders) {
            OrderResponse orderResponse = OrderResponse.builder()
                    .orderDate(myOrder.getOrderDate())
                    .id(myOrder.getId())
                    .userId(myOrder.getUser().getId())
                    .numberOfSeats(myOrder.getNumberOfSeats())
                    .phoneNumber(myOrder.getPhoneNumber())
                    .totalCost(myOrder.getTotalCost())
                    .status(myOrder.getStatus())
                    .tourId(myOrder.getTour().getId()).build();
            orderResponses.add(orderResponse);

        }
        return orderResponses;
    }



    public List<OrderResponse> getOrdersByAgency(UUID agencyId) {
        List<Order> orders = orderRepository.findByTour_Agency_Id(agencyId);
        return orders.stream()
                .map(order -> OrderResponse.builder()
                        .id(order.getId())
                        .userId(order.getUser().getId())
                        .tourId(order.getTour().getId())
                        .status(order.getStatus())
                        .orderDate(order.getOrderDate())
                        .build())
                .collect(Collectors.toList());
    }
}





