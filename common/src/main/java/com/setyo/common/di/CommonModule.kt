package com.setyo.common.di

import android.content.Context
import androidx.room.Room
import com.setyo.common.data.repository.MovieRepository
import com.setyo.common.data.source.local.LocalDataSource
import com.setyo.common.data.source.local.room.MovieDatabase
import com.setyo.common.data.source.remote.RemoteDataSource
import com.setyo.common.data.source.remote.network.ApiService
import com.setyo.common.data.source.remote.network.AuthInterceptor
import com.setyo.common.domain.repository.IMovieRepository
import com.setyo.common.domain.usecase.MovieInteractor
import com.setyo.common.domain.usecase.MovieUseCase
import com.setyo.common.ui.detail.DetailMovieViewModel
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val commonModule = module {
    factory { get<MovieDatabase>().movieDao() }
    single { provideRoomDatabase(androidContext()) }
    factory { AuthInterceptor() }
    single { provideOkHttpClient(get()) }
    single { provideApiServices(get()) }

    single { LocalDataSource(get()) }
    single { RemoteDataSource(get()) }
    single<IMovieRepository> { MovieRepository(get(), get()) }
    factory<MovieUseCase> { MovieInteractor(get())  }
}

val commonViewModelModule = module {
    viewModel { DetailMovieViewModel(get()) }
}

fun provideRoomDatabase(context: Context): MovieDatabase {
    val passphrase: ByteArray = SQLiteDatabase.getBytes("setyo".toCharArray())
    val factory = SupportFactory(passphrase)
    return Room.databaseBuilder(
            context,
            MovieDatabase::class.java, "movie.db"
        ).fallbackToDestructiveMigration().openHelperFactory(factory)
            .build()
    }


fun provideOkHttpClient(
    authInterceptor: AuthInterceptor,
): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        apply { level = HttpLoggingInterceptor.Level.BODY }
    }
    val hostname = "api.themoviedb.org"
    val certificatePinner = CertificatePinner.Builder()
        .add(hostname, "sha256/p+WeEuGncQbjSKYPSzAaKpF/iLcOjFLuZubtsXupYSI=")
        .add(hostname, "sha256/JSMzqOOrtyOT1kmau6zKhgT676hGgczD5VMdRMyJZFA=")
        .add(hostname, "sha256/++MBgDH5WGvL9Bcn5Be30cRcL0f5O+NyoXuWtQdX1aI=")
        .add(hostname, "sha256/KwccWaCgrnaw6tsrrSO61FgLacNgG2MMLq8GE6+oP5I=")
        .build()
    return OkHttpClient().newBuilder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .certificatePinner(certificatePinner)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun provideApiServices(
    okHttpClient: OkHttpClient,
): ApiService = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .client(okHttpClient)
    .baseUrl("https://api.themoviedb.org")
    .build()
    .create(ApiService::class.java)

