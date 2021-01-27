import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {Router} from '@angular/router';
import {ArticleService} from '../../../../../services/article.service';
import {ManuscriteViewerComponent} from '../../../manuscrite-viewer/manuscrite-viewer.component';
import {MatConfirmDialogService} from '../../../../../services/mat-confirm-dialog.service';
import {AddArticleComponent} from '../add-article/add-article.component';
import {UpdateArticleComponent} from '../update-article/update-article.component';
import {DetailsEvaluationComponent} from '../details-evaluation/details-evaluation.component';

@Component({
  selector: 'app-mes-articles',
  templateUrl: './mes-articles.component.html',
  styleUrls: ['./mes-articles.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class MesArticlesComponent implements OnInit {

  searchWord: any;
  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['id', 'titre', 'affiliation', 'etat', 'decision', 'actions'];
  expandedElement: any;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;


  constructor(private modalService: BsModalService, public articleService: ArticleService,
              public matConfirmDialogService: MatConfirmDialogService) { }

  bsModalRef: BsModalRef;

  onFilter() {
    this.listData.filter = this.searchWord.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchWord = '';
    this.onFilter();
  }

  onDelete(article) {
    this.matConfirmDialogService.openConfirmDialog().afterClosed().subscribe(
      res => {
        if (res) {
          this.articleService.deleteArticle(article.id).subscribe(
            data => {
              this.doGetMyArticles();
              }, error =>  {
              console.log(error);
            }
          );
        }
      }
    );
  }

  onDetailsEvaluation(article) {
    this.articleService.myArticle = article;
    this.bsModalRef = this.modalService.show(DetailsEvaluationComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'}
    );
  }

  onUpdate(article) {
    this.articleService.myArticle = article;
    this.articleService.UpdateForm(article);
    this.bsModalRef = this.modalService.show(UpdateArticleComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'}
    );
  }

  doGetMyArticles() {
    this.articleService.getArticleIntervenant().subscribe(
      data => {
        // @ts-ignore
        this.listData = new MatTableDataSource<any>(data);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      }, error => console.log(error)
    );
  }

  onManuscriteViewer(id) {
    this.articleService.idArticle = id;
    this.bsModalRef = this.modalService.show(ManuscriteViewerComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'});
  }

  ngOnInit() {
    this.doGetMyArticles();
  }

}
