package com.example.doctorcare.api.event.listener;

import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import com.example.doctorcare.api.event.OnSendAppointmentInfoEvent;
import com.example.doctorcare.api.service.EmailAppointmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendAppointmentInfoListener {

    @Autowired
    private EmailAppointmentInfoService emailAppointmentInfoService;

    @Async
    @EventListener
    public void sendAppointmentInfo(OnSendAppointmentInfoEvent event){
        emailAppointmentInfoService.sendAppointmentSuccessInfo(event.getUsername(), event.getAppointmentInfoForUser());
    }

}
