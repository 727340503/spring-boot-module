package com.cherrypicks.tcc.cms.api.util;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.cherrypicks.tcc.cms.exception.ReportDatePeriodException;
import com.cherrypicks.tcc.util.CmsCodeStatus;
import com.cherrypicks.tcc.util.DateUtil;
import com.cherrypicks.tcc.util.I18nUtil;

/**
 * 参数验证
 */
@Component
public final class ReportUtil {
	
	@Value("${report.max.query.day:16}")
	private Integer reportMaxQueryDay;

	public static Integer reportMaxQueryDayStatic;
	
	@PostConstruct
	@SuppressWarnings("static-access")
	public void init(){
		this.reportMaxQueryDayStatic = reportMaxQueryDay;
	}
	
	public static void checkReportSearchDatePeriod(final String startTime, final String endTime, final String lang) {
		
		AssertUtil.notBlank(startTime, "Start time " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		AssertUtil.notBlank(endTime, "End time " + I18nUtil.getMessage(CmsCodeStatus.ILLEGAL_ARGUMENT, null, lang));
		
		Date startDate = DateUtil.parseDate(DateUtil.DATE_PATTERN_DEFAULT, startTime);
		Date endDate = DateUtil.parseDate(DateUtil.DATE_PATTERN_DEFAULT, endTime);

		int queryDays = DateUtil.daysBetween(startDate, endDate);
		if (queryDays > reportMaxQueryDayStatic.intValue()) {

			throw new ReportDatePeriodException(I18nUtil.getMessage(CmsCodeStatus.REPORT_DATE_PERIOD_EXCEPTION,
					new String[] { String.valueOf(reportMaxQueryDayStatic) }, lang));
		}
	}
}
