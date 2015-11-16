package gid.interfaces;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by gideonbar on 12/03/15.
 */
public interface ITableDao<A extends Object>
{
    public void closeDB();
    public void createTable();
    public void dropTable();
    public A persistObject(A obj);
    public ArrayList<A> getModelObjects(String[] ids);
    public int deleteObject(A obj);
    public boolean update(A obj);
}
