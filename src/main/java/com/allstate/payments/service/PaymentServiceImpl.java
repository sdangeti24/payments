package com.allstate.payments.service;

import com.allstate.payments.data.PaymentRepository;
import com.allstate.payments.domain.Payment;
import com.allstate.payments.exceptions.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private PaymentRepository paymentRepository;

    //    @Autowired
    //    public void setPaymentRepository(PaymentRepository paymentRepository){
    //        this.setPaymentRepository(paymentRepository);
    //    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }



    @Override
    public Payment getById(Integer id) throws PaymentNotFoundException {
        Optional<Payment> optionalPayment = paymentRepository.findById(id);
        if(optionalPayment.isPresent()) {
            return optionalPayment.get();
        }
        else {
            throw new PaymentNotFoundException("There is no payment with id "+ id);
        }
    }

    @Override
    public List<Payment> getByCountry(String country) {
        return paymentRepository.findAllByCountry(country);
    }

    @Override
    public List<Payment> getByOrder(String orderId) {
        return paymentRepository.findAllByOrderId(orderId);
    }

    @Override
    public List<String> getAllCountries()
    {
        return paymentRepository.findAll().stream()
                .map(payment -> payment.getCountry())
                .distinct()
                .collect(Collectors.toList());
    }

    public Payment savePayment(Payment payment) {
        System.out.println(payment);
        return paymentRepository.save(payment);
    }

    public Payment updatePayment(Integer id, Map<String, Object> fields) {
        Payment payment = paymentRepository.findById(id).get();
        if (fields.containsKey("country")) {
            payment.setCountry(fields.get("country").toString());
        }
        if (fields.containsKey("amount")) {
            payment.setAmount(Double.parseDouble(fields.get("amount").toString()));
        }
      return paymentRepository.save(payment);
    }
}
