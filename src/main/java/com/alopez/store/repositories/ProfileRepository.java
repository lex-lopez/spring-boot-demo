package com.alopez.store.repositories;

import com.alopez.store.dto.UserSummary;
import com.alopez.store.entities.Profile;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

    @EntityGraph(attributePaths = "user") // to fix N+1 problem
    @Query("select p.id as id, p.user.email as email from Profile p where p.loyaltyPoints > :loyaltyPoints order by p.user.email desc")
    List<UserSummary> findByLoyaltyPointsGreaterThan(Integer loyaltyPoints);
}
