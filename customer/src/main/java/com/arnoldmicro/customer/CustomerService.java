package com.arnoldmicro.customer;


import com.arnoldmicro.clients.notification.NotificationClient;
import com.arnoldmicro.clients.notification.NotificationRequest;
import com.arnoldmicro.clients.fraud.FraudCheckResponse;
import com.arnoldmicro.clients.fraud.FraudClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private FraudClient fraudClient;
    private NotificationClient notificationClient;

    public void registerCustomer(CustomerRequest customerRequest) {
        Customer customer= Customer.builder()
                .firstName(customerRequest.firstName())
                .lastName(customerRequest.lastName())
                .email(customerRequest.email())
                .build();
        // TODO: check if email valid
        // TODO: check if email not taken
        customerRepository.saveAndFlush(customer);
        //check if fraudster
        final FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if(fraudCheckResponse.isFraudster()){
            throw new IllegalStateException("fraudster");
        }
        // TODO: send notification
        final String message = String.format("Hello %s, welcome to arnold micro", customer.getFirstName());
        notificationClient.sendNotification(new NotificationRequest(customer.getId(), customer.getEmail(), message));

    }
}
