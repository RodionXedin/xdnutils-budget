package com.rodionxedin.service.machine;

import com.rodionxedin.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by rodio on 17.03.2016.
 */
@Service
public class TimeMachine {

    private Map<User, TimeMachineUserEntry> timeMachineUserEntryMap = new HashMap<>();

    public Map<User, TimeMachineUserEntry> getTimeMachineUserEntryMap() {
        return timeMachineUserEntryMap;
    }
}
