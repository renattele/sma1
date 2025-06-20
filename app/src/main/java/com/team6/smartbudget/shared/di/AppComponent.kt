package com.team6.smartbudget.shared.di

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.team6.smartbudget.MainActivity
import com.team6.smartbudget.core.di.ConfigsModule
import com.team6.smartbudget.core.di.CoreModule
import com.team6.smartbudget.core.domain.ApplicationConfig
import com.team6.smartbudget.core.presentation.viewmodel.ViewModelFactoryModule
import com.team6.smartbudget.features.details.di.DetailsModule
import com.team6.smartbudget.features.graph.presentation.di.GraphPresentationModule
import com.team6.smartbudget.features.onboarding.di.OnboardingModule
import com.team6.smartbudget.features.overview.di.OverviewModule
import com.team6.smartbudget.shared.data.DatabaseModule
import com.team6.smartbudget.shared.presentation.AppFirebaseMessagingService
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        CoreModule::class,
        ViewModelFactoryModule::class,
        ConfigsModule::class,
        DatabaseModule::class,

        OverviewModule::class,
        DetailsModule::class,
        OnboardingModule::class,
        GraphPresentationModule::class,
    ],
)
interface AppComponent {
    val viewModelFactory: ViewModelProvider.Factory
    val configs: Set<@JvmSuppressWildcards ApplicationConfig>

    fun inject(activity: MainActivity)

    fun inject(service: AppFirebaseMessagingService)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance applicationContext: Context,
        ): AppComponent
    }
}
