package com.zjy.dandelion.controller;

import com.zjy.dandelion.entity.Product;
import com.zjy.dandelion.entity.ProductStyle;
import com.zjy.dandelion.service.core.ProductService;
import com.zjy.dandelion.service.core.ProductStyleService;
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
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

@Controller
@RequestMapping("productStyle")
public class ProductStyleController {

	private static final Logger logger = LoggerFactory.getLogger(ProductStyleController.class);
	
	@Resource
	private ProductStyleService productStyleService;

	@Resource
	private ProductService productService;
	
	@RequestMapping(value = "/list/{productCode}", method = RequestMethod.GET)
	public String list (@PathVariable String productCode, Model model) {

		Product product = productService.findProductByCode(productCode);
		List<ProductStyle> list = productStyleService.findByProductCode(productCode);
		model.addAttribute("list",list);
		model.addAttribute("product",product);
		return "productStyle/list";
	}
	
	@RequestMapping("/update/{id}")
	public String update(@PathVariable long id, ModelMap model) {
		ProductStyle productStyle = productStyleService.findProductStyleById(id);
		model.put("productStyle", productStyle);
		return "productStyle/update";
	}
	
	/**
	 * 修改款式
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse update(ProductStyle productStyle) {
		HttpResponse response = new HttpResponse();
		try {
			boolean add = productStyleService.updateProductStyle(productStyle);
			if (add) {
				response.setCode(HttpResponse.CODE_OK);
				response.setStatus(HttpResponse.STATUS_SUCCESS);
				response.setMessage("保存成功");
				response.setData(productStyle);
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
	
	@RequestMapping("/initAdd/{productCode}")
	public String initAdd(@PathVariable String productCode, ModelMap model) {
		Product product = productService.findProductByCode(productCode);
		model.addAttribute("product",product);
		return "productStyle/add";
	}
	
	/**
	 * 新增款式
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse add(ProductStyle productStyle) {
		HttpResponse response = new HttpResponse();
		try {
			boolean add = productStyleService.saveProductStyle(productStyle);
			if (add) {
				response.setCode(HttpResponse.CODE_OK);
				response.setStatus(HttpResponse.STATUS_SUCCESS);
				response.setMessage("保存成功");
				response.setData(productStyle);
			} else {
				response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
				response.setStatus(HttpResponse.STATUS_FAIL);
				response.setMessage("保存失败");
				response.setData(productStyle);
			}
		} catch (Exception e) {

			logger.error("保存失败", e);

			response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
			response.setStatus(HttpResponse.STATUS_FAIL);
			response.setMessage("保存失败 " + e.getMessage());
			response.setData(productStyle);
		}

		return response;

	}


	/**
	 * 删除产品款式
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public HttpResponse delete(Long id) {
		HttpResponse response = new HttpResponse();

		boolean delete = productStyleService.deleteById(id);
		if (delete) {
			response.setCode(HttpResponse.CODE_OK);
			response.setStatus(HttpResponse.STATUS_SUCCESS);
			response.setMessage("删除产品成功");
			response.setData(id);
		} else {
			response.setCode(HttpResponse.CODE_INTERNAL_SERVER_ERROR);
			response.setStatus(HttpResponse.STATUS_FAIL);
			response.setMessage("删除产品失败");
		}
		return response;
	}
}
