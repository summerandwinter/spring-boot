package io.summer.springboot.oauth2.authorization.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author summerandwinter
 * @date 2019/7/2
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationConfig extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;
  private final RedisConnectionFactory redisConnectionFactory;
  private final DataSource dataSource;

  @Autowired
  public AuthorizationConfig(AuthenticationManager authenticationManager,
      RedisConnectionFactory redisConnectionFactory, DataSource dataSource) {
    this.authenticationManager = authenticationManager;
    this.redisConnectionFactory = redisConnectionFactory;
    this.dataSource = dataSource;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
    oauthServer
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        .allowFormAuthenticationForClients();
  }
  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    // 配置客户端, 用于client认证
    clients.jdbc(dataSource);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints)  {
    endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
        .authenticationManager(authenticationManager);
  }

}