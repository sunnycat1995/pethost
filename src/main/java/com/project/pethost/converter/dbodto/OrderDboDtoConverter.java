package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.dto.OrderDto;
import org.springframework.stereotype.Service;

@Service
public class OrderDboDtoConverter implements DboDtoConverter<OrderDbo, OrderDto>{
    @Override
    public OrderDto convertToDto(final OrderDbo orderDbo) {
        return null;
    }

    @Override
    public OrderDbo convertToDbo(final OrderDto orderDto) {
        final OrderDbo dbo = new OrderDbo();
        dbo.setId(orderDto.getId());
        return dbo;
    }
}
