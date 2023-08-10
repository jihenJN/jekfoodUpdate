import { Component, OnInit } from '@angular/core';
import { ProductService } from '../services/product.service';
import { Product } from '../shared/models/product';
import { SelectItem } from 'primeng/api';

@Component({
  selector: 'app-plates-view',
  templateUrl: './plates-view.component.html',
  styleUrls: ['./plates-view.component.scss']
})
export class PlatesViewComponent implements OnInit {


  sortOptions!: SelectItem[];

  sortOrder!: number;

  sortField!: string;


  products!: Product[];

  constructor(private productService: ProductService) {}

  ngOnInit() {
      this.productService.getProducts().then((data) => (this.products = data));
      
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
