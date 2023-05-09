package com.example.doctorcare.api.utilis;

import com.example.doctorcare.api.domain.entity.TimeDoctorsEntity;

import java.util.Comparator;

public class TimeDoctorComparator implements Comparator<TimeDoctorsEntity> {

    @Override
    public int compare(TimeDoctorsEntity o1, TimeDoctorsEntity o2) {
        return o1.getTimeStart().compareTo(o2.getTimeStart());
    }
}
