package com.diyo.apointmentbookingsystem.Service;

import com.diyo.apointmentbookingsystem.Entity.Appointments;
import com.diyo.apointmentbookingsystem.Repository.AppointmentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentsService {

    private AppointmentsRepository appointmentsRepository;

    @Autowired
    public AppointmentsService(AppointmentsRepository appointmentsRepository) {
        this.appointmentsRepository = appointmentsRepository;
    }

    public Appointments createAppointment(Appointments appointments) {
        return appointmentsRepository.save(appointments);
    }

    public List<Appointments> getAllAppointments() {
        return appointmentsRepository.findAll();
    }

    public Appointments getAppointmentById(Long id) {
        return appointmentsRepository.findById(id).orElse(null);
    }

    public Appointments deleteAppointment(Long id) {
        appointmentsRepository.deleteById(id);
        return null;
    }

    public List<Appointments> saveAllAppointment(List<Appointments> appointmentsList) {
        appointmentsRepository.saveAll(appointmentsList);
        return null;
    }

    public void editAppointment(Long id, Appointments updatedAppointment) {
        Optional<Appointments> existingAppointmentOptional = appointmentsRepository.findById(id);

        if (existingAppointmentOptional.isPresent()) {
            Appointments existingAppointment = existingAppointmentOptional.get();

            // Update the properties of the existing appointment with the properties from the updatedAppointment
            existingAppointment.setAppointmentType(updatedAppointment.getAppointmentType());
            existingAppointment.setDate(updatedAppointment.getDate());
            existingAppointment.setTime(updatedAppointment.getTime());
            existingAppointment.setAddress(updatedAppointment.getAddress());
            existingAppointment.setPhone(updatedAppointment.getPhone());
            existingAppointment.setNote(updatedAppointment.getNote());

            // Save the updated appointment
            appointmentsRepository.save(existingAppointment);
        } else {
            // Handle the case when the appointment with the given id is not found
            throw new RuntimeException("Appointment not found with id: " + id);
        }
    }

    public void partialUpdateAppointment(Long id, Appointments updatedAppointment) {
        Optional<Appointments> existingAppointmentOptional = appointmentsRepository.findById(id);

        if (existingAppointmentOptional.isPresent()) {
            Appointments existingAppointment = existingAppointmentOptional.get();

            if (updatedAppointment.getAppointmentType() != null) {
                existingAppointment.setAppointmentType(updatedAppointment.getAppointmentType());
            }
            if (updatedAppointment.getDate() != null) {
                existingAppointment.setDate(updatedAppointment.getDate());
            }
            if (updatedAppointment.getTime() != null) {
                existingAppointment.setTime(updatedAppointment.getTime());
            }
            if (updatedAppointment.getAddress() != null) {
                existingAppointment.setAddress(updatedAppointment.getAddress());
            }
            if (updatedAppointment.getPhone() != null) {
                existingAppointment.setPhone(updatedAppointment.getPhone());
            }
            if (updatedAppointment.getNote() != null) {
                existingAppointment.setNote(updatedAppointment.getNote());
            }

            appointmentsRepository.save(existingAppointment);
        } else {
            throw new RuntimeException("Appointment not found id: " + id);
        }

       }

}
