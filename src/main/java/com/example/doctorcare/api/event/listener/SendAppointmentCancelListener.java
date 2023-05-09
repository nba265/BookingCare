package com.example.doctorcare.api.event.listener;

import com.example.doctorcare.api.event.OnSendAppointmentCancelEvent;
import com.example.doctorcare.api.service.EmailAppointmentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class SendAppointmentCancelListener {

    @Autowired
    private EmailAppointmentInfoService emailAppointmentInfoService;

    @Async
    @EventListener
    public void cancelAppointmentInfo(OnSendAppointmentCancelEvent event){
        emailAppointmentInfoService.sendAppointmentCancelInfo(event.getUsername(), event.getAppointmentInfoForUser());
    }

}
