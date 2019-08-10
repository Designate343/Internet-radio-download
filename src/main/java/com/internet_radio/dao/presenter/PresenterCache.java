package com.internet_radio.dao.presenter;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Component
public class PresenterCache {

    private final Set<String> presenters = new HashSet<>();

    public boolean isCached(String presenterName) {
        return presenters.contains(presenterName);
    }

    public void add(String presenter) {
        presenters.add(presenter);
    }

}
