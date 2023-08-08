package com.jekfood.be.service;

import com.jekfood.be.domain.Plate;
import com.jekfood.be.domain.Restaurant;
import com.jekfood.be.repository.PlateRepository;
import com.jekfood.be.repository.RestaurantRepository;
import com.jekfood.be.service.dto.PlateDTO;
import com.jekfood.be.service.dto.RestaurantDTO;
import com.jekfood.be.service.mapper.PlateMapper;
import com.jekfood.be.service.mapper.RestaurantMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Restaurant}.
 */
@Service
public class RestaurantService {

    private final Logger log = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository restaurantRepository;

    private final RestaurantMapper restaurantMapper;

    private final PlateRepository plateRepository;

    private final PlateMapper plateMapper;

    public RestaurantService(
        RestaurantRepository restaurantRepository,
        RestaurantMapper restaurantMapper,
        PlateRepository plateRepository,
        PlateMapper plateMapper
    ) {
        this.restaurantRepository = restaurantRepository;
        this.restaurantMapper = restaurantMapper;
        this.plateRepository = plateRepository;
        this.plateMapper = plateMapper;
    }

    /**
     * Save a restaurant.
     *
     * @param restaurantDTO the entity to save.
     * @return the persisted entity.
     */
    public RestaurantDTO save(RestaurantDTO restaurantDTO) {
        log.debug("Request to save Restaurant : {}", restaurantDTO);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    /**
     * Update a restaurant.
     *
     * @param restaurantDTO the entity to save.
     * @return the persisted entity.
     */
    public RestaurantDTO update(RestaurantDTO restaurantDTO) {
        log.debug("Request to update Restaurant : {}", restaurantDTO);
        Restaurant restaurant = restaurantMapper.toEntity(restaurantDTO);
        restaurant = restaurantRepository.save(restaurant);
        return restaurantMapper.toDto(restaurant);
    }

    /**
     * Partially update a restaurant.
     *
     * @param restaurantDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<RestaurantDTO> partialUpdate(RestaurantDTO restaurantDTO) {
        log.debug("Request to partially update Restaurant : {}", restaurantDTO);

        return restaurantRepository
            .findById(restaurantDTO.getId())
            .map(existingRestaurant -> {
                restaurantMapper.partialUpdate(existingRestaurant, restaurantDTO);

                return existingRestaurant;
            })
            .map(restaurantRepository::save)
            .map(restaurantMapper::toDto);
    }

    /**
     * Get all the restaurants.
     *
     * @return the list of entities.
     */
    public List<RestaurantDTO> findAll() {
        log.debug("Request to get all Restaurants");
        return restaurantRepository.findAll().stream().map(restaurantMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one restaurant by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<RestaurantDTO> findOne(String id) {
        log.debug("Request to get Restaurant : {}", id);
        return restaurantRepository.findById(id).map(restaurantMapper::toDto);
    }

    /*******************JN MODIFY DELETE INTO DELETE CASCADE BETWEEN RESTAURANT AND PLATES*******/

    /**
     * Delete the restaurant by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Restaurant : {}", id);

        // Find the restaurant by ID
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(id);

        if (restaurantOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();

            // Delete the associated plates
            List<Plate> plates = plateRepository.findAllByRestaurant(restaurant);
            for (Plate plate : plates) {
                plateRepository.deleteById(plate.getId());
            }

            // Delete the restaurant
            restaurantRepository.deleteById(id);
        }
    }
}
