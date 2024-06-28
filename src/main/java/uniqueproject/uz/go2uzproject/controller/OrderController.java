package uniqueproject.uz.go2uzproject.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uniqueproject.uz.go2uzproject.dto.request.OrderRequest;
import uniqueproject.uz.go2uzproject.dto.response.OrderResponse;
import uniqueproject.uz.go2uzproject.entity.enums.OrderStatus;
import uniqueproject.uz.go2uzproject.service.OrderService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PermitAll
    @PostMapping("/booking-tour")
    public ResponseEntity<Map<String, String>> orderTour(@RequestBody OrderRequest orderRequest) {
        String paymentUrl = orderService.orderTour(orderRequest);
        Map<String, String> response = new HashMap<>();
        response.put("url", paymentUrl);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/get-orders-by-userId{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(orderService.getOrdersByUserId(userId));
    }

    @GetMapping("get-orders-by-tourId{tourId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByTourId(@PathVariable UUID tourId) {
        return ResponseEntity.ok(orderService.getOrdersByTourId(tourId));
    }

    @GetMapping("/orders-by-agency/{agencyId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByAgency(@PathVariable UUID agencyId) {
        List<OrderResponse> orders = orderService.getOrdersByAgency(agencyId);
        return ResponseEntity.ok(orders);
    }

//    @PostMapping("/pay")
//    public ResponseEntity<PaymentResponseDTO> payForOrder(@RequestBody PaymentRequestDTO paymentRequest) {
//        PaymentResponseDTO paymentResponse = orderService.payForOrder(paymentRequest);
//        return ResponseEntity.status(200).body(paymentResponse);
//    }

    @PreAuthorize("hasRole('AGENCY')")
    @PatchMapping("/update-order-status/{orderId}")
    public ResponseEntity<OrderResponse> updateOrderStatus(@PathVariable UUID orderId, @RequestBody OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, status));
    }


}
