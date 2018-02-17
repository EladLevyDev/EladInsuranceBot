package t.com.myapplication.injection.component

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import t.com.myapplication.injection.ApplicationContext

@Module()
class AppModule(private val application: Application) {

    @Provides
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }
}