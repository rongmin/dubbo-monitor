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
import com.handu.open.dubbo.monitor.domain.DubboInvokeLineChartV2;
import com.handu.open.dubbo.monitor.domain.LineChartSeriesV2;
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
        List<DubboInvokeLineChartV2> dubboInvokeLineChartList = new ArrayList<DubboInvokeLineChartV2>();
        Map<String,List<StatsSum>> map = dayStatsDAO.getDatas(dubboInvoke);
        List<StatsSum>  applicationList = map.get("application");
        
        double[] data;
        makeBarLineData(dubboInvokeLineChartList, map.get("application"),"访问次数：application级别统计","application");
        makeBarLineData(dubboInvokeLineChartList, map.get("service"),"访问次数：service(java interface)级别统计","service");
        makeBarLineData(dubboInvokeLineChartList, map.get("method"),"访问次数：method级别统计","method");
        
        
        

       
        
       /* Map dubboInvokeMap = dubboMonitorService.countDubboInvokeTopTen(dubboInvoke);
        
        DubboInvokeLineChart failureDubboInvokeLineChart = new DubboInvokeLineChart();
        List<String> fxAxisCategories = Lists.newArrayList();
        LineChartSeriesV2 flineChartSeries = new LineChartSeriesV2();
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
			List<DubboInvokeLineChartV2> dubboInvokeLineChartList,
			List<StatsSum> applicationList,String title,String divId) {
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
        
        
        for (StatsSum ss :applicationList ) {
            sxAxisCategories.add(ss.getName());
        
            
            sdataList.add(ss.getNumProvider());            
            sdataList2.add(ss.getNumConsumer());
            

            if(ss.getNumProvider()>0){
            	sdataList3.add(ss.getElapsedProvider()/ss.getNumProvider());
            }else{
            	sdataList3.add(0L);
            }
            
            if(ss.getNumConsumer()>0){
            	sdataList4.add(ss.getElapsedConsumer()/ss.getNumConsumer());
            }else{
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
        slineChartSeries3.setName("Provider平均响应时间");
        slineChartSeries3.setType("spline");
        slineChartSeries3.setyAxis(1);        
        
        slineChartSeries4.setData(sdataList4);
        slineChartSeries4.setName("Consumer平均响应时间");
        slineChartSeries4.setType("spline");
        slineChartSeries4.setyAxis(1);        
        
        List<LineChartSeriesV2> seriesData = new ArrayList<LineChartSeriesV2>();
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
