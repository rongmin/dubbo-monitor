package com.handu.open.dubbo.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.handu.open.dubbo.monitor.RegistryContainer;
import com.handu.open.dubbo.monitor.domain.AppItem;
import com.handu.open.dubbo.monitor.service.AppItemService;

@Controller
@RequestMapping("/provider")
public class ProviderController {
	@Autowired
	private AppItemService appItemService;
	@Autowired
	private RegistryContainer registryContainer;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<AppItem> rows = appItemService.list();
		for (AppItem item : rows) {
			AppItem regItem = registryContainer.getHostsByApplication(item.getName());
			item.setRegister(regItem.getProvider());
			item.setRegisterNum(regItem.getProviderNum());
			if (item.getProviderNum() != item.getRegisterNum()) {
				item.setNumDiff(true);
			}
			if (!item.getProvider().equals(item.getRegister())) {
				item.setDiff(true);
			}
		}
		model.addAttribute("rows", rows);
		return "provider/list";
	}
}
