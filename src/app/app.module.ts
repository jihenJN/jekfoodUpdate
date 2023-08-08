import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarComponent } from './navbar/navbar.component';
import { RestaurantsComponent } from './restaurants/restaurants.component';
import { HomeComponent } from './home/home.component';
import { MenuComponent } from './menu/menu.component';
import { CounterComponent } from './counter/counter.component';
import { ChefsComponent } from './chefs/chefs.component';

import { GalleryComponent } from './gallery/gallery.component';
import { ContactComponent } from './contact/contact.component';
import { OrderComponent } from './order/order.component';
import { FooterComponent } from './footer/footer.component';
import { ReservationComponent } from './reservation/reservation.component';
import { BarRatingModule } from "ngx-bar-rating";
import { HttpClientModule } from '@angular/common/http';
import { PlateListComponent } from './plate-list/plate-list.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarComponent,
    RestaurantsComponent,
    HomeComponent,
    MenuComponent,
    CounterComponent,
    ChefsComponent,
    
    GalleryComponent,
         ContactComponent,
         OrderComponent,
         FooterComponent,
         ReservationComponent,
         PlateListComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BarRatingModule,
    HttpClientModule
   
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
