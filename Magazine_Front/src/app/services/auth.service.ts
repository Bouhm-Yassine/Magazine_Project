import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  token = null;
  intervenantID = null;
  roles: Array<any> = [];

  constructor(public http: HttpClient, public router: Router) {}

  form: FormGroup = new FormGroup({
    username: new FormControl(null, Validators.required),
    password: new FormControl(null, Validators.required),
  });

  initializeForm() {
    this.form.setValue({
      username: null,
      password: null,
    });
  }

  resetForm() {
    this.form.reset();
    this.initializeForm();
  }

  errorHandler(errorResponse: HttpErrorResponse) {
    let errorMessage = '';
    if (errorResponse.error instanceof ErrorEvent) {
      errorMessage = 'Error Client Side:\n${errorResponse.not-found.message}';
    } else {
      errorMessage = `Error Server Side:\nCode: ${errorResponse.status}\nMessage: ${errorResponse.message}`;
    }
    return throwError(errorMessage);
  }

  login(user: any) {
    // car login une action de spring security et il retourne pas un obj json du cout on va s interesser à la reponse http
    console.log(user);
    return this.http.post('http://localhost:8080/login', user, {observe: 'response'})
      .pipe(
        catchError(this.errorHandler)
      );
  }

  saveToken(token: string) {
    this.token = token;
    localStorage.setItem('token', token);

    const jwtHelper = new JwtHelperService();
    this.roles =  jwtHelper.decodeToken(this.token).roles;

    this.intervenantID = jwtHelper.decodeToken(this.token).intervenantId;
    localStorage.setItem('intervenantID', this.intervenantID);
  }

  loadToken() {
    this.token = localStorage.getItem('token');
    this.intervenantID = localStorage.getItem('intervenantID');
  }

  public isAuthenticated(): boolean {
    const token = localStorage.getItem('token');
    const jwtHelper = new JwtHelperService();
    return !jwtHelper.isTokenExpired(token);
  }

  isAdmin() {
    for (const r of this.roles) {
      if (r.authority === 'ADMIN') {
        return true;
      }
    }
    return false;
  }

  logout() {
    this.token = null;
    this.intervenantID = null;

    localStorage.removeItem('token');
    localStorage.removeItem('intervenantID');

    this.router.navigateByUrl('/login');

  }
}
