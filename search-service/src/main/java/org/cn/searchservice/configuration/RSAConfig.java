package org.cn.searchservice.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@ConfigurationProperties("rsa")
public record RSAConfig(RSAPublicKey publicKey, RSAPrivateKey privateKey){
}
