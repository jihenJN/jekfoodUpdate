import { Food } from "./Food";

/*export class Restaurant{
    id!:number;
    name!:string;
    imageUrl!:string;
    address!:string;
    phone!:number;
   
}*/

// copying same variable names of Restaurant dto in spring boot BE
export class Restaurant {
    id:string;
    name:string;
    phone:string;
    address:string;
    photo:any;
   
    
    constructor() {
      this.id='';
      this.name= '';
      this.phone= '';
      this.address= '';
      this.photo= '';
     
    }


  
  }
  
  
  
  export interface RestaurantDto {
    id :string;
    name:string;
    phone:string;
    address:string;
    photo:any;
   
    
  }
  