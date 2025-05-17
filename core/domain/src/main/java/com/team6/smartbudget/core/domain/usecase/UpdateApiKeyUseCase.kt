package com.team6.smartbudget.core.domain.usecase

import com.team6.smartbudget.core.domain.repository.ApiKeyRepository
import javax.inject.Inject

class UpdateApiKeyUseCase @Inject constructor(
    private val apiKeyRepository: ApiKeyRepository,
) : suspend (String) -> Result<Unit> {
    override suspend fun invoke(apiKey: String): Result<Unit> =
        apiKeyRepository.updateApiKey(apiKey)
}