package com.team6.smartbudget.core.domain.usecase

import com.team6.smartbudget.core.domain.repository.ApiKeyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

public class GetApiKeyUseCase @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
): () -> Flow<String?> {
    override fun invoke(): Flow<String?> = apiKeyRepository.apiKey()
}