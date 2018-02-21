package t.com.myapplication.base

import rx.Subscription
import rx.subscriptions.CompositeSubscription
import java.lang.ref.WeakReference

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the baseView that
 * can be accessed from the children classes by calling getBaseView().
 */
open class BasePresenter<T : BaseView> : Presenter<T> {

    private var baseView: WeakReference<T>? = null
    private val compositeSubscription = CompositeSubscription()

    override fun attachView(mvpView: T) {
        baseView = WeakReference(mvpView)
    }

    override fun detachView() {
        baseView = null;
        if (!compositeSubscription.isUnsubscribed) {
            compositeSubscription.clear()
        }
    }

    override fun getBaseView(): T? {
        return baseView?.get();
    }


    private val isViewAttached: Boolean
        get() = baseView?.get() != null

    fun checkViewAttached() {
        if (!isViewAttached) throw BaseViewNotAttachedException()
    }

    fun addSubscription(subs: Subscription) {
        compositeSubscription.add(subs)
    }

    private class BaseViewNotAttachedException internal constructor() : RuntimeException("Please call Presenter.attachView(BaseView) before" + " requesting data to the Presenter")

}

