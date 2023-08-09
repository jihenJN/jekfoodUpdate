import { Injectable } from '@angular/core';
import { Restaurant } from 'src/app/shared/models/Restaurant';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class RestaurantService {
  private url = environment.urlRestaurants;

  constructor(private http: HttpClient) { }
 
  getRestaurants(): Observable<Restaurant[]> {
    return this.http.get<Restaurant[]>(`${this.url}`);
  }

 

  
  }

