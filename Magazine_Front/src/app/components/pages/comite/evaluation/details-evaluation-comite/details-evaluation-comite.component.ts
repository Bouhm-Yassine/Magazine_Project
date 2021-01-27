import {Component, OnInit, ViewChild} from '@angular/core';
import {MatSort, MatTableDataSource} from '@angular/material';
import {ArticleService} from '../../../../../services/article.service';

@Component({
  selector: 'app-details-evaluation-comite',
  templateUrl: './details-evaluation-comite.component.html',
  styleUrls: ['./details-evaluation-comite.component.scss']
})
export class DetailsEvaluationComiteComponent implements OnInit {

  listData: MatTableDataSource<any>;
  displayedColumns: string[] = ['juge', 'date', 'decision', 'commentaire'];
  // @ts-ignore
  @ViewChild(MatSort) sort: MatSort;

  constructor(public articleService: ArticleService) { }

  doGetAffectation() {
    this.articleService.getAffectationsArticle(this.articleService.myArticle.id).subscribe(
      data => {
        // @ts-ignore
        this.listData = new MatTableDataSource<any>(data);
        this.listData.sort = this.sort;
      }, error => {
        console.log(error);
      });
  }

  ngOnInit() {
    console.log(this.articleService.myArticle);
    this.doGetAffectation();
  }

}
