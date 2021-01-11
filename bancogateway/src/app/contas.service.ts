import { Injectable } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import { Conta } from './contas/conta';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ContasService {

  constructor(private http: HttpClient) {
  }

  criarConta(conta: Conta): Observable<Conta> {
    return this.http.post<Conta>("http://localhost:8080/conta", conta);
  }

  listarContas(): Observable<Conta[]> {
    return this.http.get<Conta[]>("http://localhost:8080/conta");
  }

  encontrarConta(numero: number): Observable<Conta> {
    return this.http.get<any>(`http://localhost:8080/conta/${numero}`);
  }

  sacar(conta: Conta, valor: number): Observable<Conta> {
    return this.http.put<any>(`http://localhost:8080/conta/sacar/${conta.numero}/${valor}`, conta);
  }

  depositar(conta: Conta, valor: number): Observable<Conta> {
    return this.http.put<any>(`http://localhost:8080/conta/depositar/${conta.numero}/${valor}`, conta);
  }

  transferir(conta: Conta): Observable<Conta> {
    return this.http.put<Conta>("http://localhost:8080/conta/transferir", conta);
  }

}

