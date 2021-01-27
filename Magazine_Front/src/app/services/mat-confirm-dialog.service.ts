import { Injectable } from '@angular/core';
import {MatDialog} from '@angular/material';
import {DialogConfirmComponent} from '../components/pages/dialog-confirm/dialog-confirm.component';

@Injectable({
  providedIn: 'root'
})
export class MatConfirmDialogService {

  constructor(private dialog: MatDialog) { }

  openConfirmDialog() {
    return this.dialog.open(DialogConfirmComponent, {
      width: '400px',
      panelClass: 'confirm-dialog-container',
      position: { top: '200px' },
      disableClose: true
    });
  }
}
