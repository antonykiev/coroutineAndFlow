package flow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class CombiningOperatorTest {

    @Test
    fun `test combine`() = runBlocking {
        val flow0 = TestUtils.getIntFlow
            .take(10)

        val flow1 = TestUtils.getIntFlow
            .drop(10)
            .take(10)

        combine(flow0, flow1) { it0, it1 ->
            it0 + it1
        }.collect {
            println("operator combine collect = $it")
        }
    }

    @Test
    fun `test merge`() = runBlocking {
        val flow0 = TestUtils.getIntFlow
            .take(10)

        val flow1 = TestUtils.getIntFlow
            .drop(10)
            .take(10)

        merge(flow0, flow1)
            .collect {
                println("operator combine collect = $it")
            }
    }

    @Test
    fun `test zip`() = runBlocking {
        val flow0 = TestUtils.getIntFlow
            .take(10)

        val flow1 = TestUtils.getIntFlow
            .drop(10)
            .take(12)

        flow0.zip(flow1) { value1, value2 ->
            value1 to value2
        }.collect {
            println("operator combine collect = $it")
        }
    }

}