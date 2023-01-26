package com.allstate.payments.control;

import com.allstate.payments.domain.Payment;
import com.allstate.payments.exceptions.PaymentNotFoundException;
import com.allstate.payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping()
    public List<Payment> getAllPayments(@RequestParam(value="country", required = false) String country,
                                        @RequestParam(value="order", required = false) String order
                                        ) {
        if (order != null) {
            return paymentService.getByOrder(order);
        }
        else if (country != null) {
            return paymentService.getByCountry(country);
        }
        else {
            return paymentService.getAllPayments();
        }

    }


    @GetMapping("/{paymentId}")
    public Payment findById(@PathVariable("paymentId") Integer id) throws PaymentNotFoundException {
        return paymentService.getById(id);
    }

    @PostMapping
    public Payment saveNewPayment(@RequestBody Payment payment) {
    return paymentService.savePayment(payment);
    }

    @PutMapping("/{paymentId}")
    public Payment updatePayment(@PathVariable Integer paymentId, @RequestBody HashMap<String, Object> fields) {
        return null;
    }

}
