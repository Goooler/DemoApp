package io.github.goooler.adapter.rv.internal

import io.github.goooler.adapter.rv.core.IRvBinding
import io.github.goooler.adapter.rv.core.IVhModelType

/**
 * Created on 2020/10/21.
 *
 * A class model(same viewType) corresponds to a delegate object.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
internal interface ViewTypeDelegate<M : IVhModelType> :
  IVhModelType,
  IRvBinding<M>
