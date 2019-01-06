package com.library.service;

import com.library.domain.Reader;
import com.library.domain.dto.ReaderDto;
import com.library.expeption.*;
import com.library.mapper.ReaderMapper;
import com.library.repository.ReaderRepository;
import com.library.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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

    public Reader createReader(ReaderDto readerDto)   {
        try {
            checkReaderId(mapper.mapToReader(readerDto));
        } catch (ReaderException e) {
            //
        }
        String readerId = utils.generateId(5);

        readerDto.setReaderId(readerId);
        readerDto.setCreated(utils.createDate());

        return repository.save(mapper.mapToReader(readerDto));
    }

    private Reader checkReaderId(Reader reader) throws ReaderException {
        try {
            if (repository.findReaderByReaderId(reader.getReaderId()) != null) {
                throw new ReaderException(ErrorMessages.RECORD_ALREADY_EXISTS.getErrorMessage());
            }
        } catch (NullPointerException e) {
            //
        }
        return reader;
    }

    public Reader getReaderByReaderId(String readerId) throws NullPointerException {
        Reader reader = repository.findReaderByReaderId(readerId);

        if (reader == null) {
            throw new NullPointerException(ReaderMessages.READER_NOT_FOUND.getErrorMessage());
        }
        return reader;
    }

    public boolean deleteReader(ReaderDto readerDto) {
        Reader reader = getReaderByReaderId(readerDto.getReaderId());

        long id = reader.getId();
        repository.delete(id);

        return true;
    }

}
