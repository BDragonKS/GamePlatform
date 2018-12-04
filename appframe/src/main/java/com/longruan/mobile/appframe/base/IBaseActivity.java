package com.longruan.mobile.appframe.base;

public interface IBaseActivity<T extends AbsBasePresenter> {

    void initInject();
    T getPresenter();
}
