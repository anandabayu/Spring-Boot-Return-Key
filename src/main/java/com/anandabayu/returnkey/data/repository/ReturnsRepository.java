package com.anandabayu.returnkey.data.repository;

import com.anandabayu.returnkey.data.Returns;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : Bayu
 * @since : 27/08/22, Saturday
 **/
@Repository
public interface ReturnsRepository extends JpaRepository<Returns, Long> {
}
