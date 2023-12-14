package flow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowTerminalOperatorsTest {

    @Test
    fun `test operator toList`() = runBlocking {
        val result = TestUtils.getIntFlow.toList()
        println("operator toList result = $result")
    }

    @Test
    fun `test operator toSet`() = runBlocking {
        val result = TestUtils.getIntFlow.toSet()
        println("operator toSet result = $result")
    }

    @Test
    fun `test operator count`() = runBlocking {
        val result = TestUtils.getIntFlow.count()
        println("operator count result = $result")
    }

    @Test
    fun `test operator first`() = runBlocking {
        val result = TestUtils.getIntFlow.first()
        println("operator first result = $result")
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test operator single`() = runBlocking {
        val result = TestUtils.getIntFlow.single()
        println("operator single result = $result")
    }

    @Test
    fun `test operator singleOrNull`() = runBlocking {
        val result = TestUtils.getIntFlow.singleOrNull()
        println("operator singleOrNull result = $result")
    }

    @Test
    fun `test operator reduce`() = runBlocking {
        val result = TestUtils.getIntFlow.reduce { it0, it1 ->
            it0 + it1
        }
        println("operator reduce result = $result")
    }

    @Test
    fun `test operator fold`() = runBlocking {
        val result = TestUtils.getIntFlow.fold(0) { accumulator, value ->
            println("Accumulator: $accumulator, Value: $value")
            accumulator + value
        }
        println("operator fold result = $result")
    }
}