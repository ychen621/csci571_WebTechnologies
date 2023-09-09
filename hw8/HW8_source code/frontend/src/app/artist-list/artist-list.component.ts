import { Component, Input, OnInit } from '@angular/core';

import { artist } from '../artist-list';
import { Detail } from '../detail';
import { Artworks } from '../artworks';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-artist-list',
  templateUrl: './artist-list.component.html',
  styleUrls: ['./artist-list.component.css']
})
export class ArtistListComponent implements OnInit {
  
  @Input() artistName: string = "";
  @Input() artistList!: artist[];
  @Input() showNotFound: boolean = false;

  selectedArtist?: artist;
  selectedID: string = "";
  showTab: boolean = false;

  artistInfo!: Detail;
  targetWork: Artworks[] = [];
  loading: boolean = false;
  showNoArtwork: boolean = false;


  constructor(private backend: BackendService) { }

  ngOnInit(): void {
    
  }

  getDetail(): void{
    this.loading = true;
    var processedID = this.selectedID.substring(34);
    this.backend.getDetail(processedID).subscribe(res => {this.artistInfo = res;
    this.loading = false;});
  }

  getWork(): void{
    var processedID = this.selectedID.substring(34);
    this.backend.getArtwork(processedID).subscribe(res => {this.targetWork = res;
    if(res.length == 0){this.showNoArtwork = true;}});
  }

  onSelect(artist: artist): void{
    this.selectedArtist = artist;
    this.selectedID = this.selectedArtist.id;
    this.getDetail();
    this.getWork();
    this.showTab = true;
  }

}
