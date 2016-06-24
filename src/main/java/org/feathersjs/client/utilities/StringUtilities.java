package org.feathersjs.client.utilities;

public class StringUtilities {
    public static String trimSlashesFromStart(String string) {
        return string.replaceAll("^/+", "");
    }

    public static String trimSlashesFromEnd(String string) {
        return string.replaceAll("/$", "");
    }

    public static String trimSlashesFromStartAndEnd(String string) {
        string = trimSlashesFromStart(string);
        return trimSlashesFromEnd(string);
    }
}
