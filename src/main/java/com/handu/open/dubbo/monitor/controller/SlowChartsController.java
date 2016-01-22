package com.handu.open.dubbo.monitor.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.FastDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.handu.open.dubbo.monitor.domain.ApplicationServiceMethod;
import com.handu.open.dubbo.monitor.domain.DubboDelay;
import com.handu.open.dubbo.monitor.domain.DubboInvokeLineChart;
import com.handu.open.dubbo.monitor.domain.LineChartSeries;
import com.handu.open.dubbo.monitor.service.DubboDelayService;
import com.handu.open.dubbo.monitor.support.CommonResponse;
import com.handu.open.dubbo.monitor.vo.LowQueryVo;

@Controller
@RequestMapping("/slowquery/charts")
public class SlowChartsController {
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	@Autowired
	private DubboDelayService dubboDelayService;

	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute LowQueryVo vo, Model model) {
		DubboDelay dubboInvoke = new DubboDelay();
		dubboInvoke.setServiceId(vo.getServiceId());
		if (vo.getInvokeDateFrom() == null || vo.getInvokeDateTo() == null) {
			dubboInvoke.setInvokeDate(new Date());
		} else {
			FastDateFormat fdf = FastDateFormat.getInstance(DATETIME_FORMAT);
			try {
				dubboInvoke.setInvokeDateFrom(fdf.parse(vo.getInvokeDateFrom() + " 00:00:00"));
				dubboInvoke.setInvokeDateTo(fdf.parse(vo.getInvokeDateFrom() + " 23:59:59"));
			} catch (ParseException e) {
				dubboInvoke.setInvokeDate(new Date());
			}
		}

		// 获取Service方法
		List<ApplicationServiceMethod> methodList = dubboDelayService.getMethodsByService(dubboInvoke);
		if (methodList != null && methodList.size() > 0) {
			vo.setServiceName(methodList.get(0).getServiceName());
			List<String> methods = new ArrayList<String>();
			for (ApplicationServiceMethod m : methodList) {
				methods.add(m.getName());
			}
			model.addAttribute("rows", methods);
		}

		model.addAttribute("vo", vo);

		return "slowquery/charts";
	}

	@ResponseBody
	@RequestMapping(value = "loadChartsData")
	public CommonResponse loadChartsData(@ModelAttribute DubboDelay dubboInvoke) {
		// 计算统计平均请求次数的时间粒度
		long timeParticle = dubboInvoke.getTimeParticle() / 1000;
		List<DubboInvokeLineChart> dubboInvokeLineChartList = new ArrayList<DubboInvokeLineChart>();
		DubboInvokeLineChart qpsLineChart;
		DubboInvokeLineChart artLineChart;
		List<LineChartSeries> qpsLineChartSeriesList;
		List<LineChartSeries> artLineChartSeriesList;
		LineChartSeries qpsLineChartSeries;
		LineChartSeries artLineChartSeries;
		List<double[]> qpsSeriesDatas;
		List<double[]> artSeriesDatas;
		List<ApplicationServiceMethod> methodList = dubboDelayService.getMethodsByService(dubboInvoke);
		// List<String> methods =
		// dubboMonitorService.getMethodsByService(dubboInvoke);
		for (ApplicationServiceMethod method : methodList) {
			qpsLineChart = new DubboInvokeLineChart();
			artLineChart = new DubboInvokeLineChart();
			qpsLineChartSeriesList = new ArrayList<LineChartSeries>();
			artLineChartSeriesList = new ArrayList<LineChartSeries>();
			dubboInvoke.setMethodId(method.getId());
			// 组织Provider折线数据
			qpsLineChartSeries = new LineChartSeries();
			artLineChartSeries = new LineChartSeries();
			dubboInvoke.setType("provider");
			List<DubboDelay> providerDubboInvokeDetails = dubboDelayService.countDubboDelay(dubboInvoke);
			qpsLineChartSeries.setName(dubboInvoke.getType());
			artLineChartSeries.setName(dubboInvoke.getType());
			qpsSeriesDatas = new ArrayList<double[]>();
			artSeriesDatas = new ArrayList<double[]>();
			double[] qpsProviderSeriesData = null;
			double[] artProviderSeriesData;
			for (DubboDelay dubboInvokeDetail : providerDubboInvokeDetails) {
				qpsProviderSeriesData = new double[] { dubboInvokeDetail.getInvokeTime(),
						Double.valueOf(String.format("%.4f", 1.0 * dubboInvokeDetail.getSuccess() / timeParticle)) };
				qpsSeriesDatas.add(qpsProviderSeriesData);
				artProviderSeriesData = new double[] { dubboInvokeDetail.getInvokeTime(),
						Double.valueOf(String.format("%.4f", 1.0 * dubboInvokeDetail.getElapsed())) };
				artSeriesDatas.add(artProviderSeriesData);
			}
			qpsLineChartSeries.setData(qpsSeriesDatas);
			qpsLineChartSeriesList.add(qpsLineChartSeries);
			artLineChartSeries.setData(artSeriesDatas);
			artLineChartSeriesList.add(artLineChartSeries);
			// 组织Consumer折线数据
			qpsLineChartSeries = new LineChartSeries();
			artLineChartSeries = new LineChartSeries();
			dubboInvoke.setType("consumer");
			List<DubboDelay> consumerDubboInvokeDetails = dubboDelayService.countDubboDelay(dubboInvoke);
			qpsLineChartSeries.setName(dubboInvoke.getType());
			artLineChartSeries.setName(dubboInvoke.getType());
			qpsSeriesDatas = new ArrayList<double[]>();
			artSeriesDatas = new ArrayList<double[]>();
			double[] qpsConsumerSeriesData;
			double[] artConsumerSeriesData;
			for (DubboDelay dubboInvokeDetail : consumerDubboInvokeDetails) {
				qpsConsumerSeriesData = new double[] { dubboInvokeDetail.getInvokeTime(), 1.0
						* Double.valueOf(String.format("%.4f", 1.0 * dubboInvokeDetail.getSuccess() / timeParticle)) };
				qpsSeriesDatas.add(qpsConsumerSeriesData);
				artConsumerSeriesData = new double[] { dubboInvokeDetail.getInvokeTime(),
						1.0 * Double.valueOf(String.format("%.4f", 1.0 * dubboInvokeDetail.getElapsed())) };
				artSeriesDatas.add(artConsumerSeriesData);
			}
			qpsLineChartSeries.setData(qpsSeriesDatas);
			qpsLineChartSeriesList.add(qpsLineChartSeries);
			artLineChartSeries.setData(artSeriesDatas);
			artLineChartSeriesList.add(artLineChartSeries);

			// ====================== 统计QPS ===========================
			qpsLineChart.setSeriesData(qpsLineChartSeriesList);
			qpsLineChart.setTitle("Requests per second (QPS)");
			qpsLineChart.setyAxisTitle("t/s");
			qpsLineChart.setMethod(method.getName());
			qpsLineChart.setChartType("QPS");

			dubboInvokeLineChartList.add(qpsLineChart);

			// ====================== 统计ART ===========================
			artLineChart.setSeriesData(artLineChartSeriesList);
			artLineChart.setTitle("Average response time (ms)");
			artLineChart.setyAxisTitle("ms/t");
			artLineChart.setMethod(method.getName());
			artLineChart.setChartType("ART");

			dubboInvokeLineChartList.add(artLineChart);
		}

		CommonResponse commonResponse = CommonResponse.createCommonResponse();
		commonResponse.setData(dubboInvokeLineChartList);
		return commonResponse;
	}
}
