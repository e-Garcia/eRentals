package com.egarcia.assignment.di

import com.egarcia.assignment.rentals.service.repository.RentalApi
import com.egarcia.assignment.rentals.service.repository.RentalRepository
import com.egarcia.assignment.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun providesRentalRepository(
            rentalApi: RentalApi
    ): RentalRepository {
        return RentalRepository(rentalApi)
    }

    @Provides
    fun providesRentalApi(): RentalApi {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RentalApi::class.java)
    }
}