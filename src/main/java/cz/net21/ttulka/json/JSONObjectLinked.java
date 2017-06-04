package cz.net21.ttulka.json;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONString;
import org.json.JSONTokener;

/**
 * Created by ttulka
 * <p>
 * Patched version using LinkedHashMap as a backed map. This allows the result to keep the order of JSON elements.
 */
public class JSONObjectLinked extends JSONObject {

    private final Map<String, Object> map;

    public JSONObjectLinked() {
        map = new LinkedHashMap<>();
    }

    public JSONObjectLinked(String source) throws JSONException {
        this(new JSONTokenerLinked(source));
    }

    public JSONObjectLinked(JSONTokener x) throws JSONException {
        this();
        if (x.nextClean() != 123) {
            throw x.syntaxError("A JSONObject text must begin with '{'");
        } else {
            while (true) {
                char c = x.nextClean();
                switch (c) {
                    case '\u0000':
                        throw x.syntaxError("A JSONObject text must end with '}'");
                    case '}':
                        return;
                    default:
                        x.back();
                        String key = x.nextValue().toString();
                        c = x.nextClean();
                        if (c != 58) {
                            throw x.syntaxError("Expected a ':' after a key");
                        }

                        this.putOnce(key, x.nextValue());
                        switch (x.nextClean()) {
                            case ',':
                            case ';':
                                if (x.nextClean() == 125) {
                                    return;
                                }

                                x.back();
                                break;
                            case '}':
                                return;
                            default:
                                throw x.syntaxError("Expected a ',' or '}'");
                        }
                }
            }
        }
    }

    @Override
    public boolean has(String key) {
        return this.map.containsKey(key);
    }

    @Override
    public Set<String> keySet() {
        return this.map.keySet();
    }

    @Override
    public int length() {
        return this.map.size();
    }

    @Override
    public Object opt(String key) {
        return key == null ? null : this.map.get(key);
    }

    @Override
    public JSONObject put(String key, Object value) throws JSONException {
        if (key == null) {
            throw new NullPointerException("Null key.");
        } else {
            if (value != null) {
                testValidity(value);
                this.map.put(key, value);
            } else {
                this.remove(key);
            }

            return this;
        }
    }

    @Override
    public JSONObject putOnce(String key, Object value) throws JSONException {
        if (key != null && value != null) {
            if (this.opt(key) != null) {
                throw new JSONException("Duplicate key \"" + key + "\"");
            }

            this.put(key, value);
        }

        return this;
    }

    @Override
    public Object remove(String key) {
        return this.map.remove(key);
    }

    @Override
    public Map<String, Object> toMap() {
        Map<String, Object> results = new LinkedHashMap();

        Map.Entry entry;
        Object value;
        for (Iterator var2 = this.map.entrySet().iterator(); var2.hasNext(); results.put(entry.getKey() + "", value)) {
            entry = (Map.Entry) var2.next();
            if (entry.getValue() != null && !NULL.equals(entry.getValue())) {
                if (entry.getValue() instanceof JSONObject) {
                    value = ((JSONObject) entry.getValue()).toMap();
                } else if (entry.getValue() instanceof JSONArray) {
                    value = ((JSONArray) entry.getValue()).toList();
                } else {
                    value = entry.getValue();
                }
            } else {
                value = null;
            }
        }

        return results;
    }

    @Override
    public Writer write(Writer writer, int indentFactor, int indent) throws JSONException {
        try {
            boolean commanate = false;
            int length = this.length();
            Iterator<String> keys = this.keys();
            writer.write(123);
            if (length == 1) {
                Object key = keys.next();
                writer.write(quote(key.toString()));
                writer.write(58);
                if (indentFactor > 0) {
                    writer.write(32);
                }

                writeValue(writer, this.map.get(key), indentFactor, indent);
            } else if (length != 0) {
                for (int newindent = indent + indentFactor; keys.hasNext(); commanate = true) {
                    Object key = keys.next();
                    if (commanate) {
                        writer.write(44);
                    }

                    if (indentFactor > 0) {
                        writer.write(10);
                    }

                    indent(writer, newindent);
                    writer.write(quote(key.toString()));
                    writer.write(58);
                    if (indentFactor > 0) {
                        writer.write(32);
                    }

                    writeValue(writer, this.map.get(key), indentFactor, newindent);
                }

                if (indentFactor > 0) {
                    writer.write(10);
                }

                indent(writer, indent);
            }

            writer.write(125);
            return writer;
        } catch (IOException var9) {
            throw new JSONException(var9);
        }
    }

    Writer writeValue(Writer writer, Object value, int indentFactor, int indent) throws JSONException, IOException {
        if (value != null && !value.equals((Object) null)) {
            if (value instanceof JSONObject) {
                ((JSONObject) value).write(writer, indentFactor, indent);
            } else if (value instanceof JSONArray) {
                ((JSONArray) value).write(writer, indentFactor, indent);
            } else if (value instanceof Map) {
                Map<?, ?> map = (Map) value;
                (new JSONObject(map)).write(writer, indentFactor, indent);
            } else if (value instanceof Collection) {
                Collection<?> coll = (Collection) value;
                (new JSONArray(coll)).write(writer, indentFactor, indent);
            } else if (value.getClass().isArray()) {
                (new JSONArray(value)).write(writer, indentFactor, indent);
            } else if (value instanceof Number) {
                writer.write(numberToString((Number) value));
            } else if (value instanceof Boolean) {
                writer.write(value.toString());
            } else if (value instanceof JSONString) {
                String o;
                try {
                    o = ((JSONString) value).toJSONString();
                } catch (Exception var6) {
                    throw new JSONException(var6);
                }

                writer.write(o != null ? o.toString() : quote(value.toString()));
            } else {
                quote(value.toString(), writer);
            }
        } else {
            writer.write("null");
        }

        return writer;
    }

    void indent(Writer writer, int indent) throws IOException {
        for (int i = 0; i < indent; ++i) {
            writer.write(32);
        }
    }

    @Override
    public String toString(int indentFactor) throws JSONException {
        StringWriter w = new StringWriter();
        synchronized (w.getBuffer()) {
            return this.write(w, indentFactor, 0).toString();
        }
    }

    @Override
    public String toString() {
        try {
            return this.toString(0);
        } catch (Exception var2) {
            return null;
        }
    }
}
