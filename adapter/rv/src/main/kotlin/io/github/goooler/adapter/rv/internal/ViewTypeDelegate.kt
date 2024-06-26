package io.github.goooler.adapter.rv.internal

import io.github.goooler.adapter.rv.core.IRvBinding
import io.github.goooler.adapter.rv.core.IVhModelType

/**
 * A class model(same viewType) corresponds to a delegate object.
 */
internal interface ViewTypeDelegate<M : IVhModelType> :
  IVhModelType,
  IRvBinding<M>
