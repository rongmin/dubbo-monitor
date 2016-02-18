package com.handu.open.dubbo.monitor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.handu.open.dubbo.monitor.domain.DubboDelay;
import com.handu.open.dubbo.monitor.domain.DubboInvokeLineChartV2;
import com.handu.open.dubbo.monitor.domain.LineChartSeriesV2;
import com.handu.open.dubbo.monitor.domain.StatsSum;
import com.handu.open.dubbo.monitor.service.DelayStatusService;
import com.handu.open.dubbo.monitor.support.CommonResponse;

@Controller
@RequestMapping("/slowquery/qps")
public class SlowQpsController {

	@Autowired
	private DelayStatusService delayStatusService;

	@RequestMapping(method = RequestMethod.GET)
	public String home() {
		return "slowquery/qps";
	}

	@ResponseBody
	@RequestMapping(value = "loadTopData")
	public CommonResponse loadTopData(@ModelAttribute DubboDelay dubboInvoke) {
		CommonResponse commonResponse = CommonResponse.createCommonResponse();
		List<DubboInvokeLineChartV2> dubboInvokeLineChartList = new ArrayList<DubboInvokeLineChartV2>();
		Map<String, List<StatsSum>> map = delayStatusService.getDatas(dubboInvoke);

		makeBarLineData(dubboInvokeLineChartList, map.get("service"), "访问次数：service(java interface)级别统计", "service");
		makeBarLineData(dubboInvokeLineChartList, map.get("method"), "访问次数：method级别统计", "method");

		commonResponse.setData(dubboInvokeLineChartList);
		return commonResponse;
	}

	private void makeBarLineData(List<DubboInvokeLineChartV2> dubboInvokeLineChartList, List<StatsSum> applicationList,
			String title, String divId) {
		DubboInvokeLineChartV2 successDubboInvokeLineChart = new DubboInvokeLineChartV2();
		List<String> sxAxisCategories = Lists.newArrayList();
		LineChartSeriesV2 slineChartSeries = new LineChartSeriesV2();
		LineChartSeriesV2 slineChartSeries2 = new LineChartSeriesV2();
		LineChartSeriesV2 slineChartSeries3 = new LineChartSeriesV2();
		LineChartSeriesV2 slineChartSeries4 = new LineChartSeriesV2();

		List<Long> sdataList = Lists.newArrayList();
		List<Long> sdataList2 = Lists.newArrayList();
		List<Long> sdataList3 = Lists.newArrayList();
		List<Long> sdataList4 = Lists.newArrayList();

		for (StatsSum ss : applicationList) {
			sxAxisCategories.add(ss.getName());

			sdataList.add(ss.getNumProvider());
			sdataList2.add(ss.getNumConsumer());

			if (ss.getNumProvider() > 0) {
				sdataList3.add(1000 * ss.getElapsedProvider() / ss.getNumProvider());
			} else {
				sdataList3.add(0L);
			}

			if (ss.getNumConsumer() > 0) {
				sdataList4.add(1000 * ss.getElapsedConsumer() / ss.getNumConsumer());
			} else {
				sdataList4.add(0L);
			}
		}
		slineChartSeries.setData(sdataList);
		slineChartSeries.setName("Provider访问次数");
		slineChartSeries.setType("column");

		slineChartSeries2.setData(sdataList2);
		slineChartSeries2.setName("Consumer访问次数");
		slineChartSeries2.setType("column");

		slineChartSeries3.setData(sdataList3);
		slineChartSeries3.setName("Provider平均响应时间(微妙)");
		slineChartSeries3.setType("spline");
		slineChartSeries3.setyAxis(1);

		slineChartSeries4.setData(sdataList4);
		slineChartSeries4.setName("Consumer平均响应时间(微妙)");
		slineChartSeries4.setType("spline");
		slineChartSeries4.setyAxis(1);

		List<LineChartSeriesV2> seriesData = new ArrayList<LineChartSeriesV2>();
		seriesData.add(slineChartSeries);
		seriesData.add(slineChartSeries2);
		seriesData.add(slineChartSeries3);
		seriesData.add(slineChartSeries4);

		successDubboInvokeLineChart.setxAxisCategories(sxAxisCategories);
		successDubboInvokeLineChart.setSeriesData(seriesData);
		successDubboInvokeLineChart.setChartType(divId);// 对应页面div id
		successDubboInvokeLineChart.setTitle(title);
		successDubboInvokeLineChart.setyAxisTitle("访问次数");
		dubboInvokeLineChartList.add(successDubboInvokeLineChart);
	}
}
