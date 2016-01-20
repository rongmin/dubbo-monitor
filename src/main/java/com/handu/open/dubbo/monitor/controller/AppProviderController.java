package com.handu.open.dubbo.monitor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.handu.open.dubbo.monitor.domain.AppItem;
import com.handu.open.dubbo.monitor.service.AppItemService;

@Controller
@RequestMapping("/appprovider")
public class AppProviderController {
	@Autowired
	private AppItemService appItemService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<AppItem> rows = appItemService.list();
		model.addAttribute("rows", rows);
		return "appprovider/list";
	}
}
