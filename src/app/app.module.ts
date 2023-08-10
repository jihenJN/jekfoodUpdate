import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

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
import { PlatesViewComponent } from './plates-view/plates-view.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


// Import PrimeNG modules

import { DataViewModule } from 'primeng/dataview';
import { VirtualScrollerModule } from 'primeng/virtualscroller';
import { ImageModule } from 'primeng/image';
import { ListboxModule } from 'primeng/listbox';
import { PaginatorModule } from 'primeng/paginator';
import { RatingModule } from 'primeng/rating';
import { SelectButtonModule } from 'primeng/selectbutton';
import { SpinnerModule } from 'primeng/spinner';
import { SplitButtonModule } from 'primeng/splitbutton';
import { SplitterModule } from 'primeng/splitter';
import { StepsModule } from 'primeng/steps';
import { TagModule } from 'primeng/tag';
import { AnimateModule } from 'primeng/animate';
import { CardModule } from 'primeng/card';

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
         PlateListComponent,
         PlatesViewComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BarRatingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,

    VirtualScrollerModule,
    DataViewModule,
    ImageModule,
    ListboxModule,
    PaginatorModule,
    RatingModule,
    SelectButtonModule,
    SpinnerModule,
    SplitterModule,
    SplitButtonModule,
    StepsModule,
    TagModule,
    AnimateModule,
    CardModule,
    
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
