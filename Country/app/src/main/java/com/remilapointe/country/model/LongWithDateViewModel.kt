package com.remilapointe.country.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.toLiveData
import com.remilapointe.country.db.LongWithDateDb
import com.remilapointe.country.entity.LongWithDate
import com.remilapointe.country.repository.LongWithDateRepository

class LongWithDateViewModel(app: Application) : AndroidViewModel(app) {
    private lateinit var longWithDateRepository : LongWithDateRepository
    private lateinit var insertResult : LiveData<Int>
    private val dao = LongWithDateDb.getInstance(app).longWithDateDao()

    init {
        longWithDateRepository = LongWithDateRepository(app)
        insertResult = longWithDateRepository.getInsertResult()
    }

    val allLongWithDate = dao.getAllValues().toLiveData(
        Config(
            /**
             * A good page size is a value that fills at least a screen worth of content on a large
             * device so the User is unlikely to see a null item.
             * You can play with this constant to observe the paging behavior.
             * <p>
             * It's possible to vary this with list device size, but often unnecessary, unless a
             * user scrolling on a large device is expected to scroll through items more quickly
             * than a small device, such as when the large device uses a grid layout of items.
             */
            pageSize = 30,

            /**
             * If placeholders are enabled, PagedList will report the full size but some items might
             * be null in onBind method (PagedListAdapter triggers a rebind when data is loaded).
             * <p>
             * If placeholders are disabled, onBind will never receive null but as more pages are
             * loaded, the scrollbars will jitter as new pages are loaded. You should probably
             * disable scrollbars if you disable placeholders.
             */
            enablePlaceholders = true,

            /**
             * Maximum number of items a PagedList should hold in memory at once.
             * <p>
             * This number triggers the PagedList to start dropping distant pages as more are loaded.
             */
            maxSize = 200
        )
    )

    fun insert(longWithDate: LongWithDate) {
        longWithDateRepository.insert(longWithDate)
    }

    fun getInsertResult() : LiveData<Int> {
        return insertResult
    }

}
