package flow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test

class IntermediateOperatorsTest {

    @Test
    fun `test operator filter`() = runBlocking {
        val result = TestUtils.getIntFlow
            .filter { it % 2 == 0 }
            .toList()
        println("operator filter result = $result")
    }

    @Test
    fun `test operator filterNot`() = runBlocking {
        val result = TestUtils.getIntFlow
            .filterNot { it % 2 == 0 }
            .toList()
        println("operator filterNot result = $result")
    }


    @Test
    fun `test operator filterNotNull`() = runBlocking {
        val result = TestUtils.getIntFlow
            .filterNotNull()
            .toList()
        println("operator filterNotNull result = $result")
    }

    @Test
    fun `test operator filterIsInstance`() = runBlocking {
        val result = listOf<Any>(0, 1.2, "some", true)
            .filterIsInstance<String>()
            .toList()
        println("operator filterIsInstance result = $result")
    }

    @Test
    fun `test operator map`() = runBlocking {
        val result = TestUtils.getIntFlow
            .filter { it < 10 }
            .map { it * 10 }
            .toList()
        println("operator map result = $result")
    }

    @Test
    fun `test operator mapNotNull`() = runBlocking {
        val result = flowOf(null, 0, 1, 2)
            .mapNotNull { it?.times(10) }
            .toList()
        println("operator mapNotNull result = $result")
    }

    @Test
    fun `test operator withIndex`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .map { it * 100 }
            .withIndex()
            .toList()
        println("operator withIndex result = $result")
    }

    @Test
    fun `test operator onEach`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .onEach { print("$it, ") }
            .toList()
        println("operator onEach result = $result")
    }

    @Test
    fun `test operator runningReduce`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .runningReduce { accumulator, value ->
                accumulator + value
            }
            .toList()
        println("operator runningReduce result = $result")
    }

    @Test
    fun `test operator runningFold`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .runningFold(0) { accumulator, value ->
                accumulator + value
            }
            .toList()
        println("operator runningFold result = $result")
    }

    @Test
    fun `test operator scan`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .scan(0) { accumulator, value ->
                accumulator + value
            }
            .toList()
        println("operator scan result = $result")
    }

    @Test
    fun `test operator drop`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .drop(1)
            .toList()
        println("operator drop result = $result")
    }


    @Test
    fun `test operator dropWhile`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .dropWhile { it < 1 }
            .toList()
        println("operator dropWhile result = $result")
    }

    @Test
    fun `test operator take`() = runBlocking {
        val result = TestUtils.getIntFlow
            .take(10)
            .toList()
        println("operator take result = $result")
    }

    @Test
    fun `test operator takeWhile`() = runBlocking {
        val result = TestUtils.getIntFlow
            .takeWhile { it < 10 }
            .toList()
        println("operator takeWhile result = $result")
    }
}