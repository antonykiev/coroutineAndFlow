package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.io.IOException

class FlowCancellationsAndExceptionsTest {

    @Test
    fun `test function cancellable`() = runBlocking {
        // is cancellable by default
        val flow0 = flow {
            repeat(10) {
                delay(50)
                emit(0)
            }
        }
        // is not cancellable
        val flow1 = flowOf(listOf(0, 1, 2, 3))
        // is cancellable by default
        val flow2 = flowOf(listOf(0, 1, 2, 3)).cancellable()
    }

    @Test
    fun `test operator retry`() = runBlocking {
        var counter = 0
        val flow = flow {
            repeat(5) { // Emit elements 1 to 5
                delay(100)
                emit(it + 1)
            }
            counter++
            if (counter < 4) throw IOException("Something went wrong")
        }

        flow
            .retry(4) { cause ->
                println("failed: $cause")
                delay(200) // Delay before retrying
                true // Retry for any exception
            }
            .collect {
                println(it)
            }
    }

    @Test
    fun `test operator retryWhen`() = runBlocking {
        var counter = 0
        val flow = flow {
            repeat(5) { // Emit elements 1 to 5
                delay(100)
                emit(it + 1)
            }
            counter++
            if (counter < 4) throw IOException("Something went wrong")
        }

        flow
            .retryWhen { cause, attempt ->
                if (cause !is IOException) return@retryWhen false

                println("attempt $attempt failed: $cause")
                delay(200)
                true
            }
            .collect {
                println(it)
            }
    }

    @Test
    fun `test operator catch`() = runBlocking {
        val flow = flow<Int> {
            throw IOException("Something went wrong")
        }

        flow
            .catch { cause ->
                println("failed: $cause")
            }
            .collect {
                println(it)
            }
    }

}