import { Component, OnInit } from '@angular/core';
import { RestaurantService } from '../services/restaurant/restaurant.service';
import { Restaurant, RestaurantDto } from '../shared/models/Restaurant';

@Component({
  selector: 'app-restaurants',
  templateUrl: './restaurants.component.html',
  styleUrls: ['./restaurants.component.scss']
})
export class RestaurantsComponent implements OnInit {
  
  restaurants: Restaurant[] = [];
  restaurantsDto:  RestaurantDto[] = [];


  constructor(private restaurantService:RestaurantService ) { }

  ngOnInit(): void {

  this.restaurantService.getRestaurants().subscribe((data: Restaurant[]) => {
    this.restaurants = data;
    this.restaurantsDto = this.inintRestaurantDto(this.restaurants);
    console.log(this.restaurants);

  });
}


inintRestaurantDto(restaurants: Restaurant[]):RestaurantDto[] {
  let tempRestaurantDto: RestaurantDto[] = [];
 
  restaurants.forEach((restaurant) => {

    const restDto: RestaurantDto = {
      id: restaurant.id,
      name: restaurant.name,
      phone: restaurant.phone,
      address: restaurant.address,
      photo: restaurant.photo,
    
    };

    tempRestaurantDto.push(restDto);

   });

  return tempRestaurantDto;
}
 



}
