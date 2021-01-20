@file:Suppress("unused")

package com.martafode.lib.concurrency

import com.martafode.lib.concurrency.api.AppCoroutineDispatchers
import com.martafode.lib.concurrency.api.AppRxSchedulers
import com.martafode.lib.concurrency.api.ComputationDispatcher
import com.martafode.lib.concurrency.api.Global
import com.martafode.lib.concurrency.api.IoDispatcher
import com.martafode.lib.concurrency.api.MainDispatcher
import com.martafode.lib.di.ApplicationScoped
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executors
import java.util.concurrent.ThreadFactory
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.asCoroutineDispatcher
import org.apache.commons.lang3.concurrent.BasicThreadFactory

@Module
object ConcurrencyModule {

    // Coroutines

    @Provides
    @ApplicationScoped
    fun providesAppCoroutineDispatchers(): AppCoroutineDispatchers =
        AppCoroutineDispatchers(
            io = Dispatchers.IO,
            computation = Dispatchers.Default,
            main = Dispatchers.Main
        )

    @Provides
    @Global
    fun provideGlobalScope(): CoroutineScope = GlobalScope

    @Provides
    @ComputationDispatcher
    fun provideComputationDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    /** Every time a new dispatcher
     *
     * It is is very important to keep it unscoped and never linked to activity,
     * application or anything else.
     */
    @Provides
    @Named("SINGLE_THREAD")
    fun provideSingleThreadCoroutineDispatcher(
        @Named("SINGLE_THREAD") threadFactory: ThreadFactory
    ): CoroutineDispatcher =
        Executors.newSingleThreadExecutor(threadFactory).asCoroutineDispatcher()

    // RxJava

    @Provides
    @ApplicationScoped
    fun provideRxSchedulers(): AppRxSchedulers =
        AppRxSchedulers(
            io = Schedulers.io(),
            computation = Schedulers.computation(),
            main = AndroidSchedulers.mainThread(),
            newThread = Schedulers.newThread()
        )

    @Provides
    @ApplicationScoped
    fun provideGlobalCompositeDisposable() = CompositeDisposable()

    // Other

    // TODO(greg): Check the latest version of Apache. It no longer works.
    //   Maybe remove apache dependency here.
    // https://stackoverflow.com/questions/6113746/naming-threads-and-thread-pools-of-executorservice/6126320#6126320
    @Provides
    @ApplicationScoped
    @Named("SINGLE_THREAD")
    fun provideSingleThreadFactory(): ThreadFactory =
        BasicThreadFactory.Builder()
            .namingPattern("single-%d")
            .daemon(true)
            .priority(Thread.MAX_PRIORITY)
            .build()
}
