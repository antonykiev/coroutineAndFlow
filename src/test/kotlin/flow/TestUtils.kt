package flow

import kotlinx.coroutines.flow.flow

object TestUtils {


    val getIntList: List<Int> = List(100) { it }

    val getIntFlow = flow {
        repeat(100) {
            emit(it)
        }
    }

}