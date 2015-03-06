package nz.co.smetz.flatos;

import java.security.Principal;

import org.apache.http.auth.BasicUserPrincipal;
import org.apache.http.auth.Credentials;
/**
 * Created by Sara on 5/3/2015.
 */
public class TokenCredentials implements Credentials {
    private Principal userPrincipal;

    public TokenCredentials(String token) {
        this.userPrincipal = new BasicUserPrincipal(token);
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return null;
    }

}
