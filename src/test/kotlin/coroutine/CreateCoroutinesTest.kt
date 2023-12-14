package coroutine

import Repository
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

class CreateCoroutinesTest {

    private val repository = Repository()

    /**
     * runBlocking is a function that is primarily used in regular code to start a new coroutine and block the current
     * thread until its execution completes. It is often used in main functions or in blocking code where you need
     * to call suspending functions within a non-suspending context.
     */
    @Test
    fun `create runBlocking`() = runBlocking {
        val result = repository.fetchData()
        assertTrue { result.isNotEmpty() }
        println("`create runBlocking` passed")
    }

    /**
     * runTest is specific to Kotlin's testing framework, typically used in conjunction with libraries like Kotlin Test
     * or other testing frameworks. It's primarily used to run suspending code in test cases and manage coroutines
     * within testing contexts. This function sets up the testing environment to handle coroutines appropriately,
     * especially when dealing with asynchronous code in tests.
     */
    @Test
    fun `create runTest`() = runTest {
        val result = repository.fetchData()
        assertTrue { result.isNotEmpty() }
        println("`create runTest` passed")
    }

    @Test
    fun `create launch`() = runTest {
        launch {
            val result = repository.fetchData()
            assertTrue { result.isNotEmpty() }
            println("`create launch` passed")
        }
    }

    @Test
    fun `create async`() = runTest {
        val result = async {
            repository.fetchData()
        }.await()
        assertTrue { result.isNotEmpty() }
        println("`create async` passed")
    }

}