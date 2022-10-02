package utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IBankClient {
    private final String login;
    private final String password;
    private final String status;
}
