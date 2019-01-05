package com.library.mapper;

import com.library.domain.Reader;
import com.library.domain.RentBook;
import com.library.domain.dto.ReaderDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReaderMapper {
    private RentBookMapper rentBookMapper;

    @Autowired
    public ReaderMapper(RentBookMapper rentBookMapper) {
        this.rentBookMapper = rentBookMapper;
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
                reader.getRentBooks()
                    .stream()
                    .map(v -> rentBookMapper.mapToRentedBookDto(v))
                    .collect(Collectors.toList()));
    }


    public Reader mapToReader(ReaderDto readerDto, List<RentBook> list) {
        Reader reader = mapToReader(readerDto);
        reader.setRentBooks(list);

        return reader;
    }
}
