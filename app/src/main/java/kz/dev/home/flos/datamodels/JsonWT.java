package kz.dev.home.flos.datamodels;

public class JsonWT {
    private String jwt;

    public JsonWT(String jwt) {
        this.jwt = jwt;
    }
    public String getJwt() {
        return jwt;
    }
}
