package t.com.myapplication.injection.component

import android.content.Context
import dagger.Component
import t.com.myapplication.injection.ApplicationContext
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @ApplicationContext
    fun context(): Context
}
