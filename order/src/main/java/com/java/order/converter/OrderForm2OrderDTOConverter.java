package com.java.order.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.java.common.exception.SellException;
import com.java.order.dto.OrderDTO;
import com.java.order.entity.OrderDetail;
import com.java.order.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiangli
 * @date 2019/8/19 22:55
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm) {
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {}.getType());
        } catch (Exception e) {
            log.error("【json转换】错误, string={}", orderForm.getItems());
            throw new SellException("OrderForm2OrderDTOConverter转换出错");
        }
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }
}
