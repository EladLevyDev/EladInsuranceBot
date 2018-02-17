package t.com.myapplication.base

import io.mvpstarter.sample.features.base.Presenter
import rx.Subscription
import rx.subscriptions.CompositeSubscription

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the baseView that
 * can be accessed from the children classes by calling getBaseView().
 */
open class BasePresenter<T : BaseView> : Presenter<T> {

    var baseView: T? = null
        private set
    private val compositeSubscription = CompositeSubscription()

    override fun attachView(mvpView: T) {
        this.baseView = mvpView
    }

    override fun detachView() {
        baseView = null
        if (!compositeSubscription.isUnsubscribed) {
            compositeSubscription.clear()
        }
    }

    private val isViewAttached: Boolean
        get() = baseView != null

    fun checkViewAttached() {
        if (!isViewAttached) throw BaseViewNotAttachedException()
    }

    fun addSubscription(subs: Subscription) {
        compositeSubscription.add(subs)
    }

    private class BaseViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(BaseView) before" + " requesting data to the Presenter")

}

