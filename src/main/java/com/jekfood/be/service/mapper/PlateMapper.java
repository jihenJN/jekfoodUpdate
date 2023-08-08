package com.jekfood.be.service.mapper;

import com.jekfood.be.domain.Plate;
import com.jekfood.be.domain.Restaurant;
import com.jekfood.be.service.dto.PlateDTO;
import com.jekfood.be.service.dto.RestaurantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Plate} and its DTO {@link PlateDTO}.
 */
@Mapper(componentModel = "spring")
public interface PlateMapper extends EntityMapper<PlateDTO, Plate> {
    @Mapping(target = "restaurant", source = "restaurant", qualifiedByName = "restaurantName")
    PlateDTO toDto(Plate s);

    @Named("restaurantName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    RestaurantDTO toDtoRestaurantName(Restaurant restaurant);
}
