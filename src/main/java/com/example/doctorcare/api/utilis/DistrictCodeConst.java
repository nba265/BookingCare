package com.example.doctorcare.api.utilis;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

public class DistrictCodeConst {
    public static final  Map<String,String> district = new HashMap<>();

    DistrictCodeConst(){
        district.put("00","");
        district.put("01","Quận Hải Châu");
        district.put("02","Quận Thanh Khê");
        district.put("03","Quận Sơn Trà");
        district.put("04","Quận Ngũ Hành Sơn");
        district.put("05","Quận Liên Chiểu");
        district.put("06","Quận Hòa Vang");
        district.put("07","Quận Cẩm Lệ");
    }

}
