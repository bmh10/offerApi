package integration;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.offer.api.OfferApi;
import org.offer.api.OfferApiImpl;
import org.offer.dao.OfferDAO;
import org.offer.dao.OfferDAOImpl;
import org.offer.model.dto.OfferDTO;
import org.offer.service.OfferService;
import org.offer.service.OfferServiceImpl;
import org.offer.transformer.OfferTransformer;
import org.offer.transformer.OfferTransformerImpl;
import org.offer.validator.OfferValidatorImpl;
import org.offer.validator.Validator;
import util.OfferTestHelper;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OfferApiIntegrationTest {

    private OfferApi api;

    @Before
    public void before() {
        Validator<OfferDTO> offerValidator = new OfferValidatorImpl();
        OfferTransformer transformer = new OfferTransformerImpl();
        OfferDAO dao = new OfferDAOImpl();
        OfferService service = new OfferServiceImpl(offerValidator, transformer, dao);
        api = new OfferApiImpl(service);
    }

    @Test
    public void getOffers_whenNoOffers_returnsNothing() {
        Response response = api.getOffers();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertTrue(((List) response.getEntity()).isEmpty());
    }

    @Test
    public void getOffers_whenMultipleOffers_returnsAll() {
        insertOffers(10);
        Response response = api.getOffers();
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(10, ((List) response.getEntity()).size());
    }

    @Test
    public void getOffer_whenNotFound_givesNotFound() {
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), api.getOffer(1).getStatus());
    }

    @Test
    public void getOffer_whenFound_returnsOffer() {
        List<OfferDTO> insertedDTOs = insertOffers(1);
        Response response = api.getOffer(1);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(insertedDTOs.get(0), response.getEntity());
    }

    @Test
    public void createOffer_withMissingName_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setName(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withMissingDescription_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setDescription(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withMissingMerchant_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setMerchant(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withMissingCurrency_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withInvalidCurrency_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency("GBPXYZ");
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withMissingPrice_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setPrice(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withNegativePrice_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setPrice(BigDecimal.valueOf(-1));
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.createOffer(dto).getStatus());
    }

    @Test
    public void createOffer_withValidFields_returnsCreatedOffer() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        Response response = api.createOffer(dto);
        OfferDTO responseDto = (OfferDTO)response.getEntity();
        dto.setId(responseDto.getId());
        dto.setCreatedDate(responseDto.getCreatedDate());
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(dto, response.getEntity());
    }

    @Test
    public void updateOffer_withMissingName_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setName(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withMissingDescription_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setDescription(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withMissingMerchant_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setMerchant(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withMissingCurrency_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withInvalidCurrency_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setCurrency("GBPXYZ");
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withMissingPrice_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setPrice(null);
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withNegativePrice_givesBadRequest() {
        OfferDTO dto = OfferTestHelper.createOfferDTO();
        dto.setPrice(BigDecimal.valueOf(-1));
        Assert.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), api.updateOffer(dto.getId(), dto).getStatus());
    }

    @Test
    public void updateOffer_withValidFields_returnsUpdatedOffer() {
        List<OfferDTO> insertedOffers = insertOffers(1);

        OfferDTO dto = insertedOffers.get(0);
        dto.setDescription("A new description");
        dto.setPrice(BigDecimal.valueOf(555));

        Response response = api.updateOffer(1, dto);
        OfferDTO responseDto = (OfferDTO)response.getEntity();
        dto.setLastUpdatedDate(responseDto.getLastUpdatedDate());

        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(dto, responseDto);
    }

    @Test
    public void deleteOffer_whenNotFound_givesNotFound() {
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), api.deleteOffer(1).getStatus());
    }

    @Test
    public void deleteOffer_whenFound_deletesOffer() {
        insertOffers(1);
        Response response = api.deleteOffer(1);
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), api.getOffer(1).getStatus());
    }


    private List<OfferDTO> insertOffers(int n) {
        List<OfferDTO> createdOffers = new ArrayList<>();
        for (int i = 1; i <= n; i++) {
            OfferDTO dto = OfferTestHelper.createOfferDTO(i);
            Response response = api.createOffer(dto);
            createdOffers.add((OfferDTO)response.getEntity());
        }

        return createdOffers;
    }
}
