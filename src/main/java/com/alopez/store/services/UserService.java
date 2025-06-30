package com.alopez.store.services;

import com.alopez.store.entities.Address;
import com.alopez.store.entities.Product;
import com.alopez.store.entities.User;
import com.alopez.store.repositories.*;
import com.alopez.store.repositories.specifications.ProductSpec;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/*
 This class is used to demonstrate the persistence context with the Entity Manager
 */
@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final AddressRepository addressRepository;
    private final EntityManager entityManager;
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public void showEntityStates() {
        var user = User.builder()
                .name("John Doe")
                .email("john.doe@gmail.com")
                .password("pwd")
                .build();

        if (entityManager.contains(user)) {
            System.out.println("Persistent");
        } else {
            System.out.println("Transient / Detached");
        }

        userRepository.save(user);

        if (entityManager.contains(user)) {
            System.out.println("Persistent");
        } else {
            System.out.println("Transient / Detached");
        }
    }

    @Transactional
    public void showRelatedEntities() {
        var profile = profileRepository.findById(2L).orElseThrow();
        System.out.println(profile.getUser().getEmail());
    }

    public void fetchAddress() {
        var address = addressRepository.findById(1L).orElseThrow();
        System.out.println(address.getCity());
    }

    public void persistRelated() {
        var user = User.builder()
                .name("Ale")
                .email("ale@gmail.com")
                .password("pwd")
                .build();

        var address = Address.builder()
                .street("ignacio")
                .city("morelia")
                .state("mx")
                .zip("58179")
                .build();

        user.addAddress(address);

        userRepository.save(user);
    }

    public void deleteRelates() {
        userRepository.deleteById(19L);
    }

    @Transactional // when working with custom queries, any update operation needs to be wrapped in transactional
    public void updateProductPrices() {
        productRepository.updatePriceByCategory(BigDecimal.valueOf(10.99), (byte)1);
    }

    public void fetchProducts() {
        var products = new Product();
        products.setName("product");

        var matcher = ExampleMatcher.matching()
                .withIncludeNullValues() // modifies the query to include null values
                .withIgnorePaths("id", "description", "category", "price") // ignores columns to compare in where clause
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        var example = Example.of(products, matcher);
        productRepository.findAll(example).forEach(System.out::println);
    }

    public void fetchProductsByCriteria() {
        var products = productRepository.findProductsByCriteria("prod", BigDecimal.valueOf(6), null);
        products.forEach(System.out::println);
    }

    @Transactional
    public void fetchUsers() {
        // this shows the N+1 problem
        //var users = userRepository.findAll();
        var users = userRepository.findAllWithAddresses();
        users.forEach(u -> {
            System.out.println(u);
            u.getAddresses().forEach(System.out::println);
        });
    }

    @Transactional
    public void fetchProfiles() {
        //var profiles = profileRepository.findByLoyaltyPointsGreaterThan(8);
        var users = userRepository.findLoyalUsers(2);

        users.forEach(System.out::println);
//        profiles.forEach(p -> {
//            System.out.println(p.getId());
//            System.out.println(p.getUser().getEmail());
//        });
    }

    public void fetchProductsBySpecifications(String name, BigDecimal minPrice, BigDecimal maxPrice) {
        Specification<Product> spec = Specification.where(null);

        if (name != null) {
            spec = spec.and(ProductSpec.hasName(name));
        }
        if (minPrice != null) {
            spec = spec.and(ProductSpec.hasPriceGreaterThanOrEqualTo(minPrice));
        }
        if (maxPrice != null) {
            spec = spec.and(ProductSpec.hasPriceLessThanOrEqualTo(maxPrice));
        }

        productRepository.findAll(spec).forEach(System.out::println);
    }

    public void fetchSortedProducts() {
        var sort = Sort.by("name").and(
                Sort.by("price").descending()
        );

        productRepository.findAll(sort).forEach(System.out::println);
    }

    public void fetchPaginatedProducts(int pageNumber, int size) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size);
        Page<Product> page = productRepository.findAll(pageRequest);

        var products = page.getContent();
        products.forEach(System.out::println);

        var totalPages = page.getTotalPages();
        var totalElements = page.getTotalElements();
        System.out.println("Total pages: " + totalPages);
        System.out.println("Total elements: " + totalElements);
    }
}
