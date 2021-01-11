import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ContasDepositarComponent } from './contas-depositar/contas-depositar.component';
import { ContasFormComponent } from "./contas-form/contas-form.component";
import { ContasListaComponent } from './contas-lista/contas-lista.component';
import { ContasSacarComponent } from './contas-sacar/contas-sacar.component';
import { ContasTransferirComponent } from './contas-transferir/contas-transferir.component';

const routes: Routes = [
  { path: "contas-form", component: ContasFormComponent },
  { path: "contas-lista", component: ContasListaComponent },
  { path: "conta/sacar/:numero/:valor", component: ContasSacarComponent },
  { path: "conta/depositar/:numero/:valor", component: ContasDepositarComponent },
  { path: "conta/transferir", component: ContasTransferirComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class ContasRoutingModule { }
