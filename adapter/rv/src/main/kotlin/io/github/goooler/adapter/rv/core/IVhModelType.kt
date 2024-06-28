package io.github.goooler.adapter.rv.core

import androidx.annotation.LayoutRes

public interface IVhModelType {
  /**
   * Get the viewType. You can treat layout ID as viewType.
   */
  @get:LayoutRes
  public val viewType: Int
}
