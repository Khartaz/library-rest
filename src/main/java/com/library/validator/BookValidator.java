package com.library.validator;

import com.library.domain.dto.BookDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(BookValidator.class);

    public void validateBookId(final BookDto bookDto)  {
        if (bookDto.getBookId() == null)  {
            LOGGER.info("There is not book with this id in database");
        } else {
            LOGGER.info("Book founded");
        }
    }
}
