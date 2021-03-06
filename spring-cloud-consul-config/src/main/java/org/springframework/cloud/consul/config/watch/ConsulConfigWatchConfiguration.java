package org.springframework.cloud.consul.config.watch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.consul.config.ConsulConfigBootstrapConfiguration;
import org.springframework.cloud.consul.config.ConsulConfigProperties;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@Import(ConsulConfigBootstrapConfiguration.class)
@EnableConfigurationProperties
@EnableScheduling
@ConditionalOnProperty(name = "spring.cloud.consul.config.watch", matchIfMissing = false)
@ConditionalOnClass(Endpoint.class)
public class ConsulConfigWatchConfiguration {

	@Autowired
	ConsulConfigProperties consulConfigProperties;

	@Autowired
	private RefreshEndpoint refreshEndpoint;

	@Bean
	public ConsulConfigWatch consulConfigWatch() {
		return new ConsulConfigWatch(consulConfigProperties);
	}

	@Bean
	public ConsulConfigurationListener consulConfigurationListener() {
		return new ConsulConfigurationListener(refreshEndpoint);
	}
}
