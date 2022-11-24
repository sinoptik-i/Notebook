package sin.android.notebook.di.modules

import android.app.Application
import android.content.Context
import dagger.Binds
import dagger.Module
import sin.android.notebook.data.NoteRepository

@Module
abstract class AppModule {
    // expose Application as an injectable context

    @Binds
    abstract fun context(application: Application): Context

}