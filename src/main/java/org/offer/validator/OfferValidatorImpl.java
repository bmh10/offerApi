package org.offer.validator;

import org.offer.exception.InvalidParameterException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.dto.OfferDTO;
import org.offer.model.Operation;

public class OfferValidatorImpl extends AbstractValidator<OfferDTO> {

    public void validate(OfferDTO offerDTO, Operation op) throws RequiredParameterException, InvalidParameterException {
        if (!op.equals(Operation.CREATE)) {
            assertRequiredParam("id", offerDTO.getId());
        }
        assertRequiredParam("name", offerDTO.getName());
        assertRequiredParam("description", offerDTO.getDescription());
        assertRequiredParam("merchant", offerDTO.getMerchant());
        assertRequiredParam("currency", offerDTO.getCurrency());
        assertRequiredParam("price", offerDTO.getPrice());
        assertValidCurrency(offerDTO.getCurrency());
        assertPositivePrice(offerDTO.getPrice());
    }
}
