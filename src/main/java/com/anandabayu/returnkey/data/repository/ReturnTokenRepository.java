package com.anandabayu.returnkey.data.repository;

import com.anandabayu.returnkey.data.ReturnToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Repository
public interface ReturnTokenRepository extends JpaRepository<ReturnToken, Long> {
    ReturnToken findReturnTokenByToken(String token);
}
