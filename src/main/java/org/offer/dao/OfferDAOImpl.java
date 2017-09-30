package org.offer.dao;

import org.offer.exception.OfferNotFoundException;
import org.offer.model.Offer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OfferDAOImpl implements OfferDAO {

    private static final String OFFER_NOT_FOUND_ERR = "Offer with ID %s not found";
    private final AtomicInteger atomicId = new AtomicInteger(0);
    private final Map<Integer, Offer> offerMap = new HashMap<Integer, Offer>();

    public Collection<Offer> getAllOffers() {
        return offerMap.values();
    }

    public Offer getOffer(int id) throws OfferNotFoundException {
        checkOfferExists(id);
        return offerMap.get(id);
    }

    public Offer createOffer(Offer offer) {
        offer.setId(atomicId.incrementAndGet());
        offerMap.put(offer.getId(), offer);
        return offer;
    }

    public Offer updateOffer(Offer offer) throws OfferNotFoundException {
        checkOfferExists(offer.getId());
        offerMap.put(offer.getId(), offer);
        return offer;
    }

    public void deleteOffer(int id) throws OfferNotFoundException {
        checkOfferExists(id);
        offerMap.remove(id);
    }


    private void checkOfferExists(int id) throws OfferNotFoundException {
        if (!offerMap.containsKey(id)) {
            throw new OfferNotFoundException(String.format(OFFER_NOT_FOUND_ERR, id));
        }
    }
}
