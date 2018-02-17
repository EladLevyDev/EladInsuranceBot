package t.com.myapplication

import android.app.Application
import android.content.Context
import t.com.myapplication.injection.component.AppComponent
import t.com.myapplication.injection.component.AppModule
import t.com.myapplication.injection.component.DaggerAppComponent

class AppApplication : Application() {

    private var appComponent: AppComponent? = null

    companion object {
        operator fun get(context: Context): AppApplication {
            return context.applicationContext as AppApplication
        }
    }

    override fun onCreate() {
        super.onCreate()

        // Make sure we have no leaks!
        if (BuildConfig.DEBUG) {
            //LeakCanary.install(this)
            //Sherlock.init(this)
            //Traceur.enableLogging()
        }
    }

    //
    var component: AppComponent
        get() {
            if (appComponent == null) {
                appComponent = DaggerAppComponent.builder()
                        .appModule(AppModule(this))
                        .build()
            }
            return appComponent as AppComponent
        }
        set(appComponent) {
            this.appComponent = appComponent
        }

}