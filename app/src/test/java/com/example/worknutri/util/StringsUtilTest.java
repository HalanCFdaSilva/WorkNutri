package com.example.worknutri.util;

import org.junit.Test;

public class StringsUtilTest {
    @Test
    public void testConvertHourStringInInt() {
        assert StringsUtil.convertHourStringInInt("08:30") == 8;
        assert StringsUtil.convertHourStringInInt("23:59") == 23;
        assert StringsUtil.convertHourStringInInt("00:00") == 0;
    }

    @Test
    public void testFormatDouble() {
        assert StringsUtil.formatDouble("3.14159").equals("3.14");
        assert StringsUtil.formatDouble("2.5").equals("2.5");
        assert StringsUtil.formatDouble("100").equals("100");
        assert StringsUtil.formatDouble("0.9999").equals("0.99");
    }
}
