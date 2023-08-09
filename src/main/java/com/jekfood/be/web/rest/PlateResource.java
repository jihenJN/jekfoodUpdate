package com.jekfood.be.web.rest;

import com.jekfood.be.domain.Plate;
import com.jekfood.be.domain.Restaurant;
import com.jekfood.be.repository.PlateRepository;
import com.jekfood.be.repository.RestaurantRepository;
import com.jekfood.be.service.PlateService;
import com.jekfood.be.service.dto.PlateDTO;
import com.jekfood.be.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.jekfood.be.domain.Plate}.
 */
@RestController
@RequestMapping("/api")
public class PlateResource {

    private final Logger log = LoggerFactory.getLogger(PlateResource.class);

    private static final String ENTITY_NAME = "plate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlateService plateService;

    private final PlateRepository plateRepository;
    private final RestaurantRepository restaurantRepository;

    public PlateResource(PlateService plateService, PlateRepository plateRepository, RestaurantRepository restaurantRepository) {
        this.plateService = plateService;
        this.plateRepository = plateRepository;
        this.restaurantRepository = restaurantRepository;
    }

    /**
     * {@code POST  /plates} : Create a new plate.
     *
     * @param plateDTO the plateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new plateDTO, or with status {@code 400 (Bad Request)} if the plate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/plates")
    public ResponseEntity<PlateDTO> createPlate(@Valid @RequestBody PlateDTO plateDTO) throws URISyntaxException {
        log.debug("REST request to save Plate : {}", plateDTO);
        if (plateDTO.getId() != null) {
            throw new BadRequestAlertException("A new plate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PlateDTO result = plateService.save(plateDTO);
        return ResponseEntity
            .created(new URI("/api/plates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /plates/:id} : Updates an existing plate.
     *
     * @param id the id of the plateDTO to save.
     * @param plateDTO the plateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plateDTO,
     * or with status {@code 400 (Bad Request)} if the plateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the plateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/plates/{id}")
    public ResponseEntity<PlateDTO> updatePlate(
        @PathVariable(value = "id", required = false) final String id,
        @Valid @RequestBody PlateDTO plateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Plate : {}, {}", id, plateDTO);
        if (plateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PlateDTO result = plateService.update(plateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plateDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /plates/:id} : Partial updates given fields of an existing plate, field will ignore if it is null
     *
     * @param id the id of the plateDTO to save.
     * @param plateDTO the plateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated plateDTO,
     * or with status {@code 400 (Bad Request)} if the plateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the plateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the plateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/plates/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlateDTO> partialUpdatePlate(
        @PathVariable(value = "id", required = false) final String id,
        @NotNull @RequestBody PlateDTO plateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Plate partially : {}, {}", id, plateDTO);
        if (plateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, plateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!plateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlateDTO> result = plateService.partialUpdate(plateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, plateDTO.getId())
        );
    }

    /**
     * {@code GET  /plates} : get all the plates.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plates in body.
     */
    @GetMapping("/plates")
    public ResponseEntity<List<PlateDTO>> getAllPlates(
        @org.springdoc.api.annotations.ParameterObject Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload
    ) {
        log.debug("REST request to get a page of Plates");
        Page<PlateDTO> page;
        if (eagerload) {
            page = plateService.findAllWithEagerRelationships(pageable);
        } else {
            page = plateService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /platesByRestaurant} : get all the plates by Restaurant.
     *
     * @param pageable the pagination information.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of plates in body.
     */
    @GetMapping("/platesByRestaurant/{restaurantId}")
    public ResponseEntity<List<Plate>> getAllPlatesByRestaurant(
        @PathVariable String restaurantId,
        @org.springdoc.api.annotations.ParameterObject Pageable pageable
    ) {
        log.debug("REST request to get a page of Plates by Restaurant");

        // Retrieve the Restaurant object using the provided restaurantId
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);

        if (!restaurant.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        // Call the service method with the retrieved Restaurant object
        Page<Plate> page = plateService.findAllByRestaurant(restaurant.get(), pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);

        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /plates/:id} : get the "id" plate.
     *
     * @param id the id of the plateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the plateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/plates/{id}")
    public ResponseEntity<PlateDTO> getPlate(@PathVariable String id) {
        log.debug("REST request to get Plate : {}", id);
        Optional<PlateDTO> plateDTO = plateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(plateDTO);
    }

    /**
     * {@code DELETE  /plates/:id} : delete the "id" plate.
     *
     * @param id the id of the plateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/plates/{id}")
    public ResponseEntity<Void> deletePlate(@PathVariable String id) {
        log.debug("REST request to delete Plate : {}", id);
        plateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
