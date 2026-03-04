package br.com.curadoria.adapter.http.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppleReceiptRequest {
    @JsonProperty("receipt-data")
    private String receiptData;

    private String password;

    @JsonProperty("exclude-old-transactions")
    private boolean excludeOldTransactions;
}