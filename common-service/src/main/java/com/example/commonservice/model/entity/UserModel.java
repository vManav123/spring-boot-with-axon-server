package com.example.commonservice.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModel {
    private String userId;
    private String firstName;
    private String lastName;
    private CardDetailsModel cardDetailsModel;
}
