package com.allstate.payments.service;

import com.allstate.payments.data.PaymentRepository;
import com.allstate.payments.data.UserRepository;
import com.allstate.payments.domain.User;
import com.allstate.payments.domain.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void save(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        userRepository.save(user);

    }

    @Override
    public User findUser(String username) {
        //check if user is present or else return null
        return userRepository.findByUsername(username).get();
    }

    @Override
    public List<UserDTO> getAllUsers() {
        //logging
        Logger logger =LoggerFactory.getLogger(UserService.class);
        logger.info("getting all users");

        List<UserDTO> results = userRepository.findAll()
                .stream().map (user -> new UserDTO(user))
                .collect(Collectors.toList());
        logger.debug("There were {} users found at {}", results.size(), LocalDateTime.now());

        if (logger.isDebugEnabled()) {
            long noOfPayments = paymentRepository.count();
            long remainder = noOfPayments % 1000;
            long masterno = noOfPayments / 1000;
            logger.debug("There are {} thousand and {} payments in the system", masterno, remainder);
        }

        return results;
    }

}
