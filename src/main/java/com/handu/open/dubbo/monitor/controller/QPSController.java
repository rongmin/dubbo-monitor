/**
 * Copyright 2006-2015 handu.com
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.handu.open.dubbo.monitor.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.handu.open.dubbo.monitor.DubboMonitorService;
import com.handu.open.dubbo.monitor.dao.DayStatsDAO;
import com.handu.open.dubbo.monitor.domain.DubboInvoke;
import com.handu.open.dubbo.monitor.domain.DubboInvokeLineChart;
import com.handu.open.dubbo.monitor.domain.LineChartSeries;
import com.handu.open.dubbo.monitor.domain.StatsSum;
import com.handu.open.dubbo.monitor.support.CommonResponse;

/**
 * Home Controller
 *
 * @author Silence <me@chenzhiguo.cn>
 *         Created on 15/6/26.
 */
@Controller
@RequestMapping("/qps")
public class QPSController {

	@Autowired
	DayStatsDAO dayStatsDAO;
	
	@Autowired
    private DubboMonitorService dubboMonitorService;

    @RequestMapping(method = RequestMethod.GET)
    public String home() {
        return "qps/qps";
    }

    @RequestMapping(value = "index", method = RequestMethod.GET)
    public String index() {
        return "qps/qps";
    }

    @ResponseBody
    @RequestMapping(value = "loadTopData")
    public CommonResponse loadTopDate(@ModelAttribute DubboInvoke dubboInvoke) {
        CommonResponse commonResponse = CommonResponse.createCommonResponse();
        List<DubboInvokeLineChart> dubboInvokeLineChartList = new ArrayList<DubboInvokeLineChart>();
        Map<String,List<StatsSum>> map = dayStatsDAO.getDatas(dubboInvoke);
        List<StatsSum>  applicationList = map.get("application");
        
        double[] data;
        makeBarLineData(dubboInvokeLineChartList, map.get("application"),"访问次数：application级别统计","application");
        makeBarLineData(dubboInvokeLineChartList, map.get("service"),"访问次数：service(java interface)级别统计","service");
        makeBarLineData(dubboInvokeLineChartList, map.get("method"),"访问次数：method级别统计","method");
        
        
        

       
        
       /* Map dubboInvokeMap = dubboMonitorService.countDubboInvokeTopTen(dubboInvoke);
        
        DubboInvokeLineChart failureDubboInvokeLineChart = new DubboInvokeLineChart();
        List<String> fxAxisCategories = Lists.newArrayList();
        LineChartSeries flineChartSeries = new LineChartSeries();
        List<double[]> fdataList = Lists.newArrayList();
        List<DubboInvoke> failure = (List<DubboInvoke>) dubboInvokeMap.get("failure");
        for (DubboInvoke di : failure) {
            fxAxisCategories.add(di.getMethod());
            data = new double[]{di.getFailure()};
            fdataList.add(data);
        }
        flineChartSeries.setData(fdataList);
        flineChartSeries.setName("provider");

        failureDubboInvokeLineChart.setxAxisCategories(fxAxisCategories);
        failureDubboInvokeLineChart.setSeriesData(Arrays.asList(flineChartSeries));
        failureDubboInvokeLineChart.setChartType("FAILURE");
        failureDubboInvokeLineChart.setTitle("调用失败的次数最多的30条");
        failureDubboInvokeLineChart.setyAxisTitle("t");
        dubboInvokeLineChartList.add(failureDubboInvokeLineChart);*/

        commonResponse.setData(dubboInvokeLineChartList);
        return commonResponse;
    }

	private void makeBarLineData(
			List<DubboInvokeLineChart> dubboInvokeLineChartList,
			List<StatsSum> applicationList,String title,String divId) {
		DubboInvokeLineChart successDubboInvokeLineChart = new DubboInvokeLineChart();
        List<String> sxAxisCategories = Lists.newArrayList();
        LineChartSeries slineChartSeries = new LineChartSeries();
        LineChartSeries slineChartSeries2 = new LineChartSeries();
        LineChartSeries slineChartSeries3 = new LineChartSeries();
        LineChartSeries slineChartSeries4 = new LineChartSeries();
        
        List<double[]> sdataList = Lists.newArrayList();
        List<double[]> sdataList2 = Lists.newArrayList();
        List<double[]> sdataList3 = Lists.newArrayList();
        List<double[]> sdataList4 = Lists.newArrayList();
        double[] data,data2,data3,data4;
        
        for (StatsSum ss :applicationList ) {
            sxAxisCategories.add(ss.getName());
            data = new double[]{ss.getNumProvider()};
            sdataList.add(data);
            data2 = new double[]{ss.getNumConsumer()};
            sdataList2.add(data2);

            if(ss.getNumProvider()>0){
            	data3 = new double[]{ss.getElapsedProvider()/ss.getNumProvider()};
            }else{
            	data3 = new double[]{0};
            }
            
            sdataList3.add(data3);
            
            if(ss.getNumConsumer()>0){
            	data4 = new double[]{ss.getElapsedConsumer()/ss.getNumConsumer()};
            }else{
            	data4 = new double[]{0};
            }
            sdataList4.add(data4);

        }
        slineChartSeries.setData(sdataList);
        slineChartSeries.setName("Provider访问次数");
        slineChartSeries.setType("column");
        
        slineChartSeries2.setData(sdataList2);
        slineChartSeries2.setName("Consumer访问次数");
        slineChartSeries2.setType("column");
        
        slineChartSeries3.setData(sdataList);
        slineChartSeries3.setName("Provider平均响应时间");
        slineChartSeries3.setType("spline");
        
        slineChartSeries4.setData(sdataList2);
        slineChartSeries4.setName("Consumer平均响应时间");
        slineChartSeries4.setType("spline");
        
        
        List<LineChartSeries> seriesData = new ArrayList<LineChartSeries>();
        seriesData.add(slineChartSeries);
        seriesData.add(slineChartSeries2);        
        seriesData.add(slineChartSeries3);
        seriesData.add(slineChartSeries4);        

        successDubboInvokeLineChart.setxAxisCategories(sxAxisCategories);
        successDubboInvokeLineChart.setSeriesData(seriesData);
        successDubboInvokeLineChart.setChartType(divId);//对应页面div id
        successDubboInvokeLineChart.setTitle(title);
        successDubboInvokeLineChart.setyAxisTitle("访问次数");
        dubboInvokeLineChartList.add(successDubboInvokeLineChart);
	}
}
