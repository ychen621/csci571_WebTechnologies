import { Component, Input, OnInit } from '@angular/core';

import { Detail } from '../detail';
import { Artworks } from '../artworks';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-tabs',
  templateUrl: './tabs.component.html',
  styleUrls: ['./tabs.component.css']
})
export class TabsComponent implements OnInit {

  @Input() selectedID: string = "";
  @Input() artistInfo!: Detail;
  @Input() targetWork: Artworks[] = [];
  @Input() showNoArtwork: boolean = false;
  @Input() showTab: boolean = false;

  showInfo: boolean = true;
  showArtworks: boolean = false;

  constructor(private backend: BackendService) { }

  ngOnInit(): void {
  }

  selectInfo(): void{
    this.showArtworks = false;
    this.showInfo = true;
  }
  selectWork(): void{
    this.showInfo = false;
    this.showArtworks = true;
  }
  
}
