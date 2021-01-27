import { Component, OnInit } from '@angular/core';
import {ArticleService} from '../../../../../services/article.service';
import {BsModalRef} from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-evaluation',
  templateUrl: './evaluation.component.html',
  styleUrls: ['./evaluation.component.scss']
})
export class EvaluationComponent implements OnInit {

  decision: any;
  commentaire: any;
  myAff: any;

  constructor(public articleService: ArticleService, private bsModalRef: BsModalRef) { }

  onEvalue() {
    this.myAff.decision = this.decision;
    this.myAff.commentaire = this.commentaire;

    this.articleService.updateAffectation(this.myAff).subscribe(
      data => console.log(data),
      error => console.log(error)
    );
    this.bsModalRef.hide();
  }

  ngOnInit() {
    this.myAff = this.articleService.myAffectation;
  }

}
