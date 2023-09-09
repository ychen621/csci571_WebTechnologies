import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { artist } from './artist-list';
import { Detail } from './detail';
import { Artworks } from './artworks';
import { Genes } from './genes';
//testinf
import { LIST } from './mock-list';
import { DETAIL } from './mock-detail';
import { ARTWORKS } from './mock-artwork';
import { GENES } from './mock-genes';

@Injectable({
  providedIn: 'root'
})
export class BackendService {

  constructor(private HttpClient: HttpClient) { }

  //for local test
  //private host_url = "http://localhost:3000";
  private host_url = "https://ashleyhw8project-571.wl.r.appspot.com";

  //testing
  getTestList(): Observable<artist[]>{
    const list = of(LIST)
    return list;
  }
  getTestDetail(): Observable<Detail>{
    const info = of(DETAIL)
    return info;
  }
  getTestArtworks(): Observable<Artworks[]>{
    const work = of(ARTWORKS)
    return work;
  }
  getTestGenes(): Observable<Genes[]>{
    const genes = of(GENES)
    return genes;
  }

  //real data catch
  getSearchList(name:string): Observable<artist[]> {
    let URL = this.host_url + "/search_list/" + name;
    return this.HttpClient.get<artist[]>(URL);
  }

  getDetail(id:string): Observable<Detail> {
    let URL = this.host_url + "/info/" + id;
    return this.HttpClient.get<Detail>(URL);
  }

  getArtwork(id:string): Observable<Artworks[]> {
    let URL = this.host_url + "/artworks/" + id;
    return this.HttpClient.get<Artworks[]>(URL);
  }

  getGenes(id:string): Observable<Genes[]> {
    let URL = this.host_url + "/genes/" + id;
    return this.HttpClient.get<Genes[]>(URL);
  }
}
