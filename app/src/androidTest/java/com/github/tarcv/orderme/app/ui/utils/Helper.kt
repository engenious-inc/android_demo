import androidx.test.InstrumentationRegistry.getContext
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar

fun readJSONFromAsset(file: String): String? {
    var json: String? = null
    try {
        val inputStream = getContext().getAssets().open(file)
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun getJsonValue(value: String): String = JSONObject(readJSONFromAsset("cred.json"))
        .getString(value)

fun getOrderDate() = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
fun getOrderTime() = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)

fun getDatePlus(numberOfDaysAhead: Int): String {
    val calender = Calendar.getInstance()
    calender.add(Calendar.DAY_OF_YEAR, +numberOfDaysAhead)
    return SimpleDateFormat("yyyy-MM-dd").format(calender.time)
}
