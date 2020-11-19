package com.iii.eeit124.shopping.controller;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iii.eeit124.entity.Products;
import com.iii.eeit124.shopping.service.ProductListService;



@Controller
@RequestMapping("/product")
public class ProductListController {
	@Autowired
	ProductListService service;
	
	@Autowired
	ServletContext ctx;
	
	@GetMapping("/ProductList")
	public String goProductListPage(Model model) {
		return "products/ProductList";
	}
	//用@ResponseBody 回傳所有商品的json格式給前端
	@GetMapping("/getProducts")
	public @ResponseBody List<Products> queryAllProducts(Model model){
		List<Products> products = service.findAllProducts();
		return products;
	}
	
	@GetMapping(value = "/pagingProducts.json", produces = { "application/json; charset=UTF-8" })
	public @ResponseBody Map<String, Object> getPageProducts(
		@RequestParam(value="pageNo", required = false, defaultValue = "1") Integer pageNo,
		@RequestParam(value="totalPage", required = false) Integer totalPage,
		@RequestParam(value="colorId", required = false) Integer colorId,
		@RequestParam(value="categoryId", required = false) Integer categoryId,
		@RequestParam(value="animalTypeId", required = false) Integer animalTypeId,
		@RequestParam(value="recordsPerPage", required = false,defaultValue = "3") Integer recordsPerPage
		
			) {
		
		Long recordCounts= (long) 0;
		List<Products> list = new ArrayList<Products>();
	    

		Map<String, Object> map = new HashMap<>();
		if (colorId != null||categoryId != null||animalTypeId != null) {			
			recordCounts = service.getRecordCounts(colorId, categoryId, animalTypeId);
			totalPage = (int) (Math.ceil(recordCounts / (double) recordsPerPage));
			list = service.getPageProducts(pageNo, colorId, categoryId, animalTypeId,recordsPerPage);
		}
		else {
			list = service.getPageProducts(pageNo,recordsPerPage);
			recordCounts = service.getRecordCounts();
			totalPage = (int) (Math.ceil(recordCounts / (double) recordsPerPage));
		}
		map.put("list", list);
		map.put("totalPage", totalPage);
		map.put("currPage", pageNo);
		map.put("recordCounts", recordCounts);
		map.put("recordsPerPage", recordsPerPage);
		
		
		return map;
	}
	
	@GetMapping(value="/getProductImage")
	public ResponseEntity<byte[]> getProductImage(@RequestParam("productId") Integer productId) {
		ResponseEntity<byte[]> re = null;
		Products product = service.getProduct(productId);
		Blob blob = product.getCoverImg();
		String mimeType = ctx.getMimeType("abc.jpg");
		MediaType mediaType = MediaType.valueOf(mimeType);
		HttpHeaders headers = new HttpHeaders();
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			InputStream is = blob.getBinaryStream();
			byte[] b = new byte[81920];
			int len = 0;
			while ((len = is.read(b)) != -1) {
				baos.write(b, 0, len);
			}
			headers.setContentType(mediaType);
			headers.setCacheControl(CacheControl.noCache().getHeaderValue());
			re = new ResponseEntity<byte[]>(baos.toByteArray(), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();

		}
		return re;
	}
}