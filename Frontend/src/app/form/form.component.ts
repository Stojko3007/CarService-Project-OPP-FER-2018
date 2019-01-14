import { Component, OnInit } from '@angular/core';
import { Mechanic } from '../mechanic';
import {Router} from '@angular/router';
import { AppointmentService } from '../appointment.service';
import { ServiceService } from '../service.service';
import { Service } from '../service';

@Component({
  selector: 'app-form',
  templateUrl: './form.component.html',
  styleUrls: ['./form.component.css']
})
export class FormComponent implements OnInit {

  mechanics:Mechanic[] = [];
  services: Service[] = [];
  appointments : string[] = [];
  clicked=false;
  mechanic=false;
  appointment=false;

  constructor( private router:Router , private appointmentService : AppointmentService, private servicesService : ServiceService) {
    this.router=router;
  }

  getAppointmets() {
    const appointments = this.appointmentService.getAllAppointments();
    appointments.subscribe((appointments) => {
      this.appointments = appointments;
    });
  }

  getServices() {
    const services = this.servicesService.getServices();
    services.subscribe((services) => {
      this.services = services;
    });
  }

  ngOnInit() {
    this.getAppointmets();
    this.getServices();
    console.log(this.appointments.length);
    this.mechanics = [{name:'Ante', surname:'Žužul', email:'ante.zuzul@mehanicar.hr', mobile:'0981234567', oib:'76113742199', password:'1234'},
    {name:'Karlo', surname:'Fruhwirth', email:'karlo.fruhwirth@mehanicar.hr', mobile:'0981234566', oib:'76113742198', password:'1234'},
    {name:'Marko', surname:'Jelović', email:'marko.jelovic@mehanicar.hr', mobile:'0981234565', oib:'76113742197', password:'1234'},
    {name:'Filip', surname:'Ostojić', email:'filip.ostojic@mehanicar.hr', mobile:'0981234564', oib:'76113742196', password:'1234'},
    {name:'Jozo', surname:'Ćaćić', email:'jozo.cacic@mehanicar.hr', mobile:'0981234563', oib:'76113742195', password:'1234'},
    {name:'Nikolina', surname:'Mijoc', email:'nikolina.mijoc@mehanicar.hr', mobile:'0981234562', oib:'76113742194', password:'1234'}];
  }


  showMechanics(){

    if(this.clicked != true){  
      this.clicked = !this.clicked
    }
    this.mechanic = !this.mechanic
  }

  chooseAppoitment(){

    if(this.clicked != true){  
       this.clicked = !this.clicked
    }
    this.appointment = !this.appointment
    this.mechanic = false
  }

  finished(){

    this.clicked=false;
    this.mechanic=false;
    this.appointment=false; 

    this.router.navigateByUrl('/pocetna');
  }

}
