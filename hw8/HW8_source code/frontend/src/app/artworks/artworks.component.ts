import { Component, Input, OnInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';

import { BackendService } from '../backend.service';

import { Artworks } from '../artworks';
import { Genes } from '../genes';

@Component({
  selector: 'app-artworks',
  templateUrl: './artworks.component.html',
  styleUrls: ['./artworks.component.css']
})
export class ArtworksComponent implements OnInit {

  @Input() targetWork: Artworks[] = [];
  @Input() showArtworks: boolean = false;
  @Input() showNoArtwork: boolean = false;

  geneID: string = "";
  workName: string = "";
  workDate: string = "";
  workImage: string = "";
  geneContainer: Genes[] = [];
  closeResult = '';
  showNoCategory: boolean = false;
  test: string = "";
  loading: boolean = false;

  constructor(private modalService: NgbModal, private backend: BackendService) { }

  ngOnInit(): void {
  }

  open(content:any, target:Artworks) {
    this.geneID = target.id;
    this.workName = target.title;
    this.workDate = target.date;
    this.workImage = target.image;

    this.loading = true;

    this.backend.getGenes(this.geneID).subscribe(res => {this.geneContainer=res;
      this.loading = false;
      if(res.length == 0){this.showNoCategory = true;}});

    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', size: 'xl'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

}
