package org.offer.transformer;

import org.offer.model.Offer;
import org.offer.model.dto.OfferDTO;

import java.util.Currency;

public class OfferTransformerImpl implements OfferTransformer {

    public Offer fromDTO(OfferDTO offerDTO) {
        Offer o = new Offer();
        if (offerDTO.getId() != null) {
            o.setId(offerDTO.getId());
        }
        o.setName(offerDTO.getName());
        o.setDescription(offerDTO.getDescription());
        o.setMerchant(offerDTO.getMerchant());
        o.setCurrency(Currency.getInstance(offerDTO.getCurrency()));
        o.setPrice(offerDTO.getPrice());
        return o;
    }

    public OfferDTO toDTO(Offer offer) {
        OfferDTO dto = new OfferDTO();
        dto.setId(offer.getId());
        dto.setName(offer.getName());
        dto.setDescription(offer.getDescription());
        dto.setMerchant(offer.getMerchant());
        dto.setCurrency(offer.getCurrency().getCurrencyCode());
        dto.setPrice(offer.getPrice());
        dto.setCreatedDate(offer.getCreatedDate());
        dto.setLastUpdatedDate(offer.getLastUpdatedDate());
        return dto;
    }

}
