package org.neonsis.socialnetwork.rest.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Application Message configuration.
 *
 * @author neonsis
 */
@Configuration
public class ApplicationMessageConfig {

    private static final String MESSAGE_SOURCE_BASE_NAME = "i18n/messages";

    /**
     * Message source.
     *
     * @return message source.
     */
    @Bean
    public MessageSource messageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        messageSource.setBasename(MESSAGE_SOURCE_BASE_NAME);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setAlwaysUseMessageFormat(true);

        return messageSource;
    }

    /**
     * Local validator factory bean.
     *
     * @return validator.
     */
    @Bean
    public LocalValidatorFactoryBean getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }
}
