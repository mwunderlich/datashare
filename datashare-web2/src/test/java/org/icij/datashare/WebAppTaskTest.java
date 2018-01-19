package org.icij.datashare;

import com.google.inject.AbstractModule;
import net.codestory.http.WebServer;
import net.codestory.http.misc.Env;
import net.codestory.rest.FluentRestTest;
import net.codestory.rest.RestAssert;
import org.icij.task.Options;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.Callable;

import static org.mockito.Mockito.*;

public class WebAppTaskTest implements FluentRestTest {
    private static WebServer server = new WebServer() {
        @Override
        protected Env createEnv() {
            return Env.prod();
        }
    }.startOnRandomPort();
    private static TaskFactory taskFactory = mock(TaskFactory.class);

    @Override
    public int port() {
        return server.port();
    }

    @BeforeClass
    public static void setUpClass() {
        server.configure(WebApp.getConfiguration(new TestModule()));
    }

    @Before
    public void setUp() { reset(taskFactory);}

    @Test
    public void testRoot() {
        get("/").should().contain("Datashare REST API");
    }

    @Test
    public void testIndexUnknownDirectory() throws Exception {
        RestAssert response = post("/task/index/file/" + URLEncoder.encode("foo|bar", "utf-8"), "{}");

        response.should().haveType("application/json").should().contain("{\"result\":\"Error\"}");
    }

    @Test
    public void testIndexFile() {
        RestAssert response = post("/task/index/file/" + getClass().getResource("/docs/doc.txt").getPath().replace("/", "%7C"), "{}");

        response.should().haveType("application/json").should().contain("{\"result\":\"Error\"}");
    }

    @Test
    public void testIndexDirectory() {
        RestAssert response = post("/task/index/file/" + getClass().getResource("/docs/").getPath().replace("/", "%7C"), "{}");

        response.should().haveType("application/json").should().contain("{\"result\":\"OK\",\"taskId\":12}");
    }

    @Test
    public void testIndexDirectoryWithOptions() throws Exception {
        String path = getClass().getResource("/docs/").getPath();

        RestAssert response = post("/task/index/file/" + path.replace("/", "%7C"),
                "{\"options\":{\"key1\":\"val1\",\"key2\":\"val2\"}}");

        response.should().haveType("application/json").should().contain("{\"result\":\"OK\",\"taskId\":12}");
        verify(taskFactory).createIndexTask(path.replace("/", "|"), Options.from(new HashMap<String, String>() {{
            put("key1", "val1");
            put("key2", "val2");
        }}));
    }

    static class TestModule extends AbstractModule {
        @Override protected void configure() {
            bind(TaskFactory.class).toInstance(taskFactory);
            bind(TaskManager.class).to(DummyTaskManager.class);
        }
    }

    static class DummyTaskManager extends TaskManager {
        @Override public <V> int startTask(Callable<V> task) {
            return 12;
        }
    }
}