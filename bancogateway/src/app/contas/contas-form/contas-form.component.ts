import { Component, OnInit } from '@angular/core';
import { Conta } from "../conta";
import { ContasService } from "../../contas.service";
import { Router } from "@angular/router";

@Component({
  selector: 'app-contas-form',
  templateUrl: './contas-form.component.html',
  styleUrls: ['./contas-form.component.css']
})
export class ContasFormComponent implements OnInit {

  conta: Conta;
  success: boolean = false;
  errors: String[];

  constructor(
    private service: ContasService,
    private router: Router,

  ) {
    this.conta = new Conta();
  }

  ngOnInit(): void {

  }

  voltarParaLista() {
    this.router.navigate(["/contas-lista"])
  }

  onSubmit() {
    this.service
      .criarConta(this.conta)
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
