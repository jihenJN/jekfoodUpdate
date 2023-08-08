package com.jekfood.be.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.jekfood.be.domain.Plate} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlateDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Double price;

    private String photos;

    private String origin;

    private Integer stars;

    private Boolean favorite;

    private Integer cook_time;

    private RestaurantDTO restaurant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getCook_time() {
        return cook_time;
    }

    public void setCook_time(Integer cook_time) {
        this.cook_time = cook_time;
    }

    public RestaurantDTO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantDTO restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlateDTO)) {
            return false;
        }

        PlateDTO plateDTO = (PlateDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, plateDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlateDTO{" +
            "id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", price=" + getPrice() +
            ", photos='" + getPhotos() + "'" +
            ", origin='" + getOrigin() + "'" +
            ", stars=" + getStars() +
            ", favorite='" + getFavorite() + "'" +
            ", cook_time=" + getCook_time() +
            ", restaurant=" + getRestaurant() +
            "}";
    }
}
