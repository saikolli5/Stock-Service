package com.tcs.casestudy.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tcs.casestudy.models.Stock;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

	/**
	 * Used when creating a new Stock entry. Checks to see if a Product entry with the referenced productId exists.
	 * @param productId		The productId (long) to search for.
	 * @return 1 if the Product exists, 0 if not.
	 */
	@Query(value = "SELECT EXISTS(SELECT 1 FROM product WHERE product_id = ?1)", nativeQuery = true)
	public int productExists(long productId);
	
	/**
	 * Retrieves all Stock entries corresponding to a productId.
	 * @param id			The id (long) of the Product to search for.
	 * @return a List of Stock objects corresponding to the given ID.
	 */
	@Query(value = "SELECT * FROM stock WHERE product_id = ?1", nativeQuery = true)
	public List<Stock> getStocksByProductId(long id);
	
}
