package com.emc.belustyle.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmWebHookRequestBodyDTO {
    private String webhookUrl;

    public ConfirmWebHookRequestBodyDTO(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }
}
