import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.task.bakery.service.BakeryShopServiceImpl;
import com.task.bakery.util.TablesInit;

public class BakeryOrderTest {
	@Test
	public void whenPacksExactThenReturnMinimumPacks() {
		TablesInit tablesInit = new TablesInit();
		BakeryShopServiceImpl bakeryShopServiceImpl = new BakeryShopServiceImpl();
		assertEquals(2, bakeryShopServiceImpl.getBakeryDetails(10, "VS5"));
	}

}
