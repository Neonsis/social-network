package org.neonsis.socialnetwork.rest.config.i18n;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

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
}
