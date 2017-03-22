package com.library.base.view.refreshlayout;

import com.library.base.util.ToastUtil;

/**
 * @author zhangdeming
 * @date 创建时间 2016/11/25
 * @description 分页加载的控制者类
 */

public abstract class OnPagingController implements RefreshLayout.OnRefreshListener {

    private int pageNum = 1;
    private int pageSize = 10;
    private int pageMax = 0;
    private RefreshLayout refreshLayout = null;

    public OnPagingController(int pageSize) {
        this.pageSize = pageSize;
    }

    public OnPagingController() {
    }

    public void setRefreshLayout(RefreshLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    public void setPage(int pN, int pS, int total) {
        pageNum = pN;
        pageSize = pS;
        if (total > 0) {
            pageMax = total / pageSize;
            if (total % pageSize != 0) {
                pageMax += 1;
            }
        } else {
            pageMax = 0;
        }
        if (pageNum >= pageMax) {
            onNextNull();
        } else {
            canNext();
        }
        if (refreshLayout != null) {
            refreshLayout.setRefreshing(false);
        }
    }

    public void setPage(int pN, int total) {
        setPage(pN, pageSize, total);
    }

    @Override
    public void onPullDownToRefresh() {
        pageNum = 1;
        onRefresh(pageNum);
    }

    @Override
    public void onPullUpToRefresh() {
        if (pageNum <= pageMax - 1) {
            pageNum += 1;
            onNext(pageNum);
        } else {
            onNextNull();
        }
    }


    abstract protected void onRefresh(int pageNum);

    abstract protected void onNext(int pageNum);

    protected void onNextNull() {
//        ToastUtil.show("已经是最后一页了");
        if (refreshLayout != null) {
            refreshLayout.setDirection(RefreshLayoutDirection.TOP);
        }
    }


    protected void canNext() {
        if (refreshLayout != null) {
            refreshLayout.setDirection(RefreshLayoutDirection.BOTH);
        }
    }

}
