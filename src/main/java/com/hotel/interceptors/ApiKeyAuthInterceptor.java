package com.hotel.interceptors;

import com.hotel.config.RSAConfiguration;
import com.hotel.util.APIConstants;
import io.grpc.ServerInterceptor;
import io.grpc.ServerCall;
import io.grpc.Status;
import io.grpc.Metadata;
import io.grpc.ServerCallHandler;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.DefaultJwtSignatureValidator;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
@Slf4j
public class ApiKeyAuthInterceptor implements ServerInterceptor {
    private final RSAConfiguration rsaConfiguration;

    public boolean validateSignature(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        String[] chunks = token.split("\\.");
        SignatureAlgorithm sa = SignatureAlgorithm.RS256;
        BigInteger mod = new BigInteger(rsaConfiguration.getMod());
        BigInteger publicExp = new BigInteger(rsaConfiguration.getPublicExp());
        KeyFactory fact = KeyFactory.getInstance("RSA");
        PublicKey publicKey = fact.generatePublic(new RSAPublicKeySpec(mod, publicExp));
        RSAPublicKey rsaPublicKey = (RSAPublicKey) publicKey;

        String tokenWithoutSignature = chunks[0] + "." + chunks[1];
        String signature = chunks[2];
        DefaultJwtSignatureValidator validator = new DefaultJwtSignatureValidator(sa, rsaPublicKey);
        return validator.isValid(tokenWithoutSignature, signature);
    }

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
        log.info("Sever interceptor {}", serverCall.getMethodDescriptor());

        Metadata.Key<String> apiKeyMetadata = Metadata.Key.of(APIConstants.ACCESS_TOKEN, Metadata.ASCII_STRING_MARSHALLER);
        String apiKey = metadata.get(apiKeyMetadata);
        log.info("accesstoken from client {}", apiKey);
        try {
            if (Objects.nonNull(apiKey) && validateSignature(apiKey)) {
                return serverCallHandler.startCall(serverCall, metadata);
            } else {
                Status status = Status.UNAUTHENTICATED.withDescription("Invalid accesstoken");
                serverCall.close(status, metadata);
            }
        } catch (Exception e) {
            log.error("Invalid access token exception" + e);
            Status status = Status.UNAUTHENTICATED.withDescription("Invalid accesstoken");
            serverCall.close(status, metadata);
        }
        return new ServerCall.Listener<>() {

        };
    }
}
