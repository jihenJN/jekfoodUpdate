import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPlate, NewPlate } from '../plate.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPlate for edit and NewPlateFormGroupInput for create.
 */
type PlateFormGroupInput = IPlate | PartialWithRequiredKeyOf<NewPlate>;

type PlateFormDefaults = Pick<NewPlate, 'id' | 'favorite'>;

type PlateFormGroupContent = {
  id: FormControl<IPlate['id'] | NewPlate['id']>;
  name: FormControl<IPlate['name']>;
  price: FormControl<IPlate['price']>;
  photos: FormControl<IPlate['photos']>;
  origin: FormControl<IPlate['origin']>;
  stars: FormControl<IPlate['stars']>;
  favorite: FormControl<IPlate['favorite']>;
  cook_time: FormControl<IPlate['cook_time']>;
  restaurant: FormControl<IPlate['restaurant']>;
};

export type PlateFormGroup = FormGroup<PlateFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PlateFormService {
  createPlateFormGroup(plate: PlateFormGroupInput = { id: null }): PlateFormGroup {
    const plateRawValue = {
      ...this.getFormDefaults(),
      ...plate,
    };
    return new FormGroup<PlateFormGroupContent>({
      id: new FormControl(
        { value: plateRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        }
      ),
      name: new FormControl(plateRawValue.name, {
        validators: [Validators.required],
      }),
      price: new FormControl(plateRawValue.price, {
        validators: [Validators.required],
      }),
      photos: new FormControl(plateRawValue.photos),
      origin: new FormControl(plateRawValue.origin),
      stars: new FormControl(plateRawValue.stars),
      favorite: new FormControl(plateRawValue.favorite),
      cook_time: new FormControl(plateRawValue.cook_time),
      restaurant: new FormControl(plateRawValue.restaurant),
    });
  }

  getPlate(form: PlateFormGroup): IPlate | NewPlate {
    return form.getRawValue() as IPlate | NewPlate;
  }

  resetForm(form: PlateFormGroup, plate: PlateFormGroupInput): void {
    const plateRawValue = { ...this.getFormDefaults(), ...plate };
    form.reset(
      {
        ...plateRawValue,
        id: { value: plateRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */
    );
  }

  private getFormDefaults(): PlateFormDefaults {
    return {
      id: null,
      favorite: false,
    };
  }
}
