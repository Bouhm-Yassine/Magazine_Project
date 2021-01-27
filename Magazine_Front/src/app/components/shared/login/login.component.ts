import { Component, OnInit } from '@angular/core';
import {AuthService} from '../../../services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  error = 0;

  constructor(public authService: AuthService, public router: Router) { }

  onLogin() {
    if (this.authService.form.valid) {
      this.authService.login(this.authService.form.value).subscribe(
        res => {
          const token = res.headers.get('Authorization');

          this.authService.saveToken(token);

          if (this.authService.isAdmin()) {
            this.router.navigateByUrl('/comiteArticles');
          } else {
            this.router.navigateByUrl('/mesArticles');
          }

        }, error => {
          console.log(error);
          this.error = 1;
        });

      this.authService.resetForm();
    }
  }

  ngOnInit() {
  }

}
