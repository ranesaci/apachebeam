package podium.ppi.lib.jar;


/**
* @author sachin
* @date 17-Nov-2021
*/

import java.io.Serializable;
import java.util.Objects;

public class Stock implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String symbol;
	private long price;
	private String company;
	
	public Stock() {}
	
	public Stock(String symbol, long price, String company) {
		this.symbol = symbol;
		this.price = price;
		this.company = company;
	}
	
	public Stock(Stock stock) {
		this.symbol = stock.getSymbol();
		this.price = stock.getPrice();
		this.company = stock.getCompany();
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}

	@Override
	public int hashCode() {
		return Objects.hash(company, price, symbol);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(company, other.company) && price == other.price && Objects.equals(symbol, other.symbol);
	}

	@Override
	public String toString() {
		return "Stock [symbol=" + symbol + ", price=" + price + ", company=" + company + "]";
	}
	
	
	
}

