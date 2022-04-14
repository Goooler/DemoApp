package io.goooler.demoapp.adapter.rv.core;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public interface BindingAdapters {

  @BindingAdapter("binding_rv_dataList")
  static <M extends IVhModelType> void bindingSetList(
    @NonNull RecyclerView recyclerView,
    @Nullable List<M> list
  ) {
    if (recyclerView.getAdapter() instanceof IMutableRvAdapter) {
      if (list == null) {
        list = Collections.emptyList();
      }
      //noinspection unchecked
      ((IMutableRvAdapter<M>) recyclerView.getAdapter()).setList(list);
    }
  }

  @BindingAdapter("binding_rv_refreshItems")
  static <M extends IVhModelType> void bindingRefreshItems(
    @NonNull RecyclerView recyclerView,
    @Nullable List<M> list
  ) {
    if (recyclerView.getAdapter() instanceof IMutableRvAdapter) {
      if (list == null) {
        list = Collections.emptyList();
      }
      //noinspection unchecked
      ((IMutableRvAdapter<M>) recyclerView.getAdapter()).refreshItems(list);
    }
  }
}
