package com.line_deposit.test.bd.view.Authentication;

import com.line_deposit.test.bd.model.User;

public interface LoginObserver {
    void authenticationStatus(Boolean success, String message, User user);
}
