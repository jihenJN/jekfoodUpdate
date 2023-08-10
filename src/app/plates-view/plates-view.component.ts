import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../shared/models/product';


@Component({
  selector: 'app-plates-view',
  templateUrl: './plates-view.component.html',
  styleUrls: ['./plates-view.component.scss']
})
export class PlatesViewComponent implements OnInit {

  products!: Product[];

  constructor(private productService: ProductService) {}

  ngOnInit() {
      this.productService.getProducts().then((data) => (this.products = data));
  }

  getSeverity(product: Product) {
      switch (product.inventoryStatus) {
          case 'INSTOCK':
              return 'success';

          case 'LOWSTOCK':
              return 'warning';

          case 'OUTOFSTOCK':
              return 'danger';

              default:
                return '';
      }
  };

  
}
