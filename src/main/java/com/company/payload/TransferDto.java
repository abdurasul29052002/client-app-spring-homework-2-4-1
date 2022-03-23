package com.company.payload;

import com.company.entity.Card;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
    private Card fromCard;

    private Card toCard;

    private Timestamp date;

    private Double amount;
}
