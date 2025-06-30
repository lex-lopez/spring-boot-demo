package com.alopez.store;

import com.alopez.store.entities.User;
import com.alopez.store.repositories.UserRepository;
import com.alopez.store.services.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(StoreApplication.class, args);
		var services = context.getBean(UserService.class);

		//services.showEntityStates();
		//services.showRelatedEntities();
		//services.fetchAddress();
		//services.persistRelated();
		//services.deleteRelates();
		//services.updateProductPrices();
		//services.fetchProducts();

		//services.fetchUsers();
		//ervices.fetchProfiles();

		//services.fetchProducts();
		//services.fetchProductsByCriteria();

		//services.fetchProductsBySpecifications("prod", BigDecimal.valueOf(1), null);
		services.fetchSortedProducts();
		services.fetchPaginatedProducts(0, 10);
	}

	public static void crudUserTest(ApplicationContext context) {
		var repository = context.getBean(UserRepository.class);

		// create one user
		var user = User.builder()
				.name("John")
				.email("john@gmail.com")
				.password("pwd")
				.build();

		repository.save(user);

		// retrieve one single user
		var userRead = repository.findById(1L).orElseThrow();
		System.out.println(userRead.getEmail());

		// retrieve all users
		repository.findAll().forEach(u -> System.out.println(u.getEmail()));

		//remove user
		repository.deleteById(1L);
	}

}
