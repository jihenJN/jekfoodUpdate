package com.jekfood.be.service;

import com.jekfood.be.domain.Plate;
import com.jekfood.be.domain.Restaurant;
import com.jekfood.be.repository.PlateRepository;
import com.jekfood.be.service.dto.PlateDTO;
import com.jekfood.be.service.mapper.PlateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Plate}.
 */
@Service
public class PlateService {

    private final Logger log = LoggerFactory.getLogger(PlateService.class);

    private final PlateRepository plateRepository;

    private final PlateMapper plateMapper;

    public PlateService(PlateRepository plateRepository, PlateMapper plateMapper) {
        this.plateRepository = plateRepository;
        this.plateMapper = plateMapper;
    }

    /**
     * Save a plate.
     *
     * @param plateDTO the entity to save.
     * @return the persisted entity.
     */
    public PlateDTO save(PlateDTO plateDTO) {
        log.debug("Request to save Plate : {}", plateDTO);
        Plate plate = plateMapper.toEntity(plateDTO);
        plate = plateRepository.save(plate);
        return plateMapper.toDto(plate);
    }

    /**
     * Update a plate.
     *
     * @param plateDTO the entity to save.
     * @return the persisted entity.
     */
    public PlateDTO update(PlateDTO plateDTO) {
        log.debug("Request to update Plate : {}", plateDTO);
        Plate plate = plateMapper.toEntity(plateDTO);
        plate = plateRepository.save(plate);
        return plateMapper.toDto(plate);
    }

    /**
     * Partially update a plate.
     *
     * @param plateDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PlateDTO> partialUpdate(PlateDTO plateDTO) {
        log.debug("Request to partially update Plate : {}", plateDTO);

        return plateRepository
            .findById(plateDTO.getId())
            .map(existingPlate -> {
                plateMapper.partialUpdate(existingPlate, plateDTO);

                return existingPlate;
            })
            .map(plateRepository::save)
            .map(plateMapper::toDto);
    }

    /**
     * Get all the plates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    public Page<PlateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Plates");
        return plateRepository.findAll(pageable).map(plateMapper::toDto);
    }

    /**
     * Get all the plates by restaurant.
     * @param pageable the pagination information.
     * @return the list of entities by restaurant.
     */
    public Page<Plate> findAllByRestaurant(Restaurant restaurant, Pageable pageable) {
        log.debug("Request to get all Plates");
        log.debug("findAllByRestaurant-----------------" + restaurant);
        log.debug("saleRepository.findAllByInvoice-----------------" + plateRepository.findAllByRestaurant(restaurant, pageable));
        return plateRepository.findAllByRestaurant(restaurant, pageable);
    }

    /**
     * Get all the plates with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<PlateDTO> findAllWithEagerRelationships(Pageable pageable) {
        return plateRepository.findAllWithEagerRelationships(pageable).map(plateMapper::toDto);
    }

    /**
     * Get one plate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    public Optional<PlateDTO> findOne(String id) {
        log.debug("Request to get Plate : {}", id);
        return plateRepository.findOneWithEagerRelationships(id).map(plateMapper::toDto);
    }

    /**
     * Delete the plate by id.
     *
     * @param id the id of the entity.
     */
    public void delete(String id) {
        log.debug("Request to delete Plate : {}", id);
        plateRepository.deleteById(id);
    }
}
