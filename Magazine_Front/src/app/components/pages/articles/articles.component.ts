import {Component, OnInit, ViewChild} from '@angular/core';
import {ArticleService} from '../../../services/article.service';
import {MatPaginator, MatTableDataSource} from '@angular/material';
import {ManuscriteViewerComponent} from '../manuscrite-viewer/manuscrite-viewer.component';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {AuthService} from '../../../services/auth.service';

@Component({
  selector: 'app-articles',
  templateUrl: './articles.component.html',
  styleUrls: ['./articles.component.scss']
})
export class ArticlesComponent implements OnInit {

  listData: any;
  keyword;
  auteur;
  bsModalRef: BsModalRef;

  constructor(public authService: AuthService, public articleService: ArticleService, private modalService: BsModalService) { }

  doGetArticlesPublies() {
    this.articleService.getArticlesPublies().subscribe(
      data => {
        console.log(data);
        this.listData = data;
      },
      error => {
        console.log(error);
      });
  }

  onManuscriteViewer(idArticle) {
    this.articleService.idArticle = idArticle;
    this.bsModalRef = this.modalService.show(ManuscriteViewerComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'});
  }

  doGetArticleByKeywordAuteur() {
    this.articleService.getArticlesByKeywordAuteur(this.keyword, this.auteur).subscribe(
      data => {
        this.listData = data;
        this.auteur = '';
        this.keyword = '';
      }, error => {
        console.log(error);
      }
    );
  }

  ngOnInit() {
    this.doGetArticlesPublies();
  }

}
