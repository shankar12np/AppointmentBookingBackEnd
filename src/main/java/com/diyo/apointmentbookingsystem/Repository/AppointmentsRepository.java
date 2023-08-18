package com.diyo.apointmentbookingsystem.Repository;

import com.diyo.apointmentbookingsystem.Entity.Appointments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentsRepository extends JpaRepository <Appointments, Long> {

}
