package gg.pixelgruene.oergpbackend.serverhandler;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ConsoleHandler {

    private String error;
    private String warning;
    private String info;

    public String getError(){
        return getTimeForConsole() + this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getWarning() {
        return getTimeForConsole() + this.warning;
    }

    public void setWarning(String warning) {
        this.warning = warning;
    }

    public String getInfo() {
        return getTimeForConsole() + this.info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getTimeForConsole() {
        SimpleDateFormat sdf = new SimpleDateFormat("[HH:mm:ss] ");
        return sdf.format(new Date());
    }

}
