package unit;

import org.junit.Before;
import org.junit.Test;
import org.offer.exception.InvalidParameterException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.Operation;
import org.offer.model.dto.OfferDTO;
import org.offer.validator.OfferValidatorImpl;
import org.offer.validator.Validator;
import util.OfferTestHelper;

import java.math.BigDecimal;

public class OfferValidatorTest {

    private Validator<OfferDTO> offerValidator;

    @Before
    public void before() {
        offerValidator = new OfferValidatorImpl();
    }

    @Test(expected = RequiredParameterException.class)
    public void validate_noName_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setName(null);
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void validate_noDescription_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setDescription(null);
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void validate_noMerchant_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setMerchant(null);
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void validate_noCurrency_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setCurrency(null);
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void validate_noPrice_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setPrice(null);
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void validate_noIdOnUpdate_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setId(null);
        offerValidator.validate(offerDTO, Operation.UPDATE);
    }

    @Test(expected = InvalidParameterException.class)
    public void validate_invalidCurrencyCode_throwsInvalidParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setCurrency("GBPXYZ");
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

    @Test(expected = InvalidParameterException.class)
    public void validate_negativePrice_throwsInvalidParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDTO = OfferTestHelper.createOfferDTO();
        offerDTO.setPrice(BigDecimal.valueOf(-1));
        offerValidator.validate(offerDTO, Operation.CREATE);
    }

}
