import { Component, OnInit } from '@angular/core';
import {ArticleService} from '../../../../../services/article.service';
import {IntervenantService} from '../../../../../services/intervenant.service';
import {Article} from '../../../../../Models/Article';
import {HttpClient} from '@angular/common/http';

@Component({
  selector: 'app-add-article',
  templateUrl: './add-article.component.html',
  styleUrls: ['./add-article.component.scss']
})
export class AddArticleComponent implements OnInit {

  selectedFile: File;
  step = 0;
  listAuteur;
  article: Article = new Article();
  newArticle: any;
  listMotCle: any[] = new Array<any>();

  constructor(public articleService: ArticleService, public intervenantService: IntervenantService, private httpClient: HttpClient) { }

  public onFileChanged(event) {
    this.selectedFile = event.target.files[0];
  }

  onSaveDeposition() {
    this.articleService.saveDeposition(this.articleService.form.get('coAuteur').value,
      this.articleService.form.get('correspondant').value.id,
      this.newArticle.id).subscribe(
      res => {
        console.log('2');
      }, error => console.log(error)
    );
  }

  onSaveMotCle() {
    this.articleService.saveMotCle(this.listMotCle, this.newArticle.id).subscribe(
      response => {
        console.log('3');
      }, error => console.log(error)
    );
  }

  onUpload(idArticle) {
    console.log('Fille');
    console.log(this.selectedFile);
    // FormData API provides methods and properties to allow us easily prepare form data to be sent with POST HTTP requests.
    const uploadFileData = new FormData();
    uploadFileData.append('manuscriteFile', this.selectedFile, this.selectedFile.name);

    this.articleService.onUpload(idArticle, uploadFileData).subscribe((response) => {
      if (response.status === 200) {
        console.log('File uploaded successfully');
      } else {
        console.log('File not uploaded successfully');
      }
    });
  }

  onAddMotCle() {
    this.listMotCle.push(this.articleService.form.get('motCle').value);
    this.articleService.form.get('motCle').setValue(null);
  }

  onSave() {
    this.article.titre = this.articleService.form.get('titre').value;
    this.article.resume = this.articleService.form.get('resume').value;
    this.article.affiliation = this.articleService.form.get('affiliation').value;

    console.log(this.articleService.form.value);

    this.articleService.saveArticle(this.article).subscribe(
      data => {
        console.log('1');
        this.newArticle = data;

        this.onSaveDeposition();

        this.onUpload(this.newArticle.id);

        this.onSaveMotCle();

        this.articleService.resetForm();
        this.listMotCle = new Array<any>();
        this.selectedFile = null;

      }, error => {
        console.log(error);
      });
    console.log('4');
  }

  onReset() {
    this.articleService.resetForm();
  }

  doGetAuteurs() {
    this.intervenantService.getAuteurs().subscribe(
      data => {
        console.log('Auteeeeeeeeeeeuuuuurs');
        console.log(data);
        this.listAuteur = data;
      }, error => console.log(error)
    );
  }

  ngOnInit() {
    this.doGetAuteurs();
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
