package com.ddd.sikdorok.domain.modify

import com.ddd.sikdorok.domain.repository.ModifyRepository
import com.ddd.sikdorok.shared.base.SikdorokResponse
import com.ddd.sikdorok.shared.modify.FeedResponse
import javax.inject.Inject

class ReadFeedUseCase @Inject constructor(
    private val modifyRepository: ModifyRepository
) {
    suspend operator fun invoke(feedId: String): SikdorokResponse<FeedResponse> {
        return modifyRepository.getFeed(feedId)
    }
}
