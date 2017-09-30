package unit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.offer.dao.OfferDAO;
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

import java.util.Collection;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OfferServiceTest {

    @Mock
    private Validator<OfferDTO> mockValidator;
    @Mock
    private OfferTransformerImpl offerTransformer;
    @Mock
    private OfferDAO offerDAO;

    private OfferService offerService;

    @Before
    public void before() {
        offerService = new OfferServiceImpl(mockValidator, offerTransformer, offerDAO);
    }

    @Test
    public void getAllOffers_whenOffersExist_returnsOfferDTOs() {
        List<Offer> offers = OfferTestHelper.createOffers(5);
        List<OfferDTO> offerDtos = OfferTestHelper.createOfferDTOs(5);
        when(offerDAO.getAllOffers()).thenReturn(offers);
        for (int i = 0; i < offers.size(); i++) {
            when(offerTransformer.toDTO(offers.get(i))).thenReturn(offerDtos.get(i));
        }

        Collection<OfferDTO> returnedDtos = offerService.getAllOffers();

        for (OfferDTO returnedDto : returnedDtos) {
            Assert.assertTrue(offerDtos.contains(returnedDto));
        }
        verify(offerDAO).getAllOffers();
        verify(offerTransformer, times(offers.size())).toDTO(any(Offer.class));
    }

    @Test(expected = OfferNotFoundException.class)
    public void getOffer_whenOfferNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        when(offerDAO.getOffer(1)).thenThrow(new OfferNotFoundException("Offer not found"));
        offerService.getOffer(1);
    }

    @Test
    public void getOffer_whenOfferExists_returnsOfferDTO() throws OfferNotFoundException {
        Offer offer = OfferTestHelper.createOffer();
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        when(offerDAO.getOffer(1)).thenReturn(offer);
        when(offerTransformer.toDTO(offer)).thenReturn(offerDto);

        OfferDTO returnedDto = offerService.getOffer(1);

        Assert.assertTrue(offerDto.equals(returnedDto));

        verify(offerDAO).getOffer(1);
        verify(offerTransformer).toDTO(offer);
    }

    @Test(expected = InvalidParameterException.class)
    public void createOffer_whenInvalidParameter_throwsInvalidParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        doThrow(new InvalidParameterException("Invalid parameter")).when(mockValidator).validate(offerDto, Operation.CREATE);
        offerService.createOffer(offerDto);
        verify(mockValidator).validate(offerDto, Operation.CREATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void createOffer_whenRequiredParameterNotPresent_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        doThrow(new RequiredParameterException("Required parameter")).when(mockValidator).validate(offerDto, Operation.CREATE);
        offerService.createOffer(offerDto);
        verify(mockValidator).validate(offerDto, Operation.CREATE);
    }

    @Test
    public void createOffer_whenSuccessful_createdDateIsSet() throws InvalidParameterException, RequiredParameterException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        Offer offer = OfferTestHelper.createOffer();
        when(offerTransformer.fromDTO(offerDto)).thenReturn(offer);
        when(offerDAO.createOffer(offer)).thenReturn(offer);
        when(offerTransformer.toDTO(offer)).thenCallRealMethod();

        OfferDTO createdOfferDto = offerService.createOffer(offerDto);

        Assert.assertTrue(createdOfferDto.getCreatedDate() != null);
        offerDto.setCreatedDate(createdOfferDto.getCreatedDate());
        Assert.assertEquals(offerDto, createdOfferDto);

        verify(mockValidator).validate(offerDto, Operation.CREATE);
        verify(offerTransformer).fromDTO(offerDto);
        verify(offerDAO).createOffer(offer);
        verify(offerTransformer).toDTO(offer);
    }

    @Test(expected = InvalidParameterException.class)
    public void updateOffer_whenInvalidParameter_throwsInvalidParameterException() throws InvalidParameterException, RequiredParameterException, OfferNotFoundException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        doThrow(new InvalidParameterException("Invalid parameter")).when(mockValidator).validate(offerDto, Operation.UPDATE);
        offerService.updateOffer(offerDto);
        verify(mockValidator).validate(offerDto, Operation.UPDATE);
    }

    @Test(expected = RequiredParameterException.class)
    public void updateOffer_whenRequiredParameterNotPresent_throwsRequiredParameterException() throws InvalidParameterException, RequiredParameterException, OfferNotFoundException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        doThrow(new RequiredParameterException("Required parameter")).when(mockValidator).validate(offerDto, Operation.UPDATE);
        offerService.updateOffer(offerDto);
        verify(mockValidator).validate(offerDto, Operation.UPDATE);
    }

    @Test(expected = OfferNotFoundException.class)
    public void updateOffer_whenOfferNotFound_throwsOfferNotFoundException() throws InvalidParameterException, RequiredParameterException, OfferNotFoundException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        Offer offer = OfferTestHelper.createOffer();
        when(offerTransformer.fromDTO(offerDto)).thenReturn(offer);
        when(offerDAO.getOffer(offerDto.getId())).thenThrow(new OfferNotFoundException("Offer not found"));
        offerService.updateOffer(offerDto);
    }

    @Test
    public void updateOffer_whenSuccessful_updatedDateIsSet() throws InvalidParameterException, RequiredParameterException, OfferNotFoundException {
        OfferDTO offerDto = OfferTestHelper.createOfferDTO();
        Offer offer = OfferTestHelper.createOffer();
        when(offerTransformer.fromDTO(offerDto)).thenReturn(offer);
        when(offerDAO.getOffer(offerDto.getId())).thenReturn(offer);
        when(offerDAO.updateOffer(offer)).thenReturn(offer);
        when(offerTransformer.toDTO(offer)).thenCallRealMethod();

        OfferDTO updatedOfferDto = offerService.updateOffer(offerDto);

        Assert.assertTrue(updatedOfferDto.getCreatedDate() != null);
        Assert.assertTrue(updatedOfferDto.getLastUpdatedDate() != null);
        offerDto.setCreatedDate(offer.getCreatedDate());
        offerDto.setLastUpdatedDate(updatedOfferDto.getLastUpdatedDate());
        Assert.assertEquals(offerDto, updatedOfferDto);

        verify(mockValidator).validate(offerDto, Operation.UPDATE);
        verify(offerTransformer).fromDTO(offerDto);
        verify(offerDAO).updateOffer(offer);
        verify(offerTransformer).toDTO(offer);
    }

    @Test(expected = OfferNotFoundException.class)
    public void deleteOffer_whenOfferNotFound_throwsOfferNotFoundException() throws OfferNotFoundException {
        doThrow(new OfferNotFoundException("Offer not found")).when(offerDAO).deleteOffer(1);
        offerService.deleteOffer(1);
    }
}