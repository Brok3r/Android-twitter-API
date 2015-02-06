package com.broker.jastags.model;

import com.broker.jastags.utils.DateUtils;

/**
 * Created by broker on 6/02/15.
 */
public class Tweet {

    private String name;
    private String screenName;
    private String profileImageUrl;
    private String text;
    private String createAt;

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = DateUtils.setDateFormat(createAt);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = "@" + screenName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

