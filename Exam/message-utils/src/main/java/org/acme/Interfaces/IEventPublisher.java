package org.acme.Interfaces;

import org.acme.Domains.Message;
/**
 * @author Yingli
 */
public interface IEventPublisher {
    void publishEvent(Message message) throws Exception;

}
