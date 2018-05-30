package com.tom.clientmgr.repository;

import com.tom.clientmgr.domian.OauthClientDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthClientDetailsRepository extends JpaRepository<OauthClientDetails,String> {

}
