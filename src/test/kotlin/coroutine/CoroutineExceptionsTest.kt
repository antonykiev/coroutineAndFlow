package coroutine

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertTrue

class CoroutineExceptionsTest {

    @Test
    fun `error launch`() = runBlocking {
        val job = launch {
            try {
                throw Exception()
            } catch (e: Exception) {
                println("exception $e")
            }
        }
        delay(100)
        assertTrue { job.isCompleted }
    }

    @Test
    fun `error async`() = runBlocking {
        val scope = CoroutineScope(SupervisorJob())
        val deffered = scope.async {
            throw Exception("Exception in async")
        }
        try {
            deffered.await()
        } catch (e: Exception) {
            println("exception $e")
        }
        assertTrue { deffered.isCompleted }
    }

    @Test
    fun `error launch supervisorScope`() = runBlocking {
        supervisorScope {
            val job0 = launch {
                delay(100)
                throw Exception()
                println("unreached code")
            }

            val job1 = launch {
                delay(100)
                println("job1 finished")
            }
            val job2 = launch {
                delay(200)
                println("job2 finished")
            }

            delay(1000)
        }
    }

    @Test
    fun `error async supervisorScope`() = runBlocking {
        supervisorScope {
            val result0 = try {
                async {
                    delay(100)
                    throw Exception()
                    println("unreached code")
                }.await()
            } catch (e: Exception) {
                println("Exception ${e.message}")
            }

            val result1 = async {
                delay(100)
                println("job1 finished")
                "result1"
            }.await()
            val result2 = async {
                delay(200)
                println("job2 finished")
                "result2"
            }.await()

            println("result0 = $result0")
            println("result1 = $result1")
            println("result2 = $result2")

            delay(1000)
        }
    }

    @Test
    fun `cancellation Exception`() = runBlocking {
        val job0 = launch {
            launch {
                delay(200)
                println("Will not be printed")
            }
            throw CancellationException()
        }
        val job1 = launch {
            delay(200)
            println("Will be printed")
        }

        job0.join()
        job1.join()
    }

    @Test
    fun `test CoroutineExceptionHandler`() = runBlocking {
        val handler = CoroutineExceptionHandler { ctx, exception ->
            println("Caught $exception")
        }

        val scope = CoroutineScope(SupervisorJob() + handler)

        val job0 = scope.launch {
            delay(1000)
            throw Error("Some error")
        }

        val job1 = scope.launch {
            delay(2000)
            println("Will be printed")
        }

        job0.join()
        job1.join()
    }

    @Test(expected = Exception::class)
    fun `test job child error`() = runBlocking {
        val parentJob = launch {
            println("parent launched")
        }
        val child = launch(parentJob) {
            delay(500)
            throw Exception("error in child")
        }
        parentJob.join()
    }

    @Test
    fun `test supervisor job child error`() = runBlocking {
        supervisorScope {
            val parentJob = launch() {
                println("parent launched")
            }
            val child = launch(parentJob) {
                delay(500)
                throw Exception("error in child")
            }
            parentJob.join()
        }
    }

}