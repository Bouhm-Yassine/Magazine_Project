import { Component, OnInit } from '@angular/core';
import {ArticleService} from '../../../../services/article.service';
import {IntervenantService} from '../../../../services/intervenant.service';
import {BsModalRef} from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-affectation',
  templateUrl: './affectation.component.html',
  styleUrls: ['./affectation.component.scss']
})
export class AffectationComponent implements OnInit {

  juges: any;
  listJuges: any;
  article: any;

  constructor(public articlService: ArticleService, public intervenantService: IntervenantService, public bsModalRef: BsModalRef) { }

  adresser() {
  this.articlService.saveAffectationJuge(this.article, this.juges).subscribe(
    data => console.log(data),
    error => console.log(error)
  );
  this.bsModalRef.hide();
  }

  doGetListJuges(article: any) {
    this.intervenantService.getJuges(article).subscribe(
      data => {
        this.listJuges = data;
      }, error => console.log(error)
    );
  }

  ngOnInit() {
    this.article = this.articlService.myArticle;
    this.doGetListJuges(this.article);
  }

}
