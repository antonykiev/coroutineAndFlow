package coroutine

import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.util.concurrent.Executors
import kotlin.random.Random
import kotlin.system.measureTimeMillis

class CoroutineDispatchersTest {

    private val dispatcher = Executors
        .newSingleThreadExecutor()
        .asCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        // reset main dispatcher to
        // the original Main dispatcher
        Dispatchers.resetMain()
        dispatcher.close()
    }

    @Test
    fun `default dispatcher`() = runBlocking {
        repeat(1000) {
            launch(Dispatchers.Default) {
                List(1000) { Random.nextLong() }.maxOrNull()

                val threadName = Thread.currentThread().name
                println("Running on thread: $threadName")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `default dispatcher with limitedParallelism`() = runBlocking {
        val backgroundDispatcher = newFixedThreadPoolContext(4, "App Background")
        val limitedProcessingDispatcher = backgroundDispatcher.limitedParallelism(2)

        repeat(1000) {
            launch(limitedProcessingDispatcher) {
                List(1000) { Random.nextLong() }.maxOrNull()

                val threadName = Thread.currentThread().name
                println("Running on thread: $threadName")
            }
        }
    }

    @Test
    fun `main dispatcher`() = runBlocking {
        repeat(1000) {
            launch(Dispatchers.Main) {
                List(1000) { Random.nextLong() }.maxOrNull()
                val threadName = Thread.currentThread().name
                println("Running on thread: $threadName")
            }
        }
    }

    @Test
    fun `IO dispatcher`() = runBlocking {
        val time = measureTimeMillis {
            coroutineScope {
                repeat(50) {
                    launch(Dispatchers.IO) {
                        Thread.sleep(1000)
                    }
                }
            }
        }
        println(time)
    }

}