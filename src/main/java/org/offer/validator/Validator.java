package org.offer.validator;

import org.offer.exception.InvalidParameterException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.Operation;

public interface Validator<T> {

    void validate(T entity, Operation op) throws RequiredParameterException, InvalidParameterException;
}
