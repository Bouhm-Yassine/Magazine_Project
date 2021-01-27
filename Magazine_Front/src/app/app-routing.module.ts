import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import {LoginComponent} from './components/shared/login/login.component';
import {RegisterComponent} from './components/shared/register/register.component';
import {AddArticleComponent} from './components/pages/auteur/articles/add-article/add-article.component';
import {MesArticlesComponent} from './components/pages/auteur/articles/mes-articles/mes-articles.component';
import {MesAffectationsComponent} from './components/pages/auteur/affectations/mes-affectations/mes-affectations.component';
import {ArticlesTabsComponent} from './components/pages/comite/articles-tabs/articles-tabs.component';
import {ArticlesComponent} from './components/pages/articles/articles.component';
import {AuthGuardService} from './services/auth-guard.service';


const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'register', component: RegisterComponent},
  { path: 'articles', component: ArticlesComponent},
  { path: 'ajouterArticle', component: AddArticleComponent, canActivate: [AuthGuardService]},
  { path: 'mesArticles', component: MesArticlesComponent, canActivate: [AuthGuardService]},
  { path: 'mesAffectations', component: MesAffectationsComponent, canActivate: [AuthGuardService]},
  { path: 'comiteArticles', component: ArticlesTabsComponent, canActivate: [AuthGuardService]},
  { path: '', redirectTo: 'articles', pathMatch: 'full'},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
