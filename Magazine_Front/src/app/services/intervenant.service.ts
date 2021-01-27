import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpParams, HttpHeaders} from '@angular/common/http';
import {throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class IntervenantService {
  host = 'http://localhost:8080';

  constructor(public http: HttpClient, public authService: AuthService) {}

  errorHandler(errorResponse: HttpErrorResponse) {
    let errorMessage = '';
    if (errorResponse.error instanceof ErrorEvent) {
      errorMessage = 'Error Client Side:\n${errorResponse.not-found.message}';
    } else {
      errorMessage = `Error Server Side:\nCode: ${errorResponse.status}\nMessage: ${errorResponse.message}`;
    }
    return throwError(errorMessage);
  }

  getAuteurs() {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get(this.host + '/intervenants/auteurs', {headers: new HttpHeaders({Authorization: this.authService.token})}
    ).pipe(
      catchError(this.errorHandler)
    );
  }

  getJuges(article: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    const param = new HttpParams().set('id', String(article.id));
    return this.http.get(this.host + '/intervenants/juges', {params: param,
      headers: new HttpHeaders({Authorization: this.authService.token})}
    ).pipe(
      catchError(this.errorHandler)
    );
  }
}
