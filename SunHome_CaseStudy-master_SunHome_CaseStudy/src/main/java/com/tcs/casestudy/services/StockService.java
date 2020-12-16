package com.tcs.casestudy.services;

import java.util.List;
import java.util.Optional;

import com.tcs.casestudy.exceptions.ProductIdNotFoundException;
import com.tcs.casestudy.exceptions.StockIdNotFoundException;
import com.tcs.casestudy.models.Stock;

public interface StockService {
	public Stock addStock(Stock stock) throws ProductIdNotFoundException;
	public Stock updateStock(long id, Stock updateStock) throws StockIdNotFoundException;
	public String deleteStock(long id);
	public Optional<Stock> findById(long id);
	public Optional<List<Stock>> getStocks();
	public Optional<List<Stock>> getStocksByProductId(long id);
}
