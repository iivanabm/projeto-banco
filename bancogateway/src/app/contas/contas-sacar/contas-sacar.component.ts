import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute, Params } from "@angular/router";
import { Observable } from 'rxjs';
import { ContasService } from 'src/app/contas.service';
import { Conta } from '../conta';

@Component({
  selector: 'app-contas-sacar',
  templateUrl: './contas-sacar.component.html',
  styleUrls: ['./contas-sacar.component.css']
})
export class ContasSacarComponent implements OnInit {
  conta: Conta;
  valor: number;
  success: boolean = false;
  errors: String[];

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
            errorResponse => this.conta = new Conta()
          )
      }
    })
  }

  voltarParaLista() {
    this.router.navigate(["/contas-lista"])
  }

  onSubmit() {
    if (this.conta.numero && this.valor) {
      this.service.sacar(this.conta, this.valor)
        .subscribe(response => {
          this.success = true;
          this.errors = null;
        }, errorResponse => {
          this.errors = errorResponse.errors.error;
        })

    } else {
      this.service.encontrarConta(this.conta.numero)
        .subscribe(response => {
          this.success = true;
          this.errors = null;
          this.conta = response
        }, errorResponse => {
          this.success = false;
          this.errors = errorResponse.errors.error
        })
    }
  }
}
