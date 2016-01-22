package com.handu.open.dubbo.monitor.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.handu.open.dubbo.monitor.dao.base.ApplicationServiceBaseDAO;
import com.handu.open.dubbo.monitor.domain.ApplicationService;
import com.handu.open.dubbo.monitor.domain.ApplicationServiceMethod;
import com.handu.open.dubbo.monitor.domain.DubboDelay;
import com.handu.open.dubbo.monitor.domain.DubboStatistics;
import com.handu.open.dubbo.monitor.service.DubboDelayService;
import com.handu.open.dubbo.monitor.vo.LowQueryVo;

@Controller
@RequestMapping("/lowquery/statistics")
public class LowStatisticsController {
	@Autowired
	private DubboDelayService dubboDelayService;
	@Autowired
	private ApplicationServiceBaseDAO applicationServiceBaseDAO;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute LowQueryVo vo, Model model) {
		ApplicationService service=applicationServiceBaseDAO.getApplicationServiceById(vo.getServiceId());
		vo.setServiceName(service.getName());
		model.addAttribute("vo",vo);
		return "lowquery/statistics";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String loadData(@ModelAttribute DubboDelay dubboInvoke, Model model) {
		// Set default Search Date
		if (dubboInvoke.getInvokeDate() == null && dubboInvoke.getInvokeDateFrom() == null
				&& dubboInvoke.getInvokeDateTo() == null) {
			dubboInvoke.setInvokeDate(new Date());
		}
		// 获取Service方法
		List<ApplicationServiceMethod> methodList = dubboDelayService.getMethodsByService(dubboInvoke);
		List<DubboDelay> delayList;
		List<DubboStatistics> statisticseList = new ArrayList<DubboStatistics>();
		DubboStatistics dubboStatistics;
		for (ApplicationServiceMethod method : methodList) {
			dubboStatistics = new DubboStatistics();
			dubboStatistics.setMethod(method.getName());
			dubboInvoke.setMethodId(method.getId());
			dubboInvoke.setType("provider");
			delayList = dubboDelayService.countDubboDelayInfo(dubboInvoke);
			for (DubboDelay di : delayList) {
				if (di == null) {
					continue;
				}
				dubboStatistics.setProviderSuccess(di.getSuccess());
				dubboStatistics.setProviderFailure(di.getFailure());
				dubboStatistics.setProviderAvgElapsed(di.getSuccess() != 0
						? Double.valueOf(String.format("%.4f", 1.0 * di.getElapsed() / di.getSuccess())) : 0);
				dubboStatistics.setProviderMaxElapsed(di.getMaxElapsed());
				dubboStatistics.setProviderMaxConcurrent(di.getMaxConcurrent());
			}
			dubboInvoke.setType("consumer");
			delayList = dubboDelayService.countDubboDelayInfo(dubboInvoke);
			for (DubboDelay di : delayList) {
				if (di == null) {
					continue;
				}
				dubboStatistics.setConsumerSuccess(di.getSuccess());
				dubboStatistics.setConsumerFailure(di.getFailure());
				dubboStatistics.setConsumerAvgElapsed(di.getSuccess() != 0
						? Double.valueOf(String.format("%.4f", 1.0 * di.getElapsed() / di.getSuccess())) : 0);
				dubboStatistics.setConsumerMaxElapsed(di.getMaxElapsed());
				dubboStatistics.setConsumerMaxConcurrent(di.getMaxConcurrent());
			}
			statisticseList.add(dubboStatistics);
		}
		model.addAttribute("rows", statisticseList);
		return "lowquery/statistics_tbl";
	}
	public static void main(String[] args) {
		Calendar cc=Calendar.getInstance();
		cc.set(2016, 1, 21, 0, 0, 0);
		System.out.println(cc.getTime());
		System.out.println(DateFormatUtils.ISO_DATE_FORMAT.format(cc.getTime()));
		cc.set(2016, 1, 21, 23, 59, 59);
		System.out.println(cc.getTime());
		System.out.println(DateFormatUtils.ISO_DATE_FORMAT.format(cc.getTime()));
	}
}
