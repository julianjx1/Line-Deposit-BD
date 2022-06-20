package com.line_deposit.bd.view.Authentication;

import com.line_deposit.bd.model.User;

public interface LoginObserver {
    void authenticationStatus(Boolean success, String message, User user);
}
