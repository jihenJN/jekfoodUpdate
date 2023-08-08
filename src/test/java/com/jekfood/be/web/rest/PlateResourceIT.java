package com.jekfood.be.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jekfood.be.IntegrationTest;
import com.jekfood.be.domain.Plate;
import com.jekfood.be.repository.PlateRepository;
import com.jekfood.be.service.PlateService;
import com.jekfood.be.service.dto.PlateDTO;
import com.jekfood.be.service.mapper.PlateMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Integration tests for the {@link PlateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PlateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_PRICE = 1D;
    private static final Double UPDATED_PRICE = 2D;

    private static final String DEFAULT_PHOTOS = "AAAAAAAAAA";
    private static final String UPDATED_PHOTOS = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGIN = "AAAAAAAAAA";
    private static final String UPDATED_ORIGIN = "BBBBBBBBBB";

    private static final Integer DEFAULT_STARS = 1;
    private static final Integer UPDATED_STARS = 2;

    private static final Boolean DEFAULT_FAVORITE = false;
    private static final Boolean UPDATED_FAVORITE = true;

    private static final Integer DEFAULT_COOK_TIME = 1;
    private static final Integer UPDATED_COOK_TIME = 2;

    private static final String ENTITY_API_URL = "/api/plates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PlateRepository plateRepository;

    @Mock
    private PlateRepository plateRepositoryMock;

    @Autowired
    private PlateMapper plateMapper;

    @Mock
    private PlateService plateServiceMock;

    @Autowired
    private MockMvc restPlateMockMvc;

    private Plate plate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plate createEntity() {
        Plate plate = new Plate()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .photos(DEFAULT_PHOTOS)
            .origin(DEFAULT_ORIGIN)
            .stars(DEFAULT_STARS)
            .favorite(DEFAULT_FAVORITE)
            .cook_time(DEFAULT_COOK_TIME);
        return plate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plate createUpdatedEntity() {
        Plate plate = new Plate()
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photos(UPDATED_PHOTOS)
            .origin(UPDATED_ORIGIN)
            .stars(UPDATED_STARS)
            .favorite(UPDATED_FAVORITE)
            .cook_time(UPDATED_COOK_TIME);
        return plate;
    }

    @BeforeEach
    public void initTest() {
        plateRepository.deleteAll();
        plate = createEntity();
    }

    @Test
    void createPlate() throws Exception {
        int databaseSizeBeforeCreate = plateRepository.findAll().size();
        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);
        restPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateDTO)))
            .andExpect(status().isCreated());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeCreate + 1);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlate.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlate.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testPlate.getOrigin()).isEqualTo(DEFAULT_ORIGIN);
        assertThat(testPlate.getStars()).isEqualTo(DEFAULT_STARS);
        assertThat(testPlate.getFavorite()).isEqualTo(DEFAULT_FAVORITE);
        assertThat(testPlate.getCook_time()).isEqualTo(DEFAULT_COOK_TIME);
    }

    @Test
    void createPlateWithExistingId() throws Exception {
        // Create the Plate with an existing ID
        plate.setId("existing_id");
        PlateDTO plateDTO = plateMapper.toDto(plate);

        int databaseSizeBeforeCreate = plateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = plateRepository.findAll().size();
        // set the field null
        plate.setName(null);

        // Create the Plate, which fails.
        PlateDTO plateDTO = plateMapper.toDto(plate);

        restPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateDTO)))
            .andExpect(status().isBadRequest());

        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void checkPriceIsRequired() throws Exception {
        int databaseSizeBeforeTest = plateRepository.findAll().size();
        // set the field null
        plate.setPrice(null);

        // Create the Plate, which fails.
        PlateDTO plateDTO = plateMapper.toDto(plate);

        restPlateMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateDTO)))
            .andExpect(status().isBadRequest());

        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    void getAllPlates() throws Exception {
        // Initialize the database
        plateRepository.save(plate);

        // Get all the plateList
        restPlateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plate.getId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.doubleValue())))
            .andExpect(jsonPath("$.[*].photos").value(hasItem(DEFAULT_PHOTOS)))
            .andExpect(jsonPath("$.[*].origin").value(hasItem(DEFAULT_ORIGIN)))
            .andExpect(jsonPath("$.[*].stars").value(hasItem(DEFAULT_STARS)))
            .andExpect(jsonPath("$.[*].favorite").value(hasItem(DEFAULT_FAVORITE.booleanValue())))
            .andExpect(jsonPath("$.[*].cook_time").value(hasItem(DEFAULT_COOK_TIME)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlatesWithEagerRelationshipsIsEnabled() throws Exception {
        when(plateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(plateServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllPlatesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(plateServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPlateMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(plateRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getPlate() throws Exception {
        // Initialize the database
        plateRepository.save(plate);

        // Get the plate
        restPlateMockMvc
            .perform(get(ENTITY_API_URL_ID, plate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plate.getId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.doubleValue()))
            .andExpect(jsonPath("$.photos").value(DEFAULT_PHOTOS))
            .andExpect(jsonPath("$.origin").value(DEFAULT_ORIGIN))
            .andExpect(jsonPath("$.stars").value(DEFAULT_STARS))
            .andExpect(jsonPath("$.favorite").value(DEFAULT_FAVORITE.booleanValue()))
            .andExpect(jsonPath("$.cook_time").value(DEFAULT_COOK_TIME));
    }

    @Test
    void getNonExistingPlate() throws Exception {
        // Get the plate
        restPlateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    void putExistingPlate() throws Exception {
        // Initialize the database
        plateRepository.save(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate
        Plate updatedPlate = plateRepository.findById(plate.getId()).get();
        updatedPlate
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photos(UPDATED_PHOTOS)
            .origin(UPDATED_ORIGIN)
            .stars(UPDATED_STARS)
            .favorite(UPDATED_FAVORITE)
            .cook_time(UPDATED_COOK_TIME);
        PlateDTO plateDTO = plateMapper.toDto(updatedPlate);

        restPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plateDTO))
            )
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlate.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlate.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testPlate.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testPlate.getStars()).isEqualTo(UPDATED_STARS);
        assertThat(testPlate.getFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testPlate.getCook_time()).isEqualTo(UPDATED_COOK_TIME);
    }

    @Test
    void putNonExistingPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(UUID.randomUUID().toString());

        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithIdMismatchPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(UUID.randomUUID().toString());

        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void putWithMissingIdPathParamPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(UUID.randomUUID().toString());

        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void partialUpdatePlateWithPatch() throws Exception {
        // Initialize the database
        plateRepository.save(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate using partial update
        Plate partialUpdatedPlate = new Plate();
        partialUpdatedPlate.setId(plate.getId());

        partialUpdatedPlate.origin(UPDATED_ORIGIN).stars(UPDATED_STARS).favorite(UPDATED_FAVORITE).cook_time(UPDATED_COOK_TIME);

        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlate))
            )
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPlate.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testPlate.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testPlate.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testPlate.getStars()).isEqualTo(UPDATED_STARS);
        assertThat(testPlate.getFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testPlate.getCook_time()).isEqualTo(UPDATED_COOK_TIME);
    }

    @Test
    void fullUpdatePlateWithPatch() throws Exception {
        // Initialize the database
        plateRepository.save(plate);

        int databaseSizeBeforeUpdate = plateRepository.findAll().size();

        // Update the plate using partial update
        Plate partialUpdatedPlate = new Plate();
        partialUpdatedPlate.setId(plate.getId());

        partialUpdatedPlate
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .photos(UPDATED_PHOTOS)
            .origin(UPDATED_ORIGIN)
            .stars(UPDATED_STARS)
            .favorite(UPDATED_FAVORITE)
            .cook_time(UPDATED_COOK_TIME);

        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlate))
            )
            .andExpect(status().isOk());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
        Plate testPlate = plateList.get(plateList.size() - 1);
        assertThat(testPlate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPlate.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testPlate.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testPlate.getOrigin()).isEqualTo(UPDATED_ORIGIN);
        assertThat(testPlate.getStars()).isEqualTo(UPDATED_STARS);
        assertThat(testPlate.getFavorite()).isEqualTo(UPDATED_FAVORITE);
        assertThat(testPlate.getCook_time()).isEqualTo(UPDATED_COOK_TIME);
    }

    @Test
    void patchNonExistingPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(UUID.randomUUID().toString());

        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithIdMismatchPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(UUID.randomUUID().toString());

        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, UUID.randomUUID().toString())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void patchWithMissingIdPathParamPlate() throws Exception {
        int databaseSizeBeforeUpdate = plateRepository.findAll().size();
        plate.setId(UUID.randomUUID().toString());

        // Create the Plate
        PlateDTO plateDTO = plateMapper.toDto(plate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlateMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plateDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plate in the database
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    void deletePlate() throws Exception {
        // Initialize the database
        plateRepository.save(plate);

        int databaseSizeBeforeDelete = plateRepository.findAll().size();

        // Delete the plate
        restPlateMockMvc
            .perform(delete(ENTITY_API_URL_ID, plate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plate> plateList = plateRepository.findAll();
        assertThat(plateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
