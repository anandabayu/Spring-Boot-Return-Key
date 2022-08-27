package com.anandabayu.returnkey.controller;

import com.anandabayu.returnkey.data.Order;
import com.anandabayu.returnkey.data.OrderItem;
import com.anandabayu.returnkey.data.ReturnItem;
import com.anandabayu.returnkey.data.Returns;
import com.anandabayu.returnkey.data.ReturnToken;
import com.anandabayu.returnkey.data.dto.BodyPendingReturn;
import com.anandabayu.returnkey.data.dto.BodyReturn;
import com.anandabayu.returnkey.data.dto.BodyReturnStatus;
import com.anandabayu.returnkey.service.ReturnService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@RestController
@RequestMapping("/api")
public class ReturnController {

    private final ReturnService returnService;

    public ReturnController(ReturnService returnService) {
        this.returnService = returnService;
    }

    @PostMapping("/pending/returns")
    public ResponseEntity<?> postPendingReturns(
            @Valid @RequestBody BodyPendingReturn body
            ) {

        Order order = returnService.findOrderByIdAndEmail(body.getOrderId(), body.getEmail());

        if (order == null) {
            return notFound(String.format("Order with id %s and email %s not found", body.getOrderId(), body.getEmail()));
        }

        String token = UUID.randomUUID().toString().substring(0, 32);

        ReturnToken returnToken = new ReturnToken();
        returnToken.setOrder(order);
        returnToken.setToken(token);

        ReturnToken savedReturnToken = returnService.saveReturnToken(returnToken);
        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("token", savedReturnToken.getToken());

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PostMapping("/returns")
    public ResponseEntity<?> postReturns(
            @Valid @RequestBody BodyReturn body
            ) {
        ReturnToken returnToken = returnService.findReturnTokenByToken(body.getToken());

        if (returnToken == null) {
            return notFound("Token not valid");
        }

        List<ReturnItem> returnItems = new ArrayList<>();
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (body.getItems() != null && body.getItems().size() > 0) {
            body.getItems().forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                OrderItem orderItem = returnService.findOrderItemByOrderIdAndItemSku(returnToken.getOrder().getId(), item.getSku());

                if (orderItem == null) {
                    map.put("sku", String.format("sku %s not found in your order", item.getSku()));
                }
                if (orderItem != null && orderItem.getQty() < item.getQty()) {
                    map.put("qty", String.format("qty can't be more than %d", orderItem.getQty()));
                }

                ReturnItem ri = returnService.findReturnItemByReturns_OrderIdAndItemSku(returnToken.getOrder().getId(), item.getSku());
                if (ri != null) {
                    map.put("sku", String.format("item with sku %s already been returned", item.getSku()));
                }

                if (!map.isEmpty()) {
                    mapList.add(map);
                } else {
                    ReturnItem returnItem = new ReturnItem();
                    returnItem.setQty(item.getQty());
                    returnItem.setStatus("WAITING");
                    returnItem.setItem(orderItem.getItem());

                    returnItems.add(returnItem);
                }
            });
        } else {
            List<OrderItem> orderItems = returnService.findOrderItemsByOrderId(returnToken.getOrder().getId());
            orderItems.forEach(orderItem -> {
                Map<String, Object> map = new HashMap<>();
                ReturnItem ri = returnService.findReturnItemByReturns_OrderIdAndItemSku(returnToken.getOrder().getId(), orderItem.getItem().getSku());
                if (ri != null) {
                    map.put("sku", String.format("item with sku %s already been returned", orderItem.getItem().getSku()));
                }

                if (!map.isEmpty()) {
                    mapList.add(map);
                } else {
                    ReturnItem returnItem = new ReturnItem();
                    returnItem.setQty(orderItem.getQty());
                    returnItem.setStatus("WAITING");
                    returnItem.setItem(orderItem.getItem());

                    returnItems.add(returnItem);
                }
            });
        }

        if (mapList.size() > 0) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", "BAD_REQUEST");
            map.put("data", mapList);

            return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);
        }

        Returns returns = new Returns();
        returns.setOrder(returnToken.getOrder());
        returnItems.forEach(item -> item.setReturns(returns));
        returns.setReturnItems(returnItems);

        Returns savedReturns = returnService.saveReturns(returns);

        returnService.deleteReturnToken(returnToken);

        Double estimatedRefundAmount = savedReturns.getReturnItems().stream().mapToDouble(item -> item.getQty() * item.getItem().getPrice()).sum();
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("returnId", savedReturns.getId());
        returnMap.put("estimatedRefundAmount", estimatedRefundAmount);

        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("data", returnMap);

        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @GetMapping("/returns/{id}")
    public ResponseEntity<?> getReturn(@PathVariable Long id) {

        Optional<Returns> returns = returnService.findReturnsById(id);

        if (returns.isPresent()) {

            Returns data = returns.get();

            Integer isWaiting = data.getReturnItems().stream().mapToInt(item -> "WAITING".equals(item.getStatus()) ? 1 : 0).sum();

            Map<String, Object> map = new HashMap<>();
            map.put("status", isWaiting == 0 ? "COMPLETE" : "AWAITING_APPROVAL");

            Map<String, Object> mapData = new HashMap<>();
            mapData.put("returnId", data.getId());
            mapData.put("returnItems", data.getReturnItems());

            Double estimatedRefundAmount = data.getReturnItems().stream().mapToDouble(item -> "REJECTED".equals(item.getStatus()) ? 0 : item.getQty() * item.getItem().getPrice()).sum();
            mapData.put("estimatedRefundAmount", estimatedRefundAmount);

            map.put("data", mapData);

            return ResponseEntity.ok(map);
        }

        return notFound(String.format("Return with id %d not found", id));
    }

    @PutMapping("/returns/{id}/items/{itemId}/qc/status")
    public ResponseEntity<?> updateReturns(@PathVariable Long id, @PathVariable Long itemId, @Valid @RequestBody BodyReturnStatus body) {
        Optional<Returns> returns = returnService.findReturnsById(id);

        if (returns.isPresent()) {
            ReturnItem returnItem = returnService.findReturnItemByIdAndReturnsId(itemId, id);

            if (returnItem == null) {
                return notFound(String.format("Item with id %d not found on this returns", itemId));
            }

            returnItem.setStatus(body.getStatus());

            ReturnItem savedReturnItem = returnService.saveReturnItem(returnItem);

            Map<String, Object> map = new HashMap<>();
            map.put("status", "SUCCESS");
            map.put("data", savedReturnItem);

            return ResponseEntity.ok(map);
        }

        return notFound(String.format("Return with id %d not found", id));
    }

    public ResponseEntity<?> notFound(String message) {
        Map<String, Object> notFound = new HashMap<>();
        notFound.put("status", "NOT_FOUND");
        notFound.put("message", message);
        return new ResponseEntity<>(notFound, HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
