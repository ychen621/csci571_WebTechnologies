import { Component, Input, OnInit } from '@angular/core';
import { BackendService } from '../backend.service';

@Component({
  selector: 'app-genes',
  templateUrl: './genes.component.html',
  styleUrls: ['./genes.component.css']
})
export class GenesComponent implements OnInit {

  @Input() geneID: string = "";

  constructor() { }

  ngOnInit(): void {
  }

}
