package t.com.myapplication.injection.component

import t.com.myapplication.injection.PerFragment
import t.com.myapplication.injection.module.FragmentModule
import dagger.Subcomponent

/**
 * This component inject dependencies to all Fragments across the application
 */
@PerFragment
@Subcomponent(modules = [FragmentModule::class])
interface FragmentComponent