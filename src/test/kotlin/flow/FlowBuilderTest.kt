package flow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test

class FlowBuilderTest {

    @Test
    fun `test flow`() = runBlocking {
        TestUtils.getIntFlow
            .collect {
                println("collected $it")
            }
    }

    @Test
    fun `test flowOf`() = runBlocking {
        flowOf(TestUtils.getIntList)
            .collect {
                println("collected $it")
            }
    }

    @Test
    fun `test asFlow`() = runBlocking {
        TestUtils.getIntList.asFlow()
            .collect {
                println("collected $it")
            }
    }

}