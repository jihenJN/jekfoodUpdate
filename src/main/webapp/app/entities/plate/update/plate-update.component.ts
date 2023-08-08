import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { PlateFormService, PlateFormGroup } from './plate-form.service';
import { IPlate } from '../plate.model';
import { PlateService } from '../service/plate.service';
import { IRestaurant } from 'app/entities/restaurant/restaurant.model';
import { RestaurantService } from 'app/entities/restaurant/service/restaurant.service';

@Component({
  selector: 'jhi-plate-update',
  templateUrl: './plate-update.component.html',
})
export class PlateUpdateComponent implements OnInit {
  isSaving = false;
  plate: IPlate | null = null;

  restaurantsSharedCollection: IRestaurant[] = [];

  editForm: PlateFormGroup = this.plateFormService.createPlateFormGroup();

  constructor(
    protected plateService: PlateService,
    protected plateFormService: PlateFormService,
    protected restaurantService: RestaurantService,
    protected activatedRoute: ActivatedRoute
  ) {}

  compareRestaurant = (o1: IRestaurant | null, o2: IRestaurant | null): boolean => this.restaurantService.compareRestaurant(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ plate }) => {
      this.plate = plate;
      if (plate) {
        this.updateForm(plate);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const plate = this.plateFormService.getPlate(this.editForm);
    if (plate.id !== null) {
      this.subscribeToSaveResponse(this.plateService.update(plate));
    } else {
      this.subscribeToSaveResponse(this.plateService.create(plate));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPlate>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(plate: IPlate): void {
    this.plate = plate;
    this.plateFormService.resetForm(this.editForm, plate);

    this.restaurantsSharedCollection = this.restaurantService.addRestaurantToCollectionIfMissing<IRestaurant>(
      this.restaurantsSharedCollection,
      plate.restaurant
    );
  }

  protected loadRelationshipsOptions(): void {
    this.restaurantService
      .query()
      .pipe(map((res: HttpResponse<IRestaurant[]>) => res.body ?? []))
      .pipe(
        map((restaurants: IRestaurant[]) =>
          this.restaurantService.addRestaurantToCollectionIfMissing<IRestaurant>(restaurants, this.plate?.restaurant)
        )
      )
      .subscribe((restaurants: IRestaurant[]) => (this.restaurantsSharedCollection = restaurants));
  }
}
