import { Restaurant } from "./Restaurant";


export class Food {

    name:string;
    price:number;
    photos:any;
    favorite:boolean;
    stars:number;
    origin:string;
    idrestaurant:string;
    cook_time:number;
    restaurant?: Pick<Restaurant,'name'> | null;
   
    
    constructor() {
      this.name= '';
      this.price= 0;
      this.photos= '';
      this.favorite=false;
      this.stars=0;
      this.origin='';
      this.idrestaurant='';
      this.cook_time=0;
      this.restaurant=null;
      
    }
  
  
  }
    
  export interface foodDto {

    name:string;
    price:number
    photos:any;
    favorite:boolean;
    stars:number;
    origin:string;
    idrestaurant: string;
    cook_time:number;
    restaurant?: Pick<Restaurant,'name'> | null;
   
  }
  