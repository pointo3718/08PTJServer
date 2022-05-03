package com.model2.mvc.service.purchase.test;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.PurchaseService;

/*
 *	FileName :  UserServiceTest.java
 * �� JUnit4 (Test Framework) �� Spring Framework ���� Test( Unit Test)
 * �� Spring �� JUnit 4�� ���� ���� Ŭ������ ���� ������ ��� ���� �׽�Ʈ �ڵ带 �ۼ� �� �� �ִ�.
 * �� @RunWith : Meta-data �� ���� wiring(����,DI) �� ��ü ����ü ����
 * �� @ContextConfiguration : Meta-data location ����
 * �� @Test : �׽�Ʈ ���� �ҽ� ����
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
@ContextConfiguration	(locations = {	"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class PurchaseServiceTest {

	//==>@RunWith,@ContextConfiguration �̿� Wiring, Test �� instance DI
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	//@Test
	public void testAddPurchase() throws Exception {
		Product product = new Product();
		product.setProdNo(10003);
		
		User user = new User();
		user.setUserId("user02");
		
		Purchase purchase = new Purchase();
		purchase.setPurchaseProd(product);
		purchase.setBuyerId(user);
		purchase.setDivyRequest("����");
		purchase.setDivyDate("20220401");
		
		purchaseService.addPurchase(purchase);
		
		System.out.println(purchase);
		
		//Assert.assertEquals();
	}
	@Test
	public void testGetPurchase() throws Exception {
		Purchase purchase = purchaseService.getPurchase(10000);
		System.out.println(purchase);
	}
	
	//@Test
	public void testUpdatePurchase() throws Exception{
		Purchase purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		System.out.println("purchase update start"+purchase);
		
		Assert.assertEquals("����� ���ϱ� ", purchase.getDivyAddr());
		
		purchase.setDivyAddr("���ϱ�");
		
		System.out.println("purchase update end"+purchase);
		purchaseService.updatePurchase(purchase);
		
		purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		
		System.out.println(purchase);
//		Assert.assertEquals("�ٲ�", purchase.getDivyRequest());
	}
//	
	//@Test	
	public void testUpdateTranCode() throws Exception{
		Purchase purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		System.out.println("purchase update start"+purchase);
		
		Assert.assertEquals("3  ", purchase.getTranCode());
		
		purchase.setTranCode("2");
		
		System.out.println("purchase update end"+purchase);
		purchaseService.updateTranCode(purchase);
		
		purchase = purchaseService.getPurchase(10000);
		Assert.assertNotNull(purchase);
		
		System.out.println(purchase);
	}
//	

//	
//	 //==>  �ּ��� Ǯ�� �����ϸ�....
	 @Test
	 public void testGetPurchaseListAll() throws Exception{
//		Purchase purchase = new Purchase();
//		purchase.getBuyerId();
	 	Search search = new Search();
	 	search.setCurrentPage(1);
	 	search.setPageSize(3);
	 	Map<String,Object> map = purchaseService.getPurchaseList(search,"user01");
	 	
	 	List<Object> list = (List<Object>)map.get("list");
	 	for(int i=0;i<list.size();i++){
	 		System.out.println(list.get(i));
	 	}
	 }

}