import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { ContasService } from 'src/app/contas.service';
import { Conta } from "../conta"

@Component({
  selector: 'app-contas-lista',
  templateUrl: './contas-lista.component.html',
  styleUrls: ['./contas-lista.component.css']
})
export class ContasListaComponent implements OnInit {

  contas: Conta[] = [];

  constructor(
    private service: ContasService,
    private router: Router) { }

  ngOnInit(): void {
    this.service.listarContas()
      .subscribe(resposta => {
        this.contas = resposta
      });
  }

  novoCadastro() {
    this.router.navigate(["/contas-form"])
  }

  sacar(conta: Conta, valor: number) {
    this.router.navigate(["/conta/sacar"])
  }

  depositar(numero: number, valor: number) {
    this.router.navigate(["/conta/depositar"])
  }

  transferir(conta: Conta) {
    this.router.navigate(["/conta/transferir"])
  }

}
