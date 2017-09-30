package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.offer.dao.OfferDAO;
import org.offer.dao.OfferDAOImpl;
import org.offer.exception.InvalidParameterException;
import org.offer.exception.OfferNotFoundException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.dto.OfferDTO;
import org.offer.service.OfferService;
import org.offer.service.OfferServiceImpl;
import org.offer.transformer.OfferTransformer;
import org.offer.transformer.OfferTransformerImpl;
import org.offer.validator.OfferValidatorImpl;
import org.offer.validator.Validator;
import util.OfferTestHelper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfferServiceIntegrationTest {

    private OfferService service;

    @Before
    public void before() {
        Validator<OfferDTO> offerValidator = new OfferValidatorImpl();
        OfferTransformer transformer = new OfferTransformerImpl();
        OfferDAO dao = new OfferDAOImpl();
        service = new OfferServiceImpl(offerValidator, transformer, dao);
    }

    @Test
    public void getAllOffers_whenNoOffers_returnsNothing() {
        Assert.assertTrue(service.getAllOffers().isEmpty());
    }

    @Test
    public void getAllOffers_whenMultipleOffers_returnsAll() {
        insertOffers(10);
        Assert.assertEquals(10, service.getAllOffers().size());
    }

    @Test(expected = OfferNotFoundException.class)
    public void getOffer_whenNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        service.getOffer(1);
    }

    @Test
    public void getOffer_whenFound_returnsOffer() throws OfferNotFoundException {
        List<OfferDTO> insertedDTOs = insertOffers(1);
        OfferDTO offerDto = service.getOffer(1);
        Assert.assertEquals(insertedDTOs.get(0), offerDto);
    }

    @Test(expected = RequiredParameterException.class)
    public void createOffer_withMissingName_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setName(null);
        service.createOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void createOffer_withMissingDescription_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setDescription(null);
        service.createOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void createOffer_withMissingMerchant_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setMerchant(null);
        service.createOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void createOffer_withMissingCurrency_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency(null);
        service.createOffer(dto);
    }

    @Test(expected = InvalidParameterException.class)
    public void createOffer_withInvalidCurrency_throwsInvalidParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency("GBPXYZ");
        service.createOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void createOffer_withMissingPrice_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setPrice(null);
        service.createOffer(dto);
    }

    @Test
    public void createOffer_withValidFields_returnsCreatedOffer() throws InvalidParameterException, RequiredParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        OfferDTO createdOfferDto = service.createOffer(dto);

        dto.setId(createdOfferDto.getId());
        dto.setCreatedDate(createdOfferDto.getCreatedDate());
        Assert.assertEquals(dto, createdOfferDto);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_withMissingId_throwsRequiredParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setId(null);
        service.updateOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_withMissingName_throwsRequiredParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setName(null);
        service.updateOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_withMissingDescription_throwsRequiredParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setDescription(null);
        service.updateOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_withMissingMerchant_throwsRequiredParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setMerchant(null);
        service.updateOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_withMissingCurrency_throwsRequiredParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency(null);
        service.updateOffer(dto);
    }

    @Test(expected = InvalidParameterException.class)
    public void updateOffer_withInvalidCurrency_throwsInvalidParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency("GBPXYZ");
        service.updateOffer(dto);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_withMissingPrice_throwsRequiredParameterException() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setPrice(null);
        service.updateOffer(dto);
    }

    @Test
    public void updateOffer_withValidFields_returnsUpdatedOffer() throws OfferNotFoundException, RequiredParameterException, InvalidParameterException {
        List<OfferDTO> insertedOffers = insertOffers(1);

        OfferDTO dto = insertedOffers.get(0);
        dto.setDescription("A new description");
        dto.setPrice(BigDecimal.valueOf(555));

        OfferDTO updatedDto = service.updateOffer(dto);

        dto.setLastUpdatedDate(updatedDto.getLastUpdatedDate());
        Assert.assertEquals(dto, updatedDto);
    }

    @Test(expected = OfferNotFoundException.class)
    public void deleteOffer_whenNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        service.deleteOffer(1);
    }

    @Test
    public void deleteOffer_whenFound_deletesOffer() throws OfferNotFoundException {
        insertOffers(1);
        service.deleteOffer(1);
        Assert.assertTrue(service.getAllOffers().isEmpty());
    }

    private List<OfferDTO> insertOffers(int n) {
        List<OfferDTO> createdOffers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            OfferDTO dto = OfferTestHelper.createOfferDTO(i);
            try {
                createdOffers.add(service.createOffer(dto));
            }
            catch (Exception e) {
                Assert.fail();
            }
        }

        return createdOffers;
    }
}
