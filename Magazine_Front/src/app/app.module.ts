import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NoopAnimationsModule } from '@angular/platform-browser/animations';
import { HomeComponent } from './components/shared/home/home.component';
import {NavbarComponent} from './components/shared/navbar/navbar.component';
import {MaterialModule} from './material/material.module';
import { LoginComponent } from './components/shared/login/login.component';
import { RegisterComponent } from './components/shared/register/register.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import { AddArticleComponent } from './components/pages/auteur/articles/add-article/add-article.component';
import {PdfViewerModule} from 'ng2-pdf-viewer';
import { ManuscriteViewerComponent } from './components/pages/manuscrite-viewer/manuscrite-viewer.component';
import {BsModalRef, ModalModule} from 'ngx-bootstrap/modal';
import { MesArticlesComponent } from './components/pages/auteur/articles/mes-articles/mes-articles.component';
import { DialogConfirmComponent } from './components/pages/dialog-confirm/dialog-confirm.component';
import { UpdateArticleComponent } from './components/pages/auteur/articles/update-article/update-article.component';
import { DetailsEvaluationComponent } from './components/pages/auteur/articles/details-evaluation/details-evaluation.component';
import { AffectationComponent } from './components/pages/comite/affectation/affectation.component';
import { ArticlesEnAttenteComponent } from './components/pages/comite/articles-en-attente/articles-en-attente.component';
import { MesAffectationsComponent } from './components/pages/auteur/affectations/mes-affectations/mes-affectations.component';
import { EvaluationComponent } from './components/pages/auteur/evaluation/evaluation/evaluation.component';
import { ArticlesEnCoursComponent } from './components/pages/comite/articles-en-cours/articles-en-cours.component';
import { EvaluationComiteComponent } from './components/pages/comite/evaluation-comite/evaluation-comite.component';
// tslint:disable-next-line:max-line-length
import { DetailsEvaluationComiteComponent } from './components/pages/comite/evaluation/details-evaluation-comite/details-evaluation-comite.component';
import { ArticlesTabsComponent } from './components/pages/comite/articles-tabs/articles-tabs.component';
import {ArticlesComponent} from './components/pages/articles/articles.component';

@NgModule({
  declarations: [
    ArticlesComponent,
    AppComponent,
    HomeComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    AddArticleComponent,
    ManuscriteViewerComponent,
    MesArticlesComponent,
    DialogConfirmComponent,
    UpdateArticleComponent,
    DetailsEvaluationComponent,
    AffectationComponent,
    ArticlesEnAttenteComponent,
    MesAffectationsComponent,
    EvaluationComponent,
    ArticlesEnCoursComponent,
    EvaluationComiteComponent,
    DetailsEvaluationComiteComponent,
    ArticlesTabsComponent
  ],
  imports: [
    ModalModule.forRoot(),
    BrowserModule,
    AppRoutingModule,
    NoopAnimationsModule,
    MaterialModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    PdfViewerModule
  ],
  providers: [BsModalRef],
  entryComponents: [ManuscriteViewerComponent, DialogConfirmComponent, UpdateArticleComponent, DetailsEvaluationComponent,
    EvaluationComponent, AffectationComponent, EvaluationComiteComponent, DetailsEvaluationComiteComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
