package org.offer.service;

import org.offer.exception.InvalidParameterException;
import org.offer.exception.OfferNotFoundException;
import org.offer.exception.RequiredParameterException;
import org.offer.model.Offer;
import org.offer.model.dto.OfferDTO;

import java.util.Collection;

public interface OfferService {

    Collection<OfferDTO> getAllOffers();

    OfferDTO getOffer(int id) throws OfferNotFoundException;

    OfferDTO createOffer(OfferDTO offer)
            throws RequiredParameterException, InvalidParameterException;

    OfferDTO updateOffer(OfferDTO offer)
            throws RequiredParameterException, InvalidParameterException, OfferNotFoundException;

    void deleteOffer(int id) throws OfferNotFoundException;
}
