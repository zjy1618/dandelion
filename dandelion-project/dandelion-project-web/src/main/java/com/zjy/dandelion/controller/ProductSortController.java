package com.zjy.dandelion.controller;

import com.zjy.dandelion.entity.ProductSort;
import com.zjy.dandelion.service.core.ProductSortService;
import com.zjy.dandelion.utils.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("productSort")
public class ProductSortController {

	private static final Logger logger = LoggerFactory.getLogger(ProductSortController.class);
	
	@Resource
	private ProductSortService productSortService;
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list (Model model) {
		
		List<ProductSort> list = productSortService.findAll();
		model.addAttribute("list",list);
		
		return "productSort/list";
	}
	
	@RequestMapping("/update/{id}")
	public String close(@PathVariable long id, ModelMap model) {
		ProductSort productSort = productSortService.findProductSortById(id);
		model.put("productSort", productSort);
		return "productSort/update";
	}
	
	/**
	 * 修改展厅
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse update(ProductSort productSort) {
		HttpResponse response = new HttpResponse();
		try {
			boolean add = productSortService.updateProductSort(productSort);
			if (add) {
				response.setCode(HttpResponse.CODE_OK);
				response.setStatus(HttpResponse.STATUS_SUCCESS);
				response.setMessage("保存成功");
			} else {
				response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
				response.setStatus(HttpResponse.STATUS_FAIL);
				response.setMessage("保存失败");
			}
		} catch (Exception e) {

			logger.error("保存失败", e);

			response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
			response.setStatus(HttpResponse.STATUS_FAIL);
			response.setMessage("保存失败 " + e.getMessage());
		}

		return response;

	}
	
	@RequestMapping("/initAdd")
	public String initAdd() {
		return "productSort/add";
	}
	
	/**
	 * 新增展厅
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse add(ProductSort productSort) {
		HttpResponse response = new HttpResponse();
		try {
			boolean add = productSortService.saveProductSort(productSort);
			if (add) {
				response.setCode(HttpResponse.CODE_OK);
				response.setStatus(HttpResponse.STATUS_SUCCESS);
				response.setMessage("保存成功");
				response.setData(productSort);
			} else {
				response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
				response.setStatus(HttpResponse.STATUS_FAIL);
				response.setMessage("保存失败");
				response.setData(productSort);
			}
		} catch (Exception e) {

			logger.error("保存失败", e);

			response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
			response.setStatus(HttpResponse.STATUS_FAIL);
			response.setMessage("保存失败 " + e.getMessage());
			response.setData(productSort);
		}

		return response;

	}
	
}
