package com.devsuperior.dsmeta.repositories;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projections.SaleWithSellerNameProjection;
import com.devsuperior.dsmeta.projections.SummaryProjection;

public interface SaleRepository extends JpaRepository<Sale, Long> {
	
	@Query(nativeQuery = true, value = """
		    SELECT TB_SALES.ID, TB_SALES.DATE, TB_SALES.AMOUNT, TB_SELLER.NAME
		    FROM TB_SALES
		    LEFT JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID
		    WHERE (TB_SELLER.NAME IS NULL OR UPPER(TB_SELLER.NAME) LIKE UPPER(:name))
		      AND TB_SALES.DATE >= :minDate
		      AND TB_SALES.DATE <= :maxDate
		    """)
		Page<SaleWithSellerNameProjection> search1(String name, LocalDate minDate, LocalDate maxDate, Pageable pageable);
	
	@Query(nativeQuery = true, 
		       value = """
		           SELECT TB_SALES.ID, TB_SALES.DATE, TB_SALES.AMOUNT, TB_SELLER.NAME
		           FROM TB_SALES
		           LEFT JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID
		           WHERE TB_SALES.DATE >= :minDate AND TB_SALES.DATE <= :maxDate
		       """,
		       countQuery = """
		           SELECT COUNT(*) 
		           FROM TB_SALES
		           LEFT JOIN TB_SELLER ON TB_SELLER.ID = TB_SALES.SELLER_ID
		           WHERE TB_SALES.DATE >= :minDate AND TB_SALES.DATE <= :maxDate
		       """)
		Page<SaleWithSellerNameProjection> search2(LocalDate minDate, LocalDate maxDate, Pageable pageable);
	
	
	@Query(nativeQuery = true, value = " SELECT s.name AS sellerName, SUM(sa.amount) AS total "
			+ " FROM tb_sales sa JOIN tb_seller s ON sa.seller_id = s.id"
			+ " WHERE sa.date BETWEEN :minDate AND :maxDate GROUP BY s.name ORDER BY s.name ASC ")
	Page<SummaryProjection> sumary(LocalDate minDate, LocalDate maxDate, Pageable pageable);

}

