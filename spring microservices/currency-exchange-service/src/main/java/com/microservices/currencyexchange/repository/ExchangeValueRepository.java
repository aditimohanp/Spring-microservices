package com.microservices.currencyexchange.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservices.currencyexchange.model.ExchangeValue;
@Repository
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
   ExchangeValue findByFromAndTo(String from, String to);
}
