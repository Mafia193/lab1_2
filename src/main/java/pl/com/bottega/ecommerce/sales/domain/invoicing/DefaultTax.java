package pl.com.bottega.ecommerce.sales.domain.invoicing;

import pl.com.bottega.ecommerce.sharedkernel.Money;

import java.math.BigDecimal;

public class DefaultTax implements Tax{

	protected Money amount = null;

	protected String description = null;

	protected RequestItem item;

	public DefaultTax(RequestItem item) {
		super();
		this.item = item;
	}

	public Money getAmount() {
		return amount;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public void calculateTax() {
		String desc = null;
		Money net = item.getTotalCost();
		BigDecimal ratio = null;

		switch (item.getProductData().getType()) {
			case DRUG:
				ratio = BigDecimal.valueOf(0.05);
				desc = "5% (D)";
				break;
			case FOOD:
				ratio = BigDecimal.valueOf(0.07);
				desc = "7% (F)";
				break;
			case STANDARD:
				ratio = BigDecimal.valueOf(0.23);
				desc = "23%";
				break;

			default:
				throw new IllegalArgumentException(item.getProductData().getType() + " not handled");
		}

		amount = net.multiplyBy(ratio);
		description = desc;
	}
}
