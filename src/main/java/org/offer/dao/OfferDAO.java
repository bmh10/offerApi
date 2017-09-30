package org.offer.dao;

import org.offer.exception.OfferNotFoundException;
import org.offer.model.Offer;

import java.util.Collection;

public interface OfferDAO {

    Collection<Offer> getAllOffers();

    Offer getOffer(int id) throws OfferNotFoundException;

    Offer createOffer(Offer offer);

    Offer updateOffer(Offer offer) throws OfferNotFoundException;

    void deleteOffer(int id) throws OfferNotFoundException;
}
