package t.com.myapplication.base

/**
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface Presenter<T : BaseView> {

    fun attachView(mvpView: T)

    fun detachView()

    fun getBaseView(): T?
}
