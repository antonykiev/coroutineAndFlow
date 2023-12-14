package coroutine

import kotlinx.coroutines.*
import org.junit.Test
import kotlin.test.assertTrue

class CoroutineScopeTest {

    @Test
    fun `create coroutine scope`() = runBlocking {
        val value = coroutineScope {
            delay(100)
            "result"
        }
        assertTrue { value == "result" }
    }

    @Test
    fun `create supervisor scope`() = runBlocking {
        val value = supervisorScope {
            delay(100)
            "result"
        }
        assertTrue { value == "result" }
    }

    @Test
    fun `create GlobalScope scope`() = runBlocking {
        val value = GlobalScope.async {
            delay(100)
            "result"
        }.await()
        assertTrue { value == "result" }
    }
}