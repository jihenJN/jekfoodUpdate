import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { PlateFormService } from './plate-form.service';
import { PlateService } from '../service/plate.service';
import { IPlate } from '../plate.model';
import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/service/restaurant.service';

import { PlateUpdateComponent } from './plate-update.component';

describe('Plate Management Update Component', () => {
  let comp: PlateUpdateComponent;
  let fixture: ComponentFixture<PlateUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plateFormService: PlateFormService;
  let plateService: PlateService;
  let restaurantService: RestaurantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [PlateUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlateUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlateUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plateFormService = TestBed.inject(PlateFormService);
    plateService = TestBed.inject(PlateService);
    restaurantService = TestBed.inject(RestaurantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Restaurant query and add missing value', () => {
      const plate: IPlate = { id: 'CBA' };
      const restaurant: IRestaurant = { id: 'c089821b-63c5-410f-a07a-92db943ab031' };
      plate.restaurant = restaurant;

      const restaurantCollection: IRestaurant[] = [{ id: 'a6cff97e-941e-4f61-964b-843ba4d71936' }];
      jest.spyOn(restaurantService, 'query').mockReturnValue(of(new HttpResponse({ body: restaurantCollection })));
      const additionalRestaurants = [restaurant];
      const expectedCollection: IRestaurant[] = [...additionalRestaurants, ...restaurantCollection];
      jest.spyOn(restaurantService, 'addRestaurantToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(restaurantService.query).toHaveBeenCalled();
      expect(restaurantService.addRestaurantToCollectionIfMissing).toHaveBeenCalledWith(
        restaurantCollection,
        ...additionalRestaurants.map(expect.objectContaining)
      );
      expect(comp.restaurantsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const plate: IPlate = { id: 'CBA' };
      const restaurant: IRestaurant = { id: '205a3d43-ed63-4b30-8c3b-a179544ad2cd' };
      plate.restaurant = restaurant;

      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      expect(comp.restaurantsSharedCollection).toContain(restaurant);
      expect(comp.plate).toEqual(plate);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlate>>();
      const plate = { id: 'ABC' };
      jest.spyOn(plateFormService, 'getPlate').mockReturnValue(plate);
      jest.spyOn(plateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plate }));
      saveSubject.complete();

      // THEN
      expect(plateFormService.getPlate).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(plateService.update).toHaveBeenCalledWith(expect.objectContaining(plate));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlate>>();
      const plate = { id: 'ABC' };
      jest.spyOn(plateFormService, 'getPlate').mockReturnValue({ id: null });
      jest.spyOn(plateService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plate: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plate }));
      saveSubject.complete();

      // THEN
      expect(plateFormService.getPlate).toHaveBeenCalled();
      expect(plateService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlate>>();
      const plate = { id: 'ABC' };
      jest.spyOn(plateService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plate });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plateService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareRestaurant', () => {
      it('Should forward to restaurantService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(restaurantService, 'compareRestaurant');
        comp.compareRestaurant(entity, entity2);
        expect(restaurantService.compareRestaurant).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
