package com.example.identityservice.service;

import com.example.identityservice.dto.request.AuthRequest;
import com.example.identityservice.dto.request.IntrospectRequest;
import com.example.identityservice.dto.response.AuthResponse;
import com.example.identityservice.dto.response.IntrospectResponse;
import com.example.identityservice.exception.AppException;
import com.example.identityservice.exception.ErrorCode;
import com.example.identityservice.repository.UserRepository;
import com.example.identityservice.service.impl.AuthService;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    UserRepository userRepository;

    @NonFinal
    @Value("${jwt.signerKey}")
    protected String SIGNER_KEY;

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        var user = userRepository.findByUsername(request.getUsername()).
                orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_EXISTS));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(request.getUsername());
        return AuthResponse.builder()
                .token(token)
                .authenticated(authenticated)
                .build();
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier verifier = new MACVerifier(SIGNER_KEY.getBytes());
        SignedJWT signedJWT = SignedJWT.parse(token);

        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
        var verified = signedJWT.verify(verifier);
        return IntrospectResponse.builder()
                .valid(verified && expirationTime != null && expirationTime.after(new Date()))
                .build();
    }

    private String generateToken(String username){
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("nvl.com")
                .issueTime(new Date())
//                .expirationTime(new Date(new Date().getTime() + 60 * 60 * 1000)) // 1 hour
                .expirationTime(
                        new Date(Instant.now().
                                plus(1, ChronoUnit.HOURS)
                                .toEpochMilli())) // 1 hour
                .claim("customClaim", "Custom")
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error signing JWT", e);
            throw new RuntimeException(e);
        }
    }
}
