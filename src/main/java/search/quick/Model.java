package search.quick;

/**
 * Created by Asus on 10-03-2019.
 */

public class Model {
    String farm_id,id,secret,serv_id;

    public Model(String farm_id, String id, String secret, String serv_id) {
        this.farm_id = farm_id;
        this.id = id;
        this.secret = secret;
        this.serv_id = serv_id;
    }

    public String getFarm_id() {
        return farm_id;
    }

    public void setFarm_id(String farm_id) {
        this.farm_id = farm_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServ_id() {
        return serv_id;
    }

    public void setServ_id(String serv_id) {
        this.serv_id = serv_id;
    }
}
