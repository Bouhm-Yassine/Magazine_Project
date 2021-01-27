import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ArticleService} from '../../../../services/article.service';
import {MatConfirmDialogService} from '../../../../services/mat-confirm-dialog.service';
import {AffectationComponent} from '../affectation/affectation.component';
import {ManuscriteViewerComponent} from '../../manuscrite-viewer/manuscrite-viewer.component';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {DetailsEvaluationComponent} from '../../auteur/articles/details-evaluation/details-evaluation.component';
import {EvaluationComponent} from '../../auteur/evaluation/evaluation/evaluation.component';
import {EvaluationComiteComponent} from '../evaluation-comite/evaluation-comite.component';
import {DetailsEvaluationComiteComponent} from '../evaluation/details-evaluation-comite/details-evaluation-comite.component';

@Component({
  selector: 'app-articles-en-cours',
  templateUrl: './articles-en-cours.component.html',
  styleUrls: ['./articles-en-cours.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ArticlesEnCoursComponent implements OnInit {

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

  onDetailsEvaluation(article) {
    this.articleService.myArticle = article;
    this.bsModalRef = this.modalService.show(DetailsEvaluationComiteComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'}
    );
  }

  onEvalue(article) {
    this.articleService.myArticle = article;
    this.bsModalRef = this.modalService.show(EvaluationComiteComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'});
  }

  doGetArticlesEnCours() {
    this.articleService.getArticlesEnCours().subscribe(
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
    this.articleService.getRefresh().subscribe(() => {
      this.doGetArticlesEnCours();
    });

    this.doGetArticlesEnCours();
  }

}
