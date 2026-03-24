import {Component, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";

@Component({
    selector: 'app-header',
    imports: [],
    templateUrl: './header.component.html',
    styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  private readonly oidcSecurityService = inject(OidcSecurityService);
  isAuthenticated = false;
  username = "";
  menuOpen = false;

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$.subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
      }
    );
    this.oidcSecurityService.userData$.subscribe(
      ({userData}) => {
        this.username = userData?.preferred_username ?? '';
      }
    );
  }

  login(): void {
    console.log('Login clicked');
    this.oidcSecurityService.preloadAuthWellKnownDocument().subscribe({
      next: () => {
        try {
          this.oidcSecurityService.authorize();
          console.log('Authorize method called successfully');
        } catch (error) {
          console.error('Error calling authorize:', error);
        }
      },
      error: (err) => {
        console.error('OIDC metadata preload failed:', err);
      }
    });
  }

  logout(): void {
    this.oidcSecurityService
      .logoff()
      .subscribe((result) => console.log(result));
  }
}
