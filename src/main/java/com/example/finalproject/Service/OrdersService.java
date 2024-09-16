package com.example.finalproject.Service;

import com.example.finalproject.ApiException.ApiException;
import com.example.finalproject.Model.Orders;
import com.example.finalproject.Model.User;
import com.example.finalproject.Repository.AuthRepository;
import com.example.finalproject.Repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final AuthRepository userRepository;

    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    public void addOrder(Orders order) {
        ordersRepository.save(order);
    }
    public void updateOrder(Integer id,Orders order) {
        Orders order1=ordersRepository.findOrdersById(id);
        if(order1==null) {
            throw new ApiException("Order Not Found");
        }
        order1.setOrderDate(order.getOrderDate());
        order1.setTotalPrice(order.getTotalPrice());
        order1.setStatus(order.getStatus());
        ordersRepository.save(order1);
    }

    public void deleteOrder(Integer id) {
        Orders order1=ordersRepository.findOrdersById(id);
        if(order1==null) {
            throw new ApiException("Order Not Found");
        }

        ordersRepository.delete(order1);
    }

    //Reema
    public void applyDiscountToOrder(Integer order_id , double discountPercentage) {
        Orders order1=ordersRepository.findOrdersById(order_id);
        if(order1==null) {
            throw new ApiException("Order Not Found");
        }

        double discountAmount = order1.getTotalPrice() * (discountPercentage/100);
        double newTotalPrice = order1.getTotalPrice() - discountAmount;

        order1.setTotalPrice(newTotalPrice);
        order1.setOrderDate(LocalDate.now());

        ordersRepository.save(order1);
    }



    /*Renad*/
    // Get orders by status
    public List<Orders> getOrdersByStatus(String status) {
        List<Orders> orders1=ordersRepository.findOrdersByStatus(status);
        if(orders1.isEmpty()) {
            throw new ApiException("Order Not Found, there is no orders with the given status");
        }
        return orders1;
    }

    /*Renad*/
    public String changeOrderStatus(Integer order_id, String status) {
        Orders order1=ordersRepository.findOrdersById(order_id);
        if(order1==null) {
            throw new ApiException("Order Not Found");
        }
        order1.setStatus(status);
        ordersRepository.save(order1);
        return "Order Status Changed Successfully";
    }

    // Order History for a User (can be buyer or seller) // Added by Omar
    public List<Orders> getOrderHistoryForUser(Integer userId) {
        User user = userRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("User Not Found");
        }
        return ordersRepository.findAllByBuyerOrSeller(user, user);
    }

    // Get orders for a specific buyer // Added by Omar
    public List<Orders> getOrdersForBuyer(Integer buyerId) {
        User buyer = userRepository.findUserById(buyerId);
        if (buyer == null) {
            throw new ApiException("Buyer Not Found");
        }
        return ordersRepository.findAllByBuyer(buyer);
    }

    // Get orders for a specific seller // Added by Omar
    public List<Orders> getOrdersForSeller(Integer sellerId) {
        User seller = userRepository.findUserById(sellerId);
        if (seller == null) {
            throw new ApiException("Seller Not Found");
        }
        return ordersRepository.findAllBySeller(seller);
    }
}
