package io.github.goooler.adapter.rv.core

/**
 * Created on 2020/10/21.
 *
 * A class model(same viewType) corresponds to a delegate object.
 *
 * @author feling
 * @version 1.0.0
 * @since 1.0.0
 */
interface ViewTypeDelegate<M : IVhModelType> :
  IVhModelType,
  IRvBinding<M>
