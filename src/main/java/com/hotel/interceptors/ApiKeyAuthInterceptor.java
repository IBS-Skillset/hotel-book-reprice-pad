package com.hotel.interceptors;

import com.hotel.util.APIConstants;
import io.grpc.*;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.util.Objects;

@GrpcGlobalServerInterceptor
@Slf4j
public class ApiKeyAuthInterceptor implements ServerInterceptor {


    public boolean validateSignature(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] chunks = token.split("\\.");
        SignatureAlgorithm sa = SignatureAlgorithm.RS256;
        BigInteger mod = new BigInteger(APIConstants.MOD);
        BigInteger publicExp = BigInteger.valueOf(65537);
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey publicKey = fact.generatePublic(new RSAPublicKeySpec(mod, publicExp));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, rsaPublicKey);
        return validator.isValid(tokenWithoutSignature, signature);
    }

    @Override
    public <R, S> ServerCall.Listener<R> interceptCall(ServerCall<R, S> serverCall, Metadata metadata, ServerCallHandler<R, S> serverCallHandler) {
        log.info("Sever interceptor {}", serverCall.getMethodDescriptor());

        Metadata.Key<String> apiKeyMetadata = Metadata.Key.of("accesstoken", Metadata.ASCII_STRING_MARSHALLER);
        String apiKey = metadata.get(apiKeyMetadata);
        log.info("accesstoken from client {}", apiKey);
        try {
            if (Objects.nonNull(apiKey) && validateSignature(apiKey)) {
                return serverCallHandler.startCall(serverCall, metadata);
            } else {
                log.error(APIConstants.INVALID_ACCESS_TOKEN);
                Status status = Status.UNAUTHENTICATED.withDescription(APIConstants.INVALID_ACCESS_TOKEN);
                serverCall.close(status, metadata);
            }
        } catch (Exception e) {
            log.error("Invalid access token exception" + e);
            Status status = Status.UNAUTHENTICATED.withDescription(APIConstants.INVALID_ACCESS_TOKEN);
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<>() {

        };
    }
}
