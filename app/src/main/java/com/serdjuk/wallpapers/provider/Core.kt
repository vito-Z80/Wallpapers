package com.serdjuk.wallpapers.provider

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import com.serdjuk.wallpapers.model.MutableParams
import com.serdjuk.wallpapers.provider.net.Client
import com.serdjuk.wallpapers.provider.storage.Preference
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.lang.reflect.ParameterizedType

object Core {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }
    val coroutine = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)

    val screen = Screen
    val client = Client()
    val mutable = MutableParams()
    val gson: Gson = GsonBuilder()
        .registerTypeAdapterFactory(MutableStateAdapterFactory())
        .create()

    val preference = Preference(gson)
}


private class MutableStateAdapterFactory : com.google.gson.TypeAdapterFactory {
    override fun <T : Any?> create(gson: Gson, type: TypeToken<T>): TypeAdapter<T>? {
        val rawType = type.rawType as? Class<*> ?: return null
        if (MutableState::class.java.isAssignableFrom(rawType)) {
            val valueType = (type.type as ParameterizedType).actualTypeArguments[0]
            val valueTypeAdapter = gson.getAdapter(TypeToken.get(valueType))
            return MutableStateAdapter(valueTypeAdapter) as TypeAdapter<T>
        }
        return null
    }
}

private class MutableStateAdapter<T>(private val valueTypeAdapter: TypeAdapter<T>) :
    TypeAdapter<MutableState<T>>() {
    override fun write(out: JsonWriter, value: MutableState<T>?) {
        if (value == null) {
            out.nullValue()
        } else {
            valueTypeAdapter.write(out, value.value)
        }
    }

    override fun read(`in`: JsonReader): MutableState<T>? {
        return if (`in`.peek() == JsonToken.NULL) {
            `in`.nextNull()
            null
        } else {
            mutableStateOf(valueTypeAdapter.read(`in`))
        }
    }
}