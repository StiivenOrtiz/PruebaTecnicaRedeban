package com.stiivenortiz.pruebatecnicaredeban.data.di

import com.stiivenortiz.pruebatecnicaredeban.data.security.HashStrategy
import com.stiivenortiz.pruebatecnicaredeban.data.security.Sha256HashStrategy
import com.stiivenortiz.pruebatecnicaredeban.data.security.Sha3HashStrategy
import com.stiivenortiz.pruebatecnicaredeban.data.security.annotations.Sha256
import com.stiivenortiz.pruebatecnicaredeban.data.security.annotations.Sha3
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {

    @Binds
    @Sha256
    abstract fun bindSha256HashStrategy(
        impl: Sha256HashStrategy
    ): HashStrategy

    @Binds
    @Sha3
    abstract fun bindSha3HashStrategy(
        impl: Sha3HashStrategy
    ): HashStrategy

}