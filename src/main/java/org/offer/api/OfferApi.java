package org.offer.api;

import org.offer.model.dto.OfferDTO;

import javax.ws.rs.core.Response;

public interface OfferApi {

    Response getOffers();
    Response getOffer(int id);
    Response createOffer(OfferDTO offerDTO);
    Response updateOffer(int id, OfferDTO offerDTO);
    Response deleteOffer(int id);
}
