package ua.dp.ollu.currex.currex_app.config;

import lombok.Data;

@Data
public class DbSettings {
    private final String url;
    private final String user;
    private final String password;

    public DbSettings(String url, String user, String password) {

        this.url = url;
        this.user = user;
        this.password = password;
    }
}
