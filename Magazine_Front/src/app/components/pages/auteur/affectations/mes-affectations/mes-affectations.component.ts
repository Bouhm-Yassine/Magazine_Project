import {Component, OnInit, ViewChild} from '@angular/core';
import {animate, state, style, transition, trigger} from '@angular/animations';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {ManuscriteViewerComponent} from '../../../manuscrite-viewer/manuscrite-viewer.component';
import {ArticleService} from '../../../../../services/article.service';
import {BsModalRef, BsModalService} from 'ngx-bootstrap/modal';
import {EvaluationComponent} from '../../evaluation/evaluation/evaluation.component';

@Component({
  selector: 'app-mes-affectations',
  templateUrl: './mes-affectations.component.html',
  styleUrls: ['./mes-affectations.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class MesAffectationsComponent implements OnInit {

  bsModalRef: BsModalRef;
  searchWord: any;
  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['id', 'titre', 'affiliation', 'etat', 'decision', 'actions'];
  expandedElement: any;
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;
  // @ts-ignore
  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(public articleService: ArticleService, private modalService: BsModalService) { }

  onFilter() {
    this.listData.filter = this.searchWord.trim().toLowerCase();
  }

  onSearchClear() {
    this.searchWord = '';
    this.onFilter();
  }

  onEvaluer(aff) {
    this.articleService.myAffectation = aff;
    this.bsModalRef = this.modalService.show(EvaluationComponent, {
      backdrop: 'static',
      keyboard: true,
      animated: true,
      ignoreBackdropClick: true,
      class: 'gray modal-dialog-centered modal-lg'});
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

  doGetMesAffectations() {
    this.articleService.getMesAffectations().subscribe(
      data => {
        console.log('Affectations');
        console.log(data);
        // @ts-ignore
        this.listData = new MatTableDataSource<any>(data);
        this.listData.sort = this.sort;
        this.listData.paginator = this.paginator;
      }, error => console.log(error)
    );
  }

  ngOnInit() {
    this.doGetMesAffectations();
  }

}
