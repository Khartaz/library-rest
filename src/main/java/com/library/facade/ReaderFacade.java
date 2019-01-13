package com.library.facade;

import com.library.domain.dto.BookDto;
import com.library.domain.dto.ReaderDto;
import com.library.mapper.BookMapper;
import com.library.mapper.ReaderMapper;
import com.library.service.BookService;
import com.library.service.ReaderService;
import com.library.service.RentBookService;
import com.library.web.rest.response.OperationStatus;
import com.library.web.rest.response.ResponseOperationNames;
import com.library.web.rest.response.ResponseOperationStatus;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;

@Component
public class ReaderFacade {
    private ReaderMapper mapper;
    private ReaderService readerService;
    private BookService bookService;
    private BookMapper bookMapper;
    private RentBookService rentBookService;

    @Autowired
    public ReaderFacade(ReaderMapper mapper, ReaderService readerService,
                            BookService bookService, BookMapper bookMapper, RentBookService rentBookService) {
        this.mapper = mapper;
        this.readerService = readerService;
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.rentBookService = rentBookService;
    }

    public ReaderDto createNewReader(ReaderDto readerDto)  {
        return mapper.mapToReaderDto(readerService.createReader(readerDto));
    }

    public ReaderDto rentBook(String bookId, String readerId, long quantity)  {
        ReaderDto readerDto = mapper.mapToReaderDto(readerService.getReaderByReaderId(readerId));

        BookDto bookDto = bookMapper.mapToBookDto(bookService.getBookByBookId(bookId));

        return rentBookService.rentBook(bookDto, readerDto, quantity);
    }

    public ReaderDto returnBook(String readerId, String bookId, long quantity)  {
        ReaderDto readerDto = mapper.mapToReaderDto(readerService.getReaderByReaderId(readerId));

        return rentBookService.returnBook(readerDto, bookId, quantity);
    }

    public OperationStatus deleteReader(String readerId) {
        OperationStatus result = new OperationStatus();
        ReaderDto readerDto = mapper.mapToReaderDto(readerService.getReaderByReaderId(readerId));

        boolean status = readerService.deleteReader(readerDto);

        if (status) {
            result.setOperationName(ResponseOperationNames.DELETE.name());
            result.setOperationStatus(ResponseOperationStatus.SUCCESS.name());
        }
        return result;
    }

    public ReaderDto getReaderByReaderId(String readerId) {
      return mapper.mapToReaderDto(readerService.getReaderByReaderId(readerId));

    }
}
