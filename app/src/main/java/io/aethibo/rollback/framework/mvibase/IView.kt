package io.aethibo.rollback.framework.mvibase

interface IView<S : IState> {
    fun render(state: S)
}