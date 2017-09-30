package org.offer.validator;

import org.offer.exception.InvalidParameterException;
import org.offer.exception.RequiredParameterException;

import java.math.BigDecimal;
import java.util.Currency;

public abstract class AbstractValidator<T> implements Validator<T> {

    private static final String REQUIRED_FIELD_ERR = "%s is a required parameter";
    private static final String INVALID_CURRENCY_ERR = "%s is not a valid currency code";
    private static final String NEGATIVE_PRICE_ERR = "Cannot have negative price";

    protected void assertRequiredParam(String fieldName, String s) throws RequiredParameterException {
        if (s == null || s.isEmpty()) {
            throw new RequiredParameterException(String.format(REQUIRED_FIELD_ERR, fieldName));
        }
    }

    protected void assertRequiredParam(String fieldName, Number d) throws RequiredParameterException {
        if (d == null) {
            throw new RequiredParameterException(String.format(REQUIRED_FIELD_ERR, fieldName));
        }
    }

    protected void assertValidCurrency(String currencyCode) throws InvalidParameterException {
        try {
            Currency.getInstance(currencyCode);
        }
        catch (IllegalArgumentException e) {
            throw new InvalidParameterException(String.format(INVALID_CURRENCY_ERR, currencyCode));
        }
    }

    protected void assertPositivePrice(BigDecimal price) throws InvalidParameterException {
        if (price.signum() < 0) {
            throw new InvalidParameterException(NEGATIVE_PRICE_ERR);
        }
    }
}
