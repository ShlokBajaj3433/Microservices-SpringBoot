import {Component, DestroyRef, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {Product} from "../../model/product";
import {ProductService} from "../../services/product/product.service";
import {Router} from "@angular/router";
import {Order} from "../../model/order";
import {FormsModule} from "@angular/forms";
import {OrderService} from "../../services/order/order.service";
import {take} from "rxjs";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-homepage',
    templateUrl: './home-page.component.html',
    imports: [
        FormsModule
    ],
    styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit {
  private readonly destroyRef = inject(DestroyRef);
  private readonly oidcSecurityService = inject(OidcSecurityService);
  private readonly productService = inject(ProductService);
  private readonly orderService = inject(OrderService);
  private readonly router = inject(Router);
  isAuthenticated = false;
  products: Array<Product> = [];
  quantityIsNull = false;
  orderSuccess = false;
  orderFailed = false;

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
        if (isAuthenticated) {
          this.productService.getProducts()
            .pipe(takeUntilDestroyed(this.destroyRef))
            .subscribe(product => {
              this.products = product;
            });
        }
      }
    );
  }

  goToCreateProductPage() {
    this.router.navigateByUrl('/add-product');
  }

  orderProduct(product: Product, quantity: string) {
    const parsedQuantity = Number(quantity);

    if (!quantity || Number.isNaN(parsedQuantity) || parsedQuantity <= 0) {
      this.orderFailed = true;
      this.orderSuccess = false;
      this.quantityIsNull = true;
      return;
    }

    this.oidcSecurityService.userData$
      .pipe(take(1))
      .subscribe(result => {
      const userDetails = {
        email: result.userData.email,
        firstName: result.userData.given_name,
        lastName: result.userData.family_name
      };

        const order: Order = {
          skuCode: product.skuCode,
          price: product.price,
          quantity: parsedQuantity,
          userDetails: userDetails
        }

        this.orderService.orderProduct(order).pipe(take(1)).subscribe(() => {
          this.orderSuccess = true;
          this.orderFailed = false;
          this.quantityIsNull = false;
        }, () => {
          this.orderFailed = true;
          this.orderSuccess = false;
          this.quantityIsNull = false;
        });
    });
  }
}
