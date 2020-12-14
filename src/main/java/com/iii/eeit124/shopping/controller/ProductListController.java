package com.iii.eeit124.shopping.controller;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iii.eeit124.entity.FollowProducts;
import com.iii.eeit124.entity.Members;
import com.iii.eeit124.entity.Products;
import com.iii.eeit124.shopping.dao.ProductListDaoImpl;
import com.iii.eeit124.shopping.service.ProductListService;
import com.iii.eeit124.shopping.service.ShoppingAanlysisService;

@Controller
@RequestMapping("/product")
public class ProductListController {
	@Autowired
	ProductListService service;

	@Autowired
	ServletContext ctx;
	
	@Autowired
	HttpSession session;
	
	@Autowired
	ShoppingAanlysisService shoppingAanlysisService;
	
	@GetMapping("/ProductList")
	public String goProductListPage(Model model) {
		return "products/ProductList";
	}
	//用@ResponseBody 回傳所有商品的json格式給前端
	@GetMapping("/getProducts")
	public @ResponseBody List<Products> queryAllProducts(@RequestParam(value = "keywordSearch", required = false) String keywordSearch,Model model){
		List<Products> products =null;
		
		if(keywordSearch==null || keywordSearch.equals("")) {
			 products = service.findAllProducts();
		}else {
			 products = service.selectByName(keywordSearch);			 
		}
		return products;
	}
	
	
	@GetMapping(value = "/pagingProducts.json", produces = { "application/json; charset=UTF-8" })
	public @ResponseBody Map<String, Object> getPageProducts(
		@RequestParam(value="pageNo", required = false, defaultValue = "1") Integer pageNo,
		@RequestParam(value="totalPage", required = false) Integer totalPage,
		@RequestParam(value="colorId", required = false) Integer colorId,
		@RequestParam(value="categoryId", required = false) Integer categoryId,
		@RequestParam(value="animalTypeId", required = false) Integer animalTypeId,
		@RequestParam(value="recordsPerPage", required = false,defaultValue = "6") Integer recordsPerPage,
		@RequestParam(value ="keywordSearch", required = false) String keywordSearch,
		@RequestParam(value ="orderBy", required = false, defaultValue = "0") Integer orderBy //"0"用color_Id排序
		
			) {
		
		Long recordCounts= (long) 0;
		List<Products> list = new ArrayList<Products>();
		Map<String, Object> map = new HashMap<>();
		
		//抓ProductList.jsp，orderBy的值到ProductListDaoImpl，做switch
		ProductListDaoImpl.getPageOrderBy(orderBy);
		
		//如果分類、顏色、動物類型有值
		if (colorId != null||categoryId != null||animalTypeId != null ||keywordSearch != null) {
//			if(keywordSearch != null || !(keywordSearch.equals("")) ) {
//				//如果分類、顏色、動物類型有值 + 如果關鍵字有值，就不重算頁數(搜尋全部商品)
//				list = service.getPageProducts(pageNo,recordsPerPage);
//				recordCounts = service.getRecordCounts();
//				totalPage = (int) (Math.ceil(recordCounts / (double) recordsPerPage));
//			}else{	
				//如果分類、顏色、動物類型有值+關鍵字沒值				
				list = service.getPageProducts(pageNo, colorId, categoryId, animalTypeId,recordsPerPage,keywordSearch);
				
				recordCounts = service.getRecordCounts(colorId, categoryId, animalTypeId,keywordSearch);
				totalPage = (int) (Math.ceil(recordCounts / (double) recordsPerPage));
//			}
		}else {
			//如果分類、顏色、動物類型沒有值+不管關鍵字有沒有值都不重算
			list = service.getPageProducts(pageNo,recordsPerPage);
			recordCounts = service.getRecordCounts();
			totalPage = (int) (Math.ceil(recordCounts / (double) recordsPerPage));
		}
		Members member = (Members)session.getAttribute("LoginOK");
		List<FollowProducts> likeList = new ArrayList();
		if (member != null) {
			likeList = service.getLikeProduct(member.getId());
		}
		map.put("list", list);
		map.put("likeList", likeList);
		map.put("totalPage", totalPage);
		map.put("currPage", pageNo);
		map.put("recordCounts", recordCounts);
		map.put("recordsPerPage", recordsPerPage);
		map.put("keywordSearch",keywordSearch);
		
		return map;
	}
	
	@GetMapping(value = "/memberRecommandProducts.json", produces = { "application/json; charset=UTF-8" })
	public @ResponseBody List<Products> getPageProducts(){
		List<Products> list = new ArrayList<Products>();
		Members member = (Members)session.getAttribute("LoginOK");
		Integer mostBuyAnimalType = 1;
		Integer mostBuyColor = 1;
		Integer page = 1;
		if (member != null) {
			mostBuyAnimalType = shoppingAanlysisService.getMostBuyAnimalType(member.getId());
			mostBuyColor = shoppingAanlysisService.getMostBuyColor(member.getId());
			
			ProductListDaoImpl.getPageOrderBy((int)(Math.random()*(5-1+1)) + 1);
		}
		
		
		list = service.getPageProducts(page, mostBuyColor, null, mostBuyAnimalType,4,"");
		return list;
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
	
	@GetMapping("/select/productsByName")
	public @ResponseBody List<Products> getQueryPage(@RequestParam(value = "keywordSearch") String keywordSearch,Model model) {
		List<Products> products = service.selectByName(keywordSearch);
		return products;
	}
	
	@GetMapping("/goLikeProductPage")
	public String goLikeProductPage() {
		return "products/ProductLike";
	}
	
	@GetMapping("/getFollowProduct.controller")
	public @ResponseBody List<Products> getFollowProduct(){
		Integer memberId = ((Members)session.getAttribute("LoginOK")).getId();
		List<Products> products = service.getLikeProductList(memberId);
		return products;
	}
	
	
	@GetMapping("/goLike")
	public @ResponseBody Integer goLike(@RequestParam("productId") Integer productId) {
		Integer memberId = ((Members)session.getAttribute("LoginOK")).getId();
		System.out.println(memberId);
		Integer status = service.changeLikeStatus(productId, memberId);
		System.out.println(status);
		FollowProducts followProduct = null;
		if (status == 2) {
			followProduct = new FollowProducts();
			followProduct.setMemberId(memberId);
			followProduct.setProductId(productId);
			followProduct.setStatus(1);
			service.saveFollowProduct(followProduct);
			return 1;
		}else if (status == 1) {
			followProduct = service.getFollowProduct(productId, memberId);
			followProduct.setStatus(0);
			service.updateFollowProductStatus(followProduct);
			return 0;
		}else {
			followProduct = service.getFollowProduct(productId, memberId);
			followProduct.setStatus(1);
			service.updateFollowProductStatus(followProduct);
			return 1;
		}
	}
	
}
