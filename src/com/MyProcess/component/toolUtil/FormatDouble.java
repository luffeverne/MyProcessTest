package com.MyProcess.component.toolUtil;

import java.text.DecimalFormat;

public class FormatDouble {

    public static double formatDouble (double d) {
        /*return (double) Math.round(d * 100) / 100;*/

        DecimalFormat df = new DecimalFormat("#.00");

        return Double.parseDouble(df.format(d));
    }
}
