import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Params, Router } from "@angular/router";
import { Conta } from "../conta";
import { ContasService } from "../../contas.service";
import { Observable } from 'rxjs';

@Component({
  selector: 'app-contas-depositar',
  templateUrl: './contas-depositar.component.html',
  styleUrls: ['./contas-depositar.component.css']
})
export class ContasDepositarComponent implements OnInit {

  conta: Conta;
  numero: number;
  valor: number;
  success: boolean = false;
  errors: String[];
  saldo: number;

  constructor(
    private service: ContasService,
    private router: Router,
    private activatedRoute: ActivatedRoute
  ) {
    this.conta = new Conta();
  }

  ngOnInit(): void {
    let params: Observable<Params> = this.activatedRoute.params
    params.subscribe(urlParams => {
      this.conta.numero = urlParams["numero"];
      this.valor = urlParams["valor"];
      if (this.conta.numero && this.valor) {
        this.service.encontrarConta(this.conta.numero)
          .subscribe(response => this.conta = response,
            errorResponse => this.conta = new Conta())
      }
    })

  }

  voltarParaLista() {
    this.router.navigate(["/contas-lista"])
  }

  onSubmit() {
    if (this.conta.numero && this.valor) {
      this.service.depositar(this.conta, this.valor)
        .subscribe(response => {
          this.success = true;
          this.errors = null;
        }, errrorResponse => {
          this.errors = ["Depósito não realizado"]
        })

    } else {
      this.service.criarConta(this.conta)
        .subscribe(response => {
          this.success = true;
          this.errors = null;
          this.conta = response
        }, errorResponse => {
          this.success = false;
          this.errors = errorResponse.error.errors
        })
    }
  }

}
