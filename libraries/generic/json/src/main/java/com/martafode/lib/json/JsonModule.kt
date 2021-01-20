@file:Suppress("unused")

package com.martafode.lib.json

import com.martafode.lib.di.ApplicationScoped
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import dagger.Module
import dagger.Provides
import dagger.multibindings.Multibinds
import java.lang.reflect.Type

@Module(
    includes = [
        JsonModule.BindingModule::class,
    ]
)
object JsonModule {

    @Provides
    @ApplicationScoped
    fun provideMoshi(
        factories: Set<@JvmSuppressWildcards JsonAdapter.Factory>
    ): Moshi = Moshi.Builder()
        .add(ThrowableFactory)
        .apply {
            factories.forEach {
                add(it)
            }
        }
        .build()

    @Module
    abstract class BindingModule {
        // The declaration of multi-binding is necessary in case
        // if there is no bindings and set is empty.
        @Multibinds
        abstract fun multibindJsonAdapterFactory(): Set<JsonAdapter.Factory>
    }
}

object ThrowableFactory : JsonAdapter.Factory {

    override fun create(
        type: Type,
        annotations: Set<Annotation>,
        moshi: Moshi
    ): JsonAdapter<Throwable>? {
        if (!Types.getRawType(type).isAssignableFrom(Throwable::class.java)) {
            return null
        }
        return object : JsonAdapter<Throwable>() {

            // it is not required
            override fun fromJson(reader: JsonReader): Throwable? = null

            override fun toJson(writer: JsonWriter, value: Throwable?) {
                if (value == null) {
                    writer.value("none")
                    return
                }
                writer.beginObject();
                {
                    writer.name("exception")
                    writer.value(value.toString())
                    writer.name("stackTrace")
                    writer.beginObject();
                    {
                        value.stackTrace.forEachIndexed { index, stackTraceElement ->
                            writer.name(index.toString())
                            writer.value(stackTraceElement.toString())
                        }
                    }()
                    writer.endObject()
                }()
                writer.endObject()
            }
        }
    }
}
