package com.luojia.java.ai.langchain4j.tools;

import com.luojia.java.ai.langchain4j.entity.Appointment;
import com.luojia.java.ai.langchain4j.service.AppointmentService;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AppointmentTools {

    @Autowired
    private AppointmentService appointmentService;

//    @Tool(name = "预约挂号", value = "根据参数，先执行工具方法 queryAppointment 查询是否可预约，并直接给用户回答是否可预约，并让用户确认所有预约信息，用户确认后再进行预约")
    @Tool(name = "预约挂号", value = "根据参数，先执行工具方法 queryAppointment 查询是否可预约，并直接给用户回答是否可预约，将字段填充到Appointment对象中，true 执行工具方法 bookAppointment；false不可预约")
    public String bookAppointment(@P(value = "姓名") String username,
                                  @P(value = "科室名称") String department,
                                  @P(value = "身份证") String idCard,
                                  @P(value = "日期") String date,
                                  @P(value = "时间，可选值；上午，下午") String time,
                                  @P(value = "医生名称", required = false) String doctorName) {
        Appointment appointment = new Appointment();
        appointment.setUsername(username);
        appointment.setDepartment(department);
        appointment.setIdCard(idCard);
        appointment.setDate(date);
        appointment.setTime(time);
        appointment.setDoctorName(doctorName);
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB == null) {
            appointment.setId(null);// 防止大模型幻觉设置了id
            if (appointmentService.save(appointment)) {
                return "预约成功,并返回预约详情";
            }else {
                return "预约失败";
            }
        }
        return "您在想同的科室和时间已有预约";
    }

    @Tool(name = "取消预约挂号", value = "根据参数，查询预约是否存在，如果存在则删除预约记录并返回取消预约成功，否则返回取消预约失败")
    public String cancelAppointment(Appointment appointment) {
        Appointment appointmentDB = appointmentService.getOne(appointment);
        if (appointmentDB != null) {
            if (appointmentService.removeById(appointment)) {
                return "取消预约成功";
            }else {
                return "取消预约失败";
            }
        }
        return "您没有预约记录，请核对预约科室和时间";
    }

    @Tool(name = "查询是否有号源", value = "根据科室名称、日期、时间和医生查询是否有号源、并返回给用户")
    public boolean queryAppointment(
            @P(value = "科室名称") String name,
            @P(value = "日期") String date,
            @P(value = "时间，可选值；上午，下午") String time,
            @P(value = "医生名称", required = false) String doctorName
    ) {
        log.info("查询是否有号源，科室名称：{}，日期：{}，时间：{}，医生名称：{}", name, date, time, doctorName);

        // todo 维护医生的排班信息
        return true;
    }

}
