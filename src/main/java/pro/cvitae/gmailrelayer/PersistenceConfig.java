package pro.cvitae.gmailrelayer;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(basePackages = "pro.cvitae.gmailrelayer.persistence")
@EnableTransactionManagement
public class PersistenceConfig {

}
