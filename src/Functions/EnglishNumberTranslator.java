package Functions;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.regex.Pattern;

public final class EnglishNumberTranslator {

    private static final double MAX_LIMIT = 999999.99;
    private static final String OUT_OF_BOUNDS_INPUT = "Value must be greater than 0 "
                                                    + "and lower or equal to " + String.valueOf(MAX_LIMIT);
    private static final String INVALID_INPUT = "Unknow number pattern informed";
    private static final String ZERO = "zero dollars";

    private static final String[] oneToNineteenNames = {
        "", // sentinel value
        "one",
        "two",
        "three",
        "four",
        "five",
        "six",
        "seven",
        "eight",
        "nine",
        "ten",
        "eleven",
        "twelve",
        "thirteen",
        "fourteen",
        "fifteen",
        "sixteen",
        "seventeen",
        "eighteen",
        "nineteen"
    };

    private static final String[] tenToNinetyNames = {
        "", // sentinel value
        "ten",
        "twenty",
        "thirty",
        "forty",
        "fifty",
        "sixty",
        "seventy",
        "eighty",
        "ninety"
    };

    /**
     * Private constructor prevents initialization 
     * as this is only a method factory
     */
    private EnglishNumberTranslator() {/*deliberately empty*/}

    /**
     * Calls the chain of methods that translate the value to a string 
     * representation of the decimal values.
     * Dollars need to be separated by commas after three digits, such as: 
     * 12,345 for ten thousand three hundred and forty three,
     * and cents must be informed as two digits after a dot, such as
     * 1.45 for one and forty five 
     * @param value
     * @return String with the decimal translation of value
     * @throws IllegalArgumentException if value informed does not match the needed pattern
     */
    public static String convert(String value) throws IllegalArgumentException {
        double number;
        try {
            number = Double.parseDouble(getUSPatternNumber(value));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e.getMessage());
        }

        if (number < 0.0 || number > (int) MAX_LIMIT) {
            throw new IllegalArgumentException(OUT_OF_BOUNDS_INPUT);
        }
        if (number == 0.0) { 
            return ZERO;
        }

        return capitalize(translate(value));
    }

    /**
     * enum type that represents either integer of floating point types
     */
    private enum NUM_TYPE {
        INTEGER,
        FLOATING_POINT
    }

    /**
     * Gets the words representation for numbers according to the decimal system
     * @param value to be translated to words
     * @return String decimal representation of the digits
     */
    private static String getHundredsTensOnes(int value) {
        StringBuilder returnValue = new StringBuilder();
        if (value % 100 < 20) {
            returnValue.append(oneToNineteenNames[value % 100]);
            value /= 100;
        } else {
            returnValue.append(oneToNineteenNames[value % 10]);
            value /= 10;
            returnValue.insert(0, tenToNinetyNames[value % 10] + " "); 
            value /= 10;
        }
        if (value > 0) {
            returnValue.insert(0, oneToNineteenNames[value] + " hundred ");
        }
        return returnValue.toString();
    }

    /**
     *  Translates the dollars value of a number
     * @param dollars value
     * @return words representation of dollars, empty string if dollars equal to zero
     */
    private static String translateDollars(String dollars) throws IllegalArgumentException, ParseException {
        StringBuilder dollarsStrBuilder = new StringBuilder(256);
        String[] values = dollars.split(",");
        int valuesIndex = 0;
        if (getIntValue(dollars) == 0) {
            // value is lower than one dollar, so print only cents
            return "";
        }   
        switch (values.length) {
            case 2: // thousands
                dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex++])));
                dollarsStrBuilder.append(" thousand ");
            case 1: // hundreds or less
                dollarsStrBuilder.append(getHundredsTensOnes(getIntValue(values[valuesIndex])));
                break;
        }
        return dollarsStrBuilder.toString();
    }

    /**
     * Translates the cents values of a floating point value
     * @param cents value
     * @return words representation of cents, empty string if cents equal to zero
     */
    private static String translateCents(String cents) throws ParseException {
        if (cents.equals("00")) { 
            return "";
        }
        return getHundredsTensOnes(getIntValue(cents)) + " cent(s)";
    }

    /**
     * Does the translation of values from numeral to words
     * @param number to be translated
     * @return a String containing the word version of the informed number
     * @throws Exception
     */
    private static String translate(String number) throws IllegalArgumentException {
        StringBuilder numberInWords = new StringBuilder(256);
        try {
            switch (identifyType(number)) {
            case INTEGER:
                numberInWords.append(translateDollars(number));
                break;
            case FLOATING_POINT:
                int cents_position = number.length()-2;
                boolean isMoreThanOneDollar = true;
                String cents = "";

                numberInWords.append(translateDollars(number.substring(0, cents_position-1)));
                if (numberInWords.toString().equals("")) {
                    isMoreThanOneDollar = false;
                }
                cents = translateCents(number.substring(cents_position));
                if (isMoreThanOneDollar && !cents.equals("")) {
                    // gets the "and zero cents" case
                    numberInWords.append(" and ");
                }
                numberInWords.append(cents);
                break;
            default:
                throw new IllegalArgumentException(INVALID_INPUT);
            }
        } catch (IllegalArgumentException | ParseException ex) {
            System.err.println(ex);
            throw new IllegalArgumentException(ex);
        }
        return numberInWords.toString();
    }

    /**
     * Identifies if the informed number is an integer or a floating point number
     * @param number to be tested
     * @return {@code enum NUM_TYPE} value representing integer or floating point 
     */
    private static NUM_TYPE identifyType(String number) {
        if (isFloatingPoint(number)) {
            return NUM_TYPE.FLOATING_POINT;
        }
        return NUM_TYPE.INTEGER;
    }

    /**
     * @param number to be evaluated
     * @return true if value fits floating point pattern of string
     * @throws IllegalArgumentException if argument is of invalid type
     */
    private static boolean isFloatingPoint(String number) throws IllegalArgumentException {
        if (!isValid(number)) {
            throw new IllegalArgumentException(INVALID_INPUT);
        }
        return Pattern.matches(".*(\\.\\d\\d)$", number);
    }

    /**
     * Gets the digits of the given value by the US number standards, that is, 
     * 99,999 is transformed to 99999 
     * @param value to be evaluated
     * @return String containing the digits of the number
     * @throws ParseException if {@code value} is of unrecognizable value
     */
    private static String getUSPatternNumber(String value) throws ParseException {
        return NumberFormat.getNumberInstance(Locale.US).parse(value).toString();
    }

    /**
     * Capitalizes the first character of a string
     * @param line
     * @return String containing capitalized line
     */
    private static String capitalize(String line) {
       return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    /**
     * Helper function 
     * @param string to be evaluated
     * @return returns the integer representation of a string
     */
    private static int getIntValue(String s) throws ParseException {
        return Math.abs(Integer.valueOf(getUSPatternNumber(s)));
    }

    /**
     * Tests if user entered a number with error format regarding commas.
     * Commas required between powers of 1,000
     * Can't start with "."
     * Pass: (1,000,000), (0.01)
     * Fail: (1000000), (1,00,00,00), (.01)
     * @param values to be tested
     * @throws IllegalArgumentException if a single value has more than 3 digits 
     * @return true if value informed is within valid restrictions, false otherwise
     */
    private static boolean isValid(String number) {
         if (!Pattern.matches("^\\d{1,3}(,\\d{3})*(\\.\\d\\d)?$", number)) {
             return false;
         }
         return true;
    }
}