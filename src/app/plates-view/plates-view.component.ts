import { Component, OnInit } from '@angular/core';
import { SelectItem } from 'primeng/api';
import { Food, foodDto } from '../shared/models/Food';
import { FoodService } from '../services/food/food.service';
import { ActivatedRoute, ParamMap, Router } from '@angular/router';

@Component({
  selector: 'app-plates-view',
  templateUrl: './plates-view.component.html',
  styleUrls: ['./plates-view.component.scss']
})
export class PlatesViewComponent implements OnInit {


  sortOptions!: SelectItem[];

  sortOrder!: number;

  sortField!: string;


  foods : Food[] =[]
  foodsDto: foodDto[] = [];

  constructor(private foodService: FoodService,private route: ActivatedRoute, private router:Router) {}

  ngOnInit() {
    
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
  

         
      this.sortOptions = [
        { label: 'Price High to Low', value: '!price' },
        { label: 'Price Low to High', value: 'price' }
    ];
  }

  onSortChange(event: any) {
    let value = event.value;

    if (value.indexOf('!') === 0) {
        this.sortOrder = -1;
        this.sortField = value.substring(1, value.length);
    } else {
        this.sortOrder = 1;
        this.sortField = value;
    }
}


  getSeverity(food: Food) {
      switch (food.favorite) {
          case true:
              return 'success';

          case false:
              return 'warning';

          

              default:
                return '';
      }
  };

  inintFoodDto(foods: Food[]):foodDto[] {
    
    let tempFoodDto: foodDto[] = [];
   

    foods.forEach((food) => {
  
      const foDto: foodDto = {
        name: food.name,
        price: food.price,
        photos: food.photos,
        restaurant: this.getRestaurant(food.restaurant?.name),
        idrestaurant:food.idrestaurant,
        favorite:food.favorite,
        stars: food.stars,
        cook_time:food.cook_time,
        origin: food.origin,
      }; 
      tempFoodDto.push(foDto);
     
    });
  
    
    return  tempFoodDto;
   
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
