package com.game.snakeeyes.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BalanceRepository extends ReactiveMongoRepository<BalanceDocument, String> {}
