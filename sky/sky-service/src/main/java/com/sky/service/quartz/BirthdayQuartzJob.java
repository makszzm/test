package com.sky.service.quartz;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.sky.common.util.BirthdayUtil;
import com.sky.po.User;
import com.sky.po.UserExample;
import com.sky.service.UserInfDeal;

public class BirthdayQuartzJob extends QuartzJobBean {
	private static Logger logger = Logger.getLogger(BirthdayQuartzJob.class);
	private UserInfDeal userInfDeal;

	@Override
	protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
		UserExample example = new UserExample();
		List<User> users = userInfDeal.selectByExample(example);
		logger.info(users);

		for (User user : users) {
			String birthDay = user.getBirthday();
			String name = user.getName();
			String openId = user.getId();
			String appe = user.getAppellation();
			String birType = birthDay.substring(0, 1);
			String realBir = birthDay.substring(1, birthDay.length());
			String currentDay = BirthdayUtil.chineseDateFormat.format(new Date());
			if (birType.equalsIgnoreCase("N")) {
				Calendar today = Calendar.getInstance();
				String day = BirthdayUtil.chineseDateFormat.format(today.getTime());
				try {
					today.setTime(BirthdayUtil.chineseDateFormat.parse(day));
				} catch (ParseException e) {
					logger.error(e);
					e.printStackTrace();
				}
				currentDay = BirthdayUtil.compute(today);
			}
			boolean leap = currentDay.split("-")[1].substring(0, 1).equals("t") ? true : false;
			int year = Integer.parseInt(currentDay.split("-")[0]);
			int month = Integer.parseInt(currentDay.split("-")[1].substring(1, currentDay.split("-")[1].length()));
			int day = Integer.parseInt(currentDay.split("-")[2]);
			int realYear = Integer.parseInt(realBir.split("-")[0]);
			int realMonth = Integer.parseInt(realBir.split("-")[1]);
			int realDay = Integer.parseInt(realBir.split("-")[2]);
			logger.debug(year+":"+month+":"+day);
			logger.debug(realYear+":"+realMonth+":"+realDay);
			if (!leap && month == realMonth && day == realDay) {
				logger.info("有人今天生日,年龄:" + (year - realYear));
			}
		}
	}

	public void setUserInfDeal(UserInfDeal userInfDeal) {
		this.userInfDeal = userInfDeal;
	}
}
