package com.travelitems.beapi.security.services;

import javax.servlet.http.HttpServletRequest;

public interface SecurityService {
    String findLoggedInUsername();
    String getCurrentUsername(final HttpServletRequest request);
}
