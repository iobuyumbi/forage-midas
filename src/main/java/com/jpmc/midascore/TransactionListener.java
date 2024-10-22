package com.jpmc.midascore;

import com.jpmc.midascore.component.DatabaseConduit;
import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

//import java.util.logging.Logger;

@Service
public class TransactionListener {

//    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-consumer-group")
//    public void listen(Transaction transaction) {
//        // Log or handle the received transaction
//        System.out.println("Received transaction: " + transaction);
//    }
    private static final Logger logger = LoggerFactory.getLogger(TransactionListener.class);

    @Autowired
    private DatabaseConduit databaseConduit;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IncentiveService incentiveService;

//    @KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-consumer-group")
//    public void listen(Transaction transaction) {
//        logger.info("Received transaction: {}", transaction);
//
//        // Retrieve sender and recipient UserRecords from the database
//        UserRecord sender = userRepository.findById(transaction.getSenderId()).orElse(null);
//        UserRecord recipient = userRepository.findById(transaction.getRecipientId()).orElse(null);
//
//        // Check if both users exist and if sender has enough balance
//        if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {
//            // Get incentive amount from the Incentives API
//            Incentive incentive = incentiveService.getIncentive(transaction);
//            float incentiveAmount = (float) incentive.getAmount();
//
//            // Create and save a new TransactionRecord
//            TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount());
//            databaseConduit.saveTransaction(transactionRecord);
//
//            // Update sender and recipient balances
//            sender.setBalance(sender.getBalance() - transaction.getAmount());
//            recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);
//
//            // Save updated user records
//            userRepository.save(sender);
//            userRepository.save(recipient);
//
//            logger.info("Transaction processed successfully: {}", transaction);
//        } else {
//            // If transaction is invalid, log the issue
//            logger.info("Transaction discarded: {}", transaction);
//        }
//    }
@KafkaListener(topics = "${general.kafka-topic}", groupId = "midas-consumer-group")
public void listen(Transaction transaction) {
    logger.info("Received transaction: {}", transaction);

    // Retrieve sender and recipient UserRecords from the database
    UserRecord sender = userRepository.findById(transaction.getSenderId()).orElse(null);
    UserRecord recipient = userRepository.findById(transaction.getRecipientId()).orElse(null);

    // Check if both users exist and if sender has enough balance
    if (sender != null && recipient != null && sender.getBalance() >= transaction.getAmount()) {
        // Get incentive amount from the Incentives API
        Incentive incentive = incentiveService.getIncentive(transaction);
        float incentiveAmount = (float) incentive.getAmount();

        // Create and save a new TransactionRecord
        TransactionRecord transactionRecord = new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount);
        databaseConduit.saveTransaction(transactionRecord);

        // Update sender and recipient balances
        sender.setBalance(sender.getBalance() - transaction.getAmount());
        recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

        // Save updated user records
        userRepository.save(sender);
        userRepository.save(recipient);

        logger.info("Transaction processed successfully: {}", transaction);
    } else {
        // If transaction is invalid, log the issue
        logger.info("Transaction discarded: {}", transaction);
    }
}

}
