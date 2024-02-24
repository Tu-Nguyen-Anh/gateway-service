package com.example.springproject.utils;


import com.example.springproject.exception.base.InvalidDateOfBirthException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.example.springproject.constant.CommonConstants.AGE_THRESHOLD;

/**
 * Utility class for handling date-related operations.
 */
public class DateUtils {

    /**
     * The getCurrentDateString method in the provided code snippet returns the current
     * date as a string in the format "yyyy-MM-dd" using the LocalDate.now() method
     *
     * @return String yyyy-MM-dd
     */
    public static String getCurrentDateString() {
        return LocalDate.now().toString();
    }

    /**
     * The getCurrentTimeMillis method in the provided code snippet
     * returns the current time in milliseconds since the epoch.
     *
     * @return milliseconds
     */
    public static Long getCurrentTimeMillis() {


        return System.currentTimeMillis();
    }

    /**
     * method that allows check Date Of Birth of users
     *
     * @param dateOfBirth
     */

    public static void checkDateOfBirth(Date dateOfBirth) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = LocalDate.parse(dateOfBirth.toString(), formatter);

        int age = currentDate.getYear() - birthDate.getYear();

        if (currentDate.getMonthValue() < birthDate.getMonthValue() ||
            (currentDate.getMonthValue() == birthDate.getMonthValue() &&
             currentDate.getDayOfMonth() < birthDate.getDayOfMonth())) {
            age--;
        }

        if (age < AGE_THRESHOLD) {
            throw new InvalidDateOfBirthException();
        }
    }
}