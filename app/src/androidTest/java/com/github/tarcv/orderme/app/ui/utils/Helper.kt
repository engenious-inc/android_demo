import androidx.test.InstrumentationRegistry.getContext
import org.json.JSONObject

fun readJSONFromAsset(file: String): String? {
    var json: String?
    val inputStream = getContext().getAssets().open(file)
    json = inputStream.bufferedReader().use { it.readText() }
    return json
}

fun getJsonValue(value: String): String = JSONObject(readJSONFromAsset("cred.json"))
        .getString(value)
