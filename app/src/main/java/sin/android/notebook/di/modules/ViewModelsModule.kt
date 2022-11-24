package sin.android.notebook.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import sin.android.notebook.ViewModels.AllNotesVIewModel
import sin.android.notebook.ViewModels.OneNoteVIewModel
import sin.android.notebook.ViewModels.ViewModelFactory
import sin.android.notebook.di.ViewModelKey
import javax.inject.Provider
import javax.inject.Singleton

@Module
abstract class ViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AllNotesVIewModel::class)
    abstract fun allNotes(vm: AllNotesVIewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(OneNoteVIewModel::class)
    abstract fun oneNotes(vm: OneNoteVIewModel): ViewModel

    @Module
    companion object{
        @Provides
        @JvmStatic
        @Singleton
        fun provideViewModelFactory(
            viewModels: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
        ): ViewModelProvider.Factory = ViewModelFactory(viewModels)
    }
}
