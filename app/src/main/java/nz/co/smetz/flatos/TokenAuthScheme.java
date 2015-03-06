package nz.co.smetz.flatos;

import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AUTH;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthSchemeFactory;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.impl.auth.AuthSchemeBase;
import org.apache.http.message.BufferedHeader;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;

/**
 * Created by Sara on 5/3/2015.
 */
public class TokenAuthScheme implements AuthScheme {
    private boolean complete = false;

    @Override
    public void processChallenge(Header header) throws MalformedChallengeException {
        this.complete = true;
    }

    @Override
    public Header authenticate(Credentials credentials, HttpRequest request) throws AuthenticationException {
        CharArrayBuffer buffer = new CharArrayBuffer(32);
        buffer.append(AUTH.WWW_AUTH_RESP);
        buffer.append(": Token ");
        buffer.append(credentials.getUserPrincipal().getName());
        return new BufferedHeader(buffer);
    }

    @Override
    public String getSchemeName() {
        return "Token";
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public String getRealm() {
        return null;
    }

    @Override
    public boolean isConnectionBased() {
        return false;
    }

    @Override
    public boolean isComplete() {
        return this.complete;
    }
}
