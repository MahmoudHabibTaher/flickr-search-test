package com.bigo.flickrsearch.core.domain

interface UseCase<out A> {
    suspend operator fun invoke(): Result<A>
}

interface UseCaseWithInput<in A, out B> {
    suspend operator fun invoke(input: A): Result<B>
}