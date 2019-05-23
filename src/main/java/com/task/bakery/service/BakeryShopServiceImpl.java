package com.task.bakery.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.task.bakery.entity.Bakery;
import com.task.bakery.entity.Pack;
import com.task.bakery.util.HibernateUtil;
import com.task.bakery.util.TablesInit;

import lombok.NonNull;

public class BakeryShopServiceImpl implements BakeryShopService {
	private static Session session;

	public static void initSession() {
		session = HibernateUtil.getSession();
		session.beginTransaction();

	}

	public int getBakeryDetails(int quantity, String code) {
		// GET BAKERY BY CODE FROM THE DB
		Bakery bakery = TablesInit.getBakeryByCode(code);
		Hibernate.initialize(bakery.getPacks());
		// GET AVAILABLE PACKAGES FOR SPECIFIC CODE
//		bakery.getPacks();
		Set<Pack> packs = bakery.getPacks();
		// GET SORTED QUANTATIES FROM THE PACKAGES SET
		List<Integer> quantaties = packs.stream().map(p -> p.getQuantity()).sorted().collect(Collectors.toList());
		// GET MINIMUM QUANTATIES FROM THE AVAILABLE QUANTATIES
		List<Integer> minimumQuantaties = countPacks(quantaties, quantity);
		if (minimumQuantaties == null || minimumQuantaties.get(0) == 0) {
			System.out.println(
					"Sorry, there are no available packages for this quantity, please try to increase your quantity");
			return 0;
		}
		Map<Integer, Double> packagesPrice = new HashMap<Integer, Double>();
		double price = 0;
		for (int i = 0; i < minimumQuantaties.size(); i++) {
			int pack = minimumQuantaties.get(i).intValue();
			if (!packagesPrice.containsKey(pack)) {
				price = packs.stream().filter(p -> p.getQuantity() == pack).findFirst().get().getPrice();
				packagesPrice.put(pack, price);
			}
		}
		Map<Integer, Long> groupedPackages = minimumQuantaties.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		printMinimumPackages(packagesPrice, groupedPackages);
		return minimumQuantaties.size();
	}

	private static void printMinimumPackages(Map<Integer, Double> packagesPrice, Map<Integer, Long> groupedPackages) {
		double totalPrice = 0;
		for (Map.Entry<Integer, Long> entry : groupedPackages.entrySet()) {
			System.out.println(entry.getValue() + "*" + entry.getKey() + " " + packagesPrice.get(entry.getKey()) + "$");
			totalPrice += packagesPrice.get(entry.getKey());
		}

		System.out.println(totalPrice);
	}

	// to store package quantities
	static List<Integer> packageQuantity;
	// to store package sums
	static List<Integer> packagesSum;
	// to iterate and remove from the arr object
	static Iterator<Integer> iterator;

	// to store pack to reach
	static int tempPack;
	// to track the sum in edge cases
	static int bigNumSum = 0;

	static List<Integer> countPacks(List<Integer> packages, int quantity) {

		// to sort and reverse from biggest to smallest
		Collections.sort(packages);
		// Base case if the quantity is less than the smallest package
		if (quantity < packages.get(0)) {
			return null;
		}
		Collections.reverse(packages);

		packageQuantity = new ArrayList<>(packages);
		packagesSum = new ArrayList<Integer>();
		tempPack = quantity;

		// returning recursion
		return recursion(0, 0, 0);
	}

	/**
	 * Recursion method that finds the shortest combination of coins to reach a
	 * given value, if there is any.
	 * 
	 * @param sum
	 * @param i
	 * @param p
	 * @return
	 */
	public static List<Integer> recursion(int sum, int i, int p) {

		// successful case! Sequence of Packs is found
		if (sum == tempPack)
			return packagesSum;

		// to reshape the candidate packs when they overflow
		if (sum > tempPack) {

			if (p == packageQuantity.size() - 1) {
				packagesSum.clear();
				packagesSum.add(0);
				return packagesSum;
			}
			else if (i == packageQuantity.size() - 1) {

				int temp = packagesSum.remove(0);

				bigNumSum -= temp;
				sum = bigNumSum;
				iterator = packagesSum.iterator();

				while (iterator.hasNext()) {
					Integer a = iterator.next();
					if (a != temp)
						iterator.remove();
				}

				// Check if the Quantity is totally packed or not
				if (packagesSum.size() == 0) {
					p++;
					return recursion(sum, p, p);
				} else {
					return recursion(sum, p + 1, p);
				}
			}
			// overflow base case to delete the latest pack and adding smaller one
			else {
				if (i == p)
					bigNumSum -= packageQuantity.get(i);
				sum -= packagesSum.remove(packagesSum.size() - 1);
				return recursion(sum, ++i, p);
			}
		}
		else {
			if (p == i)
				bigNumSum += packageQuantity.get(i);
			sum += packageQuantity.get(i);
			packagesSum.add(packageQuantity.get(i));
			return recursion(sum, i, p);
		}
	}

}
