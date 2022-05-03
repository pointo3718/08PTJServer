package com.model2.mvc.web.product;

import java.awt.Menu;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

//상풍관리 Controller
@RestController
@RequestMapping("/product/*")
public class ProductRestController {
	
	//Field
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	//setter Method 구현 X
	
	public ProductRestController() {
		System.out.println(this.getClass());
		//getClass() : 객체가 속하는 클래스의 정보를 알아내는 메소드이다.
	}
	
	// @Value : properties 파일에 세팅한 내용을 Spring 변수에 주입
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
	@RequestMapping(value="json/addProduct", method=RequestMethod.POST)
	public Product addProduct(@RequestBody Product product ) throws Exception{

		System.out.println("/product/addProduct : POST");
//		System.out.println(product);
		product.setManuDate((product.getManuDate()).replace("-", ""));
		productService.addProduct(product);
//		System.out.println(product);
		
		return product;
	}
	
	@RequestMapping( value="json/getProduct/{prodNo}", method=RequestMethod.GET)
	public Product getProduct(@PathVariable int prodNo ) throws Exception {
		
		System.out.println("/product/json/getProduct : GET");
		
		return productService.getProduct(prodNo);
	}
	
	@RequestMapping( value="json/updateProduct", method=RequestMethod.POST)
	public Product updateProduct(@RequestBody Product product) throws Exception{
	
		System.out.println("/product/updateProduct : POST");
		product.setManuDate((product.getManuDate()).replace("-", ""));
		productService.updateProduct(product);
		System.out.println(product);
		
		return product;
	}
	
	@RequestMapping( value="json/listProduct")
	public Map listProduct(@RequestBody Search search ,HttpServletRequest request) throws Exception {
		
		System.out.println("/product/json/listProduct :GET/POST");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);	
		
		Map<String , Object> map = productService.getProductList(search);
		
		Page resultPage	= new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		return map;
	}

}
