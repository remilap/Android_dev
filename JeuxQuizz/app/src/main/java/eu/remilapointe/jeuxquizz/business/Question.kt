package eu.remilapointe.jeuxquizz.business

data class Question(
    var mQuestion: String,
    var mOption1: String,
    var mOption2: String,
    var mOption3: String,
    var mRightAns: Int
) {
    override fun toString() : String {
        val sb = StringBuilder("Question[question=")
        sb.append(mQuestion)
        sb.append(", opt1=")
        sb.append(mOption1)
        sb.append(", opt2=")
        sb.append(mOption2)
        sb.append(", opt3=")
        sb.append(mOption3)
        sb.append(", right ans=")
        sb.append(mRightAns)
        sb.append("]")
        return sb.toString()
    }

}
