package ru.desiolab.money.transfer.controller;

import lombok.extern.slf4j.Slf4j;
import ru.desiolab.money.transfer.dto.AccountTransferRequest;
import ru.desiolab.money.transfer.error.InvalidRequestException;
import ru.desiolab.money.transfer.service.AccountTransferService;

import javax.inject.Inject;
import java.math.BigDecimal;

@Slf4j
public class AccountTransferController {

    private final AccountTransferService accountTransferService;

    @Inject
    private AccountTransferController(AccountTransferService accountTransferService) {
        this.accountTransferService = accountTransferService;
    }

    public void transferMoney(AccountTransferRequest request) throws Exception {
        try {
            log.info("transferMoney.in");
            validate(request);
            accountTransferService.transferMoney(request);
            log.info("transferMoney.out");
        } catch (Exception e) {
            log.error("transferMoney.err", e);
            throw e;
        }
    }

    private void validate(AccountTransferRequest request) {
        if (request.fromAccountId() == null || request.toAccountId() == null || request.amount() == null) {
            throw new InvalidRequestException("fromAccountId, toAccountId, amount is required parameters");
        }
        if (request.amount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidRequestException("You can transfer only positive amount of money");
        }
    }
}
