package com.plusend.diycode.event;

import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;

/**
 * Created by plusend on 2016/11/28.
 */

public class MeEvent {
  private UserDetailInfo userDetailInfo;

  public MeEvent(UserDetailInfo userDetailInfo) {
    this.userDetailInfo = userDetailInfo;
  }

  public UserDetailInfo getUserDetailInfo() {
    return userDetailInfo;
  }
}
