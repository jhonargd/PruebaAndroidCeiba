package co.com.ceiba.mobile.pruebadeingreso;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import co.com.ceiba.mobile.pruebadeingreso.businessObject.DataBaseBO;
import co.com.ceiba.mobile.pruebadeingreso.model.Post;
import co.com.ceiba.mobile.pruebadeingreso.model.User;
import co.com.ceiba.mobile.pruebadeingreso.util.Util;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private DataBaseBO dataBaseBO;
    private Util util;

    @Before
    public void setUp(){
        dataBaseBO = new DataBaseBO();
        util = new Util();
    }

    @Test
    public void createDataBaseTest() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        if(util.checkPermission(appContext)){
            assertEquals(true,dataBaseBO.createDataBase());
        }
    }

    @Test
    public void existDataBaseTest() throws Exception {
        if(dataBaseBO.createDataBase()){
            assertEquals(true,dataBaseBO.existDataBase());
        }
    }

    @Test
    public void saveUsersTest() throws Exception {
        ArrayList<User> users = new ArrayList<User>();
        User user = new User();
        user.setId("1");
        user.setName("Leanne Graham");
        user.setEmail("Sincere@april.biz");
        user.setPhone("1-770-736-8031 x56442");
        users.add(user);
        if(dataBaseBO.existDataBase()){
            assertEquals(true,dataBaseBO.saveUsers(users));
        }
    }

    @Test
    public void savePostTest() throws Exception {
        ArrayList<Post> posts = new ArrayList<Post>();
        Post post = new Post();
        post.setId("1");
        post.setUserId("1");
        post.setTitle("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        post.setBody("\"quia et suscipit\n" +
                "suscipit recusandae consequuntur expedita et cum\n" +
                "reprehenderit molestiae ut ut quas totam\n" +
                "nostrum rerum est autem sunt rem eveniet architecto\"");
        posts.add(post);
        if(dataBaseBO.existDataBase()){
            assertEquals(true,dataBaseBO.savePosts(posts));
        }
    }

    @Test
    public void getListUserDbTest() throws Exception {
        ArrayList<User> users = new ArrayList<User>();
        User user = new User();
        user.setId("1");
        user.setName("Leanne Graham");
        user.setEmail("Sincere@april.biz");
        user.setPhone("1-770-736-8031 x56442");
        users.add(user);
        if(dataBaseBO.existDataBase()){
            if(dataBaseBO.saveUsers(users)){
                assertEquals(user.getId(),dataBaseBO.getListUserDb("Leanne Graham").get(0).getId());
            }
        }
    }

    @Test
    public void getPostUserTest() throws Exception {
        ArrayList<Post> posts = new ArrayList<Post>();
        Post post = new Post();
        post.setId("1");
        post.setUserId("1");
        post.setTitle("sunt aut facere repellat provident occaecati excepturi optio reprehenderit");
        post.setBody("\"quia et suscipit\n" +
                "suscipit recusandae consequuntur expedita et cum\n" +
                "reprehenderit molestiae ut ut quas totam\n" +
                "nostrum rerum est autem sunt rem eveniet architecto\"");
        posts.add(post);
        if(dataBaseBO.existDataBase()){
            if(dataBaseBO.savePosts(posts)){
                assertEquals(post.getId(),dataBaseBO.getPostUser(post.getUserId()).get(0).getId());
            }
        }
    }

    @Test
    public void checkInternetTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals(true, util.checkInternet(appContext));
    }

    @Test
    public void useAppContextTest() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("co.com.ceiba.mobile.pruebadeingreso", appContext.getPackageName());
    }
}
