package com.luojia.java.ai.langchain4j;

import com.luojia.java.ai.langchain4j.entity.Appointment;
import com.luojia.java.ai.langchain4j.service.AppointmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AppointmentServiceTest {

    @Autowired
    private AppointmentService appointmentService;

    @Test
    void testGetOne() {
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789012345678");
        appointment.setDepartment("内科");
        appointment.setTime("上午");
        appointment.setDate("2025-07-01");
        appointment.setDoctorName("张医生");
        Appointment one = appointmentService.getOne(appointment);
        System.out.println(one);
    }

    @Test
    void testSave() {
        Appointment appointment = new Appointment();
        appointment.setUsername("张三");
        appointment.setIdCard("123456789012345678");
        appointment.setDepartment("内科");
        appointment.setTime("上午");
        appointment.setDate("2025-07-01");
        appointment.setDoctorName("张医生");
        boolean save = appointmentService.save(appointment);
        System.out.println(save);
    }

    @Test
    void testRemoveById() {
        boolean save = appointmentService.removeById(1);
        System.out.println(save);
    }

}
