package com.library.service;

import com.library.domain.Reader;
import com.library.domain.dto.ReaderDto;
import com.library.exception.*;
import com.library.mapper.ReaderMapper;
import com.library.repository.ReaderRepository;
import com.library.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ReaderService {
    private ReaderRepository repository;
    private ReaderMapper mapper;
    private Utils utils;

    @Autowired
    public ReaderService(ReaderRepository repository, ReaderMapper mapper, Utils utils) {
        this.repository = repository;
        this.mapper = mapper;
        this.utils = utils;
    }

    public Reader createReader(ReaderDto readerDto) throws ReaderNotFoundException {
        if (!checkReaderId(readerDto.getReaderId())) {
            throw new ReaderNotFoundException(readerDto.getReaderId());
        }
        String readerId = utils.generateId(5);

        readerDto.setReaderId(readerId);
        readerDto.setCreated(utils.createDate());

        return repository.save(mapper.mapToReader(readerDto));
    }

    private boolean checkReaderId(String readerId) throws ReaderNotFoundException {
        Optional<Reader> reader = repository.findReaderByReaderId(readerId);
        if (reader.isPresent()) {
            throw new ReaderNotFoundException(ReaderMessages.READER_NOT_FOUND.getErrorMessage());
        }

        return true;
    }

    public Optional<Reader> getReaderByReaderId(String readerId) throws ReaderNotFoundException {
        Optional<Reader> reader = repository.findReaderByReaderId(readerId);

        if (!reader.isPresent()) {
            throw new ReaderNotFoundException("Id is " + readerId);
        }
        return reader;
    }

    public boolean deleteReader(ReaderDto readerDto) throws ReaderNotFoundException {
        Reader reader = getReaderByReaderId(readerDto.getReaderId()).get();

        long id = reader.getId();

        repository.delete(id);

        return true;
    }
}
