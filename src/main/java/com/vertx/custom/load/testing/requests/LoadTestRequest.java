package com.vertx.custom.load.testing.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static com.vertx.custom.load.testing.constants.ErrorMessageConstants.PASSWORD_NOT_NULL;
import static com.vertx.custom.load.testing.constants.ErrorMessageConstants.USER_NAME_NOT_NULL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoadTestRequest {

    @NotNull(message = USER_NAME_NOT_NULL)
    String username;

    @NotNull(message = PASSWORD_NOT_NULL)
    String password;

    String loadTestName;

    String curl;

    int noOfRequest;

    int noOfUsers;

}
