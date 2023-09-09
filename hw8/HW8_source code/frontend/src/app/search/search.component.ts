import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';

//import { Observable } from 'rxjs';

import { BackendService } from '../backend.service';
import { artist } from '../artist-list';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  
  artistName: string = "";
  artistList: artist[] = [];
  showSpinner: boolean = false;
  showNotFound: boolean = false;


  constructor(
    private router: Router,
    private backend: BackendService
   ) { }

   ngOnInit(): void {
    
   }

   onSubmit(inputName: string): void{
    var processedName = inputName.replace(/ /g, '%20');
    this.artistName = processedName;
    this.showSpinner = true;
    this.backend.getSearchList(this.artistName).subscribe(res => {this.artistList = res;
      this.showSpinner = false;
      if(res.length == 0){this.showNotFound = true;}});
  }

  reloadPage() {
    window.location.reload();
  }
}
