package com.model2.mvc.web.purchase;

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
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;

//상풍관리 Controller
@RestController
@RequestMapping("/purchase/*")
public class PurchaseRestController {
	
	//Field
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	@Autowired
	@Qualifier("ProductServiceImpl")
	private ProductService productService;
	//setter Method 구현 X
	
	public PurchaseRestController() {
		System.out.println(this.getClass());
		//getClass() : 객체가 속하는 클래스의 정보를 알아내는 메소드이다.
	}
	
	// @Value : properties 파일에 세팅한 내용을 Spring 변수에 주입
	@Value("#{commonProperties['pageUnit']}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	int pageSize;
	
//	@RequestMapping("/addPurchase.do")
	@RequestMapping(value="json/addPurchase", method=RequestMethod.POST)
	public Purchase addPurchase(@RequestBody Purchase purchase, HttpSession session, @PathVariable int prodNo) throws Exception {
		System.out.println("/purchase/addPurchase : POST");
		
		User user = (User) session.getAttribute("user");
		Product product = new Product();
		product.setProdNo(prodNo);
		
		//puchase 설정
		purchase.setBuyerId(user);
		purchase.setPurchaseProd(product);
		purchase.setTranCode("1");
		
		//실행
		purchaseService.addPurchase(purchase);
		
		return purchase;
	}
	
	
//	  @RequestMapping("/addPurchaseView.do")
//	  @RequestMapping(value="addPurchaseView", method=RequestMethod.GET)
//	  public ModelAndView addPurchaseView(@RequestParam("prod_no") int prodNo)throws Exception{ 
//	  Product product = productService.getProduct(prodNo);
//	  
//	  ModelAndView modelAndView = new ModelAndView("forward:/purchase/addPurchase.jsp");
//	  modelAndView.addObject("product",product);
//	  
//	  	return modelAndView; 
//	  }
	  
//	  @RequestMapping("/getPurchase.do")
	  @RequestMapping(value="json/getPurchase/{tranNo}", method=RequestMethod.GET)
		public Purchase getPurchase(@PathVariable int tranNo
										) throws Exception{
			System.out.println("/purchase/json/getPurchase : GET");
//			ModelAndView modelAndView = new ModelAndView();
//			modelAndView.addObject("purchase", purchaseService.getPurchase(tranNo));
//			
//			modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
//			return modelAndView;
		  return purchaseService.getPurchase(tranNo);
		}

//		@RequestMapping("/updatePurchaseView.do")
//		@RequestMapping(value="updatePurchaseView", method=RequestMethod.GET)
//		public ModelAndView updatePurchaseView(@RequestParam("tranNo")int tranNo,Map<String, Object> map) throws Exception{
//			ModelAndView modelAndView = new ModelAndView();
//			modelAndView.addObject("purchase", purchaseService.getPurchase(tranNo));
//			System.out.println(modelAndView+"updatePurchaseView");
//			
//			modelAndView.setViewName("forward:/purchase/updatePurchase.jsp");
//			return modelAndView;
//		}
		
//		@RequestMapping("/updatePurchase.do")
		@RequestMapping(value="json/updatePurchase", method=RequestMethod.POST)
		public Purchase updatePurchase(@PathVariable int tranNo
											,@RequestBody Purchase purchase
											) throws Exception{
			System.out.println("/purchase/updatePurchase : POST");
			purchaseService.updatePurchase(purchase);
			
//			ModelAndView modelAndView = new ModelAndView();
//			modelAndView.setViewName("redirect:/purchase/getPurchase?tranNo="+tranNo);
			
			return purchase;
		}
		
//		@RequestMapping("/updateTranCode.do")
//		@RequestMapping(value="json/updateTranCode", method=RequestMethod.POST)
//		public Purchase updateTranCode(@RequestBody Purchase purchase , @RequestBody Product product,HttpServletRequest request) throws Exception{
//			
//			System.out.println("/purchase/json/updateTranCode : POST");
//			
//			product.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
//			purchase.setPurchaseProd(product);
			
//			String tranCode = request.getParameter("tranCode").trim();
//			purchaseService.updateTranCode(purchase);
			
//			ModelAndView modelAndView = new ModelAndView();
//			if(tranCode.equals("1")) {
//				modelAndView.setViewName("redirect:/product/listProduct?menu=manage");
//				
//			}else {
//				modelAndView.setViewName("redirect:/product/listProduct?menu=search");
//			}
//			return modelAndView;
//			return purchase;
//		}
//		
//		@RequestMapping("/listPurchase.do")
		@RequestMapping(value="json/listPurchase")
		public Map listPurchase(@RequestBody Search search
										,HttpSession session
										) throws Exception{
			System.out.println("/purchase/json/listPurchase :GET/POST");
			
			if(search.getCurrentPage()==0){
				search.setCurrentPage(1);
			}
			search.setPageSize(pageSize);
			
			User user = (User)session.getAttribute("user");
			Map<String, Object> map = purchaseService.getPurchaseList(search, user.getUserId());
			Page resultPage = new Page(search.getCurrentPage()
					, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
			
			System.out.println(resultPage);
//			ModelAndView modelAndView = new ModelAndView();
//			modelAndView.addObject("list",map.get("list"));
//			modelAndView.addObject("resultPage",resultPage);
//			modelAndView.addObject("search",search);
//			
//			modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
//			return modelAndView;
			return map;
		}
	
}
