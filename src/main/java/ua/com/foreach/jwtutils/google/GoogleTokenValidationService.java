package ua.com.foreach.jwtutils.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ua.com.foreach.jwtutils.JwtUserDetailsService;
import ua.com.foreach.models.CustomUser;
import ua.com.foreach.repos.CustomUserRepository;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GoogleTokenValidationService {

    private CustomUserRepository customUserRepository;
    private JwtUserDetailsService userDetailsService;

    private final GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier
            .Builder(new NetHttpTransport(), new GsonFactory())
            .setAudience(Collections.singletonList("530287046215-k0i1o0ci7rk9nolcqsq5sjmvo4ecfj6b.apps.googleusercontent.com"))
            .build();

    public UserDetails validateGoogleUser(String credential) throws GeneralSecurityException, IOException {
        GoogleIdToken idToken;
        try {
            idToken = verifier.verify(credential);
            if (idToken != null) {
                GoogleIdToken.Payload payload = idToken.getPayload();
                String email = payload.getEmail();
                String name = (String) payload.get("name");
                String pictureUrl = (String) payload.get("picture");
                Optional<CustomUser> userForToken = customUserRepository.findByLogin(email);
                if (userForToken.isEmpty()) {
                    customUserRepository.save(CustomUser.createGoogleUser(email, name, pictureUrl));
                }
                return userDetailsService.loadUserByUsername(email);
            } else {
                throw new IOException("Invalid Google token");
            }
        } catch (GeneralSecurityException e) {
            throw new GeneralSecurityException(e);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}
