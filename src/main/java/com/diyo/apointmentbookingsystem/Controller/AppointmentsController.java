package com.diyo.apointmentbookingsystem.Controller;

import com.diyo.apointmentbookingsystem.Entity.Appointments;
import com.diyo.apointmentbookingsystem.Service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/appointments")
public class AppointmentsController {

    private final AppointmentsService appointmentsService;


    public AppointmentsController(AppointmentsService appointmentsService) {
        this.appointmentsService = appointmentsService;
    }

    @PostMapping
    public ResponseEntity<String> createAppointment(@RequestBody Appointments appointments) {
        appointmentsService.createAppointment(appointments);
        return ResponseEntity.ok("Appointment Created");
    }

    @GetMapping
    public ResponseEntity<List<Appointments>> getAllAppointments() {
        List<Appointments> allAppointments = appointmentsService.getAllAppointments();
        return ResponseEntity.ok(allAppointments);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointments> getAppointmentsById(@PathVariable Long id) {
        Appointments appointment = appointmentsService.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointments(@PathVariable Long id) {
        appointmentsService.deleteAppointment(id);
        return ResponseEntity.ok("Appointment Deleted");
    }

    @PostMapping("/multiple")
    public ResponseEntity<String> createMultipleAppointment(@RequestBody List<Appointments> appointments) {
        appointmentsService.saveAllAppointment(appointments);
        return ResponseEntity.ok("All Appointments Saved!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAppointment(@PathVariable Long id, @RequestBody Appointments updatedAppointment) {
        appointmentsService.editAppointment(id, updatedAppointment);
        return ResponseEntity.ok("Appointment Edited");
    }
    @PatchMapping("/{id}")
    public ResponseEntity<String> partialUpdateAppointment(@PathVariable Long id, @RequestBody Appointments updatedAppointment){
        appointmentsService.partialUpdateAppointment(id, updatedAppointment);
        return ResponseEntity.ok("Appointment Updated");
    }

}
