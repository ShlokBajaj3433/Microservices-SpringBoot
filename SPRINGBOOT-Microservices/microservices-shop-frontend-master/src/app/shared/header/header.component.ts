import {Component, DestroyRef, inject, OnInit} from '@angular/core';
import {OidcSecurityService} from "angular-auth-oidc-client";
import {takeUntilDestroyed} from "@angular/core/rxjs-interop";

@Component({
    selector: 'app-header',
    imports: [],
    templateUrl: './header.component.html',
    styleUrl: './header.component.css'
})
export class HeaderComponent implements OnInit {

  private readonly destroyRef = inject(DestroyRef);
  private readonly oidcSecurityService = inject(OidcSecurityService);
  isAuthenticated = false;
  username = "";
  menuOpen = false;

  toggleMenu(): void {
    this.menuOpen = !this.menuOpen;
  }

  ngOnInit(): void {
    this.oidcSecurityService.isAuthenticated$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
      ({isAuthenticated}) => {
        this.isAuthenticated = isAuthenticated;
      }
    );
    this.oidcSecurityService.userData$
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe(
      ({userData}) => {
        this.username = userData?.preferred_username ?? '';
      }
    );
  }

  login(): void {
    this.oidcSecurityService.preloadAuthWellKnownDocument().subscribe({
      next: () => {
        this.oidcSecurityService.authorize();
      },
      error: () => {
        this.isAuthenticated = false;
      }
    });
  }

  logout(): void {
    this.oidcSecurityService
      .logoff()
      .subscribe();
  }
}
