package org.offer.service;

import org.offer.dao.OfferDAO;
import org.offer.exception.InvalidParameterException;
import org.offer.exception.OfferNotFoundException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.Offer;
import org.offer.model.dto.OfferDTO;
import org.offer.model.Operation;
import org.offer.transformer.OfferTransformer;
import org.offer.validator.Validator;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

public class OfferServiceImpl implements OfferService {

    private Validator<OfferDTO> offerValidator;
    private OfferTransformer offerTransformer;
    private OfferDAO offerDAO;

    public OfferServiceImpl(
            Validator<OfferDTO> offerValidator,
            OfferTransformer offerTransformer,
            OfferDAO offerDAO) {
        this.offerValidator = offerValidator;
        this.offerTransformer = offerTransformer;
        this.offerDAO = offerDAO;
    }

    public Collection<OfferDTO> getAllOffers() {
        return offerDAO.getAllOffers().stream().map(offerTransformer::toDTO).collect(Collectors.toList());
    }

    public OfferDTO getOffer(int id) throws OfferNotFoundException {
        Offer o = offerDAO.getOffer(id);
        return offerTransformer.toDTO(o);
    }

    public OfferDTO createOffer(OfferDTO offerDTO) throws RequiredParameterException, InvalidParameterException {
        offerValidator.validate(offerDTO, Operation.CREATE);
        Offer offer = offerTransformer.fromDTO(offerDTO);
        offer.setCreatedDate(new Date());
        Offer createdOffer = offerDAO.createOffer(offer);
        return offerTransformer.toDTO(createdOffer);
    }

    public OfferDTO updateOffer(OfferDTO offerDTO)
            throws RequiredParameterException, InvalidParameterException, OfferNotFoundException {
        offerValidator.validate(offerDTO, Operation.UPDATE);
        Offer offer = offerTransformer.fromDTO(offerDTO);
        offer.setCreatedDate(offerDAO.getOffer(offer.getId()).getCreatedDate());
        offer.setLastUpdatedDate(new Date());
        Offer updatedOffer = offerDAO.updateOffer(offer);
        return offerTransformer.toDTO(updatedOffer);
    }

    public void deleteOffer(int id) throws OfferNotFoundException {
        offerDAO.deleteOffer(id);
    }
}
