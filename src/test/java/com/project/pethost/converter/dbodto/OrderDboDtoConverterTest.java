package com.project.pethost.converter.dbodto;

import com.project.pethost.dbo.OrderDbo;
import com.project.pethost.dto.OrderDto;
import org.junit.Assert;
import org.junit.Test;

public class OrderDboDtoConverterTest {
    private final OrderDboDtoConverter orderDboDtoConverter;
    private final Long TEST_ID = 1L;
    private final String TEST_COMMENTS = "test";

    public OrderDboDtoConverterTest() {
        orderDboDtoConverter = new OrderDboDtoConverter();
    }

    @Test
    public void convertToDto() {
        final OrderDbo dbo = new OrderDbo();
        dbo.setId(TEST_ID);
        dbo.setComments(TEST_COMMENTS);
        final OrderDto dto = orderDboDtoConverter.convertToDto(dbo);
        Assert.assertTrue("No matches ids", dbo.getId().equals(dto.getId()));
        Assert.assertTrue("No matches comments", dbo.getComments().equals(dto.getComments()));
    }

    @Test
    public void convertToDbo() {
        final OrderDto dto = new OrderDto();
        dto.setId(TEST_ID);
        dto.setComments(TEST_COMMENTS);
        final OrderDbo dbo = orderDboDtoConverter.convertToDbo(dto);
        Assert.assertTrue("No matches ids", dto.getId().equals(dbo.getId()));
        Assert.assertTrue("No matches comments", dto.getComments().equals(dbo.getComments()));
    }
}