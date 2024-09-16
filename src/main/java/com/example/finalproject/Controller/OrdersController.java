package com.example.finalproject.Controller;

import com.example.finalproject.ApiResponse.ApiResponse;
import com.example.finalproject.Model.Orders;
import com.example.finalproject.Service.OrdersService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrdersController {

    private final OrdersService ordersService;

    @GetMapping("/get")
    public ResponseEntity getAllOrders() {
        return ResponseEntity.status(200).body(ordersService.getAllOrders());
    }

    @PostMapping("/add")
    public ResponseEntity addOrder(@Valid @RequestBody Orders orders) {
        ordersService.addOrder(orders);
        return ResponseEntity.status(200).body("Order added ");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateOrder(@PathVariable Integer id,@Valid @RequestBody Orders orders) {
        ordersService.updateOrder(id, orders);
        return ResponseEntity.status(200).body("Order updated");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteOrder(@PathVariable Integer id) {
        ordersService.deleteOrder(id);
        return ResponseEntity.status(200).body("Order deleted");
    }
    @PutMapping("/applyDiscount/{order_id}")
    public ResponseEntity applyDiscountToOrder(@PathVariable Integer order_id, @RequestParam double discountPercentage) {
        ordersService.applyDiscountToOrder(order_id, discountPercentage);
        return ResponseEntity.status(200).body(new ApiResponse("Discount applied successfully"));
    }

    /*Renad*/
    @GetMapping("/get/order/{status}")
    public ResponseEntity<List<Orders>> getOrdersByStatus(@PathVariable String status) {
        List<Orders> orders = ordersService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }
    /*Renad*/
    @PutMapping("/changeStatus/{order_id}/{status}")
    public ResponseEntity changeOrderStatus(@PathVariable Integer order_id,@PathVariable String status) {
        return ResponseEntity.status(200).body(ordersService.changeOrderStatus(order_id,status));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<Orders>> getOrderHistoryForUser(@PathVariable Integer userId) {
        List<Orders> orders = ordersService.getOrderHistoryForUser(userId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<Orders>> getOrdersForBuyer(@PathVariable Integer buyerId) {
        List<Orders> orders = ordersService.getOrdersForBuyer(buyerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Orders>> getOrdersForSeller(@PathVariable Integer sellerId) {
        List<Orders> orders = ordersService.getOrdersForSeller(sellerId);
        return ResponseEntity.ok(orders);
    }
}