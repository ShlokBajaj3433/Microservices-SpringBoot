  import {Component, inject, OnInit} from '@angular/core';
  import {OidcSecurityService} from "angular-auth-oidc-client";
  import {RouterModule} from "@angular/router";
  import {HeaderComponent} from "./shared/header/header.component";

  @Component({
      selector: 'app-root',
      imports: [RouterModule, HeaderComponent],
      templateUrl: './app.component.html',
      styleUrl: './app.component.css'
  })
  export class AppComponent implements OnInit {
    title = 'microservices-shop-frontend';

    private readonly oidcSecurityService = inject(OidcSecurityService);

    ngOnInit(): void {
      this.oidcSecurityService.isAuthenticated$.subscribe(({isAuthenticated}) => {
        console.log('app authenticated', isAuthenticated);
      });

      // Debug: Log the configuration
      console.log('OIDC Authority:', 'http://localhost:8181/realms/spring-microservices-security-realm');
    }
  }
