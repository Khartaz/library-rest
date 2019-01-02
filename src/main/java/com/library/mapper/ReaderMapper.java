package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.RentedBook;
import com.library.domain.dto.ReaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    private RentedBookMapper rentedBookMapper;

    @Autowired
    public ReaderMapper(RentedBookMapper rentedBookMapper) {
        this.rentedBookMapper = rentedBookMapper;
    }

    public Reader mapToReader(ReaderDto readerDto) {
        return new Reader(
                readerDto.getReaderId(),
                readerDto.getFirstname(),
                readerDto.getLastname(),
                readerDto.getCreated()
        );
    }

    public ReaderDto mapToReaderDto(Reader reader) {
        return new ReaderDto(
                reader.getReaderId(),
                reader.getFirstname(),
                reader.getLastname(),
                reader.getCreated(),
                reader.getRentedBooks()
                    .stream()
                    .map(v -> rentedBookMapper.mapToRentedBookDto(v))
                    .collect(Collectors.toList()));
    }


    public Reader mapToReader(ReaderDto readerDto, List<RentedBook> list) {
        Reader reader = mapToReader(readerDto);
        reader.setRentedBooks(list);

        return reader;
    }
}
