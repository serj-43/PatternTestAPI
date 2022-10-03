package utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
@Setter
public class IBankClient {
    private final String login;
    private final String password;
    public String status;
}
