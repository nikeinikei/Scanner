import org.junit.Assert;

import java.util.List;

public class AssertUtil {
    public static <T> void assertListSameClass(List<T> list, List<Class<? extends T>> classList) {
        Assert.assertEquals(list.size(), classList.size());
        for (int i = 0; i< list.size(); i++) {
            var item = list.get(i);
            var klass = classList.get(i);
            Assert.assertEquals("wrong class", item.getClass().getName(), klass.getName());
        }
    }
}
