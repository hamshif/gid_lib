package gid.interfaces;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
/**
 * Created by gideon on 26/05/15.
 */
public interface IJsonTranslator <A>
{
    public A decode(String json_string) throws Exception, JsonParseException;
    public A decode(JsonObject jsonObject) throws Exception, NullPointerException;
    public String encode(A a);
    public boolean update(A a, String json_string);
    public boolean update(A a, JsonObject jsonObject);
}
