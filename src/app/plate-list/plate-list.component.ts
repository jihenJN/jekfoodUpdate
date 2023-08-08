import { Component, OnInit } from '@angular/core';
import { FoodService } from '../services/food/food.service';
import { Food, foodDto } from '../shared/models/Food';
import { Restaurant } from '../shared/models/Restaurant';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

@Component({
  selector: 'app-plate-list',
  templateUrl: './plate-list.component.html',
  styleUrls: ['./plate-list.component.scss']
})
export class PlateListComponent implements OnInit {


  foods: Food[] = []
  foodsDto: foodDto[] = []


  constructor(private foodService: FoodService,
              private route: ActivatedRoute,
              private router:Router,) { }

  ngOnInit(): void {


   this.route.paramMap.subscribe((param : ParamMap) => {

    var idrestaurant = String(param.get('idrestaurant'));

    console.log(idrestaurant);


   
    this.foodService.getplatesbyrestaurant(idrestaurant).subscribe((data) => {
      
      this.foods = data;
      console.log(this.foods)

      this.foodsDto = this.inintFoodDto(this.foods );
      
      console.log(this.foodsDto)

    });
    
   


  });






    
  }

  inintFoodDto(foods: Food[]): foodDto[] {

    let tempFoodDto: foodDto[] = [];

    foods.forEach((food) => {

      const foDto: foodDto = {
        name: food.name,
        price: food.price,
        photos: food.photos,
        restaurant: this.getRestaurant(food.restaurant?.name),
        idrestaurant: food.idrestaurant,
        favorite: food.favorite,
        stars: food.stars,
        cook_time: food.cook_time,
        origin: food.origin,
      };
      tempFoodDto.push(foDto);

    });


    return tempFoodDto;

  }

 
  private getRestaurant(data:any): any {
    console.log("data in getRestaurant----------" + data);
    return data;
  }

  getByIdRestaurant(idrestaurant:string) {
    this.foodService.getplatesbyrestaurant(idrestaurant).subscribe((data) => {
      this.foods = data;
      console.log(this.foods)
    });
  }



  


}
