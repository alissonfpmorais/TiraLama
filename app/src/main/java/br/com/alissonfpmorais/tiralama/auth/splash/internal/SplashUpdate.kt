package br.com.alissonfpmorais.tiralama.auth.splash.internal

import com.spotify.mobius.Effects
import com.spotify.mobius.Next

typealias NextSplash = Next<SplashModel, SplashEffect>

fun splashUpdate(model: SplashModel, event: SplashEvent): NextSplash = when (event) {
    is CheckUserPreviousLogged -> onCheckUserPreviousLogged(model)
    is UserIsLogged -> onUserIsLogged(model)
    is UserIsNotLogged -> onUserIsNotLogged(model)
    is NavigatedToLoginScreen -> onNavigatedToLoginScreen()
    is NavigatedToMainActivity -> onNavigatedToMainActivity()
}

fun onCheckUserPreviousLogged(model: SplashModel): NextSplash {
    return Next.next(model.copy(isSearching = true),
            Effects.effects(LookForLoggedUser))
}

fun onUserIsLogged(model: SplashModel): NextSplash {
    return Next.next(model.copy(isSearching = false, hasSearched = true, hasUser = true),
            Effects.effects(NavigateToMainActivity))
}

fun onUserIsNotLogged(model: SplashModel): NextSplash {
    return Next.next(model.copy(isSearching = false, hasSearched = true, hasUser = false),
            Effects.effects(NavigateToLoginScreen))
}

fun onNavigatedToLoginScreen(): NextSplash = Next.noChange()

fun onNavigatedToMainActivity(): NextSplash = Next.noChange()