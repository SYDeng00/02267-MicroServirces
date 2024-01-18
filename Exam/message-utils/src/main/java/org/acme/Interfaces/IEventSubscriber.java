package org.acme.Interfaces;

import org.acme.Domains.Message;
/**
 * @author Yingli
 */

public interface IEventSubscriber {
    void subscribeEvent(Message message) throws Exception;
}
