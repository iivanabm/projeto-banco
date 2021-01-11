import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ContasService } from 'src/app/contas.service';
import { Conta } from '../conta';

@Component({
  selector: 'app-contas-transferir',
  templateUrl: './contas-transferir.component.html',
  styleUrls: ['./contas-transferir.component.css']
})
export class ContasTransferirComponent implements OnInit {

  conta: Conta;
  outraConta: Conta;
  success: boolean = false;
  errors: String[];

  constructor(
    private service: ContasService,
    private router: Router,
  ) {
    this.conta = new Conta();
    this.outraConta = new Conta();
  }

  ngOnInit(): void {

  }

  voltarParaLista() {
    this.router.navigate(["/contas-lista"])
  }

  onSubmit() {
    this.service
      .transferir(this.conta)
      .subscribe(response => {
        this.success = true;
        this.errors = null;
        this.conta = response;
      }, errorResponse => {
        this.success = false;
        this.errors = errorResponse.error.errors;
      })
  }

}
