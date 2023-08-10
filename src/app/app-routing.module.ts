import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChefsComponent } from './chefs/chefs.component';
import { ContactComponent } from './contact/contact.component';
import { GalleryComponent } from './gallery/gallery.component';
import { HomeComponent } from './home/home.component';
import { MenuComponent } from './menu/menu.component';
import { OrderComponent } from './order/order.component';
import { PlateListComponent } from './plate-list/plate-list.component';
import { ReservationComponent } from './reservation/reservation.component';
import { RestaurantsComponent } from './restaurants/restaurants.component';
import { PlatesViewComponent } from './plates-view/plates-view.component';

const routes: Routes = [
  {path:'',component:HomeComponent},
  {path:'restaurants',component:RestaurantsComponent},
  {path:'menu',component:MenuComponent},
  {path:'chefs',component:ChefsComponent},
  {path:'gallery',component:GalleryComponent},
  {path:'contact',component:ContactComponent},
  {path:'order',component:OrderComponent},
  {path:'reservation',component:ReservationComponent},
  {path:'platelist/:idrestaurant',component:PlateListComponent},
  {path:'plates',component:PlatesViewComponent},

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
