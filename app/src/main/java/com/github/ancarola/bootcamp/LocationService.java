package com.github.ancarola.bootcamp;

import android.app.Activity;

public interface LocationService {

    Location get(); // Permissions + Enabled status

    boolean hasPermission();
    boolean isEnabled();

    boolean permissionRequest(Activity activity); // ask user to get location access permission
    boolean enableRequest(); // ask user to enable the service
}
