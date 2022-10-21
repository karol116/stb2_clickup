package pl.akademiaqa.properties;

import java.util.ResourceBundle;

public class ClickUpProperties {

    private static final String TOKEN = "clickup.token";
    private static final String TEAM_ID = "clickup.team.id";

    public static String getToken() {
        if (getProperties(TOKEN).isEmpty() || getProperties(TOKEN).startsWith("Enter")) {
            return System.getProperty("TOKEN");
        } else {
            return getProperties(TOKEN);
        }
    }

    public static String getTeamId() {
        if (getProperties(TEAM_ID).isEmpty() || getProperties(TEAM_ID).startsWith("Enter")) {
            return System.getProperty("TEAM_ID");
        } else {
            return getProperties(TEAM_ID);
        }
    }

    private static String getProperties(String key) {
        return ResourceBundle.getBundle("clickup").getString(key);
    }
}