import { Component, OnInit } from '@angular/core';
import {ArticleService} from '../../../../../services/article.service';
import {BsModalRef} from 'ngx-bootstrap/modal';

@Component({
  selector: 'app-update-article',
  templateUrl: './update-article.component.html',
  styleUrls: ['./update-article.component.scss']
})
export class UpdateArticleComponent implements OnInit {

  step = 0;
  myArticle;

  constructor(public articleService: ArticleService, private bsModalRef: BsModalRef) { }

  onUpdate() {
    this.myArticle.titre = this.articleService.updateForm.get('titre').value;
    this.myArticle.affiliation = this.articleService.updateForm.get('affiliation').value;
    this.myArticle.resume = this.articleService.updateForm.get('resume').value;

    this.articleService.updateArticle(this.myArticle).subscribe(
      data => {
        console.log(data);
      }, error => console.log(error)
    );
    this.articleService.resetForm();
    this.bsModalRef.hide();
  }

  ngOnInit() {
    this.myArticle = this.articleService.myArticle;
  }

  setStep(index: number) {
    this.step = index;
  }

  nextStep() {
    this.step++;
  }

  prevStep() {
    this.step--;
  }

}
