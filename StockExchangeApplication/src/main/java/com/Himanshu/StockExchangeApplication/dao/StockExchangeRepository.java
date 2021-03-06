package com.Himanshu.StockExchangeApplication.dao;

import com.Himanshu.StockExchangeApplication.model.StockExchange;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StockExchangeRepository extends MongoRepository<StockExchange, String>
{
    public StockExchange findById(int id);
    public StockExchange findByName(String name);
}
