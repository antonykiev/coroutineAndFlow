package flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class BufferingOperatorsTest {

    /**
     * The buffer operator in Kotlin's Flow is used to emit items from the flow in a buffered manner,
     * allowing concurrent processing of elements.
     */
    @Test
    fun `test buffer`() = runBlocking {
        TestUtils.getIntFlow
            .take(10)
            .buffer()
            .collect {
                delay(100)
                println("operator buffer collect = $it")
            }
    }

    @Test
    fun `test conflate`() = runBlocking {
        TestUtils.getIntFlow
            .take(10)
            .conflate()
            .collect {
                delay(100)
                println("operator conflate collect = $it")
            }
    }

    @Test
    fun `test collectLatest`() = runBlocking {
        TestUtils.getIntFlow
            .take(10)
            .collectLatest {
                delay(100)
                println("operator conflate collect = $it")
            }
    }
}