package org.mcast.dc.model.enums;

/**
 * Created by Darren on 12/12/2016.
 */

public enum TypingMode {
    OneHandedStationary("One Handed Stationary"),
    OneHandedWalking("One Handed Walking"),
    TwoHandedStationary("Two Handed Stationary"),
    TwoHandedWalking("Two Handed Walking");

    private final String name;

    private TypingMode(String s) {
        name = s;
    }

    public boolean equalsName(String otherName) {
        return (otherName == null) ? false : name.equals(otherName);
    }

    public String toString() {
        return this.name;
    }

    public static TypingMode getTypingModeByName(String name)
    {
        for (TypingMode mode : TypingMode.values() )
        {
            if(mode.equalsName(name))
                return mode;
        }

        return null;
    }
}
