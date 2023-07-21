package com.codexcollab.koin

import com.codexcollab.security.jwt.JwtConfig
import com.codexcollab.repo.auth.gateway.AuthPresenterImpl
import com.codexcollab.repo.auth.presenter.AuthPresenter
import com.codexcollab.repo.user.gateway.UserPresenterImpl
import com.codexcollab.repo.user.presenter.UserPresenter
import com.codexcollab.repo.auth.gateway.AuthUseCaseImpl
import com.codexcollab.repo.auth.usecase.AuthUseCase
import com.codexcollab.repo.user.gateway.UserUseCaseImpl
import com.codexcollab.repo.user.usecase.UserUseCase
import org.koin.dsl.module

val authModules = module {
    single { AuthUseCase() as AuthUseCaseImpl }
    single { AuthPresenter(get()) as AuthPresenterImpl }
}

val userModules = module {
    single { UserUseCase() as UserUseCaseImpl }
    single { UserPresenter(get()) as UserPresenterImpl }
}

val jwtModule = module {
    single { JwtConfig("ktorsimpleauth") }
}