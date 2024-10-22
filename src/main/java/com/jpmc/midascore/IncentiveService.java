package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class IncentiveService {

    private static final String INCENTIVE_API_URL = "http://localhost:8080/incentive";

    @Autowired
    private RestTemplate restTemplate;

    public Incentive getIncentive(Transaction transaction){
        return restTemplate.postForObject(INCENTIVE_API_URL, transaction, Incentive.class);

    }
}
