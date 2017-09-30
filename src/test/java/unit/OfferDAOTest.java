package unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.offer.dao.OfferDAO;
import org.offer.dao.OfferDAOImpl;
import org.offer.exception.InvalidParameterException;
import org.offer.exception.OfferNotFoundException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.Offer;
import org.offer.model.Operation;
import org.offer.model.dto.OfferDTO;
import org.offer.service.OfferService;
import org.offer.service.OfferServiceImpl;
import org.offer.transformer.OfferTransformerImpl;
import org.offer.validator.Validator;
import util.OfferTestHelper;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OfferDAOTest {

    private OfferDAO offerDAO;

    @Before
    public void before() {
        offerDAO = new OfferDAOImpl();
    }

    @Test
    public void getAllOffers_whenNoOffers_returnsEmptyCollection() {
        Assert.assertTrue(offerDAO.getAllOffers().isEmpty());
    }

    @Test
    public void getAllOffers_whenOffersExist_returnsAllOffers() {
        List<Offer> offers = OfferTestHelper.createOffers(10);
        offers.forEach(offerDAO::createOffer);
        Collection<Offer> returnedOffers = offerDAO.getAllOffers();
        Assert.assertEquals(offers.size(), returnedOffers.size());
        Assert.assertTrue(returnedOffers.containsAll(offers));
    }

    @Test (expected = OfferNotFoundException.class)
    public void getOffer_whenOfferNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        offerDAO.getOffer(1);
    }

    @Test
    public void getOffer_whenOfferFound_returnsOffer() throws OfferNotFoundException {
        Offer offer = OfferTestHelper.createOffer();
        offerDAO.createOffer(offer);
        Offer returnedOffer = offerDAO.getOffer(offer.getId());
        Assert.assertEquals(offer, returnedOffer);
    }

    @Test
    public void createOffer_whenCreateSingleOffer_persistsOffer() throws OfferNotFoundException {
        Offer offer = OfferTestHelper.createOffer();
        Offer createdOffer = offerDAO.createOffer(offer);
        Assert.assertEquals(createdOffer, offerDAO.getOffer(offer.getId()));
    }

    @Test
    public void createOffer_whenCreateSingleOffer_assignsOfferId() {
        Offer offer = OfferTestHelper.createOffer();
        offer.setId(-1);
        Offer createdOffer = offerDAO.createOffer(offer);
        Assert.assertEquals(1, createdOffer.getId());
    }

    @Test
    public void createOffer_whenCreateManyOffers_assignsUniqueOfferIds() {
        List<Offer> offers = OfferTestHelper.createOffers(10);
        Set<Integer> ids = offers.stream()
                                 .map(offer -> offerDAO.createOffer(offer).getId())
                                 .collect(Collectors.toSet());

        Assert.assertEquals(offers.size(), ids.size());
    }

    @Test (expected = OfferNotFoundException.class)
    public void updateOffer_whenOfferNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        offerDAO.updateOffer(OfferTestHelper.createOffer());
    }

    @Test
    public void updateOffer_whenOfferFound_persistsUpdatedOffer() throws OfferNotFoundException {
        Offer offer = OfferTestHelper.createOffer();
        offerDAO.createOffer(offer);
        offer.setCurrency(Currency.getInstance("USD"));
        Offer updatedOffer = offerDAO.updateOffer(offer);
        Assert.assertEquals("USD", updatedOffer.getCurrency().getCurrencyCode());
        Assert.assertEquals("USD", offerDAO.getOffer(offer.getId()).getCurrency().getCurrencyCode());
    }

    @Test (expected = OfferNotFoundException.class)
    public void deleteOffer_whenOfferNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        offerDAO.deleteOffer(1);
    }

    @Test
    public void updateOffer_whenOfferFound_deletesOffer() throws OfferNotFoundException {
        Offer offer = OfferTestHelper.createOffer();
        offerDAO.createOffer(offer);
        Assert.assertEquals(1, offerDAO.getAllOffers().size());
        offerDAO.deleteOffer(offer.getId());
        Assert.assertEquals(0, offerDAO.getAllOffers().size());
    }



}