package com.line_deposit.bd.view.fragment.admin.affiliate;

import java.util.Map;

public interface MembersObserver {
    void membersList(Map<String, String> users, String purpose);
}
