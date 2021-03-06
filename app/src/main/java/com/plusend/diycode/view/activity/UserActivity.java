package com.plusend.diycode.view.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.plusend.diycode.R;
import com.plusend.diycode.mvp.model.user.entity.UserDetailInfo;
import com.plusend.diycode.mvp.model.user.presenter.UserPresenter;
import com.plusend.diycode.mvp.model.user.view.UserView;
import com.plusend.diycode.view.fragment.TopicFragment;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class UserActivity extends AppCompatActivity implements UserView {
  private static final String TAG = "UserActivity";
  public static final String LOGIN_NAME = "loginName";
  @BindView(R.id.avatar) ImageView avatar;
  @BindView(R.id.name) TextView name;
  @BindView(R.id.topic_num) TextView topicNum;
  @BindView(R.id.favorite_num) TextView favoriteNum;
  @BindView(R.id.follow_num) TextView followNum;
  @BindView(R.id.back) ImageView back;
  @BindView(R.id.container) FrameLayout container;
  private UserPresenter userPresenter;
  private String loginName;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_user);

    SystemBarTintManager tintManager = new SystemBarTintManager(this);
    tintManager.setStatusBarTintEnabled(true);
    tintManager.setTintColor(Color.parseColor("#284fbb"));

    ButterKnife.bind(this);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    back.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        finish();
      }
    });

    Intent intent = getIntent();
    loginName = intent.getStringExtra(LOGIN_NAME);

    TopicFragment topicFragment = TopicFragment.newInstance(loginName, TopicFragment.TYPE_CREATE);
    getSupportFragmentManager().beginTransaction().add(R.id.container, topicFragment).commit();

    userPresenter = new UserPresenter(this);
  }

  @Override public void getMe(UserDetailInfo userDetailInfo) {
  }

  @Override public void getUser(UserDetailInfo userDetailInfo) {
    Log.d(TAG, "getUser: " + userDetailInfo);
    if (userDetailInfo != null) {
      name.setText(userDetailInfo.getLogin());
      topicNum.setText(String.valueOf(userDetailInfo.getTopicsCount()));
      followNum.setText(String.valueOf(userDetailInfo.getFollowingCount()));
      favoriteNum.setText(String.valueOf(userDetailInfo.getFavoritesCount()));
      Glide.with(this)
          .load(userDetailInfo.getAvatarUrl())
          .bitmapTransform(new CropCircleTransformation(this))
          .crossFade()
          .into(avatar);
    }
  }

  @Override public Context getContext() {
    return this;
  }

  @Override protected void onStart() {
    super.onStart();
    userPresenter.start();
    userPresenter.getUser(loginName);
  }

  @Override protected void onStop() {
    userPresenter.stop();
    super.onStop();
  }
}
