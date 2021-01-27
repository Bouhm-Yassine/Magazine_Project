import { Injectable } from '@angular/core';
import {Subject, throwError} from 'rxjs';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {AuthService} from './auth.service';
import {catchError, tap} from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UtilisateurService {

  private refreshNeeded = new Subject<void>();

  constructor(public http: HttpClient) {}

  registerForm: FormGroup = new FormGroup({
    id: new FormControl(null),
    nom: new FormControl('', Validators.required),
    email: new FormControl('', Validators.required),
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
    repassword: new FormControl('', Validators.required),
  });

  getRefresh() {
    return this.refreshNeeded;
  }

  initializeRegisterForm() {
    this.registerForm.setValue({
      id: null,
      nom: '',
      email: '',
      username: '',
      password: '',
      repassword: '',
    });
  }

  resetRegisterForm() {
    this.registerForm.reset();
    this.initializeRegisterForm();
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

  register(user: any) {

    return this.http.post('http://localhost:8080/register', user).pipe(
        tap(() => {
          this.refreshNeeded.next();
        }),
        catchError(this.errorHandler)
      );
  }
}
