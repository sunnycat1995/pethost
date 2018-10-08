package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.dto.OrderDto;
import org.springframework.stereotype.Service;

@Service
public class OrderDboDtoConverter implements DboDtoConverter<OrderDbo, OrderDto>{
    @Override
    public OrderDto convertToDto(final OrderDbo dbo) {
        final OrderDto dto = new OrderDto();
        dto.setId(dbo.getId());
        dto.setComments(dbo.getComments());
        return dto;
    }

    @Override
    public OrderDbo convertToDbo(final OrderDto dto) {
        final OrderDbo dbo = new OrderDbo();
        dbo.setId(dto.getId());
        dbo.setComments(dto.getComments());
        return dbo;
    }
}
