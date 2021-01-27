import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpEvent, HttpHeaders, HttpParams, HttpRequest} from '@angular/common/http';
import {Observable, Subject, throwError} from 'rxjs';
import {catchError, tap} from 'rxjs/operators';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class ArticleService {

  constructor(public http: HttpClient, public authService: AuthService) {}

  private refreshNeeded = new Subject<void>();
  host = 'http://localhost:8080';
  idArticle;
  myArticle;
  myAffectation;

  form: FormGroup = new FormGroup({
    id: new FormControl(null),
    titre: new FormControl('', Validators.required),
    affiliation: new FormControl(''),
    motCle: new FormControl(''),
    resume: new FormControl('',  Validators.required),
    correspondant: new FormControl(null),
    coAuteur: new FormControl(null)
  });

  updateForm: FormGroup = new FormGroup({
    id: new FormControl(null),
    titre: new FormControl('', Validators.required),
    affiliation: new FormControl('', Validators.required),
    resume: new FormControl('', Validators.required),
  });

  getRefresh() {
    return this.refreshNeeded;
  }

  UpdateForm(article: any) {
    this.updateForm.setValue({
      id: article.id,
      titre: article.titre,
      affiliation: article.affiliation,
      resume: article.resume
    });
  }

  initializeForm() {
    this.form.setValue({
      id: null,
      titre: null,
      affiliation: null,
      motCle: null,
      resume: null,
      correspondant: null,
      coAuteur: null
    });
  }

  resetForm() {
    this.form.reset();
    this.updateForm.reset();
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

  getArticlesPublies() {
    return this.http.get(this.host + '/articles/publies').pipe(
      catchError(this.errorHandler)
    );
  }

  getArticlesEnAttente() {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get(this.host + '/articles/enAttente', {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  getArticlesEnCours() {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get(this.host + '/articles/enCours', {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  getArticlesByKeywordAuteur(keyword, auteur) {
    const param = new HttpParams().set('keyword', String(keyword))
                                  .set('auteur', String(auteur));

    return this.http.get(this.host + '/articles/search', {params: param}).pipe(
      catchError(this.errorHandler)
    );
  }

  saveArticle(article: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.post(this.host + '/articles', article, {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  saveDeposition(coAuteur: any, idCorrespondant: any, idArticle: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    const param = new HttpParams().set('idCorrespondant', String(idCorrespondant))
                                  .set('idArticle', String(idArticle));

    return this.http.post(this.host + '/depositions', coAuteur, {params: param,
      headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  saveMotCle(listMotCle: any, idArticle: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.post(this.host + '/articles/' + idArticle + '/motCle', listMotCle,
      {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  onUpload(idArticle, uploadFileData: FormData) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.post(this.host + '/articles/' + idArticle + '/manuscrite', uploadFileData, { observe: 'response',
      headers: new HttpHeaders({Authorization: this.authService.token}) }).pipe(
      catchError(this.errorHandler)
    );
  }

  getFile(idArticle) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get('http://localhost:8080/articles/' + idArticle + '/manuscrite',
      {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  getArticleIntervenant() {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get('http://localhost:8080/intervenants/' + this.authService.intervenantID + '/articles',
      {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  getMesAffectations() {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get('http://localhost:8080/intervenants/' + this.authService.intervenantID + '/affectations',
      {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  getAffectationsArticle(idArticle: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.get('http://localhost:8080/articles/' + idArticle + '/affectations',
      {headers: new HttpHeaders({Authorization: this.authService.token})}).pipe(
      catchError(this.errorHandler)
    );
  }

  deleteArticle(id) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.delete('http://localhost:8080/articles/' + id,
      {headers: new HttpHeaders({Authorization: this.authService.token})})
      .pipe(
        catchError(this.errorHandler)
      );
  }

  updateArticle(article: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.put('http://localhost:8080/articles/' + article.id, article,
      {headers: new HttpHeaders({Authorization: this.authService.token})})
      .pipe(
        catchError(this.errorHandler)
      );
  }

  updateArticleDecision(article: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.put('http://localhost:8080/articles/' + article.id + '/decision', article,
      {headers: new HttpHeaders({Authorization: this.authService.token})})
      .pipe(
        tap(() => {
          this.refreshNeeded.next();
        }),
        catchError(this.errorHandler)
      );
  }

  updateAffectation(aff: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.put('http://localhost:8080/affectations/' + aff.id, aff,
      {headers: new HttpHeaders({Authorization: this.authService.token})})
      .pipe(
        catchError(this.errorHandler)
      );
  }

  saveAffectationJuge(article: any, listJuges: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.post('http://localhost:8080/articles/' + article.id + '/affecter', listJuges,
      {headers: new HttpHeaders({Authorization: this.authService.token})})
      .pipe(
        tap(() => {
          this.refreshNeeded.next();
        }),
        catchError(this.errorHandler)
      );
  }

  refuserArticle(article: any) {
    if (this.authService.token == null) { this.authService.loadToken(); }

    return this.http.post('http://localhost:8080/articles/' + article.id + '/refuser', article,
      {headers: new HttpHeaders({Authorization: this.authService.token})})
      .pipe(
        tap(() => {
          this.refreshNeeded.next();
        }),
        catchError(this.errorHandler)
      );
  }

}
