package org.offer.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OfferDTO implements Serializable {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss z";

    private Integer id;
    private String name;
    private String description;
    private String merchant;
    private String currency;
    private BigDecimal price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date createdDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT)
    private Date lastUpdatedDate;

    public OfferDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OfferDTO offerDTO = (OfferDTO) o;

        if (id != null ? !id.equals(offerDTO.id) : offerDTO.id != null) return false;
        if (name != null ? !name.equals(offerDTO.name) : offerDTO.name != null) return false;
        if (description != null ? !description.equals(offerDTO.description) : offerDTO.description != null) return false;
        if (merchant != null ? !merchant.equals(offerDTO.merchant) : offerDTO.merchant != null) return false;
        if (currency != null ? !currency.equals(offerDTO.currency) : offerDTO.currency != null) return false;
        if (price != null ? !price.equals(offerDTO.price) : offerDTO.price != null) return false;
        if (createdDate != null ? !createdDate.equals(offerDTO.createdDate) : offerDTO.createdDate != null) return false;
        return !(lastUpdatedDate != null ? !lastUpdatedDate.equals(offerDTO.lastUpdatedDate) : offerDTO.lastUpdatedDate != null);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (merchant != null ? merchant.hashCode() : 0);
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (createdDate != null ? createdDate.hashCode() : 0);
        result = 31 * result + (lastUpdatedDate != null ? lastUpdatedDate.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OfferDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", merchant='" + merchant + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
