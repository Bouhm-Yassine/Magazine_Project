import { Component, OnInit } from '@angular/core';
import {UtilisateurService} from '../../../services/utilisateur.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  error = 0;
  constructor(public utilisateurService: UtilisateurService, public router: Router) { }

  onRegister() {
    console.log(this.utilisateurService.registerForm.value);
    this.utilisateurService.register(this.utilisateurService.registerForm.value).subscribe(
      res => {
        console.log(res);
        this.router.navigateByUrl('/login');
        this.utilisateurService.resetRegisterForm();
      }, error => {
        this.error = 1;
        this.utilisateurService.registerForm.get('username').setValue('');
        this.utilisateurService.registerForm.get('password').setValue('');
        this.utilisateurService.registerForm.get('repassword').setValue('');
      });
  }

  ngOnInit() {
  }

}
