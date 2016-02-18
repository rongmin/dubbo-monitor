package com.handu.open.dubbo.monitor.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.handu.open.dubbo.monitor.RegistryContainer;
import com.handu.open.dubbo.monitor.domain.ApplicationService;
import com.handu.open.dubbo.monitor.domain.DubboDelay;
import com.handu.open.dubbo.monitor.domain.DubboService;
import com.handu.open.dubbo.monitor.service.DubboDelayService;
import com.handu.open.dubbo.monitor.vo.SlowQueryVo;

@Controller
@RequestMapping("/slowquery/query")
public class SlowQueryController {
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	@Autowired
	private RegistryContainer registryContainer;
	@Autowired
	private DubboDelayService dubboDelayService;

	@RequestMapping(method = RequestMethod.GET)
	public String index() {
		return "slowquery/index";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String loadData(@ModelAttribute DubboDelay dubboDelay, Model model) {
		if (dubboDelay.getInvokeDate() == null && dubboDelay.getInvokeDateFrom() == null
				&& dubboDelay.getInvokeDateTo() == null) {
			dubboDelay.setInvokeDate(new Date());
		}

		List<DubboService> rows = new ArrayList<DubboService>();
		List<ApplicationService> services = dubboDelayService.listSlowServices(dubboDelay);

		if (services != null && services.size() > 0) {
			DubboService dubboService;
			for (ApplicationService service : services) {
				dubboService = new DubboService();
				dubboService.setServiceId(service.getId());
				dubboService.setName(service.getName());
				dubboService.setSlowCount(service.getSlowCount());

				List<URL> providers = registryContainer.getProvidersByService(service.getName());
				int providerSize = providers == null ? 0 : providers.size();
				dubboService.setProviderCount(providerSize);

				List<URL> consumers = registryContainer.getConsumersByService(service.getName());
				int consumerSize = consumers == null ? 0 : consumers.size();
				dubboService.setConsumerCount(consumerSize);

				if (providerSize > 0) {
					URL provider = providers.iterator().next();
					dubboService.setApplication(provider.getParameter(Constants.APPLICATION_KEY, ""));
					dubboService.setOwner(provider.getParameter("owner", ""));
					dubboService.setOrganization(
							(provider.hasParameter("organization") ? provider.getParameter("organization") : ""));
				}

				rows.add(dubboService);
			}
		}

		model.addAttribute("rows", rows);
		SlowQueryVo vo = new SlowQueryVo();
		vo.setInvokeDateFrom(DateFormatUtils.ISO_DATE_FORMAT.format(dubboDelay.getInvokeDateFrom()));
		vo.setInvokeDateTo(DateFormatUtils.ISO_DATE_FORMAT.format(dubboDelay.getInvokeDateTo()));
		model.addAttribute("vo", vo);
		return "slowquery/index_tbl";
	}

}
