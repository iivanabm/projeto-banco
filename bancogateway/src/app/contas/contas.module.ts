import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from "@angular/forms";
import { ContasRoutingModule } from './contas-routing.module';
import { ContasFormComponent } from './contas-form/contas-form.component';
import { ContasListaComponent } from './contas-lista/contas-lista.component';
import { ContasSacarComponent } from './contas-sacar/contas-sacar.component';
import { ContasDepositarComponent } from './contas-depositar/contas-depositar.component';
import { ContasTransferirComponent } from './contas-transferir/contas-transferir.component';


@NgModule({
  declarations: [ContasFormComponent, ContasListaComponent, ContasSacarComponent, ContasDepositarComponent, ContasTransferirComponent],
  imports: [
    CommonModule,
    ContasRoutingModule,
    FormsModule
  ], exports: [
    ContasFormComponent,
    ContasListaComponent,
    ContasSacarComponent,
    ContasDepositarComponent,
    ContasTransferirComponent
  ]
})
export class ContasModule { }
