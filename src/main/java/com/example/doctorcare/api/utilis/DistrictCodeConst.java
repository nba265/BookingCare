package com.example.doctorcare.api.utilis;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class DistrictCodeConst {
    public static final Map<String,String> district = new HashMap<String, String>(){
        {
            put("00", "");
            put("01", "Quận Hải Châu");
            put("02", "Quận Thanh Khê");
            put("03", "Quận Sơn Trà");
            put("04", "Quận Ngũ Hành Sơn");
            put("05", "Quận Liên Chiểu");
            put("06", "Quận Hòa Vang");
            put("07", "Quận Cẩm Lệ");
        }
    };

}
