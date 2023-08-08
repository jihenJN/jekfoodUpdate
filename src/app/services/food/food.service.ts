import { Injectable } from '@angular/core';
import { Food } from 'src/app/shared/models/Food';
import { Restaurant } from 'src/app/shared/models/Restaurant';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FoodService {
  private url = environment.urlplates;

  constructor(private http: HttpClient) { }
  
  
  getplates(): Observable<Food[]> {
    return this.http.get<Food[]>(`${this.url}`);
  }
  
 /*getplatesbyrestaurant(idrestaurant:String): Observable<Food[]> {
    return this.http.get<Food[]>(`http://www.foodjekdelivery.com:8080/api/platesbyrestaurant/${idrestaurant}`);
  }*/

  getplatesbyrestaurant(idrestaurant:String): Observable<Food[]> {
    return this.http.get<Food[]>(`http://localhost:8080/api/platesbyrestaurant/${idrestaurant}`);
  }

   
  /*getplatesbyrestaurant(): Observable<Food[]> {
    return this.http.get<Food[]>(`http://localhost:8080/api/platesbyrestaurant/63d68c05b92e9d6e603d6a4f`);
  }*/

  
}

