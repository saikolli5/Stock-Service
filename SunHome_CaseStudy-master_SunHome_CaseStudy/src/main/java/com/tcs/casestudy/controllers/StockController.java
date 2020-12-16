package com.tcs.casestudy.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcs.casestudy.exceptions.ProductIdNotFoundException;
import com.tcs.casestudy.exceptions.StockIdNotFoundException;
import com.tcs.casestudy.models.Stock;
import com.tcs.casestudy.services.StockService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/stock")
public class StockController {
	@Autowired
	StockService stockService;
	
	@GetMapping("/all")
	public List<Stock> getStocks() {
		return stockService.getStocks().get();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Stock> getStockById(@PathVariable("id") int stockId)  throws StockIdNotFoundException{
		
		Stock stock= stockService.findById(stockId).orElseThrow(()-> new StockIdNotFoundException("Stock not found"));
		return ResponseEntity.ok().body(stock);	
	}
	
	@GetMapping("/byProduct/{id}")
	public List<Stock> getStockByProductId(@PathVariable("id") int productId) throws StockIdNotFoundException{
		return stockService.getStocksByProductId(productId).get();
	}
	
	@PostMapping
	public ResponseEntity<?> createStock(@RequestBody Stock stock, UriComponentsBuilder uriComponentsBuilder,HttpServletRequest request) {
		Stock stock2 = null;
		try {
			stock2 = stockService.addStock(stock);
		} catch (ProductIdNotFoundException e) {
			e.printStackTrace();
		}
		UriComponents uriComponents = uriComponentsBuilder.path(request.getRequestURI()+"/{id}").buildAndExpand(stock2.getStockId());
		return ResponseEntity.created(uriComponents.toUri()).body(stock2);		
	}
	
	@DeleteMapping("/{id}")
	public Map<String, Boolean> deleteStockById(@PathVariable int id) throws StockIdNotFoundException{
		stockService.findById(id).orElseThrow(()-> new StockIdNotFoundException("Stock not found"));		
		stockService.deleteStock(id);		
		HashMap<String, Boolean> hashMap=new HashMap<>();
		hashMap.put("deleted", true);
		return hashMap;
	}
	@PutMapping("/{id}")
	public ResponseEntity<Stock> updateStock(@PathVariable("id") int id,
		@Valid @RequestBody	Stock stock) throws StockIdNotFoundException{
		
		stockService.findById(id).orElseThrow(()-> new StockIdNotFoundException("Stock not found"));
		stock.setStockId((long) id);
		Stock stock3 = null;
		try {
			stock3 = stockService.addStock(stock);
		} catch (ProductIdNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok(stock3);
	}
}