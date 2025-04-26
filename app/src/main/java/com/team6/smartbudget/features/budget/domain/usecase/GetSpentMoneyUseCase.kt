package com.team6.smartbudget.features.budget.domain.usecase

import com.team6.smartbudget.core.util.ResultFlow
import com.team6.smartbudget.core.util.success
import com.team6.smartbudget.features.budget.domain.usecase.GetSpentMoneyUseCase.GetSpentMoneyUseCaseError
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

private const val DefaultSpent = 10f

class GetSpentMoneyUseCase @Inject constructor() :
    () -> ResultFlow<Float, GetSpentMoneyUseCaseError> {
        // TODO: Provide proper implementation
        override fun invoke(): ResultFlow<Float, GetSpentMoneyUseCaseError> =
            flowOf(success(DefaultSpent))

        sealed interface GetSpentMoneyUseCaseError {
            object Unknown : GetSpentMoneyUseCaseError
        }
    }
