package com.bikkadIT.ElectronicStore.payloads;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {

    private String message;

    private boolean success;

    private String status;
}
