package com.library.mapper;

import com.library.domain.BookStock;
import com.library.domain.dto.BookStockDto;
import org.springframework.stereotype.Component;

@Component
public class BookStockMapper {

    public BookStockDto mapToBookCopiesDto(BookStock bookStock) {
        return new BookStockDto(
                bookStock.getBookId(),
                bookStock.getTotal(),
                bookStock.getRemainedInStock(),
                bookStock.getRemainedToRent(),
                bookStock.getRented(),
                bookStock.getDestroyed(),
                bookStock.getLost()
        );
    }

    public BookStock mapToBookCopies(BookStockDto bookStockDto) {
        return new BookStock(
                bookStockDto.getBookId(),
                bookStockDto.getTotal(),
                bookStockDto.getRemainedInStock(),
                bookStockDto.getRemainedToRent(),
                bookStockDto.getRented(),
                bookStockDto.getDestroyed(),
                bookStockDto.getLost()
        );
    }

}
