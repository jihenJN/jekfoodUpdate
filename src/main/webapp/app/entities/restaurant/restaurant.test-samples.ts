import { IRestaurant, NewRestaurant } from './restaurant.model';

export const sampleWithRequiredData: IRestaurant = {
  id: '9a4f2ee4-d45a-4122-b55a-c0059e33895f',
  name: 'Locks Toys',
};

export const sampleWithPartialData: IRestaurant = {
  id: '791753b1-60d4-47b9-91f0-cd512462b023',
  name: 'Specialist',
  phone: '778-216-6068 x169',
  latitude: 94758,
};

export const sampleWithFullData: IRestaurant = {
  id: '7a51e68a-7a0a-4d08-a519-822e458131fb',
  name: 'Garden Shoes Clothing',
  phone: '769-886-9286 x978',
  address: 'Granite',
  photo: 'optical web-readiness Kids',
  longitude: 87380,
  latitude: 89687,
};

export const sampleWithNewData: NewRestaurant = {
  name: 'Solutions niches Metal',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
