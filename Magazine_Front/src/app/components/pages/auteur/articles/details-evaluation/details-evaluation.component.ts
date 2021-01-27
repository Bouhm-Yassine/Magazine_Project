import {Component, OnInit, ViewChild} from '@angular/core';
import {ArticleService} from '../../../../../services/article.service';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';

@Component({
  selector: 'app-details-evaluation',
  templateUrl: './details-evaluation.component.html',
  styleUrls: ['./details-evaluation.component.scss']
})
export class DetailsEvaluationComponent implements OnInit {

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
