package com.company.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Card {
    private Integer id;

    private String number;

    private String expireDate;

    private Double balance;

    private User user;

    private boolean active;
}
