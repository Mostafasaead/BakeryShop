package com.task.bakery;

import java.util.Scanner;

import com.task.bakery.service.BakeryShopService;
import com.task.bakery.service.BakeryShopServiceImpl;
import com.task.bakery.util.HibernateUtil;
import com.task.bakery.util.TablesInit;

public class BakeryShop {
	public static void main(String[] args) {
		TablesInit tableInit = new TablesInit();
		BakeryShopService bakeryShopService = new BakeryShopServiceImpl();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter your desired bakery and the quantity with space like (10 VS5)");
		String quantityCode = sc.nextLine();
		int quantity = Integer.valueOf(quantityCode.split(" ")[0]);
		String Code = quantityCode.split(" ")[1];
		bakeryShopService.getBakeryDetails(quantity, Code);
		HibernateUtil.shutdown();
	}
}
