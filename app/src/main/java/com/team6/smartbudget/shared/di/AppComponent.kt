package com.team6.smartbudget.shared.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.team6.smartbudget.MainActivity
import com.team6.smartbudget.core.di.ConfigsModule
import com.team6.smartbudget.core.di.CoreModule
import com.team6.smartbudget.core.domain.ApplicationConfig
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelFactoryModule
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelModule
import com.team6.smartbudget.features.categories.di.CategoriesModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class,
        ViewModelModule::class,
        ViewModelFactoryModule::class,
        ConfigsModule::class,
        SharedModule::class,

        CategoriesModule::class,
    ],
)
interface AppComponent {
    val viewModelFactory: ViewModelProvider.Factory
    val configs: Set<@JvmSuppressWildcards ApplicationConfig>

    fun inject(activity: MainActivity)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): AppComponent
    }
}
