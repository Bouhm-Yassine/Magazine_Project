import { Component, OnInit } from '@angular/core';
import {ArticleService} from '../../../../services/article.service';
import {BsModalRef} from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-evaluation-comite',
  templateUrl: './evaluation-comite.component.html',
  styleUrls: ['./evaluation-comite.component.scss']
})
export class EvaluationComiteComponent implements OnInit {

  decision: any;
  myArticle: any;

  constructor(public articleService: ArticleService, public bsModalRef: BsModalRef) { }

  onEvalue() {
    this.myArticle.decisionFinal = this.decision;
    this.myArticle.etat = 'traité';

    this.articleService.updateArticleDecision(this.myArticle).subscribe(
      data => console.log(data),
      error => console.log(error)
    );

    this.bsModalRef.hide();
  }

  ngOnInit() {
    this.myArticle = this.articleService.myArticle;
  }

}
