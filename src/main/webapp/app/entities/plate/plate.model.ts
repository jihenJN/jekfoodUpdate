import { IRestaurant } from 'app/entities/restaurant/restaurant.model';

export interface IPlate {
  id: string;
  name?: string | null;
  price?: number | null;
  photos?: string | null;
  origin?: string | null;
  stars?: number | null;
  favorite?: boolean | null;
  cook_time?: number | null;
  restaurant?: Pick<IRestaurant, 'id' | 'name'> | null;
}

export type NewPlate = Omit<IPlate, 'id'> & { id: null };
