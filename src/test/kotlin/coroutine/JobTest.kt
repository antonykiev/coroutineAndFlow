package coroutine

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class JobTest {

    private val CoroutineScope.job_: Job
        get() {
            return launch(CoroutineName("coroutine0") + Dispatchers.Default) {
                println("job $this started ...")
                delay(1000)
                println("job finished ...")
            }
        }

    @Test
    fun `create job`() = runBlocking {
        val job = job_
        delay(100)
        assertTrue { job.isActive }
    }

    /**
     *     The cancel() function is used to cancel the given coroutine without waiting for its completion.
     *     When you call cancel() on a coroutine, it triggers cancellation of that coroutine immediately without blocking the caller.
     *     Any code inside the coroutine will stop executing, and the coroutine will transition to a cancelled state.
     *     It does not wait for the coroutine to finish its execution.
     */
    @Test
    fun `cancel job`() = runBlocking {
        val job = job_
        delay(100)
        job.cancel()
        assertTrue { job.isCancelled }
        assertFalse { job.isCompleted }
    }

    @Test
    fun `cancel and join job`() = runBlocking {
        val job = job_
        job.cancelAndJoin()
        assertTrue { job.isCancelled }
        assertTrue { job.isCompleted }
    }

    @Test
    fun `invokeOnCompletion job`() = runBlocking {
        val job = launch {
            try {
                delay(3000)
                println("Coroutine completed.")
            } catch (e: CancellationException) {
                println("Coroutine cancelled with exception: $e")
            }
        }

        job.invokeOnCompletion { throwable ->
            if (throwable != null) {
                println("Coroutine completed exceptionally: $throwable")
            } else {
                println("Coroutine completed successfully.")
            }
        }
        job.cancelAndJoin()

        assertTrue { job.isCompleted }

        println("Main coroutine completed.")
    }

    @Test
    fun `join jobs`() = runBlocking {

        val job0 = job_
        val job1 = job_

        job0.join()
        job1.join()

        assertTrue { job0.isCompleted }
        assertTrue { job1.isCompleted }

        println("job0 and job1 is completed")
    }

    @Test
    fun `parent job`() = runBlocking {

        val parent = job_
        val child = Job(parent)

        parent.cancelAndJoin()

        assertTrue { child.isCompleted }

        println("child is completed")
    }

}