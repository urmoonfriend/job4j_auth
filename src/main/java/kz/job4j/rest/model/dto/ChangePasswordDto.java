package kz.job4j.rest.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ChangePasswordDto {
    private String login;
    private String currentPassword;
    private String newPassword;
}
