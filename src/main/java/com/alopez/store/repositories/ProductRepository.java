package com.alopez.store.repositories;

import com.alopez.store.dto.ProductSummary;
import com.alopez.store.entities.Category;
import com.alopez.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductCriteriaRepository, JpaSpecificationExecutor<Product> {

    // String
    List<Product> findByName(String name);
    List<Product> findByNameLike(String name);
    List<Product> findByNameNotLike(String name);
    List<Product> findByNameContaining(String name);
    List<Product> findByNameStartingWith(String name);
    List<Product> findByNameEndingWith(String name);
    List<Product> findByNameEndingWithIgnoreCase(String name);

    // Numbers
    List<Product> findByPrice(BigDecimal price);
    List<Product> findByPriceGreaterThan(BigDecimal price);
    List<Product> findByPriceGreaterThanEqual(BigDecimal price);
    List<Product> findByPriceLessThan(BigDecimal price);
    List<Product> findByPriceBetween(BigDecimal min, BigDecimal max);

    // Null
    List<Product> findByDescriptionIsNull();
    List<Product> findByDescriptionIsNotNull();

    // Multiple conditions
    List<Product> findByPriceGreaterThanEqualAndNameLike(BigDecimal price, String name);

    // Sort (Order by)
    List<Product> findByNameOrderByPriceAsc(String name);
    List<Product> findByNameOrderByPriceDesc(String name);

    // Limit
    List<Product> findTop5ByNameOrderByPriceAsc(String name);
    List<Product> findFirst5ByNameLikeOrderByPriceAsc(String name);

    // Find products whose prices are in a given range and sort by name
    // SQL or JPQL
    // SQL
    //@Procedure(name = "find_products_by_price") // call store proc
    //List<Product> findProducts(BigDecimal min, BigDecimal max);
    @Query(value = "select * from products p where p.price between :min and :max order by p.name", nativeQuery = true)
    List<Product> findProducts(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    // JPQL
    @Query("select p from Product p join p.category where p.price between :min and :max order by p.name")
    List<Product> findProductsJPQL(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Query("select count(p) from Product p where p.price between :min and :max")
    long countProduct(@Param("min") BigDecimal min, @Param("max") BigDecimal max);

    @Modifying //we want to tell Hibernate this is an update operation, not a select operation
    @Query("update Product p set p.price = :newPrice where p.category.id = :categoryId")
    void updatePriceByCategory(BigDecimal newPrice, Byte categoryId);

    // this will get a projection defined in ProductSummary
    // change between ProductSummaryDTO or ProductSummary
    List<ProductSummary> findByCategory(Category category);

}