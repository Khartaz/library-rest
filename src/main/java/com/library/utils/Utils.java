package com.library.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Component
public class Utils {

    public Date rentedDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public Date createDate() {
        Calendar createDate = Calendar.getInstance();
        return createDate.getTime();
    }

    public String generateId(int length) {
        StringBuilder builder = new StringBuilder(length);
        final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        final Random RANDOM = new SecureRandom();

        for (int i = 0; i < length; i++) {
            builder.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(builder);
    }

    public Date validateDateFormat(String dateToValidate) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //To make strict date format validation
        formatter.setLenient(false);
        Date parsedDate = null;
        try {
            parsedDate = formatter.parse(dateToValidate);

        } catch (ParseException e) {
            //Handle exception
        }
        return parsedDate;
    }
}
