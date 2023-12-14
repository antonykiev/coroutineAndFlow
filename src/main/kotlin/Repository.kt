import kotlinx.coroutines.delay

class Repository {

    suspend fun fetchData(): String {
        delay(1000)
        return "Data loaded"
    }

}