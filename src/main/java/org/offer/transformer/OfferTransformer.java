package org.offer.transformer;

import org.offer.model.Offer;
import org.offer.model.dto.OfferDTO;

public interface OfferTransformer {

    Offer fromDTO(OfferDTO offerDTO);
    OfferDTO toDTO(Offer offer);
}
