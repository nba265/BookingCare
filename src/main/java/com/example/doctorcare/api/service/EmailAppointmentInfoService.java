package com.example.doctorcare.api.service;

import com.example.doctorcare.api.domain.dto.EmailDTO;
import com.example.doctorcare.api.domain.dto.response.AppointmentInfoForUser;
import com.example.doctorcare.api.domain.entity.UserEntity;
import com.example.doctorcare.api.exception.UserNotFoundException;
import com.example.doctorcare.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.context.IContext;

import javax.mail.MessagingException;
import java.util.List;

@Slf4j
@Service
public class EmailAppointmentInfoService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private MailService mailService;

    public void sendAppointmentSuccessInfo(String username, AppointmentInfoForUser appointment) {
        try {
            UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("Username with: " + username + " not found!"));

            String subject = "Your upcoming appointment on " + appointment.getAppointmentHistory().getDate() + " ["
                    + appointment.getAppointmentHistory().getTimeStart() + " " + appointment.getAppointmentHistory().getTimeEnd() + "]";
            EmailDTO emailDTO = EmailDTO.builder().recipients(List.of(userEntity.getEmail()))
                    .subject(subject).appointmentInfoForUser(appointment).build();

            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("appointmentInfoForUser", emailDTO.getAppointmentInfoForUser());

            mailService.sendMimeMessage(emailDTO, ctx, "beefree-7j6fllkji84/Appointment");
        } catch (Exception e) {
            log.error("Send mail failed: {}", e.getMessage());
        }
    }

    public void sendAppointmentCancelInfo(String username, AppointmentInfoForUser appointment) {
        try {
            UserEntity userEntity = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UserNotFoundException("Username with: " + username + " not found!"));

            String subject = "Your upcoming appointment on " + appointment.getAppointmentHistory().getDate() + " ["
                    + appointment.getAppointmentHistory().getTimeStart() + " " + appointment.getAppointmentHistory().getTimeEnd() + "]" + " has been cancelled";
            EmailDTO emailDTO = EmailDTO.builder().recipients(List.of(userEntity.getEmail()))
                    .subject(subject).appointmentInfoForUser(appointment).build();

            final Context ctx = new Context(LocaleContextHolder.getLocale());
            ctx.setVariable("appointmentInfoForUser", emailDTO.getAppointmentInfoForUser());
            mailService.sendMimeMessage(emailDTO, ctx, "beefree-7j6fllkji84/CancelAppointment");
        } catch (Exception e) {
            log.error("Send mail failed: {}", e.getMessage());
        }
    }
}
