package com.tcs.casestudy.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tcs.casestudy.exceptions.ProductIdNotFoundException;
import com.tcs.casestudy.exceptions.StockIdNotFoundException;
import com.tcs.casestudy.models.Stock;
import com.tcs.casestudy.repositories.StockRepository;

@Service
public class StockServiceImpl implements StockService {
	
	@Autowired
	private StockRepository dao;

	@Override
	public Stock addStock(Stock stock) throws ProductIdNotFoundException {
		Stock ret = null;
		
		// First, check to see that the referenced Product ID exists
		if(dao.productExists(stock.getProductId()) == 0)
			throw new ProductIdNotFoundException(String.format(
					"No Product found with ID: %d , Stock with ID: %d not added",
					stock.getProductId(),
					stock.getStockId()));
		
		// If it does, let's save it
		try {
			ret = dao.save(stock);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public Stock updateStock(long id, Stock updateStock) throws StockIdNotFoundException {
		Stock ret = null;
		
		// Check to see if the Stock entry exists
		if(!dao.existsById(id))
			throw new StockIdNotFoundException(String.format(
					"No Stock found with ID: %d , update unsuccessful",
					id));
		
		// If it does, update it
		try {
			updateStock.setStockId(id);
			dao.save(updateStock);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return ret;
	}

	@Override
	public String deleteStock(long id) {
		try {
			dao.deleteById(id);
			return "success";
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		return "failure";
	}

	@Override
	public Optional<Stock> findById(long id) {
		return dao.findById(id);
	}

	@Override
	public Optional<List<Stock>> getStocks() {
		return Optional.ofNullable(dao.findAll());
	}

	@Override
	public Optional<List<Stock>> getStocksByProductId(long id) {
		return Optional.ofNullable(dao.getStocksByProductId(id));
	}

}
