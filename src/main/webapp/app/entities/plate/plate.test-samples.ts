import { IPlate, NewPlate } from './plate.model';

export const sampleWithRequiredData: IPlate = {
  id: '64df0645-00f7-485b-b54d-48b2e291384e',
  name: 'National',
  price: 82861,
};

export const sampleWithPartialData: IPlate = {
  id: '82c9b5f2-8d47-45d9-8e6b-0e59f890c687',
  name: 'Fish',
  price: 9984,
  origin: 'Burgs Implemented',
  stars: 96099,
  favorite: false,
};

export const sampleWithFullData: IPlate = {
  id: 'f0028141-3acc-4eca-9d45-47607d3e11b8',
  name: 'transform',
  price: 93808,
  photos: 'Administrator Bacon',
  origin: 'Cotton withdrawal cutting-edge',
  stars: 73352,
  favorite: false,
  cook_time: 90670,
};

export const sampleWithNewData: NewPlate = {
  name: 'bus withdrawal pixel',
  price: 59343,
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
