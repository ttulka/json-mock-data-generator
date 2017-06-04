package cz.net21.ttulka.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by ttulka
 * <p>
 * Patched version using JSONObjectLinked.
 */
public class JSONTokenerLinked extends JSONTokener {

    public JSONTokenerLinked(String s) {
        super(s);
    }

    @Override
    public Object nextValue() throws JSONException {
        char c = this.nextClean();
        switch (c) {
            case '"':
            case '\'':
                return this.nextString(c);
            case '[':
                this.back();
                return new JSONArray(this);
            case '{':
                this.back();
                return new JSONObjectLinked(this);
            default:
                StringBuilder sb;
                for (sb = new StringBuilder(); c >= 32 && ",:]}/\\\"[{;=#".indexOf(c) < 0; c = this.next()) {
                    sb.append(c);
                }

                this.back();
                String string = sb.toString().trim();
                if ("".equals(string)) {
                    throw this.syntaxError("Missing value");
                } else {
                    return JSONObject.stringToValue(string);
                }
        }
    }
}
