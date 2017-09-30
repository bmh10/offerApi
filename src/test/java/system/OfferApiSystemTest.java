package system;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.offer.AppServer;
import org.offer.model.dto.OfferDTO;
import util.OfferTestHelper;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;

public class OfferApiSystemTest {

    private static final int TEST_PORT = 2230;
    private static final String BASE_URI = "http://localhost:" + TEST_PORT + "/api/v1/offer";
    private AppServer server;
    private ObjectMapper mapper;

    @Before
    public void before() throws Exception {
        mapper = new ObjectMapper();
        server = new AppServer(TEST_PORT);
        server.runTestServer();
    }

    @After
    public void after() throws Exception {
        server.stopTestServer();
    }

    @Test
    public void crudSingleOfferWorkflowTest() throws Exception {
        // Create offer
        OfferDTO dto = OfferTestHelper.createOfferDTO();

        Response response = create(dto);
        assertResponseOk(response);

        OfferDTO createdOffer = response.readEntity(OfferDTO.class);

        // Check offer was created
        response = read(createdOffer.getId());
        assertResponseOk(response);
        Assert.assertEquals(createdOffer,  response.readEntity(OfferDTO.class));

        // Update offer
        String updatedDescription = "New description";
        BigDecimal updatedPrice = BigDecimal.valueOf(999.50);
        String updatedCurrency = "USD";

        createdOffer.setDescription(updatedDescription);
        createdOffer.setPrice(updatedPrice);
        createdOffer.setCurrency(updatedCurrency);

        response = update(createdOffer);
        assertResponseOk(response);

        OfferDTO updatedOffer = response.readEntity(OfferDTO.class);
        Assert.assertEquals(1, (int) updatedOffer.getId());
        Assert.assertEquals(updatedDescription, updatedOffer.getDescription());
        Assert.assertEquals(updatedPrice, updatedOffer.getPrice());
        Assert.assertEquals(updatedCurrency, updatedOffer.getCurrency());

        // Check update worked
        response = read(updatedOffer.getId());
        assertResponseOk(response);
        Assert.assertEquals(updatedOffer,  response.readEntity(OfferDTO.class));

        // Delete the offer
        response = delete(updatedOffer.getId());
        assertResponseOk(response);

        // Check offer deleted
        response = read(updatedOffer.getId());
        assertNotFound(response);
    }

    @Test
    public void crudMultipleOffersWorkflowTest() throws Exception {
        // Create 10 offers
        List<OfferDTO> dtos = OfferTestHelper.createOfferDTOs(10);
        for (OfferDTO dto : dtos) {
            create(dto);
        }

        // Check all 10 were created
        Response response = readAll();
        assertResponseOk(response);

        List<OfferDTO> allDtos = response.readEntity(new GenericType<List<OfferDTO>>() {});
        for (int i = 0; i < 10; i++) {
            dtos.get(i).setCreatedDate(allDtos.get(i).getCreatedDate());
            Assert.assertEquals(dtos.get(i), allDtos.get(i));
        }

        // Update first 5 offers
        String updatedDescription = "New description";
        BigDecimal updatedPrice = BigDecimal.valueOf(111);
        String updatedCurrency = "USD";

        for (int i = 0; i < 5; i++) {
            OfferDTO updatedDTO = allDtos.get(i);
            updatedDTO.setDescription(updatedDescription);
            updatedDTO.setPrice(updatedPrice);
            updatedDTO.setCurrency(updatedCurrency);
            response = update(updatedDTO);
            assertResponseOk(response);
        }

        // Check that first 5 offers updated with new values
        response = readAll();
        assertResponseOk(response);

        allDtos = response.readEntity(new GenericType<List<OfferDTO>>() {});
        for (int i = 0; i < 10; i++) {
            dtos.get(i).setLastUpdatedDate(allDtos.get(i).getLastUpdatedDate());
            if (i < 5) {
                Assert.assertEquals(updatedDescription, allDtos.get(i).getDescription());
                Assert.assertEquals(updatedPrice, allDtos.get(i).getPrice());
                Assert.assertEquals(updatedCurrency, allDtos.get(i).getCurrency());
            }
            else {
                Assert.assertEquals(dtos.get(i), allDtos.get(i));
            }
        }

        // Delete first 5 offers
        for (int i = 1; i <= 5; i++) {
            response = delete(i);
            assertResponseOk(response);
        }

        // Check that only last 5 offers remain
        response = readAll();
        assertResponseOk(response);

        allDtos = response.readEntity(new GenericType<List<OfferDTO>>() {});
        Assert.assertEquals(5, allDtos.size());
    }

    private Response create(OfferDTO offerDTO) throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(BASE_URI);
        String json = mapper.writeValueAsString(offerDTO);
        return resource.request().accept(MediaType.APPLICATION_JSON).post(Entity.json(json));
    }

    private Response read(int id) throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(BASE_URI + "/" + id);
        return resource.request().accept(MediaType.APPLICATION_JSON).get();
    }

    private Response readAll() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(BASE_URI);
        return resource.request().accept(MediaType.APPLICATION_JSON).get();
    }

    private Response update(OfferDTO offerDTO) throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(BASE_URI + "/" + offerDTO.getId());
        String json = mapper.writeValueAsString(offerDTO);
        return resource.request().accept(MediaType.APPLICATION_JSON).put(Entity.json(json));
    }

    private Response delete(int id) throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget resource = client.target(BASE_URI + "/" + id);
        return resource.request().accept(MediaType.APPLICATION_JSON).delete();
    }

    private void assertResponseOk(Response response) {
        Assert.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    private void assertNotFound(Response response) {
        Assert.assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

}
