package test.report.service.impl;

import test.report.model.Transactions;
import test.report.repository.TransactionsRepository;
import test.report.service.TransactionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionsServiceImpl implements TransactionsService {

    @Autowired
    private TransactionsRepository transactionsRepository;

    @Override
    public List<Transactions> getAll() {
        return transactionsRepository.findAll();
    }
}
