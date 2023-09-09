import { Component, OnInit, Input } from '@angular/core';

import { Detail } from '../detail';
@Component({
  selector: 'app-detail',
  templateUrl: './detail.component.html',
  styleUrls: ['./detail.component.css']
})
export class DetailComponent implements OnInit {

  @Input() artistInfo!: Detail;
  @Input() showInfo: boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

}
