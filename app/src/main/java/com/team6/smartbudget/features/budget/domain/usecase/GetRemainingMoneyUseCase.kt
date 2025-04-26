package com.team6.smartbudget.features.budget.domain.usecase

import com.team6.smartbudget.core.util.ResultFlow
import com.team6.smartbudget.core.util.success
import com.team6.smartbudget.features.budget.domain.usecase.GetRemainingMoneyUseCase.GetRemainingMoneyUseCaseError
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val DefaultRemaining = 10f

class GetRemainingMoneyUseCase @Inject constructor() :
    () -> ResultFlow<Float, GetRemainingMoneyUseCaseError> {
        // TODO: Provide proper implementation
        override fun invoke(): ResultFlow<Float, GetRemainingMoneyUseCaseError> =
            flowOf(success(DefaultRemaining))

        sealed interface GetRemainingMoneyUseCaseError {
            object Unknown : GetRemainingMoneyUseCaseError
        }
    }
