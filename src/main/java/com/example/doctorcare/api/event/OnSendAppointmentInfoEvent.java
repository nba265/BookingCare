package com.example.doctorcare.api.event;

import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class OnSendAppointmentInfoEvent extends ApplicationEvent {

    private String username;

    private AppointmentInfoForUser appointmentInfoForUser;

    public OnSendAppointmentInfoEvent(Object source, String username, AppointmentInfoForUser appointmentInfoForUser) {
        super(source);
        this.username = username;
        this.appointmentInfoForUser = appointmentInfoForUser;
    }
}
