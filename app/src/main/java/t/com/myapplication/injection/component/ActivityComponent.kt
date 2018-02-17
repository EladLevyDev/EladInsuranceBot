package t.com.myapplication.injection.component

import t.com.myapplication.injection.PerActivity
import t.com.myapplication.injection.module.ActivityModule
import dagger.Subcomponent
import t.com.myapplication.base.BaseActivity
import t.com.myapplication.feature.chat.MainActivity

@PerActivity
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(baseActivity: BaseActivity)

    fun inject(mainActivity: MainActivity)

}
