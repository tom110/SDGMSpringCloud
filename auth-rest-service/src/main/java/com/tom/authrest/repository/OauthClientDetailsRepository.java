package com.tom.authrest.repository;

import com.tom.authrest.domian.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails,String> {

}
