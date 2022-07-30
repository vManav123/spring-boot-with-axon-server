package com.example.userservice.query.api.projection;

import com.example.commonservice.model.entity.UserModel;
import com.example.commonservice.queries.GetUserPaymentDetailsQuery;
import com.example.userservice.global.data.dao.CardDetailsRepository;
import com.example.userservice.global.data.dao.UserRepository;
import com.example.userservice.global.data.model.entity.User;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    private UserRepository userRepository;
    private CardDetailsRepository cardDetailsRepository;

    public UserProjection(UserRepository userRepository , CardDetailsRepository cardDetailsRepository) {
        this.userRepository = userRepository;
        this.cardDetailsRepository = cardDetailsRepository;
    }

    @QueryHandler
    public UserModel getUserPaymentDetails(GetUserPaymentDetailsQuery query)
    {

        User user = userRepository.findById(query.getUserId()).orElse(null);
        if(user!=null)
        {
            UserModel userModel = UserModel.builder().userId(user.getUserId()).build();
            BeanUtils.copyProperties(user,userModel);
            return userModel;
        }
        else
            return null;

//        CardDetails cardDetails = CardDetails.builder()
//                .cardHolderName("Manav Verma")
//                .cardNumber("1232-3431-1231")
//                .active(true)
//                .bankName("ICICI")
//                .cvv(123)
//                .expiry(LocalDateTime.now().plusYears(10))
//                .build();
//        return User.builder()
//                .firstName("Manav")
//                .lastName("Verma")
//                .cardDetails(cardDetails)
//                .build();
    }

}
