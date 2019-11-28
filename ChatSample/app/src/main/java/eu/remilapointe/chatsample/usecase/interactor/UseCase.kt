package com.remilapointe.laser.usecase.interactor

import androidx.annotation.Nullable
import com.remilapointe.laser.usecase.executor.PostExecutionThread
import com.remilapointe.laser.usecase.executor.ThreadExecutor
import io.reactivex.Scheduler

/**
 * Each {@code UseCase} of the system orchestrate the flow of data to and from the entities.
 * <p>
 * Outer layers of system can execute use cases by calling {@link #execute(Object)}} method. Also
 * you can use {@link #useCaseExecutor} to execute the job in a background thread and {@link
 * #postExecutionThread} to post the result to another thread(usually UI thread).
 *
 * @param <P> The response type of a use case.
 * @param <Q> The request type of a use case.
 * @author Saeed Masoumi (saeed@6thsolution.com)
 */
abstract class UseCase<P, Q>(private val useCaseExecutor : ThreadExecutor, private val postExecutionThread : PostExecutionThread) {

    /**
     * Executes use case. It should call {@link #interact(Object)} to get response value.
     */
    abstract fun execute(@Nullable param: Q) : P

    /**
     * A hook for interacting with the given parameter(request value) and returning a response value for
     * each concrete implementation.
     * <p>
     * It should be called inside {@link #execute(Object)}.
     *
     * @param param The request value.
     * @return Returns the response value.
     */
    protected abstract fun interact(@Nullable param: Q) : P

    fun getUseCaseExecutor() : Scheduler {
        return useCaseExecutor.scheduler
    }

    fun getPostExecutionThread() : Scheduler {
        return postExecutionThread.scheduler
    }

}
