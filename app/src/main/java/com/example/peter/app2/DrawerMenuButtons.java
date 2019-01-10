package com.example.peter.app2;

public class DrawerMenuButtons {

    public void onAddEventButton() {

    }

    public void onSeeEventsButton() {

    }

    public void onSettingsButton() {

    }

    public void onExitButton() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
