package org.offer.model;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;

public class Offer {

    private int id;
    private String name;
    private String description;
    private String merchant;
    private Currency currency;
    private BigDecimal price;

    private Date createdDate;
    private Date lastUpdatedDate;

    public Offer() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
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

        Offer offer = (Offer) o;

        if (id != offer.id) return false;
        if (name != null ? !name.equals(offer.name) : offer.name != null) return false;
        if (description != null ? !description.equals(offer.description) : offer.description != null) return false;
        if (merchant != null ? !merchant.equals(offer.merchant) : offer.merchant != null) return false;
        if (currency != null ? !currency.equals(offer.currency) : offer.currency != null) return false;
        if (price != null ? !price.equals(offer.price) : offer.price != null) return false;
        if (createdDate != null ? !createdDate.equals(offer.createdDate) : offer.createdDate != null) return false;
        return !(lastUpdatedDate != null ? !lastUpdatedDate.equals(offer.lastUpdatedDate) : offer.lastUpdatedDate != null);

    }

    @Override
    public int hashCode() {
        int result = id;
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
        return "Offer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", merchant='" + merchant + '\'' +
                ", currency=" + currency +
                ", price=" + price +
                ", createdDate=" + createdDate +
                ", lastUpdatedDate=" + lastUpdatedDate +
                '}';
    }
}
