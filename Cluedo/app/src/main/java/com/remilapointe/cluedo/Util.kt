package com.remilapointe.cluedo

import android.util.Log
import com.remilapointe.cluedo.game.TAG

class Util {
    companion object {
        fun getMethodName(): String {
            return Thread.currentThread().stackTrace[2].methodName
        }

        fun getClassName(): String {
            return Thread.currentThread().stackTrace[2].className
        }

        fun getClassAndMethodName(): String {
/*
for ((n, s) in Thread.currentThread().stackTrace.withIndex()) {
Log.i(TAG, "n=$n, ${s.className}.${s.methodName}.${s.lineNumber}")
}
return "test"
*/
            var s = Thread.currentThread().stackTrace[4]
            return "${s.className}.${s.methodName}.${s.lineNumber}: "
        }

        fun log(msg: String) {
            Log.i(TAG, getClassAndMethodName() + msg)
        }
    }

}
