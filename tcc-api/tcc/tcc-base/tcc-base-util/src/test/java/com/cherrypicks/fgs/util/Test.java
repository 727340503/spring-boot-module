package com.cherrypicks.fgs.util;

import java.math.BigDecimal;
import java.text.ParseException;

public class Test {
	public static void main(final String[] args) throws ParseException {
		//System.out.println(DateUtil.parseDate(DateUtil.DATE_PATTERN_YYYYMMDDTHHMMSSZ, "2015-04-20T11:12:00+0800"));
	System.out.println(new BigDecimal(Double.toString(120.12)).divide(new BigDecimal(33),0,BigDecimal.ROUND_DOWN).intValue());

	}
}
