package com.task.bakery.util;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.task.bakery.entity.Bakery;
import com.task.bakery.entity.Pack;

public class TablesInit {
	private static Session session;

	public TablesInit() {
		CreatMockTables();
	}

	public static void initSession() {
		session = HibernateUtil.getSession();
		session.beginTransaction();
	}

	private void CreatMockTables() {

		initSession();
		Transaction tx = session.getTransaction();
		CreateMB11Mock();
		CreateCFMock();
		CreateVS5Mock();

		// Commit the transaction
		tx.commit();

	}

	private void CreateVS5Mock() {

		// Add new Bakery object
		Bakery bakery = new Bakery();

		bakery.setName("Vegemite Scroll");
		bakery.setCode("VS5");
		Set<Pack> packs = new HashSet<Pack>();

		Pack pack_1 = new Pack();
		pack_1.setCode("VS5");
		pack_1.setPrice(6.99);
		pack_1.setQuantity(3);

		Pack pack_2 = new Pack();
		pack_2.setCode("VS5");
		pack_2.setPrice(8.99);
		pack_2.setQuantity(5);

		packs.add(pack_1);
		packs.add(pack_2);
		bakery.setPacks(packs);
		session.save(bakery);
	}

	private void CreateMB11Mock() {

		// Add new Bakery object
		Bakery bakery = new Bakery();

		bakery.setName("Blueberry Muffin");
		bakery.setCode("MB11");
		Set<Pack> packs = new HashSet<Pack>();

		Pack pack_1 = new Pack();
		pack_1.setCode("MB11");
		pack_1.setPrice(9.95);
		pack_1.setQuantity(2);

		Pack pack_2 = new Pack();
		pack_2.setCode("MB11");
		pack_2.setPrice(16.95);
		pack_2.setQuantity(5);

		Pack pack_3 = new Pack();
		pack_3.setCode("MB11");
		pack_3.setPrice(24.95);
		pack_3.setQuantity(8);

		packs.add(pack_1);
		packs.add(pack_2);
		packs.add(pack_3);
		bakery.setPacks(packs);
		session.save(bakery);
	}

	private void CreateCFMock() {

		// Add new Bakery object
		Bakery bakery = new Bakery();

		bakery.setName("Croissant");
		bakery.setCode("CF");
		Set<Pack> packs = new HashSet<Pack>();

		Pack pack_1 = new Pack();
		pack_1.setCode("CF");
		pack_1.setPrice(5.95);
		pack_1.setQuantity(3);

		Pack pack_2 = new Pack();
		pack_2.setCode("CF");
		pack_2.setPrice(9.95);
		pack_2.setQuantity(5);

		Pack pack_3 = new Pack();
		pack_3.setCode("CF");
		pack_3.setPrice(16.99);
		pack_3.setQuantity(9);

		packs.add(pack_1);
		packs.add(pack_2);
		packs.add(pack_3);
		bakery.setPacks(packs);
		session.save(bakery);
	}

	public static Bakery getBakeryByCode(String code) {
		return session.byNaturalId(Bakery.class).using("code", code).load();
	}
}
