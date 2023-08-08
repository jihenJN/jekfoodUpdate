package com.jekfood.be.domain;

import java.io.Serializable;
import javax.validation.constraints.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Plate.
 */
@Document(collection = "plate")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Plate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("price")
    private Double price;

    @Field("photos")
    private String photos;

    @Field("origin")
    private String origin;

    @Field("stars")
    private Integer stars;

    @Field("favorite")
    private Boolean favorite;

    @Field("cook_time")
    private Integer cook_time;

    @DBRef
    @Field("restaurant")
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Plate id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Plate name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return this.price;
    }

    public Plate price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhotos() {
        return this.photos;
    }

    public Plate photos(String photos) {
        this.setPhotos(photos);
        return this;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getOrigin() {
        return this.origin;
    }

    public Plate origin(String origin) {
        this.setOrigin(origin);
        return this;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getStars() {
        return this.stars;
    }

    public Plate stars(Integer stars) {
        this.setStars(stars);
        return this;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Boolean getFavorite() {
        return this.favorite;
    }

    public Plate favorite(Boolean favorite) {
        this.setFavorite(favorite);
        return this;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getCook_time() {
        return this.cook_time;
    }

    public Plate cook_time(Integer cook_time) {
        this.setCook_time(cook_time);
        return this;
    }

    public void setCook_time(Integer cook_time) {
        this.cook_time = cook_time;
    }

    public Restaurant getRestaurant() {
        return this.restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Plate restaurant(Restaurant restaurant) {
        this.setRestaurant(restaurant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Plate)) {
            return false;
        }
        return id != null && id.equals(((Plate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Plate{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", photos='" + getPhotos() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", stars=" + getStars() +
            ", favorite='" + getFavorite() + "'" +
            ", cook_time=" + getCook_time() +
            "}";
    }
}
