import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {ArticleService} from '../../../../services/article.service';
import {MatConfirmDialogService} from '../../../../services/mat-confirm-dialog.service';
import {ManuscriteViewerComponent} from '../../manuscrite-viewer/manuscrite-viewer.component';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {AffectationComponent} from '../affectation/affectation.component';

@Component({
  selector: 'app-articles-en-attente',
  templateUrl: './articles-en-attente.component.html',
  styleUrls: ['./articles-en-attente.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class ArticlesEnAttenteComponent implements OnInit {

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

  doGetArticlesEnAttente() {
    this.articleService.getArticlesEnAttente().subscribe(
      data => {
        // @ts-ignore
        this.listData = new MatTableDataSource<any>(data);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      }, error => console.log(error)
    );
  }

  onAdresse(article: any) {
    this.articleService.myArticle = article;
    this.bsModalRef = this.modalService.show(AffectationComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'});
  }

  onRefuse(article: any) {
    this.matConfirmDialogService.openConfirmDialog().afterClosed().subscribe(
      res => {
        if (res) {
          this.articleService.refuserArticle(article).subscribe(
            data => {
              this.doGetArticlesEnAttente();
            }, error =>  {
              console.log(error);
            }
          );
        }
      }
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
      this.doGetArticlesEnAttente();
    });

    this.doGetArticlesEnAttente();
  }

}
