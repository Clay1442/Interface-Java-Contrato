package model.services;

import model.entities.Contract;
import model.entities.Installment;

import java.time.LocalDate;
import java.util.Date;


public class ContractService {

    private OnlinePaymentService onlinePaymentService;

    public ContractService(OnlinePaymentService onlinePaymentService) {
        this.onlinePaymentService = onlinePaymentService;
    }

    public void processContract(Contract contract, int months) {
        double installmentValue = contract.getTotalValue() / months;
        for (int i = 1; i <= months; i++) {

            LocalDate dueDate = contract.getDate().plusMonths(i);
            double interest = onlinePaymentService.interest(installmentValue, i);
            double paymentFee = onlinePaymentService.paymentFee(installmentValue + interest);
            double payable = installmentValue + interest + paymentFee;
            contract.getInstallments().add(new Installment(dueDate, payable));

        }
    }
}
