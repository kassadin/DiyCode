package com.plusend.diycode.mvp.model.node.presenter;

import com.plusend.diycode.mvp.model.base.BaseData;
import com.plusend.diycode.mvp.model.base.Presenter;
import com.plusend.diycode.mvp.model.node.data.NodeDataNetwork;
import com.plusend.diycode.mvp.model.node.event.NodesEvent;
import com.plusend.diycode.mvp.model.node.view.NodesView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class NodesPresenter extends Presenter {
  private static final String TAG = "NodesPresenter";
  private NodesView nodesView;
  private BaseData data;

  public NodesPresenter(NodesView nodesView) {
    this.nodesView = nodesView;
    data = NodeDataNetwork.getInstance();
  }

  public void readNodes() {
    ((NodeDataNetwork) data).readNodes();
  }

  @Subscribe(threadMode = ThreadMode.MAIN) public void showNodes(NodesEvent nodesEvent) {
    nodesView.showNodes(nodesEvent.getNodeList());
  }

  @Override public void start() {
    EventBus.getDefault().register(this);
  }

  @Override public void stop() {
    EventBus.getDefault().unregister(this);
  }
}
