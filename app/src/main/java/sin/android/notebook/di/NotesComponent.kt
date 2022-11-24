package sin.android.notebook.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import sin.android.notebook.MainActivity
import sin.android.notebook.di.modules.AppModule
import sin.android.notebook.di.modules.DataModule
import sin.android.notebook.di.modules.DataStoreModule
import sin.android.notebook.di.modules.ViewModelsModule
import javax.inject.Singleton


@Component(
    modules = [
        AppModule::class,
        ViewModelsModule::class,
        DataStoreModule::class,
        DataModule::class
    ]
)
@Singleton
interface NotesComponent {
    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): NotesComponent
    }
}