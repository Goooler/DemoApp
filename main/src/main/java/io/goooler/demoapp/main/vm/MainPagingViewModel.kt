package io.goooler.demoapp.main.vm

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import io.goooler.demoapp.base.core.BaseViewModel
import io.goooler.demoapp.base.util.MutableBooleanLiveData
import io.goooler.demoapp.base.util.unsafeLazy
import io.goooler.demoapp.common.type.Constants
import io.goooler.demoapp.main.model.MainListItemModel
import io.goooler.demoapp.main.repository.MainRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainPagingViewModel(application: Application) : BaseViewModel(application) {

    private var dataSource: PagingDataSource? = null

    val boundaryPageData = MutableBooleanLiveData()

    val listData: LiveData<PagedList<MainListItemModel>> by unsafeLazy {
        val config = PagedList.Config.Builder()
            .setPageSize(Constants.defaultPageSize)
            .setInitialLoadSizeHint(Constants.defaultPageSize)
            .build()
        LivePagedListBuilder(factory, config)
            .setBoundaryCallback(mBoundaryCallback)
            .build()
    }

    fun refresh() {
        dataSource?.invalidate()
    }

    private fun loadData(
        currentPage: Int,
        initialCallback: PageKeyedDataSource.LoadInitialCallback<Int, MainListItemModel?>?,
        callback: PageKeyedDataSource.LoadCallback<Int, MainListItemModel?>?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            if (initialCallback != null) {
                initialCallback.onResult(
                    fetchListData(currentPage),
                    currentPage - 1,
                    currentPage + 1
                )
            } else {
                callback?.onResult(fetchListData(currentPage), currentPage)
            }
            boundaryPageData.postValue(MainRepository.getRepoListCr("google").isEmpty())
        }
    }

    private suspend fun fetchListData(page: Int): List<MainListItemModel> {
        return MainRepository.getRepoListCr("google", page)
            .map { bean -> MainListItemModel(bean.owner?.avatarUrl, bean.name) }
    }

    private val factory = object : DataSource.Factory<Int, MainListItemModel>() {
        override fun create(): DataSource<Int, MainListItemModel> {
            if (dataSource == null || dataSource!!.isInvalid) {
                dataSource = PagingDataSource()
            }
            return dataSource!!
        }
    }

    //监听数据边界
    private val mBoundaryCallback = object : PagedList.BoundaryCallback<MainListItemModel>() {
        override fun onZeroItemsLoaded() {
            super.onZeroItemsLoaded()
            //初始化数据
            boundaryPageData.postValue(false)
        }

        override fun onItemAtFrontLoaded(itemAtFront: MainListItemModel) {
            super.onItemAtFrontLoaded(itemAtFront)
            //正在添加数据
            boundaryPageData.postValue(true)
        }

        override fun onItemAtEndLoaded(itemAtEnd: MainListItemModel) {
            super.onItemAtEndLoaded(itemAtEnd)
            //没有数据加载了
            boundaryPageData.postValue(false)
        }
    }

    private inner class PagingDataSource : PageKeyedDataSource<Int, MainListItemModel>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, MainListItemModel?>
        ) {
            //开始加载数据
            loadData(1, callback, null)
        }

        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, MainListItemModel?>
        ) {
            //往前加载数据
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, MainListItemModel?>
        ) {
            //往后加载数据
            loadData(params.key + 1, null, callback)
        }
    }
}