import { Component, OnInit } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ArticleService} from '../../../services/article.service';

@Component({
  selector: 'app-manuscrite-viewer',
  templateUrl: './manuscrite-viewer.component.html',
  styleUrls: ['./manuscrite-viewer.component.scss']
})
export class ManuscriteViewerComponent implements OnInit {

  retrieveResonse: any;
  retrievedFile: any;
  constructor(public articleService: ArticleService) { }

  getFile(idArticle) {
    this.articleService.getFile(idArticle).subscribe(
      res => {
        console.log(res);
        this.retrieveResonse = res;
        this.retrievedFile = 'data:application/pdf;base64,' + this.retrieveResonse.manuscrite;
      }
    );
  }

  ngOnInit() {
    this.getFile(this.articleService.idArticle);
  }

}
