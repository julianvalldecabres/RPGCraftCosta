/* 
 * Copyright 2016 jail.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.craftcosta.jailrules.rpgcraftcosta.chat;

import java.util.List;

/**
 *
 * @author jail
 */
public class RPGChatTask {

    private String name;
    private MessageType type;
    private boolean enabled;
    private String description;
    private List<String> messages;
    private long interval;

    /**
     *
     * @param name
     * @param type
     * @param enabled
     * @param description
     * @param messages
     * @param interval
     */
    public RPGChatTask(String name, MessageType type, final boolean enabled, String description, final List<String> messages, final long interval) {
        this.name = name;
        this.type = type;
        this.enabled = enabled;
        this.description = description;
        this.messages = messages;
        this.interval = interval;
    }

    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public MessageType getType() {
        return type;
    }

    /**
     *
     * @param type
     */
    public void setType(MessageType type) {
        this.type = type;
    }

    /**
     *
     * @return
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     *
     * @param enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     *
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     */
    public List<String> getMessages() {
        return messages;
    }

    /**
     *
     * @param messages
     */
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    /**
     *
     * @return
     */
    public long getCooldown() {
        return interval;
    }

    /**
     *
     * @param cooldown
     */
    public void setCooldown(int cooldown) {
        this.interval = cooldown;
    }

}
